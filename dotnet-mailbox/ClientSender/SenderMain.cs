using System;
using System.Runtime.Remoting;
using System.Runtime.Remoting.Channels;
using System.Runtime.Remoting.Channels.Tcp;
using RemotableObjects;

namespace ClientSender
{
    class SenderMain
    {
        static void Main(string[] args)
        {
            try
            {
                TcpChannel chl = new TcpChannel();
                ChannelServices.RegisterChannel(chl, false);
                Console.WriteLine("[Sender] Registered channel");

                // For the 'Builder' version:
                //   - Replace all references to "IMailBox" with "IMailBoxBuilder"
                //   - and "MailBoxObj" with "MailBoxBuilderObj".
                IMailBox obj = (IMailBox)Activator.GetObject(
                    typeof(IMailBox),
                    "tcp://localhost:1234/MailBoxObj"
                    );

                if (obj == null)
                {
                    Console.WriteLine("[Sender] Error: Could not create a proxy from the Server.");
                }
                else
                {
                    Console.WriteLine("[Sender] Got a reference to the remoted object.");

                    // For the 'Builder' version:
                    //   - Comment out the next instruction
                    //   - And uncomment the instruction after it.
                    IMailBox mailBox = obj; // just aliasing the object
                    // IMailBox mailBox = obj.buildMailBox();

                    while (true)
                    {
                        Console.WriteLine("Message: ");
                        string text = Console.ReadLine().Trim();
                        if (String.IsNullOrWhiteSpace(text)) {
                            Console.WriteLine("Empty message, ignored.");
                            continue;
                        } 

                        Message msg = new Message(text);
                        mailBox.SendMessage(msg);

                        Console.WriteLine(KaiStuff.KaiSay.AsSentMessage(msg.Text));
                        Console.WriteLine();
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("[Sender] Error: " + ex.Message);
            }

            Console.WriteLine("(Press ENTER to exit...)");
            Console.ReadLine();
        }
    }
}
