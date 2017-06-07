package JMClient;

import java.io.Serializable;

public class Message implements Serializable {

    public static final long serialVersionUID = 1L;
    public String type, sender, content, recipient;

    // constructor
    public Message(String type, String sender, String content, String recipient) {
        this.type = type;
        this.sender = sender;
        this.content = content;
        this.recipient = recipient;
    }

    // getter method returns a formatted string
    @Override
    public String toString() {
        return "{type='" + type + "', sender='" + sender + "', content='" + content + "', recipient='" + recipient + "'}";
    }
}
