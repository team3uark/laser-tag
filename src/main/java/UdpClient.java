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
            InetAddress broadcastAddress = InetAddress.getLocalHost();
            // I am not sure if we need a specific address
            //InetAddress broadcastAddress = InetAddress.getByName("192.168.1.100");
            int serverPort = 7501;

            String equipment = "Equipment ID: " + eCode;

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