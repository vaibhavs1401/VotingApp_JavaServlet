package com.sunbeam.servlets;

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
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Result</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h3>Candidate List</h3>");
		out.println("<table border='1'>");
		out.println("<thead>");
		out.println("<tr>");
		out.printf("<th>Id</th>\n");
		out.printf("<th>Name</th>\n");
		out.printf("<th>Party</th>\n");
		out.printf("<th>Votes</th>\n");
		out.println("</tr>");
		out.println("</thead>");
		out.println("<tbody>");
		for(Candidate c:list) {
			out.println("<tr>");
			out.printf("<td>%d</td>\n", c.getId());
			out.printf("<td>%s</td>\n", c.getName());
			out.printf("<td>%s</td>\n", c.getParty());
			out.printf("<td>%d</td>\n", c.getVotes());
			out.println("</tr>");
		}
		out.println("</tbody>");
		out.println("</table>");		
		out.println("</body>");
		out.println("</html>");	
		
		
	}
	
	
}
