import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

	private String host = "192.168.56.1";
	private static int port = 8000;
	private Socket socket;
	
	public Client() throws UnknownHostException, IOException{
		socket = new Socket(host, port);
	}
	
	public void talk() throws IOException{
		BufferedReader br = getReader(socket);
		PrintWriter pw = getWriter(socket);
		BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
		String msg = null;
		while((msg = localReader.readLine()) != null){
			pw.println(msg);
			if(msg.equals("bye")){
				break;
			}
		}
		socket.close();
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Client().talk();
	}
	
	private PrintWriter getWriter(Socket socket2) throws IOException {
		return new PrintWriter(socket.getOutputStream(),true);
	}
	private BufferedReader getReader(Socket socket2) throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

}
