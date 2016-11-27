<%-- 
    Document   : eventos
    Created on : 22/10/2009, 10:55:46 PM
    Author     : AnBoCa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="control.ListarCategoria,control.ListarEventos,java.util.List,java.util.ArrayList,entidad.Evento,entidad.Categoria,java.util.regex.*" %>
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
    <div id="left">
        <%
            //Revisar la categoria para mostrar subcategorias, si ya hay una subcategoria no se muestra nada
            if (request.getParameter("cat")!=null){
                //de acuerdo a la categoria mostrar las subcategorias
                ListarCategoria liscat=new ListarCategoria();
                List<String> cat;
                if (request.getParameter("cat").equals("conciertos")){
                    liscat.setCat("conciertos");
                    cat=liscat.getSubcatbyCat();
                    if (!cat.isEmpty()){
                        String sub=request.getParameter("sub");
                        out.println("<div id='subcat'><ul>");
                        for (int i=0;i<cat.size();i++){
                            if (sub!=null && cat.get(i).equals(sub))
                                out.println("<li><a href='eventos.jsp?cat=conciertos&amp;sub="+cat.get(i)+"' class='selected'>"+cat.get(i)+"</a></li>");
                            else
                                out.println("<li><a href='eventos.jsp?cat=conciertos&amp;sub="+cat.get(i)+"'>"+cat.get(i)+"</a></li>");
                        }
                        out.println("</ul></div>");
                    }
                }
                else{
                    if (request.getParameter("cat").equals("teatro")){
                        liscat.setCat("teatro");
                        cat=liscat.getSubcatbyCat();
                        if (!cat.isEmpty()){
                            String sub=request.getParameter("sub");
                            out.println("<div id='subcat'><ul>");
                            for (int i=0;i<cat.size();i++){
                                if (sub!=null && cat.get(i).equals(sub))
                                    out.println("<li><a href='eventos.jsp?cat=teatro&amp;sub="+cat.get(i)+"' class='selected'>"+cat.get(i)+"</a></li>");
                                else
                                    out.println("<li><a href='eventos.jsp?cat=teatro&amp;sub="+cat.get(i)+"'>"+cat.get(i)+"</a></li>");
                            }
                            out.println("</ul></div>");
                        }
                    }
                    else{
                        if (request.getParameter("cat").equals("deportes")){
                            liscat.setCat("deportes");
                            cat=liscat.getSubcatbyCat();
                            if (!cat.isEmpty()){
                                String sub=request.getParameter("sub");
                                out.println("<div id='subcat'><ul>");
                                //revisar si hay una categoria seleccionada para ponerle la clase selected
                                for (int i=0;i<cat.size();i++){
                                    if (sub!=null && cat.get(i).equals(sub))
                                        out.println("<li><a href='eventos.jsp?cat=deportes&amp;sub="+cat.get(i)+"' class='selected'>"+cat.get(i)+"</a></li>");
                                    else
                                        out.println("<li><a href='eventos.jsp?cat=deportes&amp;sub="+cat.get(i)+"'>"+cat.get(i)+"</a></li>");
                                }
                                out.println("</ul></div>");
                            }
                        }
                        else
                            response.sendRedirect("eventos.jsp?cat=conciertos");
                    }
                }
            }
            else
                response.sendRedirect("eventos.jsp?cat=conciertos");
        %>
    </div>
    <div id="result">
        <%
            String filtro=null;
            if (request.getParameter("sub")!=null)
                filtro=request.getParameter("sub");
            else{
                if (request.getParameter("cat")!=null)
                    filtro=request.getParameter("cat");
            }
            if (filtro!=null){
                ListarEventos eventos;
                if (session.getAttribute("eventos")==null){
                    eventos=new ListarEventos();
                    eventos.setCategoria(filtro);
                    session.setAttribute("eventos", eventos);
                }
                else{//revisar si cambio la categoria
                    eventos=(ListarEventos)session.getAttribute("eventos");
                    if (!eventos.getCategoria().equals(filtro)){
                        eventos.reset();
                        eventos.setCategoria(filtro);
                        session.setAttribute("eventos", eventos);
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
                    if (eventos.getNpages()>1){
                        out.println("<div id='paginfo'><p><strong>");
                        out.println("Mostrando resultados "+((eventos.getPageact()-1)*eventos.getNres()+1)+" a "+((eventos.getPageact()*eventos.getNres()>eventos.getNreg())? eventos.getNreg(): eventos.getPageact()*eventos.getNres())+" de "+eventos.getNreg());
                        out.println("</strong></p></div>");
                    }
                    String dir;
                    for (int i=0;i<res.size();i++){
                        out.println("<div class='evento'>");
                        if (res.get(i).getImagen()!=null && !res.get(i).getImagen().isEmpty()){//!res.get(i).getImagen().isEmpty()
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
                    if (request.getParameter("cat")!=null){
                        link+="cat=";
                        link+=request.getParameter("cat");
                        if (request.getParameter("sub")!=null){
                            link+="&amp;sub=";
                            link+=request.getParameter("sub");
                        }
                    }
                    //Mostrar navegación entre paginas
                    if (eventos.getNpages()>1){
                        out.println("<div id='paginador'>");
                        if (eventos.isPrev())
                            out.println("<a href='eventos.jsp?"+link+"&amp;prev'>Anterior</a> | ");
                        out.println("Página");
                        for (int i=1;i<=eventos.getNpages();i++){
                            if (i!=eventos.getPageact())
                                out.println("<a href='eventos.jsp?"+link+"&amp;page="+i+"'>"+i+"</a>");
                            else
                                out.println(i);
                        }
                        if (eventos.isNext())
                            out.println(" | <a href='eventos.jsp?"+link+"&amp;next'>Siguiente</a>");
                        out.println("</div>");
                    }
                }
                else
                    out.println("<h2 class='nevent'>No hay eventos registrados.</h2>");
            }
        %>
    </div>
</div>
<%@include file="footer.inc" %>
</div>
</body>
</html>
