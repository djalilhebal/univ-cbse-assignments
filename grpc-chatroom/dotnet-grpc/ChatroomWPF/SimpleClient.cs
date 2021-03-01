using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Threading.Tasks.Dataflow;
using Grpc.Core;

namespace ChatroomWPF
{

    /**
     * A thin wrapper around our gRPC clients.
     */
    public class SimpleClient
    {
        const string SERVER_ADDRESS = "localhost"; // "192.168.43.127";

        const int ACCOUNTS_PORT = 103_01;
        private Channel accountsChannel;
        private AccountsService.AccountsServiceClient accountsClient;

        const int MESSAGES_PORT = 103_02;
        private Channel messagesChannel;
        private MessagesService.MessagesServiceClient messagesClient;

        public void Setup()
        {
            accountsChannel = new Channel($"{SERVER_ADDRESS}:{ACCOUNTS_PORT}", ChannelCredentials.Insecure);
            accountsClient = new AccountsService.AccountsServiceClient(accountsChannel);

            messagesChannel = new Channel($"{SERVER_ADDRESS}:{MESSAGES_PORT}", ChannelCredentials.Insecure);
            messagesClient = new MessagesService.MessagesServiceClient(messagesChannel);
        }

        public void Shutdown()
        {
            messagesChannel?.ShutdownAsync().Wait();
            accountsChannel?.ShutdownAsync().Wait();
        }

        // ---

        public Account GetAccount(string nickname, string password)
        {
            Console.WriteLine($"[ClientMain] Attempting to login as {nickname}");
            Account currentAccount = accountsClient.GetAccount(new AccountRequest { Nickname = nickname, Password = password });

            if (currentAccount == null || string.IsNullOrWhiteSpace(currentAccount.Id))
            {
                Console.WriteLine("[ClientMain] ERROR: Could not retrieve account obj. (Maybe the nick:pass don't match)");
                return currentAccount;
            }
            else
            {
                Console.WriteLine($"[ClientMain] The current user is <{currentAccount.Nickname}>");
                return currentAccount;
            }
        }

        /**
         * Get all messages.
         * 
         * NOTE: We don't care if it's a List, a RepeatedFiled, or whatever as long as it's "enumerable".
         */
        public IEnumerable<Message> GetMessages()
        {
            return messagesClient.GetMessages(new EmptyRequest()).Messages;
        }

        public IEnumerable<Message> GetMessagesAfter(Message latestMessage)
        {
            return messagesClient.GetMessagesAfter(latestMessage).Messages;
        }

        /**
         * Listen for new messages.
         * 
         * - NOTE: We're using `BufferBlock` as a simple observable, seems to be an acceptable practice.
         * - TODO: Use `bufferBlock.AsObservable()` or properly implement the `IObservable<T>` interface.
         */
        public BufferBlock<Message> GetNewMessages(CancellationToken cancelationToken = new CancellationToken())
        {
            var bb = new BufferBlock<Message>();

            var call = messagesClient.GetNewMessages(new EmptyRequest(), cancellationToken: cancelationToken);
            var responseStream = call.ResponseStream;

            Task.Run(async () =>
            {
                while (!cancelationToken.IsCancellationRequested && await responseStream.MoveNext())
                {
                    bb.Post(responseStream.Current);
                }
            }, cancelationToken);

            return bb;
        }

        public bool SendMessage(string authorId, string content)
        {
            Message msg = new Message
            {
                Author = authorId,
                Content = content,
            };
            Console.WriteLine("[ClientMain] Sending ", msg);
            SendMessageResponse response = messagesClient.SendMessage(msg);
            
            return response.Success;
        }

    }

}
