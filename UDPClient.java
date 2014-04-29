import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class UDPClient {

	private String remoteHost = "192.168.56.1";
	private int remotePort = 8000;
	private DatagramSocket dgSocket;
	
	
	public UDPClient() throws SocketException{
		dgSocket = new DatagramSocket();
	}
	
	public void talk() throws UnknownHostException, SocketException{
		InetAddress remoteIP = InetAddress.getByName(remoteHost);
		SendThread sender = new SendThread(remoteIP, remotePort);
		sender.start();
		RecieveThread receiver = new RecieveThread(sender.getSocket());
		receiver.start();
	}
	
	public static void main(String[] args) throws UnknownHostException, SocketException {
		new UDPClient().talk();
	}
	
	class SendThread extends Thread{
		private InetAddress serverIP;
		private int serverPort;
		private DatagramSocket ssocket;
		public SendThread(InetAddress ia, int port) throws SocketException{
			this.serverIP = ia;
			this.serverPort = port;
			this.ssocket = new DatagramSocket();
		}
		
		public DatagramSocket getSocket(){
			return this.ssocket;
		}
		
		public void run(){
			System.out.println("输入信息，一行一次，回车结束一次输入,bye结束通信....");
			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
			while(true){
				String msg;
				try {
					msg = userInput.readLine();
					if(msg.equals("bye")){
						break;
					}
					byte[] data = msg.getBytes();
					DatagramPacket dgpOutput = new DatagramPacket(data, data.length, serverIP, serverPort);
					ssocket.send(dgpOutput);
					Thread.yield();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
	}
	
	class RecieveThread extends Thread{
		private DatagramSocket rsSocket;
		public RecieveThread(DatagramSocket socket){
			this.rsSocket = socket;
		}
		public void run(){
			byte[] buffer = new byte[66507];
			while(true){
				DatagramPacket dgp = new DatagramPacket(buffer, buffer.length);
				try {
					rsSocket.receive(dgp);
					String msg = new String(dgp.getData(), 0, dgp.getLength());
					System.out.println(msg);
					Thread.yield();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
