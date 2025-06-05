package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import com.sunbeam.daos.UserDao;
import com.sunbeam.daos.UserDaoImpl;
import com.sunbeam.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		doPost(req, resp);	
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String passwd = req.getParameter("passwd");
		try(UserDao userDao = new UserDaoImpl()){
			User dbUser = userDao.findByEmail(email);
			if(dbUser!=null && dbUser.getPassword().equals(passwd)) {
				Cookie c = new Cookie("uname", dbUser.getFirstName());
				c.setMaxAge(3600);
				resp.addCookie(c);
				//after login successful - store user obj in session
				HttpSession session = req.getSession();
				session.setAttribute("user", dbUser);
				
				if(dbUser.getRole().equals("admin")) {
					resp.sendRedirect("result");
					
				}else {
					resp.sendRedirect("candlist");
				}
				
			}else {
				resp.setContentType("text/html");
				PrintWriter out = resp.getWriter();
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Login Failed</title>");
				out.println("<style>");
				out.println("body { font-family: sans-serif; background-color: #f0f0f0; padding: 20px; }");
				out.println(".box { background-color: white; border: 1px solid #ccc; padding: 20px; width: 300px; margin: 100px auto; }");
				out.println("p { color: red; font-weight: bold; }");
				out.println("a { color: black; }");
				out.println("</style>");
				out.println("</head>");
				out.println("<body>");
				out.println("<div class='box'>");
				out.println("<h3>Login Failed</h3>");
				out.println("<p>Invalid email or password.</p>");
				out.println("<a href='index.html'>Login again</a>");
				out.println("</div>");
				out.println("</body>");
				out.println("</html>");

			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
	
}
