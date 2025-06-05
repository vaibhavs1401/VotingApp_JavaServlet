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

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException , IOException {
		Cookie c = new Cookie("uname", "");
		c.setMaxAge(-1);
		resp.addCookie(c);
		
		HttpSession session = req.getSession();
		session.invalidate();
		
		resp.addCookie(c);
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Logout</title>");
		out.println("<style>");
		out.println("body { font-family: sans-serif; background-color: #f0f0f0; padding: 20px; }");
		out.println(".box { background-color: white; padding: 20px; width: 300px; margin: 100px auto; border: 1px solid #ccc; text-align: center; }");
		out.println("a { color: black; }");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class='box'>");
		out.println("<h3>You have been logged out.</h3>");
		out.println("<p>Thank you for visiting us.</p>");
		out.println("<a href='index.html'>Login again</a>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");

		
		
	}
}
