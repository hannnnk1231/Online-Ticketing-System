package server;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Queue;

public class SignupConsumer implements Runnable {

	public static final int DELAY = 400;
	
	Queue<Pair<String, PrintWriter>> in;
	
	public SignupConsumer(Queue<Pair<String, PrintWriter>> in) {
		this.in = in;
	}
	
	@Override
	public void run() {
		while (true) {
			if (in.peek() != null) {
				Pair<String, PrintWriter> pair = in.remove();
				String sql = pair.getKey();
				PrintWriter out = pair.getValue();
				Connection conn = null;
				try {
					Class.forName("org.sqlite.JDBC");
					conn = DriverManager.getConnection("jdbc:sqlite:final.db");
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.executeUpdate();
					out.println("succeed");
					out.flush();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					out.println("failed");
					out.flush();
				} finally {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep((long) (Math.random()*DELAY));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
