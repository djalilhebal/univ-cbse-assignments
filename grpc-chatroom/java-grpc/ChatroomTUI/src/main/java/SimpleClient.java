import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;
import java.util.List;

/**
 * Simple Client. A simple wrapper for MessagesService and AccountsService.
 */
public class SimpleClient {

    static final String SERVER_ADDRESS = "192.168.1.39"; // "localhost"; // "192.168.43.127";

    static final int ACCOUNTS_PORT = 10301; // 'g'01
    private ManagedChannel accountsChannel;
    private AccountsServiceGrpc.AccountsServiceBlockingStub accountsClient;

    static final int MESSAGES_PORT = 10302; // 'g'02
    private ManagedChannel messagesChannel;
    private MessagesServiceGrpc.MessagesServiceBlockingStub messagesClient;

    // ---

    public AccountsServiceOuterClass.Account getAccount(String nick, String pass) {
        try {
            return accountsClient.getAccount(AccountsServiceOuterClass.AccountRequest
                    .newBuilder()
                    .setNickname(nick)
                    .setPassword(pass)
                    .build());
        } catch (Exception ex) {
            return AccountsServiceOuterClass.Account.getDefaultInstance();
        }
    }

    public List<MessagesServiceOuterClass.Message> getMessages() {
        return messagesClient
                .getMessages(MessagesServiceOuterClass.EmptyRequest.getDefaultInstance())
                .getMessagesList();
    }

    public List<MessagesServiceOuterClass.Message> getMessagesAfter(MessagesServiceOuterClass.Message targetMessage) {
        assert targetMessage != null : "targetMessage MUST NOT be null.";

        return messagesClient
                .getMessagesAfter(targetMessage)
                .getMessagesList();
    }

    // XXX: Consider using async `MessagesServiceGrpc.MessagesServiceBlockingStub`.
    public Iterator<MessagesServiceOuterClass.Message> getNewMessages() {
        return messagesClient
                .getNewMessages(MessagesServiceOuterClass.EmptyRequest.getDefaultInstance());
    }

    public boolean sendMessage(String currentAccountId, String content) {
        try {
            MessagesServiceOuterClass.SendMessageResponse response = messagesClient.sendMessage(
                    MessagesServiceOuterClass.Message
                            .newBuilder()
                            .setAuthor(currentAccountId)
                            .setContent(content)
                            .build()
            );
            return response.getSuccess();
        } catch (Exception ex) {
            return false;
        }
    }

    // ---

    public void setup() {
        accountsChannel = ManagedChannelBuilder.forAddress(SERVER_ADDRESS, ACCOUNTS_PORT)
                .usePlaintext()
                .build();
        accountsClient = AccountsServiceGrpc.newBlockingStub(accountsChannel);

        messagesChannel = ManagedChannelBuilder.forAddress(SERVER_ADDRESS, MESSAGES_PORT)
                .usePlaintext()
                .build();
        messagesClient = MessagesServiceGrpc.newBlockingStub(messagesChannel);
    }

    public void shutdown() {
        // Shutdown in reverse order
        messagesChannel.shutdown();
        accountsChannel.shutdown();
    }

}
