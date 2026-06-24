<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.CarrelloBean,model.ElementoCarrelloBean,model.UtenteBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Pagamento - BookTheVibe</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/main.css">
<script defer src="<%=request.getContextPath()%>/scripts/main.js"></script>
</head>
<body>
<%@ include file="navbar.jsp" %>
<% CarrelloBean carrello = (CarrelloBean) request.getAttribute("carrello"); UtenteBean u = (UtenteBean) session.getAttribute("utenteLoggato"); %>
<main class="section narrow">
	<h1>Pagamento</h1>
	<div class="summary">
	<% for (ElementoCarrelloBean e : carrello.getElementi()) { %>
		<p><%=e.getAttivita().getTitolo()%> · <%=e.getQuantita()%> biglietti · <%=e.getDataScelta()%></p>
	<% } %>
	<strong>Totale: € <%=String.format("%.2f", carrello.getPrezzoScontato())%></strong>
	</div>
	<form method="post" action="<%=request.getContextPath()%>/checkout" class="form-grid validated-form">
		<input name="via" value="<%=u.getVia() == null ? "" : u.getVia()%>" placeholder="Via" required><span class="error"></span>
		<input name="civico" value="<%=u.getCivico() == null ? "" : u.getCivico()%>" placeholder="Civico" required><span class="error"></span>
		<input name="cap" value="<%=u.getCap() == null ? "" : u.getCap()%>" placeholder="CAP" data-pattern="cap" required><span class="error"></span>
		<input name="citta" value="<%=u.getCitta() == null ? "" : u.getCitta()%>" placeholder="Citta" required><span class="error"></span>
		<input name="nazione" value="<%=u.getNazione() == null ? "" : u.getNazione()%>" placeholder="Nazione" required><span class="error"></span>
		<input name="intestatario" placeholder="Intestatario carta" required><span class="error"></span>
		<input name="numeroCarta" placeholder="Numero carta" data-pattern="card" required><span class="error"></span>
		<input name="scadenza" placeholder="MM/AA" data-pattern="expiry" required><span class="error"></span>
		<input name="cvv" placeholder="CVV" data-pattern="cvv" required><span class="error"></span>
		<button class="primary" type="submit">Paga</button>
	</form>
</main>
</body>
</html>
