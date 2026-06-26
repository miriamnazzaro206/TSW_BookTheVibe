<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Login</title><link rel="stylesheet" href="<%=request.getContextPath()%>/styles/main.css"><script defer src="<%=request.getContextPath()%>/scripts/main.js"></script></head>
<body>
<%@ include file="navbar.jsp" %>
<main class="section narrow">
	<h1>Login</h1>
	<% if (request.getAttribute("errorMessage") != null) { %><p class="error visible"><%=request.getAttribute("errorMessage")%></p><% } %>
	<form method="post" action="<%=request.getContextPath()%>/LoginServlet" class="form-grid validated-form">
		<input type="email" name="txtEmail" placeholder="Email" data-pattern="email" required><span class="error"></span>
		<input type="password" name="txtPwd" placeholder="Password" required><span class="error"></span>
		<button class="primary" type="submit">Accedi</button>
	</form>
	<p>Non hai un account? <a href="<%=request.getContextPath()%>/registrazione"><u>Registrati</u></a></p>
</main>
</body>
</html>
