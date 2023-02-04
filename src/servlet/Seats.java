package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/Seats")
public class Seats extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Seats() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.getRequestDispatcher("events.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selected_seats = request.getParameter("selected_seats");
		String[] seats = selected_seats.split(",");
		String event_id = request.getParameter("event_id");
		String username = request.getSession().getAttribute("myUsername").toString();
		String sql = "reservation\t"+event_id+"\t"+username+"\t";
		for (int i = 0; i < seats.length; i++) sql += seats[i]+",";
		String msg = utils.doSQL(request, sql);
		if (msg.equals("succeed")) {
			request.setAttribute("msg", "Reserveation succeed! You can check your seats at 'My Reservations'");
		} else {
			request.setAttribute("msg", "Reserveation faild! The seats have been reserved by other user, please select other seats.");
		}
		utils.goEvents(request, response);
	}

}

