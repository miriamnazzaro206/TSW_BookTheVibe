<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UtenteBean" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Profilo</title><link rel="stylesheet" href="<%=request.getContextPath()%>/styles/main.css"><script defer src="<%=request.getContextPath()%>/scripts/main.js"></script></head>
<body><%@ include file="/WEB-INF/views/common/navbar.jsp" %>
<% UtenteBean u = (UtenteBean) session.getAttribute("utenteLoggato"); %>
<main class="section narrow"><h1>Profilo</h1>
<% if (request.getAttribute("successMessage") != null) { %><p class="success-msg"><%=request.getAttribute("successMessage")%></p><% } %>
<form method="post" action="<%=request.getContextPath()%>/common/profilo" class="form-grid validated-form">
	<input name="nome" value="<%=u.getNome()%>" required><span class="error"></span>
	<input name="cognome" value="<%=u.getCognome()%>" required><span class="error"></span>
	<input type="email" name="email" value="<%=u.getEmail()%>" data-pattern="email" required><span class="error"></span>
	<input type="password" name="password" value="<%=u.getPassword()%>" data-pattern="password" required><span class="error"></span>
	<input type="date" name="data_nascita" value="<%=u.getData_nascita() == null ? "" : u.getData_nascita()%>"><span class="error"></span>
	<input name="cellulare" value="<%=u.getCellulare() == null ? "" : u.getCellulare()%>" data-pattern="phone"><span class="error"></span>
	<input name="via" value="<%=u.getVia() == null ? "" : u.getVia()%>"><span class="error"></span>
	<input name="civico" value="<%=u.getCivico() == null ? "" : u.getCivico()%>"><span class="error"></span>
	<input name="cap" value="<%=u.getCap() == null ? "" : u.getCap()%>" data-pattern="cap"><span class="error"></span>
	<input name="nazione" value="<%=u.getNazione() == null ? "" : u.getNazione()%>"><span class="error"></span>
	<input name="citta" value="<%=u.getCitta() == null ? "" : u.getCitta()%>"><span class="error"></span>
	<button class="primary" type="submit">Aggiorna</button>
</form></main></body></html>