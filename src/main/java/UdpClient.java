import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UdpClient {
    //send the equipment code to the server
    public static void sendDataToServer(int eCode) {
        try {
            // port 7500 to broadcast
            int clientPort = 7500;
            DatagramSocket socket = new DatagramSocket(clientPort);
            socket.setBroadcast(true);

            // Broadcast address
            //InetAddress broadcastAddress = InetAddress.getLocalHost();
            // use the address of traffic generator to broadcast
            InetAddress broadcastAddress = InetAddress.getByName("127.0.0.1");
            int serverPort = 7501;

            String equipment = "Code: " + eCode;

            byte[] sendData = equipment.getBytes();

            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, broadcastAddress, serverPort);

            socket.send(packet);
            //System.out.println("Sending data to server: " + equipment);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // sample test
//        int userInput = 0;
//        Scanner userInputInt = new Scanner(System.in);
//        System.out.println("Enter equipment code: ");
//        userInput = userInputInt.nextInt();  // Read user input
//        sendDataToServer(userInput);
    }
}