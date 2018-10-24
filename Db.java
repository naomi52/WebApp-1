package ctrl;

import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Brain;

/**
 * Servlet implementation class Db
 */
@WebServlet("/Db.do")
public class Db extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Db() {
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("dbcalc") == null)
		  {
		     this.getServletContext().getRequestDispatcher("/Db.html").forward(request, response);
		  }
		  else
		  {
		     Brain model = new Brain();
		     try
		     {
		        
		        response.setContentType("text/html");
		        Writer out = response.getWriter();
		        
		        
		        String userInput = request.getParameter("itemno");
		        
		        String item = model.doDb(userInput);
		        
		        
		        String html = "<html><body>";
		        // This is what should be: 
		        html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
		        //html += "<p><a href='Db.do'>Back to Dashboard</a></p>";
		        html += "<p>Item: " + item + "</p>";
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
