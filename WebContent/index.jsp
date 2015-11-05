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
<body class="site" ng-controller="HomeController" nv-file-drop="" uploader="uploader" filters="queueLimit, customFilter">
	<div id="wrapper">
		<header>
			<h1>
				ENCO<span>DER</span>
			</h1>
		</header>
		<div id="content">
			<form action="/VideoEncoder/Encode" method="post"
				enctype="multipart/form-data">
				<div ng-show="uploader.isHTML5 && !uploader.isUploading">

					<div class="well my-drop-zone" nv-file-over="" uploader="uploader">
                            Arraste para esta área os arquivos que deseja converter
                        </div>
				</div>
				<!-- <div*>

					<input type="file" id="fileToEncode" name="file" />
					
				</div>
				<div>
					<button id="encodeButton">Converter</button>
				</div> -->
				<div ng-show="fileEncoded">
					<video src="{{encodedFileUrl}}" controls></video>
				</div>
			</form>
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

	<script src="assets/js/angular-file-upload.min.js"
		type="text/javascript"></script>
	<script src="assets/js/app.js" type="text/javascript"></script>
</body>
</html>