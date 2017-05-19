import javax.swing.JFrame;

public class ClientTest {
    public static void main(String[] args) {
//        Client charlie;
//        charlie = new Client("127.0.0.1");
//        charlie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        charlie.startRunning();
		
		@SuppressWarnings("unused")
        Client client = null;
        //if (args.length != 2)
//            System.out.println("Usage: java ChatClient host port");
//        else
//            client = new ChatClient(args[0], Integer.parseInt(args[1]));
         client = new Client("0.0.0.0", Integer.parseInt("6789"));
    }
}