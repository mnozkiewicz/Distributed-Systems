package src;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class Multicastreceiver implements Runnable{
    MulticastSocket socket;
    DatagramPacket packet;
    private byte[] buff;
    private InetAddress group;
    public String userName;

    public Multicastreceiver(String userName){
        this.buff = new byte[1024];
        this.userName = userName;
    }
    
    @SuppressWarnings("deprecation")
    public void run(){
        try {
            socket = new MulticastSocket(9009);
            group = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(group);

            while(true){
                Arrays.fill(buff, (byte)0);
                packet = new DatagramPacket(buff, buff.length);
                socket.receive(packet);

                String msg = new String(packet.getData());
                String [] arr = msg.split(" ", 2);
                if(!arr[0].equals(userName)){
                    System.out.println("(" + arr[0] + ")");
                    System.out.println(arr[1]);                   
                }
            }
        } catch (Exception e) {}
        finally{
            if(socket != null){
                try {
                    socket.leaveGroup(group);
                } catch (IOException e) {}
                socket.close();
            }
        }
    }
}
