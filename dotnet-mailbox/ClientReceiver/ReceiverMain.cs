using System;
using System.Runtime.Remoting;
using System.Runtime.Remoting.Channels;
using System.Runtime.Remoting.Channels.Tcp;
using RemotableObjects;

namespace ClientReceiver
{
    class ReceiverMain
    {
        static void Main(string[] args)
        {
            try
            {
                TcpChannel chl = new TcpChannel();
                ChannelServices.RegisterChannel(chl, false);
                Console.WriteLine("[Receiver] Resigstered the channel");

                // For the 'Builder' version:
                //   - Replace all references to "IMailBox" with "IMailBoxBuilder"
                //   - and "MailBoxObj" with "MailBoxBuilderObj".
                IMailBox obj = (IMailBox)Activator.GetObject(
                    typeof(IMailBox),
                    "tcp://localhost:1234/MailBoxObj"
                    );

                if (obj == null)
                {
                    Console.WriteLine("[Receiver] Error: Could not create a proxy from the Server.");
                }
                else
                {
                    Console.WriteLine("[Receiver] Got a reference to the remoted object.");

                    // For the 'Builder' version:
                    //   - Comment out the next instruction
                    //   - And uncomment the instruction after it.
                    IMailBox mailBox = obj; // just aliasing the object
                    // IMailBox mailBox = obj.buildMailBox();

                    // Utilisation de l’objet : dépôt d’un message
                    Message[] receivedMessages = mailBox.ReceiveMessages();
                    Console.WriteLine("Received messages:");
                    Message latestMessage = null;
                    do
                    {
                        if (receivedMessages.Length == 0)
                        {
                            Console.WriteLine("\r\nNo messages.\r\n");
                        }
                        else
                        {
                            foreach (Message msg in receivedMessages)
                            {
                                Console.WriteLine(KaiStuff.KaiSay.AsReceivedMessage(msg.Text));
                                Console.WriteLine();
                                latestMessage = msg;
                            }
                        }
                        Console.WriteLine("Press ENTER to refresh...");
                        Console.ReadLine();
                        receivedMessages = mailBox.ReceiveMessagesAfter(latestMessage);
                    } while (true);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("[Receiver] Error: " + ex.Message);
            }

            Console.WriteLine("(Press ENTER to exit...)");
            Console.ReadLine();
        }
    }
}
