import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Main {
    final static String TAG = Main.class.getName();

    static private SimpleClient simpleClient;

    public static void main(String[] args) {
        try {
            KaiConsole.log(TAG, "Starting...");
            simpleClient = new SimpleClient();
            simpleClient.setup();


            // To test works `getNewMessages()`:
            new Thread(() -> {
                // Kinda like `observable.onNext(..);`
                simpleClient.getNewMessages().forEachRemaining(newMessage -> {
                    KaiConsole.log(TAG, "[NEW] " + newMessage);
                });
            }).start();


            // test();

            user();
        } catch (Exception ignoredException) {
            // NOP;
        } finally {
            KaiConsole.log(TAG, "Shutting down...");
            simpleClient.shutdown();
        }
    }

    static void test() {
        AccountsServiceOuterClass.Account currentAccount;

        System.out.println("===[ SEND MESSAGES FROM DIFFERENT ACCOUNTS ]===");
        currentAccount = simpleClient.getAccount("Alice", "mushrooms");
        System.out.println("[GrpcClient] LOG: currentAccount: " + currentAccount);
        System.out.println("[GrpcClient] LOG: message sent successfully? "
                + simpleClient.sendMessage(currentAccount.getId(), "Hieeeeee"));

        System.out.println("---");
        currentAccount = simpleClient.getAccount("Bob", "12345");
        System.out.println("[GrpcClient] LOG: currentAccount: " + currentAccount);
        System.out.println("[GrpcClient] LOG: message sent successfully? "
                + simpleClient.sendMessage(currentAccount.getId(), "What's up?"));

        System.out.println("---");
        // This should fail
        currentAccount = simpleClient.getAccount("Alice", "wrong-password");
        System.out.println("[GrpcClient] LOG: currentAccount: " + currentAccount);
        System.out.println("[GrpcClient] LOG: message sent successfully? "
                + simpleClient.sendMessage(currentAccount.getId(), "Malice!"));

        System.out.println("---");
        currentAccount = simpleClient.getAccount("Alice", "mushrooms");
        System.out.println("[GrpcClient] LOG: currentAccount: " + currentAccount);
        System.out.println("[GrpcClient] LOG: message sent successfully? "
                + simpleClient.sendMessage(currentAccount.getId(), "Rien~"));

        System.out.println("\n===[ GET ALL MESSAGES ]===\n");
        simpleClient.getMessages().forEach(System.out::println);

        System.out.println();
        System.out.println("TEST END.");
    }

    static void user() throws Exception {
        System.out.println("\n===[ User ]===\n");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        AccountsServiceOuterClass.Account currentAccount = AccountsServiceOuterClass.Account.getDefaultInstance();
        while (currentAccount.getId().isEmpty()) {
            System.out.println("Log in first...");
            System.out.print("Nickname: ");
            String nick = reader.readLine().trim();
            System.out.print("Password: ");
            String pass = reader.readLine().trim();
            if (nick.isEmpty() || pass.isEmpty()) continue;
            currentAccount = simpleClient.getAccount(nick, pass);
        }

        while (true) {
            System.out.print("Text: ");
            String text = reader.readLine().trim();
            if (!text.isEmpty()) {
                boolean isSuccessful = simpleClient.sendMessage(currentAccount.getId(), text);
                System.out.println(isSuccessful ? "SENT." : "ERROR!");
            }
            refreshMessages();
        }

    }

    // ---

    static private void refreshMessages() {
        System.out.println("----------------------------------");
        KaiConsole.clearScreen();

        simpleClient
                .getMessages()
                .stream()
                .map(m -> {
                    OffsetDateTime timestampObj = OffsetDateTime.parse(m.getTimestamp());
                    // Algeria's timezone (UTC+01:00)
                    OffsetDateTime timestampLocalObj = timestampObj.withOffsetSameInstant(ZoneOffset.of("+01:00"));
                    String timestampLocalStr = timestampLocalObj.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    String formattedLine = formatMessageOutput(timestampLocalStr, m.getAuthor(), m.getContent());
                    return formattedLine + System.lineSeparator() + System.lineSeparator();
                })
                .forEach(System.out::print);
    }

    /**
     * Format message a la IRC:
     * "{@code [TIMESTAMP] <NICK> MESSAGE...}".
     *
     * @param timestamp Timestamp, ISO DateTime, or maybe just the Time part preferably using the user's local timezone.
     * @param nick Nickname or username
     * @param content Message text
     * @return String, possibly with color escape codes.
     */
    public static String formatMessageOutput(String timestamp, String nick, String content) {
        if (KaiConsole.getIsConsoleColorSupported()) {
            return String.format("%s[%s]%s <%s%s%s> %s%s%s",
                    // BLACK_BRIGHT is basically GRAY
                    AnsiColor.BLACK_BRIGHT, timestamp, AnsiColor.RESET,
                    AnsiColor.MAGENTA_BOLD, nick, AnsiColor.RESET,
                    AnsiColor.CYAN, content, AnsiColor.RESET
            );
        } else {
            return String.format("[%s] <%s> %s",
                    timestamp,
                    nick,
                    content
            );
        }
    }

}
