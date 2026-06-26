<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,model.CodiceScontoBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Gestione Coupon</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/main.css">
<script defer src="<%=request.getContextPath()%>/scripts/main.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/common/navbar.jsp" %>
<main class="section">
	<h1>Gestione Coupon</h1>
	<form method="post" action="<%=request.getContextPath()%>/admin/coupon" class="filters validated-form">
		<input type="hidden" name="accessToken" value="<%=session.getAttribute("accessToken")%>">
		<input name="code_id" placeholder="Codice sconto" required><span class="error"></span>
		<input type="number" min="1" max="100" step="0.01" name="percentuale" placeholder="Percentuale" required><span class="error"></span>
		<button class="primary" type="submit">Aggiungi Codice sconto</button>
	</form>
	<% ArrayList<CodiceScontoBean> coupon = (ArrayList<CodiceScontoBean>) request.getAttribute("coupon"); %>
	<div class="table-wrap">
		<table id="couponAdminTable">
			<thead><tr><th>Codice</th><th>Percentuale</th><th>Stato</th><th>Azione</th></tr></thead>
			<tbody>
			<% if (coupon != null) for (CodiceScontoBean c : coupon) { %>
			<tr><td><%=c.getCode_id()%></td><td><%=c.getPercentuale()%>%</td><td><%=c.getStato() ? "Attivo" : "Non Attivo"%></td><td><form method="post" action="<%=request.getContextPath()%>/admin/coupon"><input type="hidden" name="accessToken" value="<%=session.getAttribute("accessToken")%>"><input type="hidden" name="action" value="stato"><input type="hidden" name="code_id" value="<%=c.getCode_id()%>"><input type="hidden" name="stato" value="<%=!c.getStato()%>"><button type="submit"><%=c.getStato() ? "Disattiva" : "Attiva"%></button></form></td></tr>
			<% } %>
			</tbody>
		</table>
	</div>
</main>
</body>
</html>
