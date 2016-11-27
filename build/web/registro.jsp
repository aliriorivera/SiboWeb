<%-- 
    Document   : registro
    Created on : 22/10/2009, 10:48:50 PM
    Author     : AnBoCa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Registro</title>
<link rel="stylesheet" type="text/css" href="site.css" media="screen" />
</head>
<body>
<div id="container">
<%@include file="header.inc" %>
<div id="content">
    <h1 class="form">Registro de usuario</h1>
    <%
        String error=(String)session.getAttribute("tipoerror");
        if (error!=null){
            //Mostrar los comentarios sobre el tipo de error encontrado
            /*
            *   campvac: los campos son vacios
            *   incpas: las contraseñas son diferentes
            *   incmail: el correo ingresado no es valido
            *   mailexist: el correo ya esta registrado
            *   userexist: el usuario ya esta registrado
            *   incdoc: el documento contiene caracteres
            *   incphone: el telefono contiene caracteres
            */
            //out.println("prueba::::::"+error);
            if (error.contains("campvac"))
                out.println("<p class='error'>Los campos marcados con (*) son obligatorios.</p>");
            if (error.contains("incpas"))
                out.println("<p class='error'>Las contraseñas ingresadas no son iguales o contienen menos de 6 caracteres.</p>");
            if (error.contains("incmail"))
                out.println("<p class='error'>El correo ingresado no es valido o no es igual a la confirmación.</p>");
            if (error.contains("mailexist"))
                out.println("<p class='error'>El correo ingresado ya esta registrado.</p>");
            if (error.contains("userexist"))
                out.println("<p class='error'>El usuario ingresado ya esta registrado.</p>");
            if (error.contains("incdoc"))
                out.println("<p class='error'>El documento de identidad debe contener solamente números.</p>");
            if (error.contains("incphone"))
                out.println("<p class='error'>El telefono debe contener solamente números.</p>");
            session.removeAttribute("tipoerror");
        }
    %>
    <form action="registro.do" method="post" id="registro">
        <fieldset>
            <legend>Información de ingreso</legend><br/>
            <label for="user">Usuario *</label>
            <input type="text" name="user" id="user" value="<% if (request.getParameter("user")!=null)out.println(request.getParameter("user")); %>" /><br/>
            <label for="pass1">Contraseña *</label>
            <input type="password" name="pass1" id="pass1" maxlength="12"/><br/>
            <label for="pass2">Confirmar contraseña *</label>
            <input type="password" name="pass2" id="pass2" maxlength="12"/><br/>
            <label for="mail1">E-Mail *</label>
            <input type="text" name="mail1" id="mail1" value="<% if (request.getParameter("user")!=null)out.println(request.getParameter("mail1")); %>"/><br/>
            <label for="mail2">Confirmar E-Mail *</label>
            <input type="text" name="mail2" id="mail2" value="<% if (request.getParameter("user")!=null)out.println(request.getParameter("mail2")); %>"/><br/>
        </fieldset>
        <fieldset>
            <legend>Información personal</legend><br/>
            <label for="name">Nombres *</label>
            <input type="text" name="name" id="name" value="<% if (request.getParameter("user")!=null)out.println(request.getParameter("name")); %>"/><br/>
            <label for="lastname">Apellidos *</label>
            <input type="text" name="lastname" id="lastname" value="<% if (request.getParameter("user")!=null)out.println(request.getParameter("lastname")); %>"/><br/>
            <label for="doctype">Tipo documento *</label>
            <select name="doctype" id="doctype">
                <option <% if (request.getParameter("doctype")!=null && request.getParameter("doctype").equals("CC")) out.println("selected='selected'"); %> value="CC">Cédula de ciudadania</option>
                <option <% if (request.getParameter("doctype")!=null && request.getParameter("doctype").equals("TI")) out.println("selected='selected'"); %> value="TI">Tarjeta de Identidad</option>
                <option <% if (request.getParameter("doctype")!=null && request.getParameter("doctype").equals("LI")) out.println("selected='selected'"); %> value="LI">Libreta Militar</option>
            </select><br/>
            <label for="doc">Documento *</label>
            <input type="text" name="doc" id="doc" maxlength="12" value="<% if (request.getParameter("user")!=null)out.println(request.getParameter("doc")); %>"/><br/>
            <label for="city">Ciudad *</label>
            <input type="text" name="city" id="city" maxlength="30" value="<% if (request.getParameter("user")!=null)out.println(request.getParameter("city")); %>"/><br/>
            <label for="phone">Telefono *</label>
            <input type="text" name="phone" id="phone" maxlength="10" value="<% if (request.getParameter("user")!=null)out.println(request.getParameter("phone")); %>"/><br/>
            <label for="adress">Dirección</label>
            <input type="text" name="adress" id="adress" value="<% if (request.getParameter("user")!=null)out.println(request.getParameter("adress")); %>"/><br/><br/>
            <button name="registrar" value="Registrarme" type="submit">Registrar</button>
        </fieldset>
    </form>
</div>
<%@include file="footer.inc" %>
</div>
</body>
</html>
