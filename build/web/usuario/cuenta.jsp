<%--
    Document   : detalle
    Created on : 6/11/2009, 03:54:20 PM
    Author     : UNAL
--%>
<% if (session.getAttribute("usuario")==null) response.sendRedirect("/login.jsp"); %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entidad.Boleta,control.ListarBoletas,control.DetalleBoleta,control.CambiarContrasena,java.util.List,java.util.regex.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Zona usuario</title>
<link rel="stylesheet" type="text/css" href="../site.css" media="screen" />
</head>
<body>
<div id="container">
<%@include file="../header.inc" %>
<div id="content">
    <div id="left">
        <div id="subcat">
            <ul>
                <li><a href="cuenta.jsp?op=modcon">Contraseña</a></li>
                <li><a href="cuenta.jsp?op=cancelar">Cancelar reserva</a></li>
            </ul>
        </div>
    </div>
    <div id="result">
        <%
            //cambiar contraseña
            if (request.getParameter("cambiar")!=null && request.getParameter("cambiar").equals("cambiar")){
                //cambiar la contraseña
                if (request.getParameter("old")!=null && request.getParameter("new_1")!=null && request.getParameter("new_2")!=null){
                    if (request.getParameter("new_1").equals(request.getParameter("new_2"))){
                        //mirar que tengan datos
                        if (request.getParameter("old").length()>=6 && request.getParameter("old").length()<=12 && request.getParameter("new_1").length()>=6 && request.getParameter("new_1").length()<=12 && request.getParameter("new_2").length()>=6 && request.getParameter("new_1").length()<=12){
                            boolean res;
                            CambiarContrasena cambio=new CambiarContrasena();
                            res=cambio.cambiarContrasena((Cliente)session.getAttribute("usuario"), request.getParameter("old"),request.getParameter("new_1"));
                            if (res)
                                out.println("<h1>Se cambio la contraseña correctamente.</h1>");
                            else
                                out.println("<h1>No se pudo cambiar la contraseña.</h1>");
                        }
                        else
                            out.println("La contraseña debe tener minimo 6 y máximo 12 caracteres.");
                    }
                    else
                        out.println("Los campos contraseña nueva y confirmación deben ser iguales.");
                }
                else
                    out.println("Debe ingresar todos los campos.");
            }
            //caso cancelar reserva
            if (request.getParameter("op")!=null && request.getParameter("op").equals("creserva")){
                //verificar el id de la boleta
                int id=-1;
                Pattern patron=Pattern.compile("\\d+");
                Matcher matcher=patron.matcher(request.getParameter("id"));
                if (matcher.find()){
                    if ((matcher.end()-matcher.start())==request.getParameter("id").length()){
                        id=Integer.parseInt(request.getParameter("id"));
                    }
                }
                //obtener el id de la boleta y cargarla
                if (id>0){
                    //cargar boleta
                    DetalleBoleta detalle=new DetalleBoleta();
                    Boleta boleta=detalle.getBoletaById(id);
                    if (boleta!=null){
                        out.println("<div><h1>Cancelacion de reserva</h1><p>Para cancelar la reserva se devuelve el 10% del valor abonado</p>");
                        out.println("<p>Valor devolución: "+boleta.getAbono()*10/100+"</p>");
                        out.println("<form action='cuenta.jsp' method='post'><fieldset>");
                        out.println("<input type='hidden' name='rcreserva'/>");
                        out.println("<label for='acepto_si'>Acepto</label><input type='radio' name='acepto' id='acepto_si'/>");
                        out.println("<label for='acepto_no'>No acepto</label><input type='radio' name='acepto' id='acepto_no'/>");
                        out.println("<br/><button name='cancelar' value='cancelar' type='submit'>Cancelar reserva</button>");
                        out.println("</fieldset></form></div>");
                    }
                }
            }
            //codigo para el listado de boletas
            if (request.getParameter("op")!=null && request.getParameter("op").equals("cancelar")){
                ListarBoletas lista;
                if (session.getAttribute("cancelar")==null){
                    lista=new ListarBoletas();
                    lista.setCliente(((Cliente)session.getAttribute("usuario")).getId());
                    session.setAttribute("cancelar", lista);
                }
                else{//revisar si cambio la categoria
                    lista=(ListarBoletas)session.getAttribute("cancelar");
                }
                List<Boleta> res;
                if (request.getParameter("page")!=null){
                    Pattern patron=Pattern.compile("\\d+");
                    Matcher matcher=patron.matcher(request.getParameter("page"));
                    if (matcher.find()){
                        if ((matcher.end()-matcher.start())==request.getParameter("page").length()){
                            if (Integer.parseInt(request.getParameter("page"))>0 && Integer.parseInt(request.getParameter("page"))<=lista.getNpages())
                                lista.setPageact(Integer.parseInt(request.getParameter("page")));
                        }
                    }
                }else{
                    if (request.getParameter("next")!=null){
                        lista.next();
                    }
                    else{
                        if (request.getParameter("prev")!=null){
                            lista.prev();
                        }
                    }
                }
                res=lista.cargar();
                if (!res.isEmpty()){
                    if (lista.getNpages()>1){
                        out.println("<div id='paginfo'><p><strong>");
                        out.println("Mostrando resultados "+((lista.getPageact()-1)*lista.getNres()+1)+" a "+((lista.getPageact()*lista.getNres()>lista.getNreg())? lista.getNreg(): lista.getPageact()*lista.getNres())+" de "+lista.getNreg());
                        out.println("</strong></p></div>");
                    }
                    int i;
                    for (i=0;i<res.size();i++){
                        out.println("<div class='evento'><h2>"+res.get(i).getFkLocacion().getFkPresentacion().getFkEvento().getNombre()+"</h2>");
                        out.println("<p>Ubicacion: "+res.get(i).getFkLocacion().getNombre()+"</p>");
                        out.println("<p>Fecha: "+res.get(i).getFkLocacion().getFkPresentacion().getFecha()+"</p>");
                        out.println("<p><a href='cuenta.jsp?op=creserva&amp;id="+res.get(i).getId()+"'>Cancelar reserva</a></p>");
                        out.println("</div>");
                    }
                    if (lista.getNpages()>1){
                        out.println("<div id='paginador'>");
                        if (lista.isPrev())
                            out.println("<a href='cuenta.jsp?op=cancelar&amp;prev'>Anterior</a> | ");
                        out.println("Página");
                        for (i=1;i<=lista.getNpages();i++){
                            if (i!=lista.getPageact())
                                out.println("<a href='cuenta.jsp?op=cancelar&amp;page="+i+"'>"+i+"</a>");
                            else
                                out.println(i);
                        }
                        if (lista.isNext())
                            out.println(" | <a href='cuenta.jsp?op=cancelar&amp;next'>Siguiente</a>");
                        out.println("</div>");
                    }
                }
                else
                    out.println("<h2 class='nevent'>No hay reservas registradas.</h2>");
            }
            //codigo para el cambio de contraseña
            if (request.getParameter("op")!=null && request.getParameter("op").equals("modcon")){
                //mostrar formulario de cambio de contraseña
                out.println("<div id='registro'><h1>Cambiar contraseña</h1><form action='cuenta.jsp' method='post'><fieldset>");
                out.println("<label for='old'>Contraseña actual</label><input type='password' name='old' id='old'/>");
                out.println("<br/><label for='new_1'>Contraseña nueva</label><input type='password' name='new_1' id='new_1'/>");
                out.println("<br/><label for='new_2'>Confirmación contraseña</label><input type='password' name='new_2' id='new_2'/>");
                out.println("<br/><button name='cambiar' value='cambiar' type='submit'>Cambiar</button>");
                out.println("</fieldset></form></div>");
            }
        %>
    </div>
</div>
<%@include file="../footer.inc" %>
</div>
</body>
</html>