package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import web.Listener.myLoginListener;

import java.sql.*;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("in") == null) {
			try {
					Socket socket = new Socket("localhost", 9898);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter out = new PrintWriter(socket.getOutputStream());
					session.setAttribute("in", in);
					session.setAttribute("out", out);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String sql = "query\tSELECT password FROM Accounts WHERE username='"+username+"';";
		String msg = utils.doSQL(request, sql);
		String[] msg_part = msg.split(";");
		if (msg_part.length > 1) {
			String secretPassword = msg_part[1].split("\t")[0];
			if (secretPassword.equals(password)) {
				if (!myLoginListener.checkUser(username)) {
					session.setAttribute("myUsername",username);
					utils.goEvents(request, response);
				} else {
					request.setAttribute("error", "User already logged-in");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("error", "User not found or wrong password.");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("error", "User not found or wrong password.") ;
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}

