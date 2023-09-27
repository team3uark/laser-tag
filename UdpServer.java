import java.net.*;
import java.io.*;

public class UdpServer {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(7501); // Server listens on port 7501
            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received " + receivedData);

                // Clear the buffer for the next packet
                receiveData = new byte[1024];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}