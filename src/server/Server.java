package server;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;


public class Server extends JFrame implements Runnable {

	private static int WIDTH = 400;
	private static int HEIGHT = 300;
	JTextArea ta;
	JScrollPane sp;
	private int clientId = 0;
	ArrayList<HandleAClient> clients;
	Queue<Pair<String, PrintWriter>> signupQueue;
	Queue<Pair<String[], PrintWriter>> reserveQueue;
	
	public Server() {
		super("Server");
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createMenu();
		createTextArea();
		this.setVisible(true);
		new Thread(this).start();

		signupQueue = new LinkedList<Pair<String, PrintWriter>>();
		reserveQueue = new LinkedList<Pair<String[], PrintWriter>>();
		new Thread(new SignupConsumer(signupQueue)).start();
		new Thread(new ReservationConsumer(reserveQueue)).start();
	}
	
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener((e) -> System.exit(0));
		menu.add(exitItem);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}
	
	private void createTextArea() {
		ta = new JTextArea();
		ta.setEditable(false);
		sp = new JScrollPane(ta);
		DefaultCaret caret = (DefaultCaret)ta.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		this.add(sp);
	}


	@Override
	public void run() {
		try {

			ServerSocket serverSocket = new ServerSocket(9898);
		    ta.append("Server started at " + new java.util.Date() + "\n");
		    clients = new ArrayList<>();
			while(true) {
				Socket socket = serverSocket.accept();
				clientId ++;
	            InetAddress inetAddress = socket.getInetAddress();
	            ta.append("Starting thread for client " + clientId + " at " + new java.util.Date() + "\n");
	            ta.append("Client " + clientId + "'s host name is " + inetAddress.getHostName() + "\n");
	            ta.append("Client " + clientId + "'s IP Address is " + inetAddress.getHostAddress() + "\n");
	            
	            HandleAClient client = new HandleAClient(socket, clientId);
	            clients.add(client);
	            Thread thread = new Thread(client);
	            thread.start();

			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	class HandleAClient implements Runnable {
	    int id;
	    BufferedReader in;
	    PrintWriter out;
	    
	    public HandleAClient(Socket socket, int id) {
	    	this.id = id;
	    	try {
		    	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    		out = new PrintWriter(socket.getOutputStream());
	    	} catch(IOException ex) {
	    		ex.printStackTrace();
	    	}
	    }
	    
	    public String DBQuery(String sql) {
	    	Connection conn;
			String rowString = ";";
			try {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection("jdbc:sqlite:final.db");
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rset = stmt.executeQuery();
				ResultSetMetaData rsmd = rset.getMetaData();
				
				int numColumns = rsmd.getColumnCount();
				while (rset.next()) {
					for (int i=1;i<=numColumns;i++) {
						Object o = rset.getObject(i);
						rowString += o.toString() + "\t";
					}
					rowString += ";";
				}
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			return rowString;
	    }

	    public void run() {
	    	String msg;
	    	try {
	    		while ((msg = in.readLine()) != null) {
    				ta.append(msg+"\n");
	    			String[] msg_part = msg.split("\t");
	    			String type = msg_part[0];
	    			String sql = msg_part[1];
	    			if (type.equals("signup")) {
	    				signupQueue.add(new Pair<>(sql, out));
	    			} else if (type.equals("reservation")) {
	    				reserveQueue.add(new Pair<>(new String[] {msg_part[1], msg_part[2], msg_part[3]}, out));
	    			} else if (type.equals("query")) {
	    				String result = DBQuery(sql);
	    				out.println(result);
	    				out.flush();
	    			}
	    		}
	    	} catch(IOException ex) {
	    		ex.printStackTrace();
	    	}
	    }
	}
	
	public static void main(String[] args) {
		new Server();
	}
}


