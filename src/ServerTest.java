import javax.swing.JFrame;

public class ServerTest {
    public static void main(String[] args) {
        // Server server = new Server();
        // server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // //server.startRunning();

        @SuppressWarnings("unused")
        Server server = null;
        // if (args.length != 1)
        // System.out.println("Usage: java ChatServer port");
        // else {
        // server = new ChatServer(Integer.parseInt(args[0]));
        // server = new Server(Integer.parseInt("1234"));
        server = new Server();
        server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
