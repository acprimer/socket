import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	private static int port = 8000;
	private ServerSocket serverSocket;
	
	public Server() throws IOException{
		serverSocket = new ServerSocket(port);
		System.out.println("server is starting....");
	}
	public void servcie(){
		while(true){
			Socket socket = null;
			try{
				socket = serverSocket.accept();
				Thread workThread = new ServerThread(socket);
				workThread.start();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws IOException {
		new Server().servcie();
	}

}
