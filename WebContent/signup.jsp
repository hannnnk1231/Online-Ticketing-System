<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Simple Ticketing System</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
	<link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
	<link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
	<link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
	<link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
	
	<div class="limiter">
		<div class="container-login">
			<div class="wrap-login">
				<div class="login-pic js-tilt" data-tilt>
					<img src="images/img-01.png" alt="IMG">
				</div>
				<div>
					<form class="login-form validate-form" action="SignUp" method="post">
						<span class="login-form-title">
							Member Sign Up
						</span>
						
						<% if (request.getAttribute("error")!=null) { %>
						<span class="login-error">User email already existed</span>
						<% } %>
	
						<div class="wrap-input validate-input" data-validate = "Valid email is required: ex@abc.xyz">
							<input class="input" type="text" name="username" placeholder="Email">
							<span class="focus-input"></span>
							<span class="symbol-input">
								<i class="fa fa-envelope" aria-hidden="true"></i>
							</span>
						</div>
	
						<div class="wrap-input validate-input" data-validate = "Password is required">
							<input class="input" type="password" name="password" placeholder="Password">
							<span class="focus-input"></span>
							<span class="symbol-input">
								<i class="fa fa-lock" aria-hidden="true"></i>
							</span>
						</div>
						
						<div class="container-login-form-btn">
							<button class="login-form-btn">
								Sign up
							</button>
						</div>
					</form>
						
					<form class="login-form" action="Login" method="get">
						<div class="container-login-form-btn">
							<button class="signup-btn">
								Already have an account? Login
								<i class="fa fa-long-arrow-right m-l-5" aria-hidden="true"></i>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	

	
<!--===============================================================================================-->	
	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/tilt/tilt.jquery.min.js"></script>
	<script >
		$('.js-tilt').tilt({
			scale: 1.1
		})
	</script>
<!--===============================================================================================-->
	<script src="js/main.js"></script>

</body>
</html>