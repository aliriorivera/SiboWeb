/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import java.util.Properties;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author AnBoCa
 */
public class SendMail {
    private String host;
    private String user;
    private String pass;
    private String message;
    private String from;
    private String to;
    private boolean auth;
    private boolean debug;
    private Properties prop;
    private Session mailSession;
    private Transport trans;
    private MimeMessage mimeMessage;

    public SendMail(){
        this.host="smtp.gmail.com";
        this.user="miboleta.sibo";
        this.pass="@#d25aca#f";
        this.auth=true;
        this.from="miboleta.sibo@gmail.com";
        prop=new Properties();
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.host", this.host);
        prop.setProperty("mail.smtp.starttls.enable","true");
        prop.setProperty("mail.smtp.socketFactory.port", "465");
        this.debug=false;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the auth
     */
    public boolean isAuth() {
        return auth;
    }

    /**
     * @param auth the auth to set
     */
    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    /**
     * @return the debug
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * @param debug the debug to set
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    public boolean sendMail() throws NoSuchProviderException, MessagingException{
        if (this.from.length()>0 && this.to.length()>0){
            mailSession = Session.getDefaultInstance(prop,null);
            if (debug)
                mailSession.setDebug(true);
            trans = mailSession.getTransport();
            mimeMessage = new MimeMessage(mailSession);
            mimeMessage.setSubject("Activación de usuario miboleta.com");
            mimeMessage.setFrom(new InternetAddress(this.from));
            mimeMessage.addRecipients(RecipientType.TO, this.to);
            mimeMessage.setText(this.message);
            trans.connect(this.host, this.user, this.pass);
            if (trans.isConnected())
                trans.sendMessage(mimeMessage, mimeMessage.getRecipients(RecipientType.TO));
            else
                return false;
            trans.close();
        }
        return true;
    }
}
