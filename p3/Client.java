import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    private Socket socket = null;
    private Scanner in = null;
    private BufferedWriter out = null;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Client started");
            in = new Scanner(System.in);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void run() {
        String line;
        while (true) {
            try {
                line = in.nextLine();
                if (line == null || line.equals("EOT")) break;
                out.write(line + '\n');
                out.flush();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public void close() {
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String args[]) {
        Client client = new Client("127.0.0.1", 5000);
        client.run();
        client.close();
    }
}