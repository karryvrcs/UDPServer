import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Main {
    public static void main(String[] args) {

        // UDP: User Datagram Protocol (UDP is often used for time-sensitive communication)
        // When using TCP, some handshaking has to take place between server and client.
        // (The client has to connect to server and the server has to accept)
        // TCP is reliable protocol but that reliability requires overhead which can ultimately affect performance

        // UDP does not have handshaking.
        // Use UDP when you don't need a reliable connection or a two-way connection or when speed is essential.
        // a datagram is a self-contained message, it is not guaranteed to arrive at its destination!

        // UDP Example: Voiceover IP application like Skype, and video streaming usually use UDP
        // Our eyes won't notice if the occasional packet isn't received.
        // because speed is more important than ensuring that absolutely every package arrives.

        try {
            DatagramSocket datagramSocket = new DatagramSocket(5000);

            while (true) {
                byte[] buffer = new byte[50];
                // Whatever receive from the socket, the "byte[] buffer" going to be populated in datagram packet
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // fill
                datagramSocket.receive(packet);
                // The buffer array is initialized with a length of 50 bytes, if the received data is less than 50 bytes,
                // the remaining part of the byte array will contain the default initialization values.
                // 如果发送的数据小于50字节，字符串构造函数将包括这些额外的空字符作为字符串的一部分，导致输出看起来像是包含了乱码。
                // Use packet.getLength() to ensure onlyu actual received data is converted into a String.
                System.out.println("[SERVER] Text received is: " + new String(buffer, 0, packet.getLength()));

                String returnString = "echo: " + new String(buffer, 0, packet.getLength());
                byte[] buffer2 = returnString.getBytes();
                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                packet = new DatagramPacket(buffer2, buffer2.length, address, port);
                datagramSocket.send(packet);
            }

        } catch (SocketException e) {
            System.out.println("SocketException: " + e.getMessage());
        } catch (IOException e){
            System.out.println("IOException: " + e.getMessage());
        }
    }
}