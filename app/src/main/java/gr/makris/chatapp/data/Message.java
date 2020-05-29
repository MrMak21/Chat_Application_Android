package gr.makris.chatapp.data;

public class Message {

    public String msgId;
    public String senderId;
    public String receipientId;
    public String messageType;
    public String message;
    public String chatId;
    public String timestamp;

    public Message() {
    }

    public Message(String msgId, String senderId, String receipientId, String messageType, String message, String chatId, String timestamp) {
        this.msgId = msgId;
        this.senderId = senderId;
        this.receipientId = receipientId;
        this.messageType = messageType;
        this.message = message;
        this.chatId = chatId;
        this.timestamp = timestamp;
    }
}
