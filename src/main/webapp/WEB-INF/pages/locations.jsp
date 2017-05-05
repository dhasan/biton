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
	
</body> 
</html>