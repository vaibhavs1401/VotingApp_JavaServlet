package com.sunbeam.servlets;

import jakarta.servlet.http.Cookie;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.sunbeam.daos.*;
import com.sunbeam.entities.*;

@WebServlet("/result")
public class ResultServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		List<Candidate> list = null;
		try(CandidateDao candDao = new CandidateDaoImpl()){
			list = candDao.findAll();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Result</title>");
		out.println("<style>");
		out.println("body { font-family: sans-serif; background-color: #f0f0f0; padding: 20px; }");
		out.println(".container { background-color: white; padding: 20px; width: 600px; margin: 50px auto; border: 1px solid #ccc; }");
		out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
		out.println("th, td { padding: 8px; border: 1px solid #999; text-align: left; }");
		out.println("th { background-color: lightgray; }");
		out.println("a { margin-top: 20px; color: black; }");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class='container'>");


		String uname = "";
		Cookie[] arr = req.getCookies();
		if (arr != null && arr.length > 0) {
		    for (Cookie c : arr) {
		        if (c.getName().equals("uname"))
		            uname = c.getValue();
		    }
		}
		out.println("<p>Hello, " + uname + "</p>");

		out.println("<h3>Candidate List</h3>");
		out.println("<table>");
		out.println("<thead>");
		out.println("<tr>");
		out.println("<th>Id</th>");
		out.println("<th>Name</th>");
		out.println("<th>Party</th>");
		out.println("<th>Votes</th>");
		out.println("</tr>");
		out.println("</thead>");
		out.println("<tbody>");
		for (Candidate c : list) {
		    out.println("<tr>");
		    out.printf("<td>%d</td>", c.getId());
		    out.printf("<td>%s</td>", c.getName());
		    out.printf("<td>%s</td>", c.getParty());
		    out.printf("<td>%d</td>", c.getVotes());
		    out.println("</tr>");
		}
		out.println("</tbody>");
		out.println("</table>");

		out.println("<a href='logout'>Logout</a>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");

		
	}
	
	
}
