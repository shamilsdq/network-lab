import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class Server {
    private DatagramSocket socket;
    private DatagramPacket packet;
    private Scanner in;
    private byte[] buffer;

    private InetAddress address;
    private int port;

    public Server(String sendIp, int sendPort, int recievePort) {
        try {
            this.socket = new DatagramSocket(recievePort);
            this.port = sendPort;
            this.address = InetAddress.getByName(sendIp);
            this.in = new Scanner(System.in);
            this.buffer = new byte[1024];
            this.packet = new DatagramPacket(this.buffer, this.buffer.length);
            System.out.println("Server started");
        } catch (Exception ex) {
            System.out.println(ex);
            return;
        }
    }

    public void run() {
        while (true) {
            try {
                this.socket.receive(this.packet);
                String recievedMessage = new String(this.packet.getData(), 0, this.packet.getLength());
                if (recievedMessage.equals("EOT")) break;
                System.out.println("CLIENT: " + recievedMessage);
                System.out.print("ME: ");
                String message = new String(this.in.nextLine());
                this.buffer = message.getBytes();
                socket.send(new DatagramPacket(this.buffer, message.length(), this.address, this.port));
            } catch(Exception ex) {
                System.out.println(ex);
                return;
            }
        }
    }

    public void close() {
        this.in.close();
        this.socket.close();
    }

    public static void main(String args[]) {
        Server server = new Server("127.0.0.1", 4000, 5000);
        server.run();
        server.close();
    }
}