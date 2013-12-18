package Sympoze;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;



import java.util.Map;
//DB CONNECTION:
import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

//import java.util.logging.Level;
//import java.util.logging.Logger;


/**
 * Servlet implementation class Hello
 */
@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatServlet(){
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		getAllMessages(response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Entering Post");
		//convert string request.getParameter("lastTime") to timestamp		
		//System.out.println(nowTime.toString());
		try {
			String username=request.getParameter("username");
			String type=request.getParameter("type");
			
			if (request.getParameter("message")!=null){
				String message=request.getParameter("message");
				ChatLine chatline = new ChatLine(username, message);
				if (type.equals("sendMessage")){
					sendMessageToDB(response,chatline);
				}					
			}
			
			if (type.equals("getMessage"))
			{
				System.out.println("Last Time: "+request.getParameter("lastTime"));
				Timestamp nowTime;
				if (request.getParameter("lastTime")!=null)
				{
					nowTime = Timestamp.valueOf(request.getParameter("lastTime"));
					getLastCycleMessages(nowTime,response);//getRecentMessages
				}
				
			}		
	        
	        
	        //PrintWriter output = response.getWriter();      
	        //while (recentMessages.next())
	        //	output.println(recentMessages.toString());
		}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	protected void getAllMessages(HttpServletResponse response)
	{
		Connection con = null;
	    Statement st = null;
	    ResultSet rs = null;
	    
	    try {
	    	Class.forName(Constants4DB.driver).newInstance();
	        con = DriverManager.getConnection(Constants4DB.url, Constants4DB.user, Constants4DB.password);
	        st = con.createStatement();
	        String query = "SELECT * FROM simpoze.cht_chat";
	        rs = st.executeQuery(query);
	        
	        PrintWriter output = response.getWriter();
	        List<Map<String,String>> chat = convertResultSetToChatObj(rs);
	        
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        output.println(gson.toJson(chat));
	        
		} catch (SQLException ex) {
	        //Logger lgr = Logger.getLogger(Version.class.getName());
	        //lgr.log(Level.SEVERE, ex.getMessage(), ex);
	    	System.out.println(ex.toString());
	    } catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    finally{
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (st != null) {
	                st.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	
	        } catch (SQLException ex) {
	            //Logger lgr = Logger.getLogger(Version.class.getName());
	            //lgr.log(Level.WARNING, ex.getMessage(), ex);
	        } 
	    }//finally
	      
	}//getAllMessages
	

	protected void getLastCycleMessages(Timestamp time, HttpServletResponse response )
	{
		Connection con = null;
	    Statement st = null;
	    ResultSet rs = null;
	    
	    try {
	    	Class.forName(Constants4DB.driver).newInstance();
	        con = DriverManager.getConnection(Constants4DB.url, Constants4DB.user, Constants4DB.password);
	        st = con.createStatement();
	        String query = "SELECT * FROM simpoze.cht_chat WHERE insertDate > "+"'"+time.toString()+"'";
	        rs = st.executeQuery(query); // +time.toString());
	    
	        PrintWriter output = response.getWriter();
	        	        
	        List<Map<String,String>> chat = convertResultSetToChatObj(rs);
	        
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        output.println(gson.toJson(chat));
	        
	        //response
	        
	    } catch (SQLException ex) {
	        //Logger lgr = Logger.getLogger(Version.class.getName());
	        //lgr.log(Level.SEVERE, ex.getMessage(), ex);
	    	System.out.println(ex.toString());
	    } catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	    finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (st != null) {
	                st.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	
	        } catch (SQLException ex) {
	            //Logger lgr = Logger.getLogger(Version.class.getName());
	            //lgr.log(Level.WARNING, ex.getMessage(), ex);
	        } 
	    }
	    
	}
	
	private List<Map<String,String>> convertResultSetToChatObj(ResultSet rs) throws SQLException
	{
		List<Map<String,String>> chat =new LinkedList<Map<String,String>>();
        while (rs.next()){
        	Map<String,String> chatLine=new HashMap<String,String>();
        	chatLine.put("text", rs.getString("text"));
        	chatLine.put("username", rs.getString("username"));
        	chatLine.put("insertDate", rs.getString("insertDate"));	
        	//System.out.println(rs.getString("text"));
        	chat.add(chatLine);
        }
        return chat;
	}
	
	protected void sendMessageToDB(HttpServletResponse response,ChatLine chatMessage) throws InstantiationException, IllegalAccessException, ClassNotFoundException{ 
		System.out.println(chatMessage.toString());
		Connection con = null;
	    Statement st = null;
	    ResultSet rs = null;
	    try { 
    		Class.forName(Constants4DB.driver).newInstance();
	        con = DriverManager.getConnection(Constants4DB.url, Constants4DB.user, Constants4DB.password);
	        st = con.createStatement();
	        PreparedStatement ps = con.prepareStatement("INSERT INTO simpoze.cht_chat (username, text) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, chatMessage.getUsername());
	        ps.setString(2, chatMessage.getTextMsg());        
 
	        ps.executeUpdate();
	        //rs = st.execute("INSERT INTO simpoze.cht_chat (username, text, insertDate) VALUES (TBD,"+message+ new Timestamp((new java.util.Date()).getTime()) +")");	        
	        //rs.in
	        //output.println("<html><body>");
	        //while (rs.next()) {
	        //    output.println(rs.getString(1));
	        //}
	        //output.println("</body></html>");
	
	    } catch (SQLException ex) {
	        //Logger lgr = Logger.getLogger(Version.class.getName());
	        //lgr.log(Level.SEVERE, ex.getMessage(), ex);
	    	System.out.println(ex.toString());
	    }
	    finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (st != null) {
	                st.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	
	        } catch (SQLException ex) {
	            //Logger lgr = Logger.getLogger(Version.class.getName());
	            //lgr.log(Level.WARNING, ex.getMessage(), ex);
	        }
	    }
	}
}
