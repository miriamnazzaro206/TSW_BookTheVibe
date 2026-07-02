<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,model.AttivitaBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Catalogo - BookTheVibe</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/main.css">
<script defer src="<%=request.getContextPath()%>/scripts/main.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/common/navbar.jsp" %>
<main class="section">
	<h1><%=request.getAttribute("filtro")%></h1>
	<section class="catalog-toolbar" id="catalogToolbar" data-url="<%=request.getContextPath()%>/common/catalogo/filtra" data-context="<%=request.getContextPath()%>">
		<label>Categoria
			<select id="catalogCategory">
				<option value="">Tutte</option>
				<% if (categorieNav != null) for (String categoria : categorieNav) { %>
				<option value="<%=categoria%>"><%=categoria%></option>
				<% } %>
			</select>
		</label>
		<label>Citta'
			<select id="catalogCity">
				<option value="">Tutte</option>
				<% if (cittaNav != null) for (String citta : cittaNav) { %>
				<option value="<%=citta%>"><%=citta%></option>
				<% } %>
			</select>
		</label>
		<p class="muted" id="catalogCount"></p>
	</section>
	<div class="grid" id="catalogGrid">
	<%
	ArrayList<AttivitaBean> attivita = (ArrayList<AttivitaBean>) request.getAttribute("attivita");
	if (attivita != null && !attivita.isEmpty()) for (AttivitaBean a : attivita) {
	%>
		<a class="activity-card" href="<%=request.getContextPath()%>/common/attivita?id=<%=a.getId_attivita()%>">
			<img src="<%=request.getContextPath()%>/image?attivitaId=<%=a.getId_attivita()%>" onerror="this.src='<%=request.getContextPath()%>/images/logo.png'" alt="<%=a.getTitolo()%>">
			<strong><%=a.getTitolo()%></strong>
			<span><%=a.getCitta()%> &middot; <%=a.getCategoria()%> &middot; &euro; <%=String.format("%.2f", a.getPrezzo_unitario())%></span>
		</a>
	<% } else { %>
		<p>Nessuna attivita disponibile per questo filtro.</p>
	<% } %>
	</div>
</main>
</body>
</html>
