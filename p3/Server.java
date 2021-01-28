import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;


public class Server {
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private BufferedReader in = null;

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting connection request");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void run() {
        this.socket = serverSocket.accept();
        System.out.println("Client connected");
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while (true) {
            try {
                line = this.in.readLine();
                if (line == null) break;
                System.out.println("CLIENT: " + line);
                System.out.flush();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public void close() {
        try {
            this.in.close();
            this.socket.close();
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