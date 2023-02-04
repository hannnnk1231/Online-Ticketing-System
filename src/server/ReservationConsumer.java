package server;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Queue;

public class ReservationConsumer implements Runnable {

	public static final int DELAY = 400;
	
	Queue<Pair<String[], PrintWriter>> in;
	
	public ReservationConsumer(Queue<Pair<String[], PrintWriter>> in) {
		this.in = in;
	}
	
	@Override
	public void run() {
		String sql = "";
		
		while (true) {
			if (in.peek() != null) {
				Pair<String[], PrintWriter> pair = in.remove();
				String[] arr = pair.getKey();
				String e_id = arr[0];
				String username = arr[1];
				String[] seats = arr[2].split(",");
				PrintWriter out = pair.getValue();
				Connection conn = null;
				try {
					Class.forName("org.sqlite.JDBC");
					conn = DriverManager.getConnection("jdbc:sqlite:final.db");
					
					// Check availabilities
					sql = "SELECT * FROM Seats WHERE event_id="+e_id+" AND (seat_id="+seats[0];
					for (int i = 1; i < seats.length; i++) sql += " OR seat_id="+seats[i];
					sql += ");";
					PreparedStatement stmt = conn.prepareStatement(sql);
					ResultSet rset = stmt.executeQuery();
					if (rset.next()) {
						out.println("failed");
						out.flush();
						continue;
					}
					
					// Reserve
					sql = "INSERT INTO Seats (event_id, buyer, seat_id) VALUES ("+e_id+", '"+username+"', ";
					for (String seat: seats) conn.prepareStatement(sql+seat+");").executeUpdate();
					out.println("succeed");
					out.flush();
					
					sql = "SELECT event_name, date, location FROM Events WHERE id="+e_id;
					stmt = conn.prepareStatement(sql);
					rset = stmt.executeQuery();
					String ename="", etime="", eloc="";
					if (rset.next()) {
						ename = rset.getObject(1).toString();
						etime = rset.getObject(2).toString();
						eloc = rset.getObject(3).toString();
					}
					// Send email
					String msg = "Dear user,\n\n" + 
								"Your reservation for event: " + ename + " is completed.\n" +
								"Your seat(s): " + seats[0];
					for (int i = 1; i < seats.length; i++) msg += ", "+seats[i];
					msg += "\nLooking forward to see you at " + eloc + " on " + etime;
					msg += "\n\nBest regards,\nSimple Ticketing System";
					SendMail.send(username, msg);
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
				e.printStackTrace();
			}
		}
		
	}

}
