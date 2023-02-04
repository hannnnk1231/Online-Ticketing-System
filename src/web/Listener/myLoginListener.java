package web.Listener;

import java.util.HashSet;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class myLoginListener implements HttpSessionListener, HttpSessionAttributeListener {
	private static int tSessions;
	private static HashSet<String> users;
	
    public myLoginListener() {
    	users = new HashSet<String>();
    }
    
    public static boolean checkUser(String username) {
    	return users.contains(username);
    }
    
    public static int getSessions() {
    	return myLoginListener.tSessions;
    }

    public void attributeAdded(HttpSessionBindingEvent arg0)  {
    	if (arg0.getName().equals("myUsername")) {
    		myLoginListener.tSessions ++;
    		users.add((String) arg0.getValue());
    	}
    }

    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
    	if (arg0.getName().equals("myUsername")) {
    		myLoginListener.tSessions --;
    		users.remove(arg0.getValue());
    	}
    }

    public void attributeReplaced(HttpSessionBindingEvent arg0)  {
    }

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		myLoginListener.tSessions --;
		String username = (String) se.getSession().getAttribute("myUsername");
		users.remove(username);
		System.out.println("Session Destroyed, user: " + username);
	}
	
}
