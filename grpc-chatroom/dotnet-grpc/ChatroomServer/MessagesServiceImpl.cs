using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Google.Protobuf.Collections;
using Grpc.Core;

using KaiStuff;

namespace ChatroomServer
{

    public class MessagesServiceImpl: MessagesService.MessagesServiceBase
    {

        // --- START gRPC methods ---

        public override Task<SendMessageResponse> SendMessage(Message msg, ServerCallContext context)
        {
            Utils.LogOnce(nameof(MessagesServiceImpl), $"{context.Method} called by {context.Peer}");

            // First, map Message to CustomMessage
            CustomMessage customMsg = AsCustomMessage(msg);

            return Task.FromResult(new SendMessageResponse
            {
                Success = SendMessage(customMsg)
            });
        }

        public override Task<GetMessagesResponse> GetMessages(EmptyRequest request, ServerCallContext context)
        {
            // Like Java's `X.class.getName()` -> CSharp' `typeof(X).Name` -> `nameof(X)` (as the Visual Studio IDE suggested)
            Utils.LogOnce(nameof(MessagesServiceImpl), $"{context.Method} called by {context.Peer}");

            // Map List<CustomMessage> (or CustomMessage[]) to IEnumerable<Message>
            IEnumerable<Message> enumerableMessages = GetMessages().Select(AsContractMessage);
            
            // Fill the RepeatedField<Message> object with the INumerable<>.
            RepeatedField<Message> repeatedMessages = new RepeatedField<Message>();
            repeatedMessages.AddRange(enumerableMessages);
            
            return Task.FromResult(new GetMessagesResponse
                {
                    Messages = { repeatedMessages }
                }
            );
        }

        // TODO: Refactor. `GetMessagesAfter` and `GetMessages` are almost the same.
        public override Task<GetMessagesResponse> GetMessagesAfter(Message targetMessage, ServerCallContext context)
        {
            Utils.LogOnce(nameof(MessagesServiceImpl), $"{context.Method} called by {context.Peer}");

            IEnumerable<Message> enumerableMessages =
                GetMessagesAfter(new CustomMessage { Id = targetMessage.Id })
                .Select(AsContractMessage);

            RepeatedField<Message> repeatedMessages = new RepeatedField<Message>();
            repeatedMessages.AddRange(enumerableMessages);

            return Task.FromResult(new GetMessagesResponse
                {
                    Messages = { repeatedMessages }
                });
        }

        public override Task GetNewMessages(EmptyRequest request, IServerStreamWriter<Message> responseStream, ServerCallContext context)
        {
            // We are storing the name/URI of the peer now because we won't be able to retrieve it later after the connection ends.
            // Trying to do it would throw `ObjectDisposedException`, as I learned after a long session of debugging:
            //      "[2021-02-28T16:06:37.7600669+01:00][GetNewMessages.delegatedCancelHandler] ERROR:
            //       System.ObjectDisposedException: Safe handle has been closed."
            var peer = context.Peer;

            var observer = responseStream;

            void delegatedCancelHandler()
            {
                // try {
                _observers.Remove(observer);
                // Utils.Log(nameof(MessagesServiceImpl), $"<SERVER> Peer left: {context.Peer}"); // <- This throws an exception.
                Utils.Log(nameof(MessagesServiceImpl), $"<SERVER> Peer left: {peer}");
                // } catch (Exception e) { Utils.Log("GetNewMessages.delegatedCancelHandler", $"ERROR: {e}"); }
            }
            context.CancellationToken.Register(delegatedCancelHandler);

            _observers.Add(observer);
            Utils.Log(nameof(MessagesServiceImpl), $"<SERVER> Peer joined: {peer}");

            // Delay the completion of this task (effectively the closing of the response stream)
            // indefinitely (until the connection is cancelled).
            return Task.Delay(-1, context.CancellationToken);
        }

        /**
         * Notify connected peers about the new message.
         * 
         * "Writes a message asynchronously. Only one write can be pending at a time." -- `IAsyncStreamWriter::WriteAsync`
         * It basically says that, for each stream/peer, we should wait for the first `Write` operation to complete
         * before writing the next message. That's why we're synchronizing on each "observer" object.
         * 
         * See:
         * - <a href="https://stackoverflow.com/a/541348">"C# version of java's synchronized keyword?" - Stack Overflow</a>
         * - <a href="https://docs.microsoft.com/en-us/dotnet/csharp/language-reference/keywords/lock-statement">`lock` - MSDocs</a>
         * - <a href="https://github.com/grpc/grpc/issues/7659">"Multi ClientAsyncWriter::Write() calls will crash." - GitHub</a>
         */
        private async Task OnNewMessage(CustomMessage msg)
        {
            try
            {
                Utils.Log("OnNewMessage", $"Going to notify {_observers.Count} peers...");
                await Task.WhenAll(
                    _observers.AsParallel().Select(observer => {
                        lock (observer)
                        {
                            return observer
                                    .WriteAsync(AsContractMessage(msg))
                                    /*
                                    // Debugging
                                    .ContinueWith(t => {
                                        Utils.Log("OnNewMessage.ContinueWith", $"CompletedSuccessfully? {t.IsCompletedSuccessfully}");
                                        return t.IsCompletedSuccessfully;
                                    })
                                    */
                                    ;
                        }
                    })
                );
            }
            catch (Exception ex)
            {
                Utils.Log("OnNewMessage", ex.ToString());
            }
        }

