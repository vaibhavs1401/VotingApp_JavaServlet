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
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Candidates</title>");
		out.println("</head>");
		out.println("<body>");
		String uname = "";
		Cookie arr[] = req.getCookies();
		if(arr != null && arr.length > 0) {
			for(Cookie c: arr) {
				if(c.getName().equals("uname"))
					uname = c.getValue();
			}
		}
		out.println("Hello, " + uname + "<br/>");
		out.println("<h3>Candidate List</h3>");
		out.println("<form method='post' action='vote'>");
		// <input type='radio' name='candidate' value='id?'/> candname?
		for(Candidate c:list)
			out.printf("<input type='radio' name='candidate' value='%d'/> %s - %s <br/>\n", c.getId(), c.getName(), c.getParty());
		out.println("<input type='submit' value='Vote'/>");
		out.println("</form>");		
		out.println("</body>");
		out.println("</html>");	
		
		
		
	}
}
