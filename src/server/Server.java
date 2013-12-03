package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import canvas.*;

/**
 * The Server class accepts requests from clients to connect
 *  The handling of each connection is abstracted into 
 *  WhiteboardConnectionHandler.java. This is a shell for all
 *  the connections the server has, and contains all whiteboards.
 * @author jains
 *
 */
public class Server /*implements WhiteBoardServer */{
    private final List<Whiteboard> whiteboards;
    private final ServerSocket serverSocket;
    private final List<WhiteboardConnectionHandler> clients;
    
    public Server(int port) throws IOException{
        whiteboards = new ArrayList<Whiteboard>();
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<WhiteboardConnectionHandler>();
    }
    
    /**
     * Start server, default port 8888
     */
    public Server() throws IOException{
        this(8888);
    }
    
//    public List<Integer> getWhiteBoardIds() {
//        ArrayList<Integer> ids = new ArrayList<Integer>();
//        for (int i = 0; i < whiteboards.size(); ++i) {
//            ids.add(i);
//        }
//        return ids;
//    }
//    
//    /**
//     * Retrieve a canvas with a particular ID number
//     * @param id The index of the desired canvas in the server's list
//     * @return A Canvas object
//     */
//    public Whiteboard getWhiteboardByID(int id) {
//        return whiteboards.get(id);
//    }
//    
//    /**
//     * Create a new blank whiteboard and return its ID number.
//     * @param width The width of the whiteboard to create, in pixels
//     * @param height The height of the whiteboard to create, in pixels
//     * @return The ID number of the newly created canvas
//     */
//    public synchronized int createNewWhiteBoard(int width, int height) {
//        Whiteboard w = new Whiteboard();//createImage(BOARD_WIDTH, BOARD_HEIGHT));
//        w.fillWithWhite();
//        whiteboards.add(w);
//        return whiteboards.size() - 1;
//    }
//    
//    /**
//     * Create a new canvas with dimensions 800 (width) by 600 (height) and return its ID number.
//     * @param width The width of the canvas to create, in pixels
//     * @param height The height of the canvas to create, in pixels
//     * @return The ID number of the newly created canvas
//     */
//    public synchronized int createNewWhiteBoard() {
//        return createNewWhiteBoard(800, 600);
//    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
    
    void announceNewWhiteboard(int newWhiteboardId) {
        for (WhiteboardConnectionHandler client : clients) {
            client.announceNewWhiteboard(newWhiteboardId);
        }
    }
    
    /**
     * TODO Will cause problems because it'll block forever. Need a new connection
     *  handler 
     * @throws IOException
     */
    public void serve() throws IOException {
        while(true) {
            final Socket socket = serverSocket.accept();
            WhiteboardConnectionHandler wch = 
                    new WhiteboardConnectionHandler(whiteboards, socket, this);
            clients.add(wch);
            new Thread(wch).run();
        }
    }

}