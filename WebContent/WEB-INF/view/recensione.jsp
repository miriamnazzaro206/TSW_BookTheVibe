<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.AttivitaBean" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Recensione</title><link rel="stylesheet" href="<%=request.getContextPath()%>/styles/main.css"><script defer src="<%=request.getContextPath()%>/scripts/main.js"></script></head>
<body><%@ include file="navbar.jsp" %><% AttivitaBean a = (AttivitaBean) request.getAttribute("attivita"); %>
<main class="section narrow"><h1>Recensione per <%=a.getTitolo()%></h1>
<form method="post" action="<%=request.getContextPath()%>/recensione" class="form-grid validated-form">
	<input type="hidden" name="attivitaId" value="<%=a.getId_attivita()%>">
	<select name="punteggio" required><option value="">Punteggio</option><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option></select><span class="error"></span>
	<textarea name="testo" placeholder="Racconta la tua esperienza" required></textarea><span class="error"></span>
	<button class="primary" type="submit">Pubblica recensione</button>
</form></main></body></html>
