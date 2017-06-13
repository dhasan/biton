<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/Utils" prefix="cel" %>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<c:url value="resources/js/lib/node_modules/bootstrap/dist/css/bootstrap.min.css" />">
<script src="<c:url value="resources/js/lib/node_modules/jquery/dist/jquery.min.js" />"></script>
<script src="<c:url value="resources/js/lib/node_modules/bootstrap/dist/js/bootstrap.min.js" />"></script>
</head>

<body>
<jsp:include page="../jsp-includes/menu.jsp"/>
<div class="container-fluid">
<div class="row">
<div class="col-sm-2"></div>
<div class="col-sm-8">
<c:if test="${not empty data}">
	<table class="table table-hover">
		<c:forEach var="location" items="${data}">
			<tr>
				<td>${location.id}</td> <td>${location.nicename}</td> <td>${location.name}</td> 
			</tr>
		</c:forEach>
	</table>
</c:if>
<c:url value="${requestScope['javax.servlet.forward.request_uri']}" var="nexturl">
	<c:param name="page" value="${page + 1}"/>
	<c:param name="pagescount" value="${pagescount}"/>
	<c:param name="pagesize" value="${pagesize}"/>
	<c:param name="itemscount" value="${itemscount}"/>
</c:url>
<c:url value="${requestScope['javax.servlet.forward.request_uri']}" var="prevurl">
	<c:param name="page" value="${page -1}"/>
	<c:param name="pagescount" value="${pagescount}"/>
	<c:param name="pagesize" value="${pagesize}"/>
	<c:param name="itemscount" value="${itemscount}"/>
</c:url>
<c:if test="${page <= 5 }">
	<c:set var="beginpage" value="1"/>
	<c:set var="endpage" value="10"/>
</c:if>
<c:if test="${page>=(pagescount - 5)}">
	<c:set var="beginpage" value="${pagescount - 10}"/>
	<c:set var="endpage" value="${pagescount}"/>
</c:if>
<c:if test="${page > 5 }">
	<c:if test="${page<(pagescount - 5)}">
		<c:set var="beginpage" value="${page-5 }"/>
	<c:set var="endpage" value="${page + 5 }"/>
	</c:if>
</c:if>
<ul class="pagination"><li><a href='<c:url value="${prevurl}"></c:url>'>prev</a></li>
<c:forEach begin="${beginpage}" end="${endpage}" step="1" varStatus="loop">
	<li><a href='<c:url value="${requestScope['javax.servlet.forward.request_uri']}">
				<c:param name="page" value="${loop.index}"/>
				<c:param name="pagescount" value="${pagescount}"/>
				<c:param name="pagesize" value="${pagesize}"/>
				<c:param name="itemscount" value="${itemscount}"/>
			</c:url>'>${loop.index} </a></li>
</c:forEach>
<li><a href='<c:url value="${nexturl}"></c:url>'>next</a></li></ul>
</div>
<div class="col-sm-2"></div>
</div>
</div>
</body> 
</html>