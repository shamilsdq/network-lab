import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {
    private Socket socket;
    private ServerSocket serverSocket;
    
    private BufferedReader messageReader;
    private BufferedWriter messageSender;
    private Scanner in;
    
    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.in = new Scanner(System.in);
            System.out.println("Server started");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void run() {
        try {
            this.socket = this.serverSocket.accept();
            this.messageReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.messageSender = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            System.out.println("Client connected");
        } catch (Exception ex) {
            System.out.println(ex);
            return;
        }
        
        String recievedMessage, message;
        while (true) {
            try {
                recievedMessage = this.messageReader.readLine();
                if (recievedMessage.equals("EOT")) break;
                System.out.println("CLIENT: " + recievedMessage);
                System.out.print("ME: ");
                message = this.in.nextLine();
                this.messageSender.write(message + '\n');
                this.messageSender.flush();
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
            this.serverSocket.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String args[]) {
        Server server = new Server(5000);
        server.run();
        server.close();
    }
}