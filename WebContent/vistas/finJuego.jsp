<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery-ui.js"></script>
	<script type="text/javascript" src="js/finJuego.js"></script>
	<link rel="stylesheet" href="css/estilos.css" />
</head>

<body>
	<c:if test="${mensaje eq 'victoria'}">
	<h3 id="mensaje">VICTORIA PARA LAS FICHAS <c:if test="${ modelo.fichaActual eq 'X' }">ROJAS</c:if> <c:if test="${ modelo.fichaActual eq 'O' }">AZULES</c:if></h3>
	</c:if>
	<c:if test="${mensaje eq 'empate'}">
	<h3 id="mensaje">Nadie ha conseguido ganar :(</h3>
	</c:if>
	<br/>
		<input type="hidden" name="peticion" id="peticion" value="volverEmpezar"/>
		<div id="botones"><input type="button" name="otraVez" id="otraVez" value="Volver a jugar" class="boton"/></div>
	
</body>

</html>