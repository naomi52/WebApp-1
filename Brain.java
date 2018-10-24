package model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Brain 
{ 
        public static final double BITS_PER_DIGIT = 3.0; 
        public static final Random RNG = new Random();   
        public static final String TCP_SERVER = "red.eecs.yorku.ca"; 
        public static final int TCP_PORT = 12345; 
        public static final String DB_URL = "jdbc:derby://red.eecs.yorku.ca:64413/EECS;user=student;password=secret"; 
        //public static final String DB_URL = "jdbc:derby://localhost:64413/EECS;user=student;password=secret";
        public static final String HTTP_URL = "https://www.eecs.yorku.ca/~roumani/servers/4413/f18/World.cgi"; 
        public static final String ROSTER_URL = "https://www.eecs.yorku.ca/~roumani/servers/4413/f18/Roster.cgi"; 
        
        private static Brain instance = null;
         
        public Brain() 
        { 
        	
        } 
        
        public synchronized static Brain getInstance() {
        	if(instance == null) instance = new Brain();
        	return instance;
        }
        
        public String doTime() 
        { 
        	Date date = new Date();
            String strDateFormat = "E MMM dd hh:mm:ss z yyyy";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate= dateFormat.format(date);
            return formattedDate;
        }
        
        public String doAddedTime(int added) 
        { 
        	Date date = new Date();
        	Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, added);
            Date newdate = calendar.getTime();
            
            //String strDateFormat = "hh:mm:ss a";
            String strDateFormat = "E MMM dd hh:mm:ss z yyyy";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate= dateFormat.format(newdate);
            return formattedDate;
    
        } 
      
        public BigInteger doPrime(String digitCount)
    	{
    		//final float DEC_BIN_RATIO = 3.33f;
    		int digToInt = Integer.parseInt(digitCount);
    		//int totalDigitCount = (int) (DEC_BIN_RATIO * digToInt);
    		int totalDigitCount = (int) (BITS_PER_DIGIT * digToInt);
    		//BigInteger intToBig = BigInteger.valueOf(totalDigitCount)		//new BigInteger(totalDigitCount);
    		BigInteger randomPrime = BigInteger.probablePrime(totalDigitCount, new Random());
    		//long methodReturnedPrime = randomPrime.longValue();
    		
    		return randomPrime;
    	}
        
        
        public String doTcp(String inDigit) throws UnknownHostException, IOException {
        try 
        {
        Socket client = new Socket(TCP_SERVER, TCP_PORT);
        //clients outputstream is talking to or giving the server input
        PrintStream out = new PrintStream(client.getOutputStream(), true);
        //input stream receives output from a server
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out.println("prime " + inDigit);
        String ans = in.readLine();
        in.close();
        out.close();
        client.close();
        return ans;
        
        }
        catch(IOException e){
        System.out.println(e.getMessage());
        }
        	return "hi";
        }
        
        
        
        public String doDb(String itemNo) throws Exception {
        Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        Connection con = DriverManager.getConnection(DB_URL);
        Statement s = con.createStatement();
        s.executeUpdate("set schema roumani");
        
        String query = "SELECT Name, Price FROM item WHERE Number = ?";
        PreparedStatement prepared = con.prepareStatement(query);
    	prepared.setString(1, itemNo);
        //String query = "SELECT Name, Price FROM ITEM WHERE number=" +itemNo;
        //SQL query to obtain the NAME and PRICE of an item whose number is itemNo in a table ITEM
        ResultSet r = prepared.executeQuery();
        String result = "";
        if (r.next())
        {
        	result = "$" + r.getDouble("PRICE") + " - " + r.getString("NAME");
        }
        else
        {
        	throw new Exception(itemNo + " not found!");
        }
        r.close(); s.close(); con.close();
        return result;
			//return itemNo;
        	
        }
        
        public String doHttp(String countryname ,String query) throws UnknownHostException, IOException {
            try 
            {
           	//URL myURL = new URL("https://www.eecs.yorku.ca/~roumani/servers/4413/f18/World.cgi?country="+countryname + "&query=" +query);
            	URL myURL = new URL( HTTP_URL +"?country=" +countryname + "&query=" +query);
            	URLConnection myURLConnection = myURL.openConnection();
            	((HttpURLConnection) myURLConnection).setRequestMethod("GET");            	
            	BufferedReader in = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
            	String inputLine;
            	StringBuffer myresponse = new StringBuffer();
            	while((inputLine = in.readLine())!= null)
            	{
            		myresponse.append(inputLine);
            	}
            	in.close();
            	
            
            return myresponse.toString();
            
            
            }
            catch(IOException e){
            System.out.println(e.getMessage());
            }
            	return "hi";
            }
        
       public String doRoster(String course) throws IOException, TransformerException, SAXException, ParserConfigurationException {
            try 
            {
            	StringBuilder result = new StringBuilder();
            	
            	URL myURL = new URL( ROSTER_URL + "?course=" +course);
            	URLConnection myCon = myURL.openConnection();
            	((java.net.HttpURLConnection) myCon).setRequestMethod("GET");
            	((java.net.HttpURLConnection) myCon).setRequestProperty("Accept", "application/xml");
            	
            	BufferedReader reader = new BufferedReader(new InputStreamReader(myCon.getInputStream()));
            	String line;
    			while ((line = reader.readLine()) != null) {
    				result.append(line);
    			}
    			
    			reader.close();
    		    
    		    
    		    org.w3c.dom.Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(result.toString())));
    		    
    		    StringBuffer htmlTableCode = new StringBuffer("<table border=\"1pt\">");
    		    htmlTableCode.append("<tr>"
    		    		+ "<td align=\"center\"><b>ID</b></td>"
    		    		+ "<td align=\"center\"><b>Last Name</b></td>"
    		    		+ "<td align=\"center\"><b>First Name</b></td>"
    		    		+ "<td align=\"center\"><b>City</b></td>"
    		    		+ "<td align=\"center\"><b>Program</b></td>"
    		    		+ "<td align=\"center\"><b>Hours</b></td>"
    		    		+ "<td align=\"center\"><b>GPA</b></td>"
    		    		+ "</tr>");
    		    NodeList nodes = doc.getChildNodes().item(0).getChildNodes();
    		    for (int i = 2 ; i < nodes.getLength() ; i++) {
    		    	NodeList students = nodes.item(i).getChildNodes();
    		    	htmlTableCode.append("<tr>");
    		    	for (int j = 0 ; j  < students.getLength() ; j++) {
    		    		htmlTableCode.append("<td>" + students.item(j).getTextContent() + "</td>");
    		    	}
    		    	htmlTableCode.append("</tr>");
    		    }
    		    htmlTableCode.append("</table>");
    		    return htmlTableCode.toString();
    		    //System.out.println(htmlTableCode.toString());
    			
    			
            }
            catch(IOException e){
            	return(e.getMessage());
            }
			
            	
            }
            
       
}

