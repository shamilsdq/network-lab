import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    private Socket socket;

    private BufferedReader messageReader;
    private BufferedWriter messageSender;
    private Scanner in;
    
    public Client(String address, int port) {
        try {
            this.socket = new Socket(address, port);
            System.out.println("Client started");
            this.in = new Scanner(System.in);
            this.messageReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.messageSender = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void run() {
        String recievedMessage, message;
        while (true) {
            try {
                System.out.print("ME: ");
                message = this.in.nextLine();
                this.messageSender.write(message + '\n');
                this.messageSender.flush();
                if (message.equals("EOT")) break;
                recievedMessage = this.messageReader.readLine();
                System.out.println("SERVER: " + recievedMessage);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public void close() {
        try {
            this.in.close();
            this.messageReader.close();
            this.messageSender.close();
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