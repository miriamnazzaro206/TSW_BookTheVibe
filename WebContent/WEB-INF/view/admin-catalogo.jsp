<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,model.AttivitaBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Gestione Catalogo</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/main.css">
<script defer src="<%=request.getContextPath()%>/scripts/main.js"></script>
</head>
<body>
<%@ include file="navbar.jsp" %>
<main class="section">
	<h1>Gestione Catalogo</h1>
	<form method="post" action="<%=request.getContextPath()%>/admin/catalogo" enctype="multipart/form-data" class="admin-form validated-form">
		<input type="hidden" name="accessToken" value="<%=session.getAttribute("accessToken")%>">
		<input name="titolo" placeholder="Titolo" required><span class="error"></span>
		<input name="provider" placeholder="Agenzia provider" required><span class="error"></span>
		<input name="prezzo_unitario" type="number" min="0" step="0.01" placeholder="Prezzo unitario" required><span class="error"></span>
		<input name="categoria" placeholder="Categoria" required><span class="error"></span>
		<input name="durata" placeholder="Durata" required><span class="error"></span>
		<input name="capacita_massima" type="number" min="1" placeholder="Posti complessivi" required><span class="error"></span>
		<input name="date_evento" placeholder="Date evento separate da virgola: 2026-07-01,2026-07-08" required><span class="error"></span>
		<input name="citta" placeholder="Citta" required><span class="error"></span>
		<textarea name="descrizione" placeholder="Descrizione" required></textarea><span class="error"></span>
		<input type="file" name="foto" accept="image/*" multiple>
		<button class="primary" type="submit">Inserisci attivita</button>
	</form>
	<% ArrayList<AttivitaBean> attivita = (ArrayList<AttivitaBean>) request.getAttribute("attivita"); %>
	<div class="table-wrap">
		<table id="catalogAdminTable" class="admin-catalog-table">
			<thead>
				<tr>
					<th>Immagine</th>
					<th>Nome</th>
					<th>Categoria</th>
					<th>Prezzo</th>
					<th>Stato</th>
					<th>Stato attivita</th>
					<th>Modifica</th>
					<th>Nuove date</th>
				</tr>
			</thead>
			<tbody>
			<% if (attivita != null) for (AttivitaBean a : attivita) { %>
			<tr>
				<td><img class="thumb" src="<%=request.getContextPath()%>/image?attivitaId=<%=a.getId_attivita()%>" onerror="this.style.display='none'" alt=""></td>
				<td><%=a.getTitolo()%></td>
				<td><%=a.getCategoria()%></td>
				<td>&euro; <%=String.format("%.2f", a.getPrezzo_unitario())%></td>
				<td><%=a.isStato() ? "Attivo" : "Non Attivo"%></td>
				<td>
					<form method="post" action="<%=request.getContextPath()%>/admin/catalogo" class="inline-form catalog-status-form">
						<input type="hidden" name="accessToken" value="<%=session.getAttribute("accessToken")%>">
						<input type="hidden" name="action" value="stato">
						<input type="hidden" name="id" value="<%=a.getId_attivita()%>">
						<input type="hidden" name="stato" value="<%=!a.isStato()%>">
						<button type="submit"><%=a.isStato() ? "Disattiva" : "Attiva"%></button>
					</form>
				</td>
				<td>
					<form method="post" action="<%=request.getContextPath()%>/admin/catalogo" class="inline-form catalog-edit-form">
						<input type="hidden" name="accessToken" value="<%=session.getAttribute("accessToken")%>">
						<input type="hidden" name="action" value="modifica">
						<input type="hidden" name="id" value="<%=a.getId_attivita()%>">
						<input type="number" name="prezzo" step="0.01" value="<%=a.getPrezzo_unitario()%>">
						<input name="descrizione" value="<%=a.getDescrizione() == null ? "" : a.getDescrizione().replace("\"", "&quot;")%>">
						<button type="submit">Modifica</button>
					</form>
				</td>
				<td>
					<form method="post" action="<%=request.getContextPath()%>/admin/catalogo" class="inline-form catalog-date-form">
						<input type="hidden" name="accessToken" value="<%=session.getAttribute("accessToken")%>">
						<input type="hidden" name="action" value="date">
						<input type="hidden" name="id" value="<%=a.getId_attivita()%>">
						<input name="date_evento" placeholder="Nuove date: 2026-07-01,2026-07-08" required>
						<input type="number" name="posti_nuove_date" min="1" placeholder="Posti" required>
						<button type="submit">Aggiungi date</button>
					</form>
				</td>
			</tr>
			<% } %>
			</tbody>
		</table>
	</div>
</main>
</body>
</html>
