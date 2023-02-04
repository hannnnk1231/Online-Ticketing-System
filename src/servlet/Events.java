package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.sql.*;
import java.util.ArrayList;


@WebServlet("/Events")
public class Events extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Events() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		utils.goEvents(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		String event_id = request.getParameter("id");
		String sql = "query\tSELECT seat_id FROM Seats WHERE event_id='"+event_id+"';";
		String msg = utils.doSQL(request, sql);
		String[] msg_part = msg.split(";");
		ArrayList<Integer> arr = new ArrayList<>();
		for (int i = 1; i < msg_part.length; i++) {
			arr.add(Integer.parseInt(msg_part[i].split("\t")[0]));
		}
		request.setAttribute("sold_seats", arr);
		request.setAttribute("event_id", event_id);
		request.getRequestDispatcher("seats.jsp").forward(request, response);
	}

}

