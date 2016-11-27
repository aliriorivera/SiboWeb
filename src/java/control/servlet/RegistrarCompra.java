/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control.servlet;

import DAO.BoletaDAO;
import DAO.EventoDAO;
import DAO.LocacionDAO;
import DAO.RegistroventaDAO;
import DAO.UsuarioDAO;
import control.Pedido;
import entidad.Boleta;
import entidad.Cliente;
import entidad.Evento;
import entidad.Registroventa;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author AnBoCa
 */
public class RegistrarCompra extends HttpServlet {
   
    private RegistroventaDAO registroventaDAO;
    private LocacionDAO locacionDAO;
    private BoletaDAO boletaDAO;
    private EventoDAO eventoDAO;
    private UsuarioDAO usuarioDAO;
    private Registroventa registroventa;
    private Boleta boleta;
    private Evento evento;

    @Override
    public void init() throws ServletException{
        super.init();
        registroventaDAO=new RegistroventaDAO();
        locacionDAO=new LocacionDAO();
        boletaDAO=new BoletaDAO();
        eventoDAO=new EventoDAO();
        usuarioDAO=new UsuarioDAO();
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
        PrintWriter out = response.getWriter();
        HttpSession session=request.getSession();
        //definir si es una compra o una reserva y dependiendo de lo que sea hacer el ingreso a la bd
        if (request.getParameter("submit").equals("comprar")){
            if (validarFormulario(request,"comprar")){
                Pedido pedido=(Pedido)session.getAttribute("pedido");
                Cliente cliente=(Cliente)session.getAttribute("usuario");
                registroventa=new Registroventa();
                registroventa.setCantidadBoletas(String.valueOf(pedido.getCantBoletas()));
                registroventa.setFkCliente(cliente);
                registroventa.setFecha(new Date());
                registroventa.setTotalVendido(pedido.getTotal());
                registroventa.setEvento(eventoDAO.findEvento(pedido.getEvento()).getNombre());
                registroventa.setFkUsuario(usuarioDAO.findUsuarioByNombre("web"));
                registroventaDAO.create(registroventa);
                //crear las boletas, toca obtener el id del registro de venta
                for (int i=0;i<pedido.getCantBoletas();i++){
                    boleta=new Boleta();
                    boleta.setEstado("Comprada");
                    boleta.setFechaEntrega(new Date());
                    boleta.setFkLocacion(locacionDAO.findLocacion(pedido.getLocacion()));
                    boleta.setFkRegistroventa(registroventa);
                    boletaDAO.create(boleta);
                }
                out.println("Se registro la compra con exito.");
                
            }
            else
                response.sendRedirect("error.html");
        }
        else{
            if (request.getParameter("submit").equals("reservar")){
                if (validarFormulario(request,"reservar")){
                    Pedido pedido=(Pedido)session.getAttribute("pedido");
                    Cliente cliente=(Cliente)session.getAttribute("usuario");
                    registroventa=new Registroventa();
                    registroventa.setCantidadBoletas(String.valueOf(pedido.getCantBoletas()));
                    registroventa.setFkCliente(cliente);
                    registroventa.setFecha(new Date());
                    registroventa.setTotalVendido(pedido.getTotal());
                    registroventa.setEvento(eventoDAO.findEvento(pedido.getEvento()).getNombre());
                    registroventa.setFkUsuario(usuarioDAO.findUsuarioByNombre("web"));
                    registroventaDAO.create(registroventa);
                    //crear las boletas, toca obtener el id del registro de venta
                    for (int i=0;i<pedido.getCantBoletas();i++){
                        boleta=new Boleta();
                        boleta.setEstado("Reservada");
                        boleta.setFechaEntrega(new Date());
                        boleta.setAbono(pedido.getPrecio()*25/100);
                        boleta.setFkLocacion(locacionDAO.findLocacion(pedido.getLocacion()));
                        boleta.setFkRegistroventa(registroventa);
                        boletaDAO.create(boleta);
                    }
                    out.println("La reserva se realizo con exito.");
                }
                else
                    response.sendRedirect("error.html");
            }
        }
        try {
            
        } finally { 
            out.close();
        }
    } 

    private boolean validarFormulario(HttpServletRequest request, String tipo){
        if (tipo.equals("comprar")){
            //mirar que tipo de pago es
            
        }
        else{
            if (tipo.equals("reservar")){

            }
        }
        return true;
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
        //processRequest(request, response);
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
