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
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Vote</title>");
		out.println("</head>");
		out.println("<body>");
		
		
		// Hello name logic
		String uname = "";
		Cookie[] arr = req.getCookies();
		if(arr != null && arr.length > 0) {
			for(Cookie c : arr) {
				if(c.getName().equals("uname"))
					uname = c.getValue();
			}
		}
		out.println("Hello, " + uname + "<br/>");
		
		// get candidate id from req param
				int candId = Integer.parseInt(req.getParameter("candidate"));
				// get current user from session
				HttpSession session = req.getSession();
				User user = (User) session.getAttribute("user");
				// check if user already voted
				if(user.getStatus() != 0) {
					// already voted
					out.println("Already voted.");
				}
				else {
					// vote to candidate
					// user status = 1
					try(UserDao userDao = new UserDaoImpl()) {
						user.setStatus(1);
						userDao.update(user);
					}
					catch (Exception e) {
						e.printStackTrace();
						throw new ServletException(e);
					}
					// increment vote count of candidate
					try(CandidateDao candDao = new CandidateDaoImpl()) {
						candDao.incrVote(candId);
					} catch (Exception e) {
						e.printStackTrace();
						throw new ServletException(e);
					}
					
					out.println("Your vote registered.");
				}
				
				out.println("<br/><br/><a href='logout'>Sign Out</a>");
				out.println("</body>");
				out.println("</html>");
		
		
		
		
	}
}
