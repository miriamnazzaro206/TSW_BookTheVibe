<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.CarrelloBean,model.ElementoCarrelloBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Carrello - BookTheVibe</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/main.css">
<script defer src="<%=request.getContextPath()%>/scripts/main.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/common/navbar.jsp" %>
<% CarrelloBean carrello = (CarrelloBean) request.getAttribute("carrello"); %>
<main class="section cart-layout">
	<section>
		<h1>Carrello</h1>
		<% if (carrello.getElementi().isEmpty()) { %>
			<p>Il carrello e' vuoto.</p>
		<% } %>
		<% for (ElementoCarrelloBean e : carrello.getElementi()) { %>
			<div class="cart-row">
				<div>
					<strong><%=e.getAttivita().getTitolo()%></strong>
					<p><%=e.getDataScelta()%> &middot; &euro; <%=String.format("%.2f", e.getAttivita().getPrezzo_unitario())%></p>
				</div>
				<label class="quantity-control">Quantita
					<input class="cart-quantity" type="number" min="1" value="<%=e.getQuantita()%>"
						data-url="<%=request.getContextPath()%>/common/carrello"
						data-id="<%=e.getAttivita().getId_attivita()%>"
						data-data="<%=e.getDataScelta()%>">
				</label>
				<form method="post" action="<%=request.getContextPath()%>/common/carrello">
					<input type="hidden" name="action" value="rimuovi">
					<input type="hidden" name="id" value="<%=e.getAttivita().getId_attivita()%>">
					<input type="hidden" name="data" value="<%=e.getDataScelta()%>">
					<button class="danger" type="submit">X</button>
				</form>
			</div>
		<% } %>
		<form method="post" action="<%=request.getContextPath()%>/common/carrello">
			<input type="hidden" name="action" value="svuota">
			<button type="submit">Svuota carrello</button>
		</form>
	</section>
	<aside class="booking-panel">
		<h2>Totale</h2>
		<p class="total">&euro; <span id="cartTotal"><%=String.format("%.2f", carrello.getPrezzoScontato())%></span></p>
		<form id="couponForm" method="post" data-url="<%=request.getContextPath()%>/common/carrello">
			<input type="hidden" name="action" value="sconto">
			<input type="text" name="codice" placeholder="Codice sconto">
			<button type="submit">Applica</button>
			<p class="error" id="couponMessage"></p>
		</form>
		<p class="error" id="cartMessage"></p>
		<a class="primary button-link" href="<%=request.getContextPath()%>/common/checkout">Effettua pagamento</a>
	</aside>
</main>
</body>
</html>
