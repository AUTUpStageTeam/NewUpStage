package servlets;


import POJO.ConnectToDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Class check_username_availability
 * @Version 1.0
 * @Date 1.0 : 02/09/2016 - created
 * @author Minju Park (13839910)
 * 
 * Created for handling jqueries checking username availability
 */
public class CheckUsernameAvailability extends HttpServlet {
    
    private ConnectToDatabase con;
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        String username = request.getParameter("user");
        
        System.out.println("Checking availability for " + username);
        
        try {
            con = new ConnectToDatabase();
            if (con.checkUsernameAvailability(username)) {
                username = "<span class = 'status-available'> Username is available </span>";
     
            } else {
                username = "<span class = 'status-not-available'> Username is not available.</span>";
            }
            
            
            response.setContentType("html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(username);
         
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CheckUsernameAvailability.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CheckUsernameAvailability.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
