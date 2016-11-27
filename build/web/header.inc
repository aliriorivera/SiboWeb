<%@page import="entidad.Cliente" %>
<div id="header">
	<div id="links">
		<a href="/index.jsp"><img src="/include/images/logo.jpg" alt="SIBO"/></a>
		<ul>
			<li><a href="/index.jsp" <% if (request.getRequestURI().contains("index.jsp") || request.getRequestURI().equals("/")) out.println("class='selected'"); %>>Inicio</a></li>
			<li><a href="/eventos.jsp?cat=conciertos" <% if (request.getParameter("cat")!=null && request.getParameter("cat").equals("conciertos")) out.println("class='selected'"); %>>Conciertos</a></li>
			<li><a href="/eventos.jsp?cat=teatro" <% if (request.getParameter("cat")!=null && request.getParameter("cat").equals("teatro")) out.println("class='selected'"); %>>Teatro</a></li>
			<li><a href="/eventos.jsp?cat=deportes" <% if (request.getParameter("cat")!=null && request.getParameter("cat").equals("deportes")) out.println("class='selected'"); %>>Deportes</a></li>
		</ul>
	</div>
	<div id="search">
            <div id="option">
            <%
                if (session.getAttribute("usuario")!=null) out.println("<a href='/usuario/cuenta.jsp'>"+((Cliente)session.getAttribute("usuario")).getNombres()+"</a> | <a href='/login.jsp?op=logout'>Cerrar session</a>");
                else
                    out.println("<a href='/login.jsp'>Mi cuenta</a> | <a href='/registro.jsp'>Registro</a>");
           %>
        </div>
        <div id="box">
            <form action="busqueda.jsp" method="post">
                    <fieldset>
                            <label for="busqueda">Buscar</label>
                            <input type="text" name="busqueda" id="busqueda" size="35" maxlength="35"/>
                    </fieldset>
            </form>
        </div>
	</div>
</div>