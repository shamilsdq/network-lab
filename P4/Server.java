import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class Server {
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    private byte[] buffer = null;

    public Server(int port) {
        try {
            this.socket = new DatagramSocket(port);
            this.buffer = new byte[1024];
            this.packet = new DatagramPacket(this.buffer, this.buffer.length);
            System.out.println("Server started");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void run() {
        while (true) {
            try {
                this.socket.receive(this.packet);
                String s = new String(this.packet.getData(), 0, this.packet.getLength());
                if (s.equals("EOT")) break;
                System.out.println("CLIENT: " + s);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public void close() {
        this.socket.close();
    }

    public static void main(String args[]) {
        Server server = new Server(5000);
        server.run();
        server.close();
    }
}