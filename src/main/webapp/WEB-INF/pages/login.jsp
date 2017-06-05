<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<c:url value="resources/js/lib/node_modules/bootstrap/dist/css/bootstrap.min.css" />">
<script src="<c:url value="resources/js/lib/node_modules/jquery/dist/jquery.min.js" />"></script>
<script src="<c:url value="resources/js/lib/node_modules/bootstrap/dist/js/bootstrap.min.js" />"></script>
</head>
<body>
<jsp:include page="../jsp-includes/menu.jsp"/>
<div class="container">
<div class="row">

<div class="col-sm-4 col-md-4 col-lg-4"></div>
<div class="col-sm-4 col-md-4 col-lg-4">
<form method="POST" action="/processlogin" id="loginform" role="form">
	<div class="form-group">
		<div class="input-group">
			<input name="username" type="text" class="form-control" id="uLogin" placeholder="Username" />
			<label for="uLogin" class="input-group-addon glyphicon glyphicon-user" ></label>
		</div>
	</div> <!-- /.form-group -->

	<div class="form-group">
		<div class="input-group">
			<input name="password" type="password" class="form-control" id="uPassword" placeholder="Password"/>
			<label for="uPassword" class="input-group-addon glyphicon glyphicon-lock" ></label>
		</div> <!-- /.input-group -->
	</div> <!-- /.form-group -->

	<div class="checkbox">
		<label>
			<input type="checkbox"> Remember me
		</label>
	</div> <!-- /.checkbox -->
	<div class="modal-footer">
		<button onclick="registration_form_submit()" class="form-control btn btn-primary" type="submit" value="Submit">Register</button>
			
	</div>
</form>
</div> <!-- col -->
<div class="col-sm-4 col-md-4 col-lg-4"></div>
</div> <!-- row div -->
</div> <!-- container div -->	
</body>
</html>