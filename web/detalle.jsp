<%-- 
    Document   : detalle
    Created on : 6/11/2009, 03:54:20 PM
    Author     : UNAL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="control.DetalleEvento,java.util.List,java.util.ArrayList,entidad.Evento,entidad.Presentacion,entidad.Locacion,java.util.regex.*,java.text.SimpleDateFormat,java.text.DateFormat,control.Pedido" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Catalogo de eventos</title>
<link rel="stylesheet" type="text/css" href="site.css" media="screen" />
</head>
<body>
<div id="container">
<%@include file="header.inc" %>
<div id="content">
    <%
        //mirar si la peticion viene por el methodo post, si hay datos de un evento se crea un objeto pedido y se guarda
        //luego se redirecciona a procesar.jsp donde se piden los datos de pago
        if (request.getParameter("submit")!=null && request.getParameter("submit").equals("Procesar")){
            Pedido pedido=new Pedido();
            pedido.setEvento(Integer.parseInt(request.getParameter("eventoid")));
            pedido.setPresentacion(request.getParameter("fecha"));
            pedido.setLocacion(Integer.parseInt(request.getParameter("loc")));
            pedido.setUbicacion(request.getParameter("locname"));
            pedido.setCantBoletas(Integer.parseInt(request.getParameter("cant")));
            pedido.setTipoCompra(request.getParameter("tipo"));
            pedido.setPrecio(Double.parseDouble(request.getParameter("precio")));
            pedido.setMetodoPago(request.getParameter("pago"));
            pedido.calcularPrecio();
            session.setAttribute("pedido", pedido);
            response.sendRedirect("procesar.jsp");
        }
        //recoger el argumento y mostrar los datos de un evento, si no hay argumento redireccionar
        if (request.getParameter("evento")!=null){
            DetalleEvento detalle=new DetalleEvento();
            Evento evento=detalle.getEvento(request.getParameter("evento"));
            if (evento!=null){
                int i, j;
                Pattern patron;
                Matcher matcher;
                out.println("<div id='evento'>");
                out.println("<p>"+evento.getNombre()+"</p>");
                out.println("<p>"+evento.getArtista()+"</p>");
                out.println("<p>"+evento.getDescripcion()+"</p>");
                out.println("<p>"+evento.getFkOrganizador().getNombre()+"</p>");
                String dir="/contenido/";
                if (evento.getFkCategoria().getFkCategoria()==null)
                    dir+=evento.getNombre();
                else
                    dir+=evento.getFkCategoria().getFkCategoria().getNombre();
                dir+="/";
                if (evento.getImagen()!=null)//!evento.getImagen().isEmpty()
                    out.println("<img src='"+dir+evento.getImagen()+"' alt='evento'/>");
                out.println("</div>");
                //mostrar presentaciones
                List<Presentacion> presentacion=evento.getPresentacionList();
                if (!presentacion.isEmpty()){
                    out.println("<div id='form'>");
                    //mirar como hacer si hay varias presentaciones para cargar las locaciones de acuerdo a la presentacion seleccionada
                    int pres=0;
                    if (request.getParameter("pres")!=null){
                        patron=Pattern.compile("^\\d+");
                        matcher=patron.matcher(request.getParameter("pres"));
                        if (matcher.find()){
                            if ((matcher.end()-matcher.start())==request.getParameter("pres").length())
                                pres=Integer.parseInt(request.getParameter("pres"));
                        }
                    }
                    DateFormat formato=new SimpleDateFormat("E, d MMM yyyy");
                    if (presentacion.size()==1){
                        out.println("<p>Fecha: "+formato.format(presentacion.get(0).getFecha())+"</p>");
                    }
                    else{
                        out.println("<form action='detalle.jsp' method='get'><fieldset><input type='hidden' name='evento' value='"+evento.getId()+"'/><label for='pres'>Presentacion</label><select name='pres' id='pres' onchange='submit()'>");
                        for (i=0;i<presentacion.size();i++){
                            if (i==pres)
                                out.println("<option value='"+i+"' selected='selected'>"+formato.format(presentacion.get(i).getFecha())+"</option>");
                            else
                                out.println("<option value='"+i+"'>"+formato.format(presentacion.get(i).getFecha())+"</option>");
                        }
                        out.println("</select></fieldset></form>");
                    }
                    //mostrar las locaciones
                    List<Locacion> locacion;
                    
                    if (pres>=0 && pres<presentacion.size()){
                        //buscar la presentacion activa
                        String aux="";
                        int nboletas=0;
                        int loc=0;
                        if (request.getParameter("loc")!=null){
                            patron=Pattern.compile("^\\d+");
                            matcher=patron.matcher(request.getParameter("loc"));
                            if (matcher.find()){
                                if ((matcher.end()-matcher.start())==request.getParameter("loc").length())
                                    loc=Integer.parseInt(request.getParameter("loc"));
                            }
                        }
                        locacion=presentacion.get(pres).getLocacionList();
                        //revisar que las locaciones que se muestran tienen boletas disponibles
                        if (locacion!=null && !locacion.isEmpty()){
                            for (j=0;j<locacion.size();j++){
                                if ((locacion.get(j).getCupo()-locacion.get(j).getVendidas())>0){//si hay boletas disponibles muestra la locacion
                                    if (j==loc)
                                        aux+=("<option value='"+j+"' selected='selected'>"+locacion.get(j).getNombre()+" $"+locacion.get(j).getPrecio()+"</option>");
                                    else
                                        aux+=("<option value='"+j+"'>"+locacion.get(j).getNombre()+" $"+locacion.get(j).getPrecio()+"</option>");
                                }
                            }
                            if (aux.isEmpty())
                                out.println("<p>Boletas agotadas</p>");
                            else{
                                out.println("<form action='detalle.jsp' method='get'><fieldset><input type='hidden' name='evento' value='"+evento.getId()+"'/><input type='hidden' name='pres' value='"+pres+"'/>");
                                out.println("<label for='loc'>Ubicacion</label><select name='loc' id='loc' onchange='submit()'>");
                                out.println(aux);
                                out.println("</select></fieldset></form>");
                            }
                        }
                        if (!aux.isEmpty()){
                            formato=new SimpleDateFormat("dd-MMM-yyyy");
                            out.println("<form action='detalle.jsp' method='post'><fieldset><input type='hidden' name='precio' value='"+locacion.get(loc).getPrecio()+"'/><input type='hidden' name='eventoid' value='"+evento.getId()+"'/><input type='hidden' name='fecha' value='"+formato.format(presentacion.get(pres).getFecha())+"'/><input type='hidden' name='loc' value='"+locacion.get(loc).getId()+"'/><input type='hidden' name='locname' value='"+locacion.get(loc).getNombre()+"'/>");
                            //mostrar el numero maximo de boletas a comprar, si hay suficiente cupo se muestran las opciones de 1 a 5, si no se dejan solo las que queden
                            out.println("<label for='cant'>Cantidad de boletas</label><select name='cant' id='cant'>");
                            int bdispo=locacion.get(loc).getCupo()-locacion.get(loc).getVendidas();
                            if (bdispo>5)
                                bdispo=5;
                            for (i=1;i<=bdispo;i++)
                                out.println("<option value='"+i+"'>"+i+"</option>");
                            out.println("</select><br/>Tipo de compra:<br/><label for='tipo_comprar'>Comprar</label><input type='radio' name='tipo' id='tipo_comprar' value='comprar' checked='checked'/><br/><label for='tipo_reservar'>Reservar</label><input type='radio' name='tipo' id='tipo_reservar' value='reservar'/><br/>");
                            out.println("<br/>Método de pago:<br/><label for='pago_debito'>Débito</label><input type='radio' name='pago' id='pago_debito' value='debito' checked='checked'/><br/><label for='pago_credito'>Crédito</label><input type='radio' name='pago' id='pago_credito' value='credito'/><br/>");
                            out.println("<button name='submit' value='Procesar'>Comprar</button>");
                            out.println("</fieldset></form>");
                        }
                    }
                    out.println("</div>");
                }
            }
            else
                response.sendRedirect("eventos.jsp?cat=conciertos");
        }
        else
            if (request.getParameter("evento")==null && request.getParameter("submit")==null)
                response.sendRedirect("eventos.jsp?cat=conciertos");
    %>
</div>
<%@include file="footer.inc" %>
</div>
</body>
</html>
