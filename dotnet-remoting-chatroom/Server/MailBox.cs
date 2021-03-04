using RemotableObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Collections; // for ArrayList
using KaiStuff;

namespace Server
{
    public class MailBox : MarshalByRefObject, RemotableObjects.IMailBox
    {
        // CSharp's ArrayList is like Java's ArrayList<Object>, and its use is discouraged.
        // List<Message> should be used instead.
        // private List<Message> messagesList = new List<Message>();
        private ArrayList messagesList = new ArrayList();

        // Constructor
        public MailBox()
        {
            Utils.LogWithTime(ToString(), "Constructed.");
        }

        // Destructor (AKA finalizer)
        ~MailBox()
        {
            Utils.LogWithTime(ToString(), "Destroyed.");
        }

        // ---

        public void SendMessage(Message msg)
        {
            // Generate a unique ID
            // private static long count = 0;
            // string messageId = "M-" + (++count).ToString(); // Maybe add a Random number generator or timestamp or whatever
            string messageId = System.Guid.NewGuid().ToString();
            msg.Id = messageId;
            msg.Timestamp = DateTimeOffset.UtcNow.ToString("O");
            Utils.LogWithTime(ToString(), "Adding message to list: " + msg.Text);
            messagesList.Add(msg);
        }

        /**
         * Return all messages in the list as an array.
         */
        public Message[] ReceiveMessages()
        {
            Utils.LogWithTime(ToString(), "Returning messages list as array");
            // NOTE: The casting, Cast<Message>(), is needed because we are using an ArrayList.
            //       With List<Message>, we wouldn't need it.
            return messagesList.Cast<Message>().ToArray();
        }

        /**
         * Return all messages after `targetMessage`.
         * Like, if we have a list `{A, B, C, D, E}` and we call `ReceiveMessagesAfter(C)`, it will return `{D, E}`.
         *
         * If `targetMessage` is null then calling this method is like calling `ReceiveMessages()`.
         */
        public Message[] ReceiveMessagesAfter(Message targetMessage)
        {
            if (targetMessage == null)
            {
                return ReceiveMessages();
            }

            var countTotal = messagesList.Count;
            var messageIndex = messagesList.IndexOf(targetMessage);
            // Return an empty array if the target message was not found for whatever reason or if it is the last message in the list.
            if (messageIndex < 0 || messageIndex == countTotal - 1)
            {
                return new Message[] {};
            }

            var startFrom = messageIndex + 1;
            var countAfter = countTotal - startFrom;
            Utils.LogWithTime(
                ToString(),
                $"ReceiveMessagesAfter: messageIndex={messageIndex}, startFrom={startFrom} countAfter={countAfter}, countTotal={countTotal}"
                );
            var subList = messagesList.GetRange(startFrom, countAfter);
            return subList.Cast<Message>().ToArray();
        }

        // ---

        /**
         * - TODO MAYBE: Can we access this method from a remote client (using, e.g. Reflection) 
         *      even though it is not specified in the interface?
         */
        public string SpillTheTea() {
            return "The tea is piping hot!";
        }
        
        // ---

        /**
         * Returns a string a la Java's `Object::toString`.
         */
        override public string ToString()
        {
            // Java's toString is defined as `getClass().getName() + '@' + Integer.toHexString(hashCode())`
            // or: `String.format("%s@%X", getClass().getName(), hashCode())`.
            return String.Format("{0}@{1:X}", this.GetType().FullName, this.GetHashCode());
        }

    }

}
