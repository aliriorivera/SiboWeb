<%-- 
    Document   : procesar
    Created on : 1/12/2009, 07:06:29 PM
    Author     : AnBoCa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entidad.Evento,control.Pedido,control.DetalleEvento,java.text.DateFormat,java.text.SimpleDateFormat,java.util.Date" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Confirmación compra</title>
<link rel="stylesheet" type="text/css" href="/site.css" media="screen" />
</head>
<body>
<div id="container">
<%@include file="header.inc" %>
<div id="content">
    <%
        if (session.getAttribute("pedido")!=null){
            if (session.getAttribute("usuario")!=null){
                //mostrar los detalles y el formulario de pago
                //obtener los detalles del evento
                DateFormat formato=new SimpleDateFormat("dd-MMM-yyyy");
                DetalleEvento detalle=new DetalleEvento();
                Pedido pedido=(Pedido)session.getAttribute("pedido");
                Date fecha=formato.parse(pedido.getPresentacion());
                formato=new SimpleDateFormat("E, d MMM yyyy");
                Evento evento=detalle.getEvento(Integer.toString(((Pedido)session.getAttribute("pedido")).getEvento()));
                out.println("<div id='formpago'><h1>Resumen de la compra</h1><table class='resumen'><thead><tr><td>Evento</td><td>Fecha</td><td>Ubicacion</td><td>Cantidad</td><td>Precio</td></tr></thead>");
                out.println("<tfoot><tr><td>Total</td><td>"+pedido.getTotal()+"</td></tr></tfoot>");
                out.println("<tbody><tr><td>"+evento.getNombre()+"</td><td>"+formato.format(fecha)+"</td><td>"+pedido.getUbicacion()+"</td><td>"+pedido.getCantBoletas()+"</td><td>"+pedido.getPrecio()+"</td></tr></tbody></table>");
                //mirar el tipo de pago, si es debio o credito para poner el formulario respectivo
                out.println("<form action='procesar.do' method='post'><fieldset>");
                if (pedido.getMetodoPago().equals("debito")){
                    out.println("<label for='numtarjedeb'>Numero de tarjeta</label><input type='text' name='numtarjedeb' id='numtarjedeb'/>");
                }
                else{
                    if (pedido.getMetodoPago().equals("credito")){
                        out.println("<label for='tarjeta'>Tarjeta de credito</label><select name='tarjeta' id='tarjeta'><option value='american'>American Express</option><option value='master'>Master Card</option><option value='visa'>Visa</option></select>");
                        out.println("<br/><label for='numtarjecred'>Numero de tarjeta</label><input type='text' name='numtarjecred' id='numtarjecred'/>");
                        out.println("");
                    }
                }
                out.println("<br/><button name='submit' value='"+((pedido.getTipoCompra().equals("comprar"))? "comprar":"reservar")+"'>"+((pedido.getTipoCompra().equals("comprar"))? "comprar":"reservar")+"</button>");
                out.println("</fieldset></form></div>");
            }
            else
                response.sendRedirect("login.jsp");
        }
        else
            response.sendRedirect("eventos.jsp?cat=conciertos");
            
    %>
</div>
<%@include file="footer.inc" %>
</div>
</body>
</html>
