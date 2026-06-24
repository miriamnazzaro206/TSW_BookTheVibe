<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Registrazione</title><link rel="stylesheet" href="<%=request.getContextPath()%>/styles/main.css"><script defer src="<%=request.getContextPath()%>/scripts/main.js"></script></head>
<body>
<%@ include file="navbar.jsp" %>
<main class="section narrow">
	<h1>Registrazione</h1>
	<% if (request.getAttribute("errorMessage") != null) { %><p class="error visible"><%=request.getAttribute("errorMessage")%></p><% } %>
	<form method="post" action="<%=request.getContextPath()%>/registrazione" class="form-grid validated-form">
		<input name="nome" placeholder="Nome" required><span class="error"></span>
		<input name="cognome" placeholder="Cognome" required><span class="error"></span>
		<input type="email" name="email" placeholder="Email" data-pattern="email" required><span class="error"></span>
		<input type="password" name="password" placeholder="Password" data-pattern="password" required><span class="error"></span>
		<input type="date" name="data_nascita" required><span class="error"></span>
		<input name="cellulare" placeholder="Cellulare" data-pattern="phone" required><span class="error"></span>
		<input name="via" placeholder="Via" required><span class="error"></span>
		<input name="civico" placeholder="Civico" required><span class="error"></span>
		<input name="cap" placeholder="CAP" data-pattern="cap" required><span class="error"></span>
		<input name="nazione" placeholder="Nazione" required><span class="error"></span>
		<input name="citta" placeholder="Citta" required><span class="error"></span>
		<button class="primary" type="submit">Registrati</button>
	</form>
</main>
</body>
</html>
