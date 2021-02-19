using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RemotableObjects
{
    public interface IMailBox
    {
        void SendMessage(Message msg);

        Message[] ReceiveMessages();

        Message[] ReceiveMessagesAfter(Message msg);
    }

    public interface IMailBoxBuilder
    {
        IMailBox buildMailBox();
    }

}
