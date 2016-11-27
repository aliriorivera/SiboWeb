<%--
    Document   : index
    Created on : 19/10/2009, 06:32:33 PM
    Author     : AnBoCa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="control.ListarCategoria,java.util.List,java.util.ArrayList,entidad.Evento,entidad.Categoria" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Miboleta.com</title>
<link rel="stylesheet" type="text/css" href="site.css" media="screen" />
<link rel="icon"
      type="image/jpg"
      href="include/images/logo.jpg" />
</head>
<body>
<div id="container">
<%@include file="header.inc" %>
<div id="content">
    <div id="destacados">
        <h1>Eventos destacados</h1>
        <div id="conciertos">
            <h2>Conciertos</h2>
            <div class="evento">
                <h3>Titulo</h3>
                <img src="contenido/conciertos/pru.jpg" alt="imagen evento" width="330" height="90"/>
                <p>Descripción</p>
            </div>
        </div>
        <div id="teatro">
            <h2>Teatro</h2>
            <div class="evento">
                <h3>Titulo</h3>
                <img src="contenido/conciertos/pru.jpg" alt="imagen evento" width="330" height="90"/>
                <p>Descripción</p>
            </div>
        </div>
        <div id="deportes">
            <h2>Deportes</h2>
            <div class="evento">
                <h3>Titulo</h3>
                <img src="contenido/conciertos/pru.jpg" alt="imagen evento" width="330" height="90"/>
                <p>Descripción</p>
            </div>
        </div>
    </div>
    <h1 class="center">Categorias</h1>
    <div id="tags">
        <div id="cat">
            <h2><a href="eventos.jsp?cat=conciertos">Conciertos</a></h2>
            <h2><a href="eventos.jsp?cat=teatro">Teatro</a></h2>
            <h2><a href="eventos.jsp?cat=deportes">Deportes</a></h2>
        </div>
        <%
            //mostrar conciertos
            ListarCategoria liscat=new ListarCategoria();
            List<String> cat;
            liscat.setCat("conciertos");
            cat=liscat.getSubcatbyCat();
            if (!cat.isEmpty()){
                out.println("<ul class='conciertos'>");
                for (int i=0;i<cat.size();i++){
                    out.println("<li><a href='eventos.jsp?cat=conciertos&amp;sub="+cat.get(i)+"'>"+cat.get(i)+"</a></li>");
                }
                out.println("</ul>");
            }
            liscat.setCat("teatro");
            cat=liscat.getSubcatbyCat();
            if (!cat.isEmpty()){
                out.println("<ul class='teatro'>");
                for (int i=0;i<cat.size();i++){
                    out.println("<li><a href='eventos.jsp?cat=teatro&amp;sub="+cat.get(i)+"'>"+cat.get(i)+"</a></li>");
                }
                out.println("</ul>");
            }
            liscat.setCat("deportes");
            cat=liscat.getSubcatbyCat();
            if (!cat.isEmpty()){
                out.println("<ul class='deportes'>");
                for (int i=0;i<cat.size();i++){
                    out.println("<li><a href='eventos.jsp?cat=deportes&amp;sub="+cat.get(i)+"'>"+cat.get(i)+"</a></li>");
                }
                out.println("</ul>");
            }
        %>
    </div>
</div>
<%@include file="footer.inc" %>
</div>
</body>
</html>