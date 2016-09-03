package servlets;



import POJO.ConnectToDatabase;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Class signup_request
 * @Version 1.0
 * @Date 1.0 : 01/09/2016 - created for rework
 *             02/09/2016 - added extra database methods that ensures the creation 
 *                          of database and table if it does not exists 
 *             03-09-0216 - added email function
 * @author Minju Park (13839910)
 * 
 * created for handling signup request from an audience. Handles checking inputs
 * from user. If they are valid the request will be saved into the database and
 * an email will be sent to notify admin about new audience wanting to be a player.
 */

public class signup_request extends HttpServlet {

    
    // for sending email
    private static String USER_NAME = "upstage.test2016";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "test2016"; // GMail password
    private static String RECIPIENT = "minjupk@hotmail.com";
    
    // for connecting to database
    private ConnectToDatabase con;
   
    
    // for confirmation page
    private String resultTitle = "";
    private String result = "";

    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, MessagingException {
        
        con = new ConnectToDatabase();

        String methodToImplement = request.getParameter("formName");
        
        System.out.println(methodToImplement);
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String introduction = request.getParameter("introduction");
        String reason = request.getParameter("reason");
        
        
        switch(methodToImplement) 
        {
            case "signup_request": 
                
                if(checkInputFields(username, password, email, introduction, reason)) {
                    if(con.insertToTable(username, password, email, introduction, reason)) {
                        System.out.println("Successfully saved signup request.");
                        request.setAttribute("resultTitle", "Thank you!");
                        request.setAttribute("result", "Once your account information has been approved "
                                + "by an UpStage adminstratorm, you will receive email notification.");
                        sendGmailAlert(username);
                    } else {
                        System.out.println("Unsucessful: inserting signup info to the table.");
                        request.setAttribute("resultTitle", "Sorry, something went wrong");
                        request.setAttribute("result", "There was a problem processing your "
                                + "account. Please try again later or contact the administrator of this UpStage server.");
                    }
                    
                } else {
                    System.out.println("Unsucessful: input fields are not valid.");
                    request.setAttribute("resultTitle", "Sorry, something went wrong");
                    request.setAttribute("result", "Please check if your username is "
                            + "available and your input fields are valid.");
                }
                break;
        }
        
        request.getRequestDispatcher("user_signup_confirmation.jsp").forward(request, response);
        
    }
    
    
    /****************** CHECK INPUT FIELDS ******************/
    
    private boolean checkInputFields(String username, String password, String email, String introduction, String reason) throws SQLException {
        
        boolean validInput = false;
        // check if input fields are not empty or null
        if((username != null && !username.isEmpty()) && (password != null && !password.isEmpty()) &&
                (email != null && !email.isEmpty()) && (introduction != null && !introduction.isEmpty()) && (reason != null && !reason.isEmpty())) {
            // check if it fits username & password pattern and introduction and reason more than 5 characters
            if(checkUsername(username) && checkPassword(password) && lengthOverFive(introduction) && lengthOverFive(reason)) {
                System.out.println("all the input fields are valid");
                return true;
            } else {
                System.out.println("Input fields are not valid");
                return false;
            }
            
            
        } else {
            
            return false;
        
        }
        
        
    }
    
    /** Get Today's date **/
    private String today() {
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println("todays date: " + dateFormat.format(date));
        return dateFormat.format(date);
        
    }
    
    private boolean lengthOverFive(String introduction) {
        
        if(introduction.length() > 5) {
            return true;
        } else {
            return false;
        }
        
        
    }
    
    private boolean checkUsername(String username) throws SQLException {
        
        String pattern ="^[a-z0-9]{3,15}$";
        
        // Create a Pattern object
        Pattern p = Pattern.compile(pattern);
        
        if(p.matcher(username).matches()){
            System.out.println("username pattern matches");
            if(con.checkUsernameAvailability(username)) {
                System.out.println("username is available");
                return true;
            } else {
                System.out.println("username is not available");
                return false;
            }
        } else {
            System.out.println("username pattern does not match");
            return false;
        }
    }
    
    private boolean checkPassword(String password) {
        String pattern ="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{6,30}$";
        
        // Create a Pattern object
        Pattern p = Pattern.compile(pattern);
        
        // Now create matcher object
        if(p.matcher(password).matches()){
            System.out.println("password pattern matches");
            return true;
        } else {
            System.out.println("password pattern does not match");
            return false;
        }
    }
    
     /***************** DATABASE FUNCTION ****************/
        // moved to ConnectToDatabase class 
   
    
    /******************* EMAIL FUNCTION ****************/
    
    private void sendGmailAlert(String username) throws MessagingException {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { RECIPIENT };
        String subject = "Audience wants to be a Player!";
        String body = "Hello admins, a player "+ username + " "
                + "wants to be a player. Please check and confirm the request. ";
        
        sendEmail(from, pass, to, subject, body);
        
    }
    
    
    private void sendEmail(String from, String pass, String[] to, String subject, String body) throws AddressException, MessagingException {
        
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
        

    
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(signup_request.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(signup_request.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(signup_request.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(signup_request.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(signup_request.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(signup_request.class.getName()).log(Level.SEVERE, null, ex);
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
