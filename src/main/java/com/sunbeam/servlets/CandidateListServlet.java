package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.server.ServerCloneException;
import java.util.List;

import com.sunbeam.entities.Candidate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;

@WebServlet("/candlist")
public class CandidateListServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		List<Candidate> list = null;
		
		try(CandidateDao canDao = new CandidateDaoImpl()){
			list = canDao.findAll();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException(e);
		}
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Candidates</title>");
		out.println("<style>");
		out.println("body { font-family: sans-serif; background-color: #f4f4f4; padding: 20px; }");
		out.println("form { background-color: white; padding: 20px; border: 1px solid #ccc; width: 300px; }");
		out.println("h3 { margin-bottom: 15px; }");
		out.println("input[type='submit'] { margin-top: 10px; padding: 6px; }");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");


		String uname = "";
		Cookie arr[] = req.getCookies();
		if (arr != null && arr.length > 0) {
		    for (Cookie c : arr) {
		        if (c.getName().equals("uname"))
		            uname = c.getValue();
		    }
		}
		out.println("<p>Hello, " + uname + "</p>");
		out.println("<h3>Candidate List</h3>");
		out.println("<form method='post' action='vote'>");

		for (Candidate c : list)
		    out.printf("<label><input type='radio' name='candidate' value='%d'/> %s - %s</label><br/>\n", c.getId(), c.getName(), c.getParty());

		out.println("<input type='submit' value='Vote'/>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	
		
		
		
	}
}
