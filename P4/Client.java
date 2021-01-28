import java.util.Scanner;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Client {
    private DatagramSocket socket;
    private DatagramPacket packet;
    private Scanner in;

    private InetAddress address;
    private int port;

    public Client(String ip, int port) {
        try {
            this.socket = new DatagramSocket();
            this.in = new Scanner(System.in);
            this.address = InetAddress.getByName(ip);
            this.port = port;
        } catch (Exception ex) {
            System.out.println(ex);
            return;
        }
    }

    public void run() {
        while (true) {
            System.out.print("ME: ");
            String message = this.in.nextLine();
            byte[] buffer = message.getBytes();
            packet = new DatagramPacket(buffer, buffer.length, this.address, this.port);
            try {
                socket.send(packet);
            } catch (Exception ex) {
                System.out.println(ex);
                return;
            }
            if (message.equals("EOT")) break;
        }
    }

    public void close() {
        this.in.close();
        this.socket.close();
    }

    public static void main(String args[]) {
        Client client = new Client("127.0.0.1", 5000);
        client.run();
        client.close();
    }
}