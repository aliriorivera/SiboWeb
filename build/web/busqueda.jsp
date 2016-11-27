<%-- 
    Document   : busqueda
    Created on : 22/11/2009, 05:53:13 PM
    Author     : AnBoCa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="control.BuscarEvento,java.util.List,entidad.Evento,java.util.regex.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Busqueda de eventos</title>
<link rel="stylesheet" type="text/css" href="../site.css" media="screen" />
</head>
<body>
<div id="container">
<%@include file="header.inc" %>
<div id="content">
    <%
        if (request.getParameter("busqueda")!=null){
            //Cargar eventos
            BuscarEvento eventos;
            if (session.getAttribute("busqueda")==null){
                eventos=new BuscarEvento();
                eventos.setBusqueda(request.getParameter("busqueda"));
                session.setAttribute("busqueda", eventos);
            }
            else{ //mirar si cambio la condicion de busqueda
                eventos=(BuscarEvento)session.getAttribute("busqueda");
                if (!eventos.getBusqueda().equals(request.getParameter("busqueda"))){
                    eventos.reset();
                    eventos.setBusqueda(request.getParameter("busqueda"));
                    session.setAttribute("busqueda", eventos);
                }
            }
            List<Evento> res;
            if (request.getParameter("page")!=null){
                Pattern patron=Pattern.compile("\\d+");
                Matcher matcher=patron.matcher(request.getParameter("page"));
                if (matcher.find()){
                    if ((matcher.end()-matcher.start())==request.getParameter("page").length()){
                        if (Integer.parseInt(request.getParameter("page"))>0 && Integer.parseInt(request.getParameter("page"))<=eventos.getNpages())
                            eventos.setPageact(Integer.parseInt(request.getParameter("page")));
                    }
                }
            }else{
                if (request.getParameter("next")!=null){
                    eventos.next();
                }
                else{
                    if (request.getParameter("prev")!=null){
                        eventos.prev();
                    }
                }
            }
            res=eventos.cargar();
            if (!res.isEmpty()){
                out.println("<div id='paginfo'><p>");
                if (eventos.getNpages()>1){
                    out.println("página "+eventos.getPageact()+" de "+eventos.getNpages());
                }
                out.println("</p></div>");
                String dir;
                for (int i=0;i<res.size();i++){
                    out.println("<div class='evento'>");
                    if (!res.get(i).getImagen().isEmpty()){
                        dir="/contenido/";
                        if (res.get(i).getFkCategoria().getFkCategoria()==null)
                            dir+=res.get(i).getNombre();
                        else
                            dir+=res.get(i).getFkCategoria().getFkCategoria().getNombre();
                        dir+="/";
                        out.println("<a href='detalle.jsp?evento="+res.get(i).getId()+"'><img src='"+dir+res.get(i).getImagen()+"' alt='imagen evento'/></a>");
                    }
                    out.println("<h2>"+res.get(i).getNombre()+"</h2>");
                    if (res.get(i).getDescripcion()!=null)
                        out.println("<p>"+res.get(i).getDescripcion()+"</p>");
                    out.println("<p>Organizador: "+res.get(i).getFkOrganizador().getNombre()+"</p>");
                    out.println("<p>Lugar: "+res.get(i).getFkEscenario().getNombre()+"</p>");
                    //esconder el id bajo una funcion que oculte los verdaderos id del evento
                    out.println("<p><a href='detalle.jsp?evento="+res.get(i).getId()+"'>Más información</a></p></div>");
                }
                String link="";
                if (request.getParameter("busqueda")!=null){
                    link+="busqueda=";
                    link+=request.getParameter("busqueda");
                }
                //Mostrar navegación entre paginas
                if (eventos.getNpages()>1){
                    out.println("<div id='pag'>");
                    if (eventos.isPrev())
                        out.println("<a href='busqueda.jsp?"+link+"&amp;prev'>Anterior</a>");
                    for (int i=1;i<=eventos.getNpages();i++){
                        if (i!=eventos.getPageact())
                            out.println("<a href='busqueda.jsp?"+link+"&amp;page="+i+"'>"+i+"</a>");
                        else
                            out.println(i);
                    }
                    if (eventos.isNext())
                        out.println("<a href='busqueda.jsp?"+link+"&amp;next'>Siguiente</a>");
                    out.println("</div>");
                }
            }
            else
                out.println("<h2>No se encontraron resultados.</h2>");
        }
        else
            response.sendRedirect("eventos.jsp?cat=conciertos");
    %>
</div>
<%@include file="footer.inc" %>
</div>
</body>
</html>
