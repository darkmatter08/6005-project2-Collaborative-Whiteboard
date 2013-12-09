package canvas;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerHandler {
	final String getIdMessage = "getWhiteboardIds";
	final String createNewWhiteboardMessage = "createNewWhiteboard";
	Socket mySocket;
	PrintWriter out;
	ObjectInputStream in;
	
	public synchronized void init() {
		try {
			mySocket = new Socket("127.0.0.1", shared.Ports.MASTER_PORT);
			out = new PrintWriter(mySocket.getOutputStream());
			in = new ObjectInputStream(mySocket.getInputStream());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized List<Integer> getWhiteBoardIds() {
		out.println(getIdMessage);
		System.out.println("sent a message");
		List<Integer> boards = null;
		try {
			boards = (List<Integer>)in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boards;
	}

	public synchronized int createNewWhiteBoard() {
		out.println(createNewWhiteboardMessage);
		Integer boardId = null;
		try {
			boardId = (Integer)in.readObject();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return boardId;
	}

}
