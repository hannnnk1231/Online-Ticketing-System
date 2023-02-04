package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import web.Listener.myLoginListener;


public class utils {
	static String doSQL(HttpServletRequest request, String sql) {
		HttpSession session = request.getSession();
		BufferedReader in = (BufferedReader) session.getAttribute("in");
		PrintWriter out = (PrintWriter) session.getAttribute("out");
		out.println(sql);
		out.flush();
		
		String msg = null;
		try {
			while ((msg = in.readLine()) != null) {
				break;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return msg;
	}
	
	static void goEvents(HttpServletRequest request, HttpServletResponse response) {
		String sql = "query\tSELECT * FROM Events;";
		String msg = doSQL(request, sql);
		String[] msg_part = msg.split(";");
		JSONObject o = new JSONObject();
		JSONArray arr = new JSONArray();
		
		for (int i = 1; i < msg_part.length; i++) {
			JSONObject event = new JSONObject();
			String[] cols = msg_part[i].split("\t");
			event.put("id", cols[0]);
			event.put("name", cols[1]);
			event.put("date", cols[2]);
			event.put("loc", cols[3]);
			event.put("img", cols[4]);
			arr.put(event);
		}
			
		o.put("events", arr);

		HttpSession session = request.getSession();
		String username = session.getAttribute("myUsername").toString();
		request.setAttribute("myUsername",username);
		request.setAttribute("events", o);
		request.setAttribute("cont",myLoginListener.getSessions());
		
		try {
			request.getRequestDispatcher("events.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void goReservations(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String username = session.getAttribute("myUsername").toString();
		String sql = "query\tSELECT e.id, e.event_name, e.date, e.location, e.img_url, s.seat_id FROM Events e, Seats s WHERE e.id=s.event_id AND s.buyer='"+username+"' ORDER BY 1,6;";
		String msg = doSQL(request, sql);
		String[] msg_part = msg.split(";");
		JSONObject o = new JSONObject();
		JSONArray arr = new JSONArray();
		
		for (int i = 1; i < msg_part.length; i++) {
			JSONObject event = new JSONObject();
			String[] cols = msg_part[i].split("\t");
			event.put("id", cols[0]);
			event.put("name", cols[1]);
			event.put("date", cols[2]);
			event.put("loc", cols[3]);
			event.put("img", cols[4]);
			event.put("seat", cols[5]);
			arr.put(event);
		}
			
		o.put("reservations", arr);

		request.setAttribute("myUsername",username);
		request.setAttribute("reservations", o);
		request.setAttribute("cont",myLoginListener.getSessions());
		
		try {
			request.getRequestDispatcher("reservations.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
