package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.sunbeam.daos.UserDao;
import com.sunbeam.daos.UserDaoImpl;
import com.sunbeam.entities.User;
import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;



@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		doPost(req, resp);
	}
	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Vote</title>");
		out.println("<style>");
		out.println("body { font-family: sans-serif; background-color: #f0f0f0; padding: 20px; }");
		out.println(".box { background-color: white; border: 1px solid #ccc; padding: 20px; width: 400px; margin: 80px auto; }");
		out.println("a { color: black; }");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class='box'>");

		// Hello name logic
		String uname = "";
		Cookie[] arr = req.getCookies();
		if (arr != null && arr.length > 0) {
		    for (Cookie c : arr) {
		        if (c.getName().equals("uname"))
		            uname = c.getValue();
		    }
		}
		out.println("<p>Hello, " + uname + "</p>");

		// get candidate ID and user from session
		int candId = Integer.parseInt(req.getParameter("candidate"));
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		if (user.getStatus() != 0) {
		    out.println("<p>You have already voted.</p>");
		} else {
		    try (UserDao userDao = new UserDaoImpl()) {
		        user.setStatus(1);
		        userDao.update(user);
		    } catch (Exception e) {
		        e.printStackTrace();
		        throw new ServletException(e);
		    }

		    try (CandidateDao candDao = new CandidateDaoImpl()) {
		        candDao.incrVote(candId);
		    } catch (Exception e) {
		        e.printStackTrace();
		        throw new ServletException(e);
		    }

		    out.println("<p>Your vote has been registered.</p>");
		}

		out.println("<br/><a href='logout'>Sign Out</a>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");

		
		
	}
}
