<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sim2Travel 維運管理系統(New)</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
<!-- AngularJS -->
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.8/angular.min.js"></script>

<style type="text/css">
html, body{
      height:100%;
}

.max_height {
	height: 100%;
	min-height: 100%; 
	margin:0px;
	padding:0px; 
}
.odd_columm{
	background-color: #FEFFAF
}
.even_columm{
	background-color: #C7FF91
}
.div_valign_middle{
	position:absolute;
	top: 50%;
	height:50%
	margin-top:-25% 
}
</style>

</head>
<body>
<div class="container-fluid max_height">
	<div class="row max_height">
		<table class="col-md-12 max_height">
			<tr>
				<td bgcolor="#AFFEFF" height="50px"><div><tiles:insertAttribute name="top"/> </div></td>
			</tr>
			<tr>
				<td bgcolor="#AFFFD8" height="100%"><div class="max_height"><tiles:insertAttribute name="middle" /> </div></td>
			</tr>
		</table>
	</div>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</body>
</html>