        // "Pseudo observers" for implementing a pubsub kinda thing.
        // SEE https://grpc.io/docs/languages/csharp/basics/#server-side-streaming-rpc
        private HashSet<IServerStreamWriter<Message>> _observers = new HashSet<IServerStreamWriter<Message>>();

        // --- END gRPC methods ---

        private AccountsServiceImpl accountsService;
        
        // The assignment text says to store messages in a "<i lang="fr">tableau</i>".
        // But what does it mean? An "array" as always? Or maybe CSharp's DataTable?
        //      (DataTable Class (System.Data) "represents one table of in-memory data."
        //       -- https://docs.microsoft.com/en-us/dotnet/api/system.data.datatable?view=net-5.0)
        //
        // Using an "array" is totally unacceptable,
        //   while using a DataTable feels a little too overkill for this assignment,
        //   that's why we settled for an ArrayList/List, which is basically an optimized, array-backed collection. 
        private List<CustomMessage> messagesList = new List<CustomMessage>();
        // Uncomment the following line to use the "array version"...
        // private CustomMessage[] messagesArray = new CustomMessage[]{ };

        public MessagesServiceImpl(AccountsServiceImpl accountsService)
        {
            this.accountsService = accountsService;
        }

        // ---
        
        public CustomMessage[] GetMessages()
        {
            return messagesList.ToArray();
            // If we were using an array:
            // return messagesArray;
        }

        public CustomMessage[] GetMessagesAfter(CustomMessage targetMessage)
        {
            var countTotal = messagesList.Count;
            var messageIndex = messagesList.IndexOf(targetMessage);
            // Return an empty array if the target message was not found for whatever reason or if it is the last message in the list.
            if (messageIndex < 0 || messageIndex == countTotal - 1)
            {
                return Array.Empty<CustomMessage>();
            }

            var startFrom = messageIndex + 1;
            var countAfter = countTotal - startFrom;
            Utils.LogOnce(
                nameof(MessagesServiceImpl),
                $"ReceiveMessagesAfter: messageIndex={messageIndex}, startFrom={startFrom} countAfter={countAfter}, countTotal={countTotal}"
            );
            var subList = messagesList.GetRange(startFrom, countAfter);
            return subList.ToArray();
        }

        public bool SendMessage(CustomMessage msg)
        {

            if (msg == null)
            {
                Utils.Log(nameof(MessagesServiceImpl), $"Rejected null message: {msg}");
                return false;
            }

            if (String.IsNullOrWhiteSpace(msg.Content))
            {
                Utils.Log(nameof(MessagesServiceImpl), $"Rejected message with no content: {msg}");
                return false;
            }

            var account = accountsService.FindAccountById(msg.Author);
            if (account == null)
            {
                Utils.Log(nameof(MessagesServiceImpl), $"Rejected message by unconnected user: {msg}");
                return false;
            }
            else
            {
                // Replace the Author's (secret?) ID with their public nickname.
                msg.Author = account.Nickname;
            }

            // The timestamp is determined by the server (currently we are ignoring the timestamp sent by the client).
            msg.Timestamp = DateTimeOffset.UtcNow.ToString("O");

            // Add an ID to "freeze" the message.
            msg.Id = System.Guid.NewGuid().ToString();

            // Now save the message.
            SaveMessage(msg);
            Utils.Log(nameof(MessagesServiceImpl), $"Added message: {msg}");

            return true;
        }

        private void SaveMessage(CustomMessage msg)
        {
            messagesList.Add(msg);
            // If we were using an array:
            // messagesArray = messagesArray.Append(msg).ToArray();

            OnNewMessage(msg);
        }

        // ---

        private static Message AsContractMessage(CustomMessage customMessage)
        {
            return new Message
            {
                Author = customMessage.Author,
                Content = customMessage.Content,
                Timestamp = customMessage.Timestamp,
                Id = customMessage.Id,
            };
        }

        private static CustomMessage AsCustomMessage(Message contractMessage)
        {
            return new CustomMessage
            {
                Author = contractMessage.Author,
                Content = contractMessage.Content,
                Timestamp = contractMessage.Timestamp,
                Id = contractMessage.Id,
            };
        }

    }

}
