import javax.swing.JFrame;

public class ServerTest {
    public static void main(String[] args) {
        Server server = new Server(1234);
        server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        server.startRunning();
    }
}
