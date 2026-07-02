<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,model.AttivitaBean,model.DisponibilitaBean,model.RecensioneBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Attivita - BookTheVibe</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/main.css">
<script defer src="<%=request.getContextPath()%>/scripts/main.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/common/navbar.jsp" %>
<%
AttivitaBean a = (AttivitaBean) request.getAttribute("attivita");
ArrayList<DisponibilitaBean> disponibilita = (ArrayList<DisponibilitaBean>) request.getAttribute("disponibilita");
ArrayList<RecensioneBean> recensioni = (ArrayList<RecensioneBean>) request.getAttribute("recensioni");
%>
<main class="detail-layout">
	<section>
		<div class="detail-gallery">
			<img src="<%=request.getContextPath()%>/image?attivitaId=<%=a.getId_attivita()%>&index=0" onerror="this.src='<%=request.getContextPath()%>/images/logo.png'" alt="<%=a.getTitolo()%>">
			<img src="<%=request.getContextPath()%>/image?attivitaId=<%=a.getId_attivita()%>&index=1" onerror="this.style.display='none'" alt="<%=a.getTitolo()%>">
		</div>
		<h1><%=a.getTitolo()%></h1>
		<p class="muted"><%=a.getCitta()%> &middot; <%=a.getCategoria()%> &middot; <%=a.getDurata()%></p>
		<p><%=a.getDescrizione()%></p>
		<h2>Recensioni</h2>
		<% if (recensioni != null && !recensioni.isEmpty()) for (RecensioneBean r : recensioni) { %>
			<article class="review"><strong><%=r.getPunteggio()%>/5</strong><p><%=r.getTesto()%></p></article>
		<% } else { %>
			<p class="muted">Non ci sono ancora recensioni per questa attivita.</p>
		<% } %>
	</section>
	<aside class="booking-panel" data-availability-url="<%=request.getContextPath()%>/common/disponibilita/controlla" data-attivita-id="<%=a.getId_attivita()%>">
		<h2>&euro; <%=String.format("%.2f", a.getPrezzo_unitario())%></h2>
		<form method="post" action="<%=request.getContextPath()%>/common/attivita" class="validated-form">
			<input type="hidden" name="id" value="<%=a.getId_attivita()%>">
			<label>Data
				<select name="dataEvento" id="dataEvento" required>
				<% if (disponibilita != null) for (DisponibilitaBean d : disponibilita) if (d.getPosti_rimanenti() > 0) { %>
					<option value="<%=d.getData_evento()%>" data-posti="<%=d.getPosti_rimanenti()%>"><%=d.getData_evento()%> - <%=d.getPosti_rimanenti()%> posti</option>
				<% } %>
				</select>
				<span class="error"></span>
			</label>
			<label>Biglietti
				<input type="number" name="quantita" id="quantita" min="1" value="1" required>
				<span class="error"></span>
			</label>
			<p class="muted availability-message" id="availabilityMessage"></p>
			<button class="primary" type="submit">Aggiungi al carrello</button>
		</form>
	</aside>
</main>
</body>
</html>
