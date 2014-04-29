import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServerThread extends Thread {
	private Socket socket;
	public ServerThread(Socket socket){
		this.socket = socket;
	}
	public void run(){
		try{
			System.out.println("New connection accepted" + socket.getInetAddress()
					+ ":" + socket.getPort());
			BufferedReader br = getReader(socket);
			PrintWriter pw = getWriter(socket);
			String msg = null;
			while((msg = br.readLine()) != null){
				msg = socket.getInetAddress() + ":" + socket.getPort() + " say: " + msg;
				System.out.println(msg);
				pw.println(msg);
				if(msg.equals("bye")){
					break;
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(socket!=null) socket.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	private PrintWriter getWriter(Socket socket2) throws IOException {
		return new PrintWriter(socket.getOutputStream(),true);
	}
	private BufferedReader getReader(Socket socket2) throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
}
