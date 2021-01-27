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
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting connection request");
            socket = serverSocket.accept();
            System.out.println("Client accepted");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void run() {
        String line;
        while (true) {
            try {
                line = in.readLine();
                if (line == null) break;
                System.out.println(line);
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