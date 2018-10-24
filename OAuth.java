package ctrl;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Brain;

/**
 * Servlet implementation class OAuth
 */
@WebServlet("/OAuth.do")
public class OAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String myOutput;
	String back = "http://localhost:4413/ProjB/OAuth.do";
	String myHeader;
	String myUser;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OAuth() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if ((request.getParameter("calc") == null)&&(request.getParameter("user") == null))
		  {
		    this.getServletContext().getRequestDispatcher("/OAuth.html").forward(request, response);
		  }
		
		else if ((request.getParameter("calc") != null) &&(request.getParameter("user") == null))
		  {
			 // back = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
			  response.sendRedirect("https://www.eecs.yorku.ca/~roumani/servers/auth/oauth.cgi?"+"back=" +back );
			  //RequestDispatcher rs = request.getRequestDispatcher("OAuth.html");
		     try
		     {
		    	//response.sendRedirect("https://www.eecs.yorku.ca/~roumani/servers/auth/oauth.cgi?"+"back=" +back );
				myHeader = request.getHeader("Referer");
			    myOutput = myHeader.substring(myHeader.indexOf("?")+1);
			    
			    myUser= myHeader.substring(myHeader.indexOf("?")+6, myHeader.indexOf("?")+13);
			    System.out.println("calc value is " + request.getParameter("calc") + "\n");
		        System.out.println("user value is " + myUser + "\n" + request.getParameter("user") + "\n");
		        
		        response.setContentType("text/html");
		        Writer out = response.getWriter();
		        String html = "<html><body>";
		        html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
		        html += "<b>Authentication Result:</b>" + "<br>" + "<code> " +request.getQueryString()+ "</code>";
		        html += "</body></html>"; 
		        out.write(html);
		        

		        System.out.println(myOutput+"\n");
		        //System.out.println(back);
		     }
		     catch (Exception e)
		     {
		        response.setContentType("text/html");
		        Writer out = response.getWriter();
		        String html = "<html><body>";
		        html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
		        html += "<p>Error " + e.getMessage() + "</p>";
		        out.write(html);
		     }
		     
		  }
		
		else if (request.getParameter("user") != null) {	//(request.getParameter("user") != null)
			try
		     {
		        response.setContentType("text/html");
		        Writer out = response.getWriter();
		        String html = "<html><body>";
		        html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
		        html += "<b>Authentication Result:</b>" + "<br>" + "<code> " + request.getQueryString()+ "</code>";
		        html += "</body></html>"; 
		        out.write(html);
		        

		        System.out.println(myOutput + "\n");
		        //System.out.println(back);
		     }
		     catch (Exception e)
		     {
		        response.setContentType("text/html");
		        Writer out = response.getWriter();
		        String html = "<html><body>";
		        html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
		        html += "<p>Error " + e.getMessage() + "</p>";
		        out.write(html);
		     }
		     
	     }
		
		
		
		
		/*
		if((request.getParameter("calc") != null)){	//&& (request.getParameter("user") == null)
			try
		     {
		    	back = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
				response.sendRedirect("https://www.eecs.yorku.ca/~roumani/servers/auth/oauth.cgi?"+"back=" +back );
				myHeader = request.getHeader("Referer");
				myOutput = myHeader.substring(myHeader.indexOf("?")+1);
				System.out.println(myOutput + " first issue of output");
				
				System.out.println("calc " + request.getParameter("calc"));
				System.out.println("user " + request.getParameter("user") + "\n");
				
		        response.setContentType("text/html");
		        Writer out = response.getWriter();
		        String html = "<html><body>";
		        html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
		        html += "<b>Authentication Result:</b>" + "<br>" + "<code> " + myOutput + "</code>";
		        html += "</body></html>"; 
		        out.write(html);
		     }
		     catch (Exception e)
		     {
		        response.setContentType("text/html");
		        Writer out = response.getWriter();
		        String html = "<html><body>";
		        html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
		        html += "<p>Error " + e.getMessage() + "</p>";
		        out.write(html);
		     }
		}
		
		else if ((request.getParameter("user") != null))	//&& (request.getParameter("user") == null)
		{	
		    try
		     {
				System.out.println(myOutput + " second issue of output");
				
		        response.setContentType("text/html");
		        Writer out = response.getWriter();
		        String html = "<html><body>";
		        html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
		        html += "<b>Authentication Result:</b>" + "<br>" + "<code> " + myOutput + "</code>";
		        html += "</body></html>"; 
		        out.write(html);
		     }
		     catch (Exception e)
		     {
		        response.setContentType("text/html");
		        Writer out = response.getWriter();
		        String html = "<html><body>";
		        html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
		        html += "<p>Error " + e.getMessage() + "</p>";
		        out.write(html);
		     }
		}
		
		else
		  {
			this.getServletContext().getRequestDispatcher("/OAuth.html").forward(request, response);
		  }*/
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
