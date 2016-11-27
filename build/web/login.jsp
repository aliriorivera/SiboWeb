<%-- 
    Document   : login
    Created on : 22/10/2009, 05:45:04 PM
    Author     : AnBoCa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (request.getParameter("op")!=null && request.getParameter("op").equals("logout"))
        if (session.getAttribute("usuario")!=null){
            session.removeAttribute("usuario");
            response.sendRedirect("index.jsp");
        }
    if (request.getParameter("op")!=null && request.getParameter("op").equals("cancelar")){
        if (session.getAttribute("login")!=null)
            session.removeAttribute("login");
    }
    if (session.getAttribute("usuario")!=null)
        response.sendRedirect("index.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Inicio de sesión</title>
<link rel="stylesheet" type="text/css" href="site.css" media="screen" />
</head>
<body>
<div id="container">
<%@include file="header.inc" %>
<div id="content">
    <div id="login">
        <form action="login.do" method="post">
            <fieldset>
                <legend>Inicio de sesión</legend><br/>
                <label for="user">Usuario</label>
                <input type="text" name="user" id="user" <% if (session.getAttribute("loginerror")!=null) out.println("value='"+request.getParameter("user")+"'"); %> /><br/>
                <label for="pass">Contraseña</label>
                <input type="password" name="pass" id="pass"/><br/>
                <%
                    if ((request.getParameter("op")!=null && request.getParameter("op").equals("activar")) || session.getAttribute("login")!=null){
                        if (session.getAttribute("login")==null)
                            session.setAttribute("login", "activar");
                        out.println("<label for='actcode'>Codigo activación</label><input type='text' name='actcode' id='actcode' maxlength='12'/><br/>");
                    }
                    else
                        session.removeAttribute("login");
                %>
                <button name="ingresar" value="Iniciar" type="submit">Iniciar</button>
            </fieldset>
        </form>
    </div>
    <div id="logerror">
        <%
            //Recibir los datos de error en una session
            if (session.getAttribute("loginerror")!=null){
                if (session.getAttribute("loginerror").equals("userexist") || session.getAttribute("loginerror").equals("incdat"))
                    out.println("<p class='error'>Los datos ingresados no son validos.</p>");
                if (session.getAttribute("loginerror").equals("actcode"))
                    out.println("<p class='error'>El codigo de activación no es valido.</p>");
                if (session.getAttribute("loginerror").equals("campvac"))
                    out.println("<p class='error'>Debe ingresar un usuario y contraseña.</p>");
                if (session.getAttribute("loginerror").equals("bderror"))
                    out.println("<p class='error'>No se puede procesar la solicitud.</p>");
                if (session.getAttribute("loginerror").equals("userinact"))
                    out.println("<p class='error'>El usuario se ecuentra inactivo.</p>");
                session.removeAttribute("loginerror");
            }
        %>
        <p>No esta registrado? Hagalo <a href="registro.jsp">aquí</a>.</p>
        <% if (session.getAttribute("login")!=null && session.getAttribute("login").equals("activar")) out.println("<p><a href='login.jsp?op=cancelar'>No activar</a></p>"); %>
    </div>
</div>
    <%@include file="footer.inc" %>
</div>
</body>
</html>
