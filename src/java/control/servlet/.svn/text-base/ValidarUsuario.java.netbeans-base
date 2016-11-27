/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control.servlet;

import DAO.ClienteDAO;
import DAO.exceptions.NonexistentEntityException;
import entidad.Cliente;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author AnBoCa
 */
public class ValidarUsuario extends HttpServlet {

    private ClienteDAO clienteDAO;
    private Cliente cliente;
    private String username;
    private String password;
    private String actcode;
   
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
        //PrintWriter out = response.getWriter();
        username=request.getParameter("user");
        password=request.getParameter("pass");
        actcode=request.getParameter("actcode");
        String error="";
        HttpSession session=request.getSession();
        if (session.getAttribute("loginerror")!=null)
                session.removeAttribute("loginerror");
        if (username.length()>0 && password.length()>0){
            //revisar si esta la opcion de activacion
            clienteDAO=new ClienteDAO();
            List lista=new ArrayList();
            lista=clienteDAO.readByUsername(username);
            if (lista.isEmpty())
                error+="userexist";//El usuario ingresado no existe, pero no se dice esto
            else{
                cliente=(Cliente)lista.get(0);
                if (cliente.getContrasena().equals(password) && cliente.getActivo()){
                    error+="correcto";//Ingreso correcto
                }
                else{
                    if (cliente.getContrasena().equals(password) && !cliente.getActivo() && session.getAttribute("login")!=null && session.getAttribute("login").equals("activar")){
                        //verificar el codigo y activar el usuario
                        if (!cliente.getActivo() && cliente.getCodigoActivacion().equals(actcode)){
                            try {
                                //actualizar el cliente
                                cliente.setActivo(true);
                                clienteDAO.edit(cliente);
                                error+="correcto";
                            } catch (NonexistentEntityException ex) {
                                Logger.getLogger(ValidarUsuario.class.getName()).log(Level.SEVERE, null, ex);
                                error+="bderror";
                            } catch (Exception ex) {
                                Logger.getLogger(ValidarUsuario.class.getName()).log(Level.SEVERE, null, ex);
                                error+="bderror";
                            }
                        }
                        else
                            error+="actcode";//el codigo de activacion es incorrecto
                    }
                    else{
                        if (!cliente.getContrasena().equals(password))
                            error+="incdat";
                        else
                            if (!cliente.getActivo())
                                error+="userinact";
                    }
                }
            }
        }
        else
            error+="campvac";
        session=request.getSession();
        if (error.equals("correcto")){
            if (session.getAttribute("loginerror")!=null)
                session.removeAttribute("loginerror");
            if (session.getAttribute("login")!=null)
                session.removeAttribute("login");
            session.setAttribute("usuario", cliente);
            if (session.getAttribute("pedido")!=null)
                response.sendRedirect(response.encodeRedirectURL("procesar.jsp"));
            else
                response.sendRedirect(response.encodeRedirectURL("usuario/cuenta.jsp"));
        }
        else{
            session.setAttribute("loginerror", error);
            ServletContext sc=getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        }
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
