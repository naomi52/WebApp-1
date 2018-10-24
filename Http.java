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
 * Servlet implementation class Http
 */
@WebServlet("/Http.do")
public class Http extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Http() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("countryname") == null || request.getParameter("countryoption") == null)
		  {
		     this.getServletContext().getRequestDispatcher("/Http.html").forward(request, response);
		  }
		  else
		  {
		     Brain model = new Brain();
		     try
		     {
		        
		        response.setContentType("text/html");
		        Writer out = response.getWriter();
		        String countryname = request.getParameter("countryname");
		        String countryquery = request.getParameter("countryoption");
		        //int myPrime = Integer.parseInt(userInput);
		        String capital= model.doHttp(countryname, countryquery );
		        
		        
		        String html = "<html><body>";
		        // This is what should be: 
		        html += "<p><a href='Dash.do'>Back to Dashboard</a></p>";
		        html += "<b>Country Data:</b></br><code>" + capital + "</code>";
		        html += "</body></html>";
		        out.write(html);
		     }
		     catch (Exception e)
		     {
		        response.setContentType("text/html");
		        Writer out = response.getWriter();
		        String html = "<html><body>";
		        html += "<p><a href=' Http.do'>Back to Dashboard</a></p>";
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
