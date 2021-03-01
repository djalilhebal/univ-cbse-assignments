using Grpc.Core;
using System;

using KaiStuff;

namespace ChatroomServer
{
    class Program
    {
        const string SERVER_ADDRESS = "localhost"; //"192.168.43.127";

        // NOTE: We're using char 'g' (we could use 'G') as a port prefix. (g is for gRPC.)
        // int PORT_NUMBER_LOWER = 103_01 = int.Parse("10301") = int.Parse((int)'g' + "01");
        // int PORT_NUMBER_UPPER =  71_01 = int.Parse("7101")  = int.Parse((int)'G' + "01");
        const int ACCOUNTS_PORT = 103_01;
        const int MESSAGES_PORT = 103_02;

        public static void Main(string[] args)
        {

            try
            {
                AccountsServiceImpl accountsService = new AccountsServiceImpl();
                MessagesServiceImpl messagesService = new MessagesServiceImpl(accountsService);
                
                Server server = new Server
                {

                    Services =
                    {
                        AccountsService.BindService(accountsService),
                        MessagesService.BindService(messagesService)
                    },
                    Ports = 
                    {
                        new ServerPort(SERVER_ADDRESS, ACCOUNTS_PORT, ServerCredentials.Insecure),
                        new ServerPort(SERVER_ADDRESS , MESSAGES_PORT, ServerCredentials.Insecure) 
                    }

                };
                server.Start();
                
                Utils.Log("ServerMain", $"The Accounts service is listening on port {ACCOUNTS_PORT}");
                Utils.Log("ServerMain", $"The Messages service is listening on port {MESSAGES_PORT}");
                
                Console.WriteLine("Press ESCAPE to stop the server...");
                while (Console.ReadKey().Key != ConsoleKey.Escape) /* NOP */;
                server.ShutdownAsync().Wait();
            }
            catch (Exception ex)
            {
                Utils.Log("ServerMain", $"Exception encountered: {ex}");
            }
        }
    }

}
