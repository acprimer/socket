import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class UDPServer {
	private int port = 8000;
	private DatagramSocket socket;
	
	public UDPServer() throws SocketException{
		socket = new DatagramSocket(port);
		System.out.println("server is starting...");
	}
	
	public void service() throws IOException{
		while(true){
			DatagramPacket packet = new DatagramPacket(new byte[512], 512);
			socket.receive(packet);
			String msg = new String(packet.getData(), 0, packet.getLength());
			System.out.println(packet.getAddress() + ":" + packet.getPort() + " > " + msg);
			packet.setData(echo(msg).getBytes());
			socket.send(packet);
		}
	}
	
	private String echo(String msg) {
		return "echo: " + msg;
	}

	public static void main(String[] args) throws SocketException, IOException {
		new UDPServer().service();
	}

}
