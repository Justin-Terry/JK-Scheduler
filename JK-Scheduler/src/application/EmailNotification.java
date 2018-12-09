/* Significant portions of this class provided
 * by Amazon as part of their Amazon Simple Email
 * Service. Used to ensure a the class would  work
 * with their API.
 */

package application;
import java.time.LocalDateTime;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class EmailNotification {
	private static int not_Id;
	static LocalDateTime timeToSend;

    // Replace sender@example.com with your "From" address.
    // This address must be verified.
    static final String FROM = "jkschedulerNotifier@gmail.com";
    static final String FROMNAME = "Scheduler Notifier";
	
    // Replace recipient@example.com with a "To" address. If your account 
    // is still in the sandbox, this address must be verified.
    static String to = "z400jt618@gmail.com";
    
    // Replace smtp_username with your Amazon SES SMTP user name.
    static final String SMTP_USERNAME = "AKIAIRMAF36RX4ZDKMFQ";
    
    // Replace smtp_password with your Amazon SES SMTP password.
    static final String SMTP_PASSWORD = "AhiX7ikgGf+dj9WAxp23W8YJvLxNUed67dXeZRK3yzal";
    
    // The name of the Configuration Set to use for this message.
    // If you comment out or remove this variable, you will also need to
    // comment out or remove the header below.
    //static final String CONFIGSET = "ConfigSet";
    
    // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
    // See https://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
    // for more information.
    static final String HOST = "email-smtp.us-west-2.amazonaws.com";
    
    // The port you will connect to on the Amazon SES SMTP endpoint. 
    static final int PORT = 587;
    
    
    static String subject = "";
    
    static String body = String.join(
    	    System.getProperty("line.separator"),
    	    "<h1>Amazon SES SMTP Email Test</h1>",
    	    "<p>This email was sent with Amazon SES using the ", 
    	    "<a href='https://github.com/javaee/javamail'>Javamail Package</a>",
    	    " for <a href='https://www.java.com'>Java</a>."
    	);

    public EmailNotification(String sub, String mes, String email, LocalDateTime time) {
    	subject = sub;
    	body = mes;
    	to = email;
    	timeToSend = time;
    	Main.getNotifications().put(this.timeToSend, this);
    }
    
    public String getSub() {
    	return subject;
    }
    
    public String getMes() {
    	return body;
    }
    
    public String getToEmail() {
    	return to;
    }
    
    public LocalDateTime getTime() {
    	return timeToSend;
    }
    
    public void setID(int n) {
    	not_Id = n;
    }
    
    public int getID() {
    	return not_Id;
    }
    
    
    public static boolean sendNotification() throws Exception {

        // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);
        msg.setContent(body,"text/html");
        
        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
        //msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
            
        // Create a transport.
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Sending...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
            return true;
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        	return false;
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }
}
