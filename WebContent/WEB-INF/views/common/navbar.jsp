<%@ page import="model.UtenteBean,java.util.ArrayList" %>
<%
UtenteBean utenteNav = (UtenteBean) session.getAttribute("utenteLoggato");
boolean isAdminNav = utenteNav != null && "ADMIN".equalsIgnoreCase(utenteNav.getRuolo());
ArrayList<String> categorieNav = (ArrayList<String>) request.getAttribute("categorieNav");
ArrayList<String> cittaNav = (ArrayList<String>) request.getAttribute("cittaNav");
String ctx = request.getContextPath();
%>
<nav class="topbar">
	<a class="brand" href="<%=ctx%>/common/home">BookTheVibe</a>
	<div class="nav-left">
		<div class="dropdown">
			<button type="button">Categorie</button>
			<div class="dropdown-menu">
				<% if (categorieNav != null) for (String categoria : categorieNav) { %>
					<a href="<%=ctx%>/common/catalogo?categoria=<%=java.net.URLEncoder.encode(categoria, "UTF-8")%>"><%=categoria%></a>
				<% } %>
			</div>
		</div>
		<div class="dropdown">
			<button type="button">Citta'</button>
			<div class="dropdown-menu">
				<% if (cittaNav != null) for (String citta : cittaNav) { %>
					<a href="<%=ctx%>/common/catalogo?citta=<%=java.net.URLEncoder.encode(citta, "UTF-8")%>"><%=citta%></a>
				<% } %>
			</div>
		</div>
	</div>
	<div class="nav-right">
		<a href="<%=ctx%>/common/home">Home</a>
		<% if (utenteNav != null) { %>
			<a href="<%=ctx%>/common/prenotazioni">Le mie prenotazioni</a>
		<% } %>
		<% if (isAdminNav) { %>
			<a href="<%=ctx%>/admin/catalogo">Catalogo</a>
			<a href="<%=ctx%>/admin/ordini">Riepilogo Prenotazioni</a>
			<a href="<%=ctx%>/admin/coupon">Codici Sconto</a>
		<% } %>
		<% if (utenteNav != null) { %>
			<a class="icon-link" href="<%=ctx%>/common/profilo" aria-label="Profilo">
				<svg viewBox="0 0 24 24" aria-hidden="true">
					<circle cx="12" cy="8" r="4.2"></circle>
					<path d="M4.6 20.2c1.2-4 4-6.1 7.4-6.1s6.2 2.1 7.4 6.1"></path>
				</svg>
			</a>
		<% } %>
		<a class="icon-link" href="<%=ctx%>/common/carrello" aria-label="Carrello">
			<svg viewBox="0 0 24 24" aria-hidden="true">
				<path d="M6.3 8.2h15l-1.4 8.1a2.2 2.2 0 0 1-2.2 1.9H9a2.2 2.2 0 0 1-2.2-1.9L5 4H2.8"></path>
				<path d="M9.2 11.5h8.9"></path>
				<circle cx="9.6" cy="21" r="1.25"></circle>
				<circle cx="17.7" cy="21" r="1.25"></circle>
			</svg>
		</a>
		<% if (utenteNav == null) { %>
			<a class="icon-link" href="<%=ctx%>/common/login" aria-label="Login">
				<svg viewBox="0 0 24 24" aria-hidden="true">
					<circle cx="12" cy="8" r="4.2"></circle>
					<path d="M4.6 20.2c1.2-4 4-6.1 7.4-6.1s6.2 2.1 7.4 6.1"></path>
				</svg>
			</a>
		<% } else { %>
			<a href="<%=ctx%>/common/logout">Logout</a>
		<% } %>
	</div>
</nav>