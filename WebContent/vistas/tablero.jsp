<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/estilos.css" />
	<script type="text/javascript" src="js/tablero.js"></script>
</head>

<body>
<h3>Le toca jugar al jugador con fichas de color <c:if test="${ modelo.fichaActual eq 'X' }"><p style ="color: red;">Rojo</p></c:if> <c:if test="${ modelo.fichaActual eq 'O' }"><p style ="color: blue;">Azul</p></c:if></h3>
	<c:set value="${ fn:length(modelo.tablero) }" var="tamTablero"/>
	<div class="fondo">
	<c:forEach begin="0" end="${tamTablero}" varStatus="i">
		<div >
			<c:set value="${ tamTablero - (i.index) }" var="indice"/>
			<c:forEach items="${ modelo.tablero[indice] }" var="columna" varStatus="j">
				<span class="celda" id="${ i.index }-${ j.index }"
					<c:if test="${  modelo.tablero[indice][j.index] eq 'X'}">style ="background-color:red;"</c:if>
					<c:if test="${  modelo.tablero[indice][j.index] eq 'O'}">style="background-color: blue;"</c:if>>
				</span>
			</c:forEach>
		</div>
	</c:forEach>
	</div>
</body>

</html>