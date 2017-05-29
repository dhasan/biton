<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
		</ul>
	</div>
	</div></div>
</nav>

<div class="modal fade" id="registrationModal" tabindex="-1" role="dialog" aria-labelledby="registrationModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">�</button>
				<h4 class="modal-title" id="registrationModalLabel">New user registration</h4>
			</div> <!-- /.modal-header -->
			<div class="modal-body">
			 	<form:form method="POST" action="/mvc/userregistrationaction" modelAttribute="userregister" id="registrationform" role="form">
									
					<div class="form-group">
						<label for="username" class="cols-sm-2 control-label">Username</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
								<form:input path="username" type="text" class="form-control" name="username" id="username"  placeholder="Enter your Username"/>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="email" class="cols-sm-2 control-label">Your Email</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
								<form:input path="email" type="text" class="form-control" name="email" id="email"  placeholder="Enter your Email"/>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="usertype" class="cols-sm-2 control-label">User Type</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
								<form:select path="usertype" type="text" class="form-control" name="usertype" id="usertype">
									<form:option value="BUYER" label="Buyer" />
									<form:option value="SELLER" label="Seler" />
								</form:select>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="password" class="cols-sm-2 control-label">Password</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
								<input type="password" class="form-control" name="password" id="password"  placeholder="Enter your Password"/>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="confirm" class="cols-sm-2 control-label">Confirm Password</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
								<input type="password" class="form-control" name="confirm" id="confirm"  placeholder="Confirm your Password"/>
							</div>
						</div>
					</div>
					
				</form:form>
			</div>
			<div class="modal-footer">
				<button onclick="registration_form_submit()" class="form-control btn btn-primary" type="submit" value="Submit">Register</button>
			
			</div>
			
		</div>
	</div>
</div>

<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">�</button>
				<h4 class="modal-title" id="loginModalLabel">Log in</h4>
			</div> <!-- /.modal-header -->

			<div class="modal-body">
				<form:form method="POST" action="/mvc/userloginaction" modelAttribute="userlogin" id="loginform" role="form">
					<div class="form-group">
						<div class="input-group">
							<form:input path="username" type="text" class="form-control" id="uLogin" />
							<label for="uLogin" class="input-group-addon glyphicon glyphicon-user" ></label>
						</div>
					</div> <!-- /.form-group -->

					<div class="form-group">
						<div class="input-group">
							<form:input path="password" type="password" class="form-control" id="uPassword" placeholder="Password" />
							<label for="uPassword" class="input-group-addon glyphicon glyphicon-lock" ></label>
						</div> <!-- /.input-group -->
					</div> <!-- /.form-group -->

					<div class="checkbox">
						<label>
							<input type="checkbox"> Remember me
						</label>
					</div> <!-- /.checkbox -->
				</form:form>

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
  function login_form_submit() {
	  document.getElementById("loginform").submit();
   }    
  function registration_form_submit(){
	  document.getElementById("registrationform").submit();
  }
  </script>
