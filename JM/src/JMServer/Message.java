package JMServer;

import java.io.Serializable;

public class Message implements Serializable {

    public static final long serialVersionUID = 1L;
    public String type = "", sender = "", content = "", recipient = "";
    private String timeStamp = "";

    // constructor
    public Message(String type, String sender, String content, String recipient) {
        this.type = type;
        this.sender = sender;
        this.content = content;
        this.recipient = recipient;
    }
    
    // set the time stamp
    public void setTimeStamp(String _timeStamp) {
        timeStamp = _timeStamp;
    }
    
    // get the time stamp
    public String getTimeStamp() {
        return timeStamp;
    }

    // getter method returns a formatted string
    @Override
    public String toString() {
        return "{type='" + type + "', sender='" + sender + "', content='" + content + "', recipient='" + recipient + "'}";
    }
}
