/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control.servlet;

import DAO.ClienteDAO;
import control.SendMail;
import entidad.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author AnBoCa
 */
public class RegistrarUsuario extends HttpServlet {

    private Cliente cliente;
    private ClienteDAO clienteDAO;
    private String codigo;
    private String tipoerror;
    private SendMail mail;

    @Override
    public void init() throws ServletException{
        super.init();
        clienteDAO=new ClienteDAO();
    }

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        boolean res=false;
        //PrintWriter out = response.getWriter();//solo si se hace debug
        //Se valida la información ingresada
        //validar email, contraseña, campos obligatorios, existencia de usuario
        //Se crea un usuario con la información enviada por el formulario cuando esta validada
        //Array con el nombre de los campos
        tipoerror="";
        boolean error=validarFormulario(request);
        if (!error){
            //generar codigo de activacion
            codigo=crearCodigoActivacion();
            cliente=new Cliente();
            cliente.setActivo(false);
            cliente.setCodigoActivacion(codigo);
            cliente.setUsuario(request.getParameter("user"));
            cliente.setContrasena(request.getParameter("pass1"));
            cliente.setEMail(request.getParameter("mail1"));
            cliente.setNombres(request.getParameter("name"));
            cliente.setApellidos(request.getParameter("lastname"));
            cliente.setTipodocumento(request.getParameter("doctype"));
            cliente.setDocumento(request.getParameter("doc"));
            cliente.setCiudad(request.getParameter("city"));
            cliente.setTelefono(request.getParameter("phone"));
            cliente.setDireccion(request.getParameter("adress"));
            clienteDAO.create(cliente);
            //revisar esto por los nuevos daos
            /*if (!res)
                response.sendRedirect("error.html");//error de conexion pero no se le informa al usuario la causa
            else{*/
                //enviar correo con el codigo de activacion
            mail=new SendMail();
            mail.setTo(cliente.getEMail());
            mail.setMessage("Bienvenido a miboleta.com, el código de activación para el usuario "+cliente.getUsuario()+" es "+cliente.getCodigoActivacion()+" para activar la cuenta debe hacer clic en la dirección http://frankdc.selfip.com:8084/SIBOWeb/login.jsp?op=activar");
            mail.setDebug(true);
            try {
                mail.sendMail();
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(RegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(RegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("login.jsp");
            //}
        }
        else{
            //crear session para guardar los errores
            HttpSession session;
            session=request.getSession();
            session.setAttribute("tipoerror", tipoerror);
            ServletContext sc=getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/registro.jsp");
            rd.forward(request, response);
        }
    } 

    private boolean validarFormulario(HttpServletRequest request){
        //mirar si un campo es nulo
        boolean error=false;
        if (request.getParameter("user").length()==0 ||
            request.getParameter("pass1").length()==0 ||
            request.getParameter("pass2").length()==0 ||
            request.getParameter("mail1").length()==0 ||
            request.getParameter("mail2").length()==0 ||
            request.getParameter("name").length()==0 ||
            request.getParameter("lastname").length()==0 ||
            request.getParameter("doc").length()==0 ||
            request.getParameter("city").length()==0 ||
            request.getParameter("phone").length()==0
        ){
            error=true;//se notifica que todos son vacios
            tipoerror+="campvac";
        }
        //verificar si todos los campos son vacios, si lo son no se validan los campos
        if (!(request.getParameter("user").length()==0 &&
            request.getParameter("pass1").length()==0 &&
            request.getParameter("pass2").length()==0 &&
            request.getParameter("mail1").length()==0 &&
            request.getParameter("mail2").length()==0 &&
            request.getParameter("name").length()==0 &&
            request.getParameter("lastname").length()==0 &&
            request.getParameter("doc").length()==0 &&
            request.getParameter("city").length()==0 &&
            request.getParameter("phone").length()==0)
        ){
            //verificar que las contraseñas ingresadas sean iguales
            if (!request.getParameter("pass1").equals(request.getParameter("pass2")) || (request.getParameter("pass1").length()>0 && request.getParameter("pass1").length()<6) || request.getParameter("pass2").length()>12){
                error=true;
                tipoerror+="incpas";
            }
            //verificar que el correo ingresado sea valido, sea igual al de confirmacion y no este registrado
            if (!(request.getParameter("mail1").equals(request.getParameter("mail2")) && request.getParameter("mail1").length()>0 && validarEmail(request.getParameter("mail1"))))
            {
                error=true;
                tipoerror+="incmail";
            }
            //verificar existencia del correo en la bd
            if (request.getParameter("mail1").length()>0){
                if (comprobarEmail(request.getParameter("mail1"))){
                    error=true;
                    tipoerror+="mailexist";
                    if (tipoerror.contains("incmail"))
                        tipoerror=tipoerror.replace("incmail", "");
                }
            }
            //revisar que el usuario ingresado no exista
            if (request.getParameter("user").length()>0){
                if (comprobarUsuario(request.getParameter("user"))){
                    error=true;
                    tipoerror+="userexist";
                }
            }
            //revisar que los campos numericos contengan numeros
            if (request.getParameter("doc").length()>0 && !validarNumero(request.getParameter("doc"))){
                error=true;
                tipoerror+="incdoc";
            }
            if (request.getParameter("phone").length()>0 && !validarNumero(request.getParameter("phone"))){
                error=true;
                tipoerror+="incphone";
            }
        }
        return error;
    }

    private boolean validarEmail(String email){//validacion por expresion regular
        /*Pattern patron=Pattern.compile("\\w+@\\w+\\.\\w+");
        Matcher matcher=patron.matcher(email);
        if (matcher.find()){
            if ((matcher.end()-matcher.start())==email.length())
                return true;
            else
                return false;
        }
        return false;*/
        String local="", host="";
        String aux;
        Pattern patron;
        Matcher matcher;
        boolean error=true;
        if (email.length()>0){
            if (!email.contains("@"))
                error=false;
            else{
                local=email.substring(0, email.indexOf("@"));
                host=email.substring(email.indexOf("@")+1,email.length());
                //revisar si el correo tiene mas arrobas
                int i=0, j=0;
                patron=Pattern.compile("@");
                matcher=patron.matcher(local);
                while (matcher.find())
                    i++;
                matcher=patron.matcher(host);
                while (matcher.find())
                    j++;
                if (i>0 || j>0)
                    error=false;
                else{
                    patron=Pattern.compile("^[()['']\\;:,<>]+");
                    matcher=patron.matcher(local);
                    if (matcher.find())
                        if ((matcher.end()-matcher.start())>0)
                            error=false;
                    matcher=patron.matcher(host);
                    if (matcher.find())
                        if ((matcher.end()-matcher.start())>0)
                            error=false;
                    if (!error){
                        if (local.length()==0 || host.length()==0)
                            error=false;
                        else{
                            if (local.contains(".."))
                                error=false;
                            if (host.contains(".."))
                                error=false;
                            if (!error){//revisar que el ultimo caracter no sea un simbolo
                                aux="";
                                aux+=local.charAt(local.length()-1);
                                patron=Pattern.compile("\\W");
                                matcher=patron.matcher(aux);
                                if(matcher.find())
                                    error=false;
                                aux="";
                                aux+=host.charAt(host.length()-1);
                                matcher=patron.matcher(aux);
                                if(matcher.find())
                                    error=false;
                            }
                        }
                    }
                }
            }
        }
        else
            error=false;
        return error;
    }
    private boolean validarNumero(String numero){
        Pattern patron=Pattern.compile("\\d+");
        Matcher matcher=patron.matcher(numero);
        if (matcher.find())
            if ((matcher.end()-matcher.start())==numero.length())
                return true;
        return false;
    }
    private boolean comprobarUsuario(String user){
        List lista=new ArrayList();
        if (clienteDAO==null)
            clienteDAO=new ClienteDAO();
        lista=clienteDAO.readByUsername(user);
        if (lista.isEmpty())
            return false;//el usuario esta disponible
        return true;//el usuario esta registrado
    }
    private boolean comprobarEmail(String email){
        List lista=new ArrayList();
        if (clienteDAO==null)
            clienteDAO=new ClienteDAO();
        lista=clienteDAO.readByEmail(email);
        if (lista.isEmpty())
            return false;//el correo esta disponible
        return true;//el correo esta registrado
    }
    private String crearCodigoActivacion(){//crear un ramdon con letras y simbolos
        char simbolos[]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','#','@','1','2','3','4','5','6','7','8','9'};
        int i;
        codigo="";
        SecureRandom random=null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (random!=null){
            for (i=0;i<12;i++)
                codigo+=simbolos[random.nextInt(simbolos.length)];
        }
        return codigo;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}