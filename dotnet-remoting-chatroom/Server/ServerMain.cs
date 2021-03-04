using System;
using System.Runtime.Remoting;
using System.Runtime.Remoting.Channels;
using System.Runtime.Remoting.Channels.Tcp;
using RemotableObjects;

namespace Server
{
    class ServerMain
    {
        static void Main(string[] args)
        {
            try
            {
                TcpChannel chnl = new TcpChannel(1234);
                ChannelServices.RegisterChannel(chnl, false);
                // NOTE: It's like in Java: `new MailBox()` <=> `MailBox.class.newInstance()`.
                //       and CSharp's `typeof(MailBox)` is equivalent to Java's `MailBox.class`.

                // For the 'Builder' version:
                //  - Replace `typeof(MailBox)` with `typeof(MailBoxBuilder)`
                //  - and replace "MailBoxObj" with "MailBoxBuilderObj"
                RemotingConfiguration.RegisterWellKnownServiceType(
                    typeof(MailBox),
                    "MailBoxObj",
                    WellKnownObjectMode.Singleton
                    // For the SingleCall version: Comment out the previous line and uncomment the following line.
                    // WellKnownObjectMode.SingleCall 
                    );

                Console.WriteLine("[Server] Started");
            }
            catch (Exception ex)
            {
                Console.WriteLine("[Server] Error: Could not start: : " + ex.Message);
            }

            Console.WriteLine("(Press ENTER to exit...)\r\n");
            Console.ReadLine();
        }
    }
}
