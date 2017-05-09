<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h1>Spring MVC locations example</h1>

<c:if test="${not empty locs}">
	<table>
	
		<c:forEach var="location" items="${locs}">
			<tr>
				<td>${location.id}</td> <td>${location.nicename}</td> <td>${location.name}</td> 
			</tr>
		</c:forEach>
	</table>

</c:if>
${requestScope['javax.servlet.forward.request_uri']}
<br>
Hello <b><%= request.getParameter("page") %></b>!
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
<a href='<c:url value="${prevurl}"></c:url>'>prev</a>
<c:forEach begin="${beginpage}" end="${endpage}" step="1" varStatus="loop">
   
	<a href='<c:url value="${requestScope['javax.servlet.forward.request_uri']}">
				<c:param name="page" value="${loop.index}"/>
				<c:param name="pagescount" value="${pagescount}"/>
				<c:param name="pagesize" value="${pagesize}"/>
				<c:param name="itemscount" value="${itemscount}"/>
			</c:url>'>${loop.index} </a>
</c:forEach>

<a href='<c:url value="${nexturl}"></c:url>'>next</a>
</body> 
</html>