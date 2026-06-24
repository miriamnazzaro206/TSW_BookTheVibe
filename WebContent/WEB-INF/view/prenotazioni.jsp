<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.time.LocalDate,model.PrenotazioneBean,model.AttivitaBean,dao.AttivitaDaoImp" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Prenotazioni</title><link rel="stylesheet" href="<%=request.getContextPath()%>/styles/main.css"></head>
<body><%@ include file="navbar.jsp" %><main class="section"><h1>Le mie prenotazioni</h1>
<% ArrayList<PrenotazioneBean> prenotazioni = (ArrayList<PrenotazioneBean>) request.getAttribute("prenotazioni"); AttivitaDaoImp attDao = (AttivitaDaoImp) request.getAttribute("attivitaDao"); %>
<div class="table-wrap"><table><tr><th>Attivita</th><th>Data evento</th><th>Biglietti</th><th>Prezzo</th><th>Pagamento</th><th>Azione</th></tr>
<% if (prenotazioni != null) for (PrenotazioneBean p : prenotazioni) { AttivitaBean a = attDao.doRetrieveByKey(p.getAttivita_id()); %>
<tr><td><%=a == null ? p.getAttivita_id() : a.getTitolo()%></td><td><%=p.getData_evento()%></td><td><%=p.getNum_prenotati()%></td><td>€ <%=String.format("%.2f", p.getPrezzo_tot())%></td><td><%=p.getStato_pagamento()%></td><td><% if (p.getData_evento().isBefore(LocalDate.now())) { %><a class="button-link" href="<%=request.getContextPath()%>/recensione?attivitaId=<%=p.getAttivita_id()%>">Scrivi una Recensione</a><% } %></td></tr>
<% } %></table></div></main></body></html>
