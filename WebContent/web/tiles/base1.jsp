<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>  

<!DOCTYPE html>
<html ng-app="MainApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sim2Travel 維運管理系統(New)</title>
<!-- CSS -->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
<!-- JQuery UI CSS-->
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<!-- CommonCss -->
<link rel="stylesheet" href="css/common.css">
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
</head>
<body>
<div class="container-fluid max_height">
	<div class="row max_height" >
		<table class="col-md-12 max_height" style="height: 99%">
			<tr>
				<td bgcolor="#AFFEFF" height="50px"><div><tiles:insertAttribute name="top"/> </div></td>
			</tr>
			<tr> 
				<td bgcolor="#AFFFD8" height="100%"><div class="max_height"><tiles:insertAttribute name="middle" /> </div></td>
			</tr>
		</table>
	</div>
</div>

<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<!-- JQuery UI JS-->
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<!-- AngularJS -->
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.19/angular.js" ></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.19/angular-route.js"></script>
<div id="sc">
	<script src="js/service.js"></script>
	<script src="js/common.js"></script>
</div>
</body>

</html>