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
            this.socket = new Socket(address, port);
            System.out.println("Client started");
            this.in = new Scanner(System.in);
            this.out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (Exception ex) {
            System.out.println(ex);
            return;
        }
    }

    public void run() {
        String line;
        while (true) {
            try {
                System.out.print("ME: ");
                line = this.in.nextLine();
                if (line == null || line.equals("EOT")) break;
                this.out.write(line + '\n');
                this.out.flush();
            } catch (Exception ex) {
                System.out.println(ex);
                return;
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
            return;
        }
    }

    public static void main(String args[]) {
        Client client = new Client("127.0.0.1", 5000);
        client.run();
        client.close();
    }
}