<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,model.PrenotazioneBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Gestione Ordini</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/main.css">
<script defer src="<%=request.getContextPath()%>/scripts/main.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/common/navbar.jsp" %>
<main class="section">
	<h1>Gestione Ordini</h1>
	<form method="get" action="<%=request.getContextPath()%>/admin/ordini" class="filters" id="orderFilters">
		<select name="filtro"><option value="tutti">Tutti</option><option value="date">Filtra per data</option><option value="utente">Filtra per utente</option></select>
		<input type="date" name="da" class="filter-date">
		<input type="date" name="a" class="filter-date">
		<select name="utenteId" class="filter-user">
		<% ArrayList<Integer> utenti = (ArrayList<Integer>) request.getAttribute("utentiConPrenotazioni"); if (utenti != null) for (Integer id : utenti) { %><option value="<%=id%>"><%=id%></option><% } %>
		</select>
		<button type="submit">Applica</button>
	</form>
	<% ArrayList<PrenotazioneBean> prenotazioni = (ArrayList<PrenotazioneBean>) request.getAttribute("prenotazioni"); %>
	<div class="table-wrap">
		<table id="ordersAdminTable">
			<thead><tr><th>ID</th><th>Utente</th><th>Attivita</th><th>Data evento</th><th>Data prenotazione</th><th>Biglietti</th><th>Totale</th><th>Pagamento</th></tr></thead>
			<tbody>
			<% if (prenotazioni != null) for (PrenotazioneBean p : prenotazioni) { %>
			<tr><td><%=p.getId_prenotazione()%></td><td><%=p.getUtente_id()%></td><td><%=p.getAttivita_id()%></td><td><%=p.getData_evento()%></td><td><%=p.getData_prenotazione()%></td><td><%=p.getNum_prenotati()%></td><td>&euro; <%=String.format("%.2f", p.getPrezzo_tot())%></td><td><%=p.getStato_pagamento()%></td></tr>
			<% } %>
			</tbody>
		</table>
	</div>
</main>
</body>
</html>
