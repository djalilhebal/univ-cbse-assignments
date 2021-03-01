using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Threading.Tasks.Dataflow;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;


namespace ChatroomWPF
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private readonly SimpleClient simpleClient;

        public MainWindow()
        {
            InitializeComponent();

            simpleClient = new SimpleClient();
            simpleClient.Setup();
            StartAutoRefresher();
        }
        
        // Destructor
        ~MainWindow()
        {
            StopAutoRefresher();
            simpleClient.Shutdown();
        }

        // ---

        #region AutoRefresher

        private void StartAutoRefresher()
        {
            _cancellationSource = new CancellationTokenSource();
            var cancellationToken = _cancellationSource.Token;
            _refresher = AutoRefresh(cancellationToken);
        }

        private void StopAutoRefresher()
        {
            _cancellationSource.Cancel();
            _refresher.Wait();
        }
        
        async Task AutoRefresh(CancellationToken cancellationToken = new CancellationToken())
        {
            IEnumerable<Message> messages;
            Message latestMessage = null;

            while (!cancellationToken.IsCancellationRequested)
            {
                messages = latestMessage == null ? simpleClient.GetMessages() : simpleClient.GetMessagesAfter(latestMessage);

                var newLatestMessage = messages.LastOrDefault();
                if (newLatestMessage != null)
                {
                    latestMessage = newLatestMessage;
                }

                AppendMessages(messages);
                // ReplaceWithMessages(messages);

                var observable = simpleClient.GetNewMessages(cancellationToken);
                while (!cancellationToken.IsCancellationRequested)
                {
                    var message = await observable.ReceiveAsync();
                    var messageElement = CreateColorfulMessageElement(message);
                    AppendMessage(messageElement);
                }

                // Wait for ~3 seconds
                await Task.Delay(3 * 1000, cancellationToken);
            }
        }

        private Task _refresher;
        private CancellationTokenSource _cancellationSource;

        #endregion

        // ---

        public void AppendMessages(IEnumerable<Message> messages)
        {
            foreach (var msg in messages)
            {
                AppendMessage(msg);
            }
        }

        public void ReplaceWithMessages(IEnumerable<Message> messages)
        {
            messagesList.Items.Clear();
            AppendMessages(messages);
        }

        /**
         * Create a new message UI output element.
         */
        private TextBlock CreatePlainMessageElement(Message msg)
        {
            var output = $"[{msg.Timestamp}] <{msg.Author}> {msg.Content}";
            var item = new TextBlock
            {
                Text = output,
            };

            item.TextWrapping = TextWrapping.Wrap;

            return item;
        }

        /**
         * Some monospaced font family.
         *
         * NOTES:
         * - We want to use a monospaced font to ensure that the first parts --timestamps and nicknames-- always have the exact
         * same width, and to ensure that all messages start at the same "horizontal line".
         * - A monospaced font was *needed* because "normally" letters (and characters in general) don't always have the same width,
         *   like 'M' vs 'n'.
         * - I didn't come up with this "UI idea," I saw it in some IRC client (WeeChat) and thought to try it.
         *
         * SEE:
         * - Microsoft-Supplied Monospaced TrueType Fonts
         *   https://support.microsoft.com/en-us/topic/microsoft-supplied-monospaced-truetype-fonts-93aa7a47-2149-be09-31a9-c22df598c952
         */
        private static readonly FontFamily MONO_FONT_FAMILY = new FontFamily("Noto Mono, Lucida Sans Typewriter, Consolas");

        /**
         * The maximum length for nicknames.
         * 
         * For reference, max lengths in different services:
         * - IRC specifies 9, but says more SHOULD be allowed.
         * - Auth0 defaults to 1-15.
         * - Twitter max is 15.
         * - Reddit max is 20.
         * - Discord max is 2-32.
         * - Linux max is 32, but 8 should be considered.
         */
        const int MAX_NICKNAME_LENGTH = 15;

        /**
         * Create a new message UI output element.
         */
        private TextBlock CreateColorfulMessageElement(Message m)
        {
            TextBlock lineElement = new TextBlock();

            Run timestampRun = new Run
            {
                Text = DateTimeOffset.Parse(m.Timestamp).LocalDateTime.ToString("G"), // readable/"General" timestamp
                ToolTip = m.Timestamp,
                Foreground = Brushes.DarkGray,
                FontFamily = MONO_FONT_FAMILY,
            };

            Run nicknameRun = new Run
            {
                Text = $"<{m.Author}>".PadLeft(MAX_NICKNAME_LENGTH + "<>".Length, ' '), // right-aligned
                Foreground = Brushes.Purple,
                FontWeight = FontWeights.Bold,
                FontFamily = MONO_FONT_FAMILY,
            };

            Run contentRun = new Run
            {
                Text = m.Content,
                Foreground = Brushes.Black,
            };

            lineElement.Inlines.AddRange(new []{
                timestampRun,
                new Run(" "),
                nicknameRun,
                new Run(" "),
                contentRun,
            });

            
            lineElement.TextWrapping = TextWrapping.Wrap;

            return lineElement;
        }

        private void AppendMessage(Message msg)
        {
            var item = CreateColorfulMessageElement(msg);
            messagesList.Items.Add(item);
        }

        private void AppendMessage(TextBlock msgItem)
        {
            messagesList.Items.Add(msgItem);
        }

        // ---

        // Our initial state is "Disconnected" each time the app starts since we are not saving/persisting the session.
        private ConnectionStatus _status = ConnectionStatus.DISCONNECTED;

        public enum ConnectionStatus
        {
            CONNECTED,
            DISCONNECTED,
            CONNECTING,
            DISCONNECTING,
        }

        public void OnConnectionStatusUpdated()
        {
            MaybeToggleControls();

            // Update Button action
            string actionName;
            switch (CurrentStatus)
            {
                case ConnectionStatus.CONNECTED: actionName = "Disconnect"; break;
                case ConnectionStatus.DISCONNECTED: actionName = "Connect"; break;
                case ConnectionStatus.CONNECTING: actionName = "Connecting..."; break;
                case ConnectionStatus.DISCONNECTING: actionName = "Disconnecting..."; break;
                default:
                    throw new InvalidOperationException($"Unexpected value for CurrentStatus: ${CurrentStatus}");
            }
            connectionButton.Content = actionName;
        }

        // ---

        // TODO: Make it `private` and move it to the top of the class definition.
        Account currentAccount;

        private void connectionButton_Click(object sender, RoutedEventArgs e)
        {
            // User is trying to disconnect.
            if (CurrentStatus == ConnectionStatus.CONNECTED)
            {
                Console.WriteLine("[ClientMain][LOG] Disconnecting...");
                CurrentStatus = ConnectionStatus.DISCONNECTING;
                currentAccount = null;
                CurrentStatus = ConnectionStatus.DISCONNECTED;
                Console.WriteLine("[ClientMain][LOG] Disconnected.");
                return;
            }

            // Else, the user is trying to connect.
            string nickname = nicknameInput.Text;
            string password = passwordInput.Password;
            passwordInput.Password = ""; // Clear the password

            CurrentStatus = ConnectionStatus.CONNECTING;
            currentAccount = simpleClient.GetAccount(nickname, password);
            bool hasConnected = currentAccount != null && !string.IsNullOrEmpty(currentAccount.Id);
            if (hasConnected)
            {
                CurrentStatus = ConnectionStatus.CONNECTED;
            }
            else
            {
                CurrentStatus = ConnectionStatus.DISCONNECTED;
            }

        }

        // TODO/FIXME: Should be called after the inputs' values change too.
        private void MaybeToggleControls()
        {
            // Only if connected can they type and send message.
            messageInput.IsEnabled = CurrentStatus == ConnectionStatus.CONNECTED;

            // The `nickname` and `password` are only used to login (i.e. when the user is currently disconnected).
            nicknameInput.IsEnabled = passwordInput.IsEnabled = CurrentStatus == ConnectionStatus.DISCONNECTED;

            // The "Connect" button is disabled (not enabled) if the user is disconnected and has not filled the connection inputs.
            connectionButton.IsEnabled = !(
                CurrentStatus == ConnectionStatus.DISCONNECTED &&
                (string.IsNullOrWhiteSpace(nicknameInput.Text) || string.IsNullOrWhiteSpace(nicknameInput.Text))
                );
        }

        ConnectionStatus CurrentStatus
        {
            get { return _status; }
            set {
                _status = value;
                OnConnectionStatusUpdated();
            }
        }

        private void messageInput_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Enter)
            {
                Console.WriteLine("[ClientMain] ENTER pressed...");

                string content = messageInput.Text.Trim();
                messageInput.Text = "";

                if (string.IsNullOrWhiteSpace(content))
                {
                    Console.WriteLine("[ClientMain] Empty message, ignored by the client.");
                    return;
                }
                else
                {
                    Console.WriteLine($"[ClientMain] Sending message: {content}");
                    DoSendMessage(content);
                }
            }
        }

        private void DoSendMessage(string text)
        {
            bool succeeded = simpleClient.SendMessage(currentAccount.Id, text);

            // if succeeded, do nothing and let the refresher re-get it from the server.
            // Else, if it's failed, display the sent message in red with the text "FAILED!" appended to it.
            if (!succeeded)
            {
                Message failedMessage = new Message
                {
                    Id = "#FAILED-" + Guid.NewGuid().ToString(),
                    Author = currentAccount.Nickname,
                    Content = text + "\r\nFAILED!",
                    Timestamp = DateTimeOffset.Now.ToString("O"),
                };
                var failedMessageElement = CreatePlainMessageElement(failedMessage);
                failedMessageElement.Foreground = Brushes.Red;
                AppendMessage(failedMessageElement);
            }
        }

    }

}
