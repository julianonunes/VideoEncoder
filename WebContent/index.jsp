<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html id="ng-app" ng-app="videoEncoder">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Video Encoder</title>
<link
	href='https://fonts.googleapis.com/css?family=Rubik:400,500italic,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"
	integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ=="
	crossorigin="anonymous">
<link rel="stylesheet" href="assets/css/style.css" />

<script src="http://nervgh.github.io/js/es5-shim.min.js"></script>
<script src="http://nervgh.github.io/js/es5-sham.min.js"></script>
</head>
<body class="site"  nv-file-drop="" uploader="uploader" filters="queueLimit, customFilter" ng-controller="HomeController">
	<div id="wrapper">
		<header>
			<h1>
				ENCO<span>DER</span>
			</h1>
		</header>
		<div id="content">
			<div ng-show="uploader.isHTML5 && !uploader.isUploading">
				<!-- 3. nv-file-over uploader="link" over-class="className" -->
				<div class="well my-drop-zone" nv-file-over="" uploader="uploader">
					<span id="uploadMessage">{{uploadMessage}}</span>
				</div>
			</div>
		</div>
	</div>
	
	

	<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"
		integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ=="
		crossorigin="anonymous"></script>

	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular.js"
		type="text/javascript"></script>
		
	<script
		src="assets/js/angular-file-upload.min.js" type="text/javascript"></script>

	<script src="assets/js/app.js" type="text/javascript"></script>
</body>
</html>