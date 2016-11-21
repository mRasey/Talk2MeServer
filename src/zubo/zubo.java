package zubo;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;

/**
 * Created by billy on 2016/11/19.
 */
public class zubo {


    public zubo() throws IOException {

    }

    public static void main(String[] args) throws Exception {
//        for(int i = 1; i < 255; i++) {
//            String ip = "127.0.0." + 1;
//            new Thread(() -> {
//                try {
//                    byte[] bytes = new byte[1024];
//                    DatagramPacket datagramPacket = new DatagramPacket(bytes, 1024);
////                    DatagramSocket multicastSocket = new MulticastSocket(new InetSocketAddress(ip, 2333));
////                    multicastSocket.joinGroup(InetAddress.getByName("239.0.0.0"));
////                    multicastSocket.receive(datagramPacket);
//                    DatagramSocket datagramSocket = new DatagramSocket();
//                    datagramSocket.receive(datagramPacket);
//                    System.out.println(new String(bytes));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }

        String ip = "239.192.233.233";
        System.setProperty("java.net.preferIPv4Stack", "true");
        new Thread(() -> {
            try {
                byte[] bytes = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(bytes, 1024);
//                DatagramSocket datagramSocket = new DatagramSocket(2333);
//                datagramSocket.receive(datagramPacket);
                MulticastSocket multicastSocket = new MulticastSocket(2333);
                multicastSocket.joinGroup(InetAddress.getByName(ip));
                multicastSocket.receive(datagramPacket);
                System.out.println(new String(bytes));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        String info = "hello world";
        byte[] bytes = info.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, new InetSocketAddress(ip, 2333));
        MulticastSocket multicastSocket = new MulticastSocket();
        multicastSocket.send(datagramPacket);
//        DatagramSocket datagramSocket = new DatagramSocket();
//        datagramSocket.send(datagramPacket);

    }
}
