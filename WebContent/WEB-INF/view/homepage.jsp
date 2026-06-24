<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,model.AttivitaBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BookTheVibe</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/main.css">
<script defer src="<%=request.getContextPath()%>/scripts/main.js"></script>
</head>
<body>
<%@ include file="navbar.jsp" %>
<main>
	<section class="hero">
		<p class="logo-mark">BTV</p>
		<h1>ESPLORA.PRENOTA.VIVI</h1>
		<div class="photo-carousel">
			<div style="background-image:url('https://images.unsplash.com/photo-1523906834658-6e24ef2386f9?auto=format&fit=crop&w=900&q=80')"></div>
			<div style="background-image:url('https://images.unsplash.com/photo-1529260830199-42c24126f198?auto=format&fit=crop&w=900&q=80')"></div>
			<div style="background-image:url('https://images.unsplash.com/photo-1531572753322-ad063cecc140?auto=format&fit=crop&w=900&q=80')"></div>
			<div style="background-image:url('https://images.unsplash.com/photo-1552832230-c0197dd311b5?auto=format&fit=crop&w=900&q=80')"></div>
			<div style="background-image:url('https://images.unsplash.com/photo-1516483638261-f4dbaf036963?auto=format&fit=crop&w=900&q=80')"></div>
			<div style="background-image:url('https://images.unsplash.com/photo-1534445867742-43195f401b6c?auto=format&fit=crop&w=900&q=80')"></div>
			<div style="background-image:url('https://images.unsplash.com/photo-1533676802871-eca1ae998cd5?auto=format&fit=crop&w=900&q=80')"></div>
			<div style="background-image:url('https://images.unsplash.com/photo-1523906834658-6e24ef2386f9?auto=format&fit=crop&w=900&q=80')"></div>
			<div style="background-image:url('https://images.unsplash.com/photo-1529260830199-42c24126f198?auto=format&fit=crop&w=900&q=80')"></div>
			<div style="background-image:url('https://images.unsplash.com/photo-1531572753322-ad063cecc140?auto=format&fit=crop&w=900&q=80')"></div>
		</div>
	</section>
	<section class="section">
		<h2>Attivita disponibili</h2>
		<div class="activity-strip">
			<%
			ArrayList<AttivitaBean> attivita = (ArrayList<AttivitaBean>) request.getAttribute("attivita");
			if (attivita != null) for (AttivitaBean a : attivita) {
			%>
			<a class="activity-card" href="<%=request.getContextPath()%>/attivita?id=<%=a.getId_attivita()%>">
				<img src="<%=request.getContextPath()%>/image?attivitaId=<%=a.getId_attivita()%>" onerror="this.src='https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=600&q=80'" alt="<%=a.getTitolo()%>">
				<strong><%=a.getTitolo()%></strong>
				<span><%=a.getCitta()%> · € <%=String.format("%.2f", a.getPrezzo_unitario())%></span>
			</a>
			<% } %>
		</div>
	</section>
</main>
</body>
</html>
