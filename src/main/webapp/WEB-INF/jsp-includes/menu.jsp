<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 
<security:authorize access="isAuthenticated()">
    authenticated as <security:authentication property="principal.username" /> 
</security:authorize>
-->
<nav class="navbar navbar-inverse">
	<div class="container-fluid">
	<div class="row">
	<div class="col-sm-2"></div>
	<div class="col-sm-8">
		<ul class="nav navbar-nav">
			<li class="active"><a href="#">Fasion</a></li>
			<li><a href="#">Electronics</a></li>
			<li><a href="#">Collectibles & Art</a></li>
			<li><a href="#">Home & Garden</a></li>
			<li><a href="#">Sporting Goods</a></li>
			<li><a href="#">Motors</a></li>
		</ul>

		<ul class="nav navbar-nav navbar-right">
			<li><a href="" data-toggle="modal" data-target="#registrationModal"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
			<li><a href="" data-toggle="modal" data-target="#loginModal"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
			<li>
				<div class="btn-group">
					<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
      					<img src="<c:url value="/resources/images/smflags/${pageContext.response.locale}.png"/>">&#160;<spring:message code="label.localization.name"/>
            			<span class="glyphicon glyphicon-chevron-down"></span>
    				</button>
    				<ul class="dropdown-menu">
    					<li>
        					<a href="?locale=en" title="Select this card"><img src="<c:url value="/resources/images/smflags/en.png"/>">&#160;English</a>
      					</li>
    					<li>
        					<a href="?locale=tr" title="Select this card"><img src="<c:url value="/resources/images/smflags/tr.png"/>">&#160;Türkçe</a>
      					</li>
    				</ul>
				</div>
			</li>
		</ul>
	</div>
	</div></div>
</nav>

<div class="modal fade" id="registrationModal" tabindex="-1" role="dialog" aria-labelledby="registrationModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h4 class="modal-title" id="registrationModalLabel"><spring:message code="label.newuserregistration"/></h4>
			</div> <!-- /.modal-header -->
			<div class="modal-body">
				<c:url value="/userregistrationaction" var="userregistrationactionurl"/>
			 	<form:form method="POST" action="${userregistrationactionurl}" modelAttribute="userregister" id="registrationform" role="form">
									
					<div class="form-group">
						<label for="username" class="cols-sm-2 control-label"><spring:message code="label.username" /></label>
						<span id="username_span" class="text-danger"></span>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
								<form:input path="username" type="text" class="form-control" name="username" id="username"  placeholder="Enter your Username" onblur="verify_username();"/>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="email" class="cols-sm-2 control-label"><spring:message code="label.email" /></label>
						<span id="email_span" class="text-danger"></span>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
								<form:input path="email" type="text" class="form-control" name="email" id="email"  placeholder="Enter your Email" onblur="verify_email();"/>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="usertype" class="cols-sm-2 control-label"><spring:message code="label.usertype" /></label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
								<form:select path="usertype" type="text" class="form-control" name="usertype" id="usertype">
									<form:option value="ROLE_BUYER"><spring:message code="label.usertype.buyer" /></form:option>
									<form:option value="ROLE_SELLER"><spring:message code="label.usertype.seller" /></form:option>
								</form:select>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="password" class="cols-sm-2 control-label"><spring:message code="label.password"/></label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
								<input type="password" class="form-control" name="password" id="password"  placeholder="Enter your Password" onblur="verify_pass()"/>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="confirm" class="cols-sm-2 control-label"><spring:message code="label.confirmpassword"/></label>
						<span id="password_span" class="text-danger"></span>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
								<input type="password" class="form-control" name="confirm" id="confirm"  placeholder="Confirm your Password" onblur="verify_pass();"/>
							</div>
						</div>
					</div>
					
				</form:form>
			</div>
			<div class="modal-footer">
				<button onclick="registration_form_submit()" class="form-control btn btn-primary" type="submit" value="Submit"><spring:message code="label.register"/></button>
			
			</div>
			
		</div>
	</div>
</div>

<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h4 class="modal-title" id="loginModalLabel">Log in</h4>
			</div> <!-- /.modal-header -->

			<div class="modal-body">
				<form method="POST" action="/processlogin" id="loginform" role="form">
					<div class="form-group">
						<div class="input-group">
							<input type="text" class="form-control" id="uLogin" />
							<label for="uLogin" class="input-group-addon glyphicon glyphicon-user" ></label>
						</div>
					</div> <!-- /.form-group -->

					<div class="form-group">
						<div class="input-group">
							<input type="password" class="form-control" id="uPassword" placeholder="Password"/>
							<label for="uPassword" class="input-group-addon glyphicon glyphicon-lock" ></label>
						</div> <!-- /.input-group -->
					</div> <!-- /.form-group -->

					<div class="checkbox">
						<label>
							<input type="checkbox"> Remember me
						</label>
					</div> <!-- /.checkbox -->
				</form>

			</div> <!-- /.modal-body -->

			<div class="modal-footer">
				<button onclick="login_form_submit()" class="form-control btn btn-primary" type="submit" value="Submit">Ok</button>
				<!-- 
				<div class="progress">
					<div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="1" aria-valuemin="1" aria-valuemax="100" style="width: 0%;">
						<span class="sr-only">progress</span>
					</div>
				</div>
				-->
			</div> <!-- /.modal-footer -->

		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/javascript">
	var userverify = -1;
	var emailverify = -1;
	var passwordverify = -1;
	function login_form_submit() {
		document.getElementById("loginform").submit();
	} 
	
	function validateEmail(email) {
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	}
	
	function validateUsername(uname){
		var re = /^[a-z0-9_-]{6,15}$/;
		return re.test(uname);
	}
	function validatePassword(passw){
		var re = /^[a-zA-Z0-9!@#$%^&*]{6,16}$/;
		return re.test(passw);
	}
	
	function verify_pass(){
		pass = $("#password").val();
		conf = $("#confirm").val(); 
		if (pass!=conf){
			$('#password_span').text("Passwords doesn't match!");
			passwordverify = 0;
		}else{
			if (validatePassword(pass)){
				$('#password_span').empty();
				userverify = 1;
			}else{			
				$('#password_span').text("Invalid password!");
				passwordverify = 0;
			}
		}
	}
  
  	function verify_username(){
  		value = $("#username").val(); 
  		if (validateUsername(value)){
  			$.ajax({url: "verifyusername/"+value, success: function(result){
				console.log("uresult: "+result);
				if (result!=0){
					$('#username_span').text("Username already taken!");
					userverify = 0;
				}else{
					$('#username_span').empty();
					userverify = 1;
				}
		
			}});
  		}else{
  			$('#username_span').text("Invalid username!");
			userverify = 0;
  		}
  	}
  	
  	function verify_email(){
  		value = $("#email").val(); 
  		if (validateEmail(value)){
  			$.ajax({url: "verifyemail/"+value+"/wa", success: function(result){
  		  		console.log("eresult: "+result);
  		  		if (result!=0){
  		  			$('#email_span').text("Email already exists!");
  		  			emailverify = 0;
  		  		}else{
  		  			$('#email_span').empty();
  		  			emailverify = 1;
  		  		}
  			}});
  		}else{
  			$('#email_span').text("Invalid email address!");
  			emailverify = 0;
  		}
   	}
  	
  	function registration_form_submit(){
  		if ((emailverify==1) && (userverify==1) && (emailverify==1)){
    		document.getElementById("registrationform").submit();
  		}
  	}
	  
  
  </script>
