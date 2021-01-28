import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class Client {
    private DatagramSocket socket;
    private DatagramPacket packet;
    private Scanner in;
    private byte[] buffer;

    private InetAddress address;
    private int port;

    public Client(String sendIp, int sendPort, int recievePort) {
        try {
            this.socket = new DatagramSocket(recievePort);
            this.port = sendPort;
            this.address = InetAddress.getByName(sendIp);
            this.in = new Scanner(System.in);
            this.buffer = new byte[1024];
            this.packet = new DatagramPacket(this.buffer, this.buffer.length);
            System.out.println("Client started");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void run() {
        while (true) {
            try {
                System.out.print("ME: ");
                String message = new String(this.in.nextLine());
                this.buffer = message.getBytes();
                this.socket.send(new DatagramPacket(this.buffer, message.length(), this.address, this.port));
                if (message.equals("EOT")) break;
                this.socket.receive(this.packet);
                String recievedMessage = new String(this.packet.getData(), 0, this.packet.getLength());
                System.out.println("SERVER: " + recievedMessage);
            } catch(Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public void close() {
        this.in.close();
        this.socket.close();
    }

    public static void main(String args[]) {
        Client client = new Client("127.0.0.1", 5000, 4000);
        client.run();
        client.close();
    }
}