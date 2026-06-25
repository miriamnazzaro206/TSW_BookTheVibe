<%@ page import="model.UtenteBean,java.util.ArrayList" %>
<%
UtenteBean utenteNav = (UtenteBean) session.getAttribute("utenteLoggato");
boolean isAdminNav = utenteNav != null && "ADMIN".equalsIgnoreCase(utenteNav.getRuolo());
ArrayList<String> categorieNav = (ArrayList<String>) request.getAttribute("categorieNav");
ArrayList<String> cittaNav = (ArrayList<String>) request.getAttribute("cittaNav");
String ctx = request.getContextPath();
String accessToken = (String) session.getAttribute("accessToken");
%>
<script>window.BTV_ACCESS_TOKEN = "<%=accessToken == null ? "" : accessToken%>";</script>
<nav class="topbar">
	<a class="brand" href="<%=ctx%>/home">BookTheVibe</a>
	<div class="nav-left">
		<div class="dropdown">
			<button type="button">Categorie</button>
			<div class="dropdown-menu">
				<% if (categorieNav != null) for (String categoria : categorieNav) { %>
					<a href="<%=ctx%>/catalogo?categoria=<%=java.net.URLEncoder.encode(categoria, "UTF-8")%>"><%=categoria%></a>
				<% } %>
			</div>
		</div>
		<div class="dropdown">
			<button type="button">Citta'</button>
			<div class="dropdown-menu">
				<% if (cittaNav != null) for (String citta : cittaNav) { %>
					<a href="<%=ctx%>/catalogo?citta=<%=java.net.URLEncoder.encode(citta, "UTF-8")%>"><%=citta%></a>
				<% } %>
			</div>
		</div>
	</div>
	<div class="nav-right">
		<a href="<%=ctx%>/home">Home</a>
		<% if (utenteNav != null) { %>
			<a href="<%=ctx%>/prenotazioni">Le mie prenotazioni</a>
		<% } %>
		<% if (isAdminNav) { %>
			<a href="<%=ctx%>/admin/catalogo">Catalogo</a>
			<a href="<%=ctx%>/admin/ordini">Riepilogo Prenotazioni</a>
			<a href="<%=ctx%>/admin/coupon">Codici Sconto</a>
		<% } %>
		<% if (utenteNav != null) { %>
			<a class="icon-link" href="<%=ctx%>/profilo" aria-label="Profilo">&#128100;</a>
		<% } %>
		<a class="icon-link" href="<%=ctx%>/carrello" aria-label="Carrello">&#128722;</a>
		<% if (utenteNav == null) { %>
			<a class="icon-link" href="<%=ctx%>/LoginServlet" aria-label="Login">&#128100;</a>
		<% } else { %>
			<a href="<%=ctx%>/logout">Logout</a>
		<% } %>
	</div>
</nav>
