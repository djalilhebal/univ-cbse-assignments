using System;
using System.Collections.Generic;
using System.Text;

namespace ChatroomServer
{
    [Serializable]
    public class CustomMessage
    {
        
        private string _id;
        private string _author;
        private string _content;
        private string _timestamp;

        // ---

        public string Id
        {
            get { return _id; }
            set { EnsureNotFrozen(); _id = value; }
        }

        public string Author
        {
            get { return _author; }
            set { EnsureNotFrozen(); _author = value; }
        }

        public string Content
        {
            get { return _content; }
            set { EnsureNotFrozen(); _content = value; }
        }

        public string Timestamp
        {
            get { return _timestamp; }
            set { EnsureNotFrozen(); _timestamp = value; }
        }

        // ---

        private bool EnsureNotFrozen()
        {
            if (String.IsNullOrEmpty(Id))
            {
                return true;
            }
            else
            {
                // Kinda like Java's InvalidStateException: https://docs.oracle.com/javase/8/docs/api/java/lang/IllegalStateException.html
                // SEE: https://docs.microsoft.com/en-us/dotnet/api/system.invalidoperationexception?redirectedfrom=MSDN&view=net-5.0
                throw new InvalidOperationException("Message is frozen and unmodifiable.");
                // return false;
            }
        }

        // ---

        public override string ToString()
        {
            return $"Message({Author}, {Content}, Timestamp={Timestamp}, Id={Id})";
        }

        public override bool Equals(object obj)
        {
            return obj is CustomMessage message &&
                   Id == message.Id;
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(Id);
        }

    }
}
