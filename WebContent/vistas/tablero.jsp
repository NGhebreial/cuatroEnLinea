<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>

<body>
	<c:forEach items="${ tablero }" var = "filas" varStatus="i">
		<c:forEach items="${ filas }" var = "columna" varStatus="j">		
			<div id ="${ i }${ j }"><c:out value="${ columna }"/></div>
		</c:forEach>
	</c:forEach>
</body>

</html>