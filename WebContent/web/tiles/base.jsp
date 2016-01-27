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

</head>
<body>
	<tiles:insertAttribute name="page"/>
	
	<form action="createExcelbyJSON" method="post"  id="reportFrom" style="display: none;">
		<input type="text" name="reportDataList">
		<input type="text" name="reportColHead">
		<input type="text" name="reportName">
	</form>
	<form action="" method="post"  id="reportFrom2" style="display: none;">
	</form>
	<iframe name="sub_iframe" width="0" height="0" style="display: none;"></iframe>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<!-- JQuery UI JS-->
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<!-- AngularJS -->
	<!-- <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.19/angular.js" ></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.19/angular-route.js"></script> -->
<div id="sc">
	<script src="js/angular.js"></script>
	<script src="js/cookies.js"></script>
	<script src="js/cookieStore.js"></script>
	<script src="js/cookieWriter.js"></script>
	<script src="js/angular-route.js"></script>
	<script src="js/jquery.blockUI.js"></script>
	<script src="js/ui-bootstrap-tpls-0.13.3.js"></script>
	
	
	
	<script src="js/service.js"></script>
	<script src="js/common.js"></script>
	<script src="js/CRM/Menu.js"></script>
	<script src="js/CRM/Subscriber.js"></script>
	<script src="js/CRM/SMS.js"></script>
	<script src="js/CRM/Qos.js"></script>
	<script src="js/CRM/Application.js"></script>
	<script src="js/CRM/CurrentMonth.js"></script>
	<script src="js/CRM/CurrentDay.js"></script>
	<script src="js/CRM/DataRate.js"></script>
	<script src="js/CRM/Else.js"></script>
	
	<script src="js/directive.js"></script>
	
	<script type="text/javascript">
		function createExcel(head,data,name){
			if(data==null ||data.length==0){
				alert("No data!");
				return;
			}
			$("[name='reportDataList']").val(encodeURI(JSON.stringify(data)));
			$("[name='reportColHead']").val(encodeURI(JSON.stringify(head)));
			$("[name='reportName']").val(encodeURI(JSON.stringify(name)));
			$("#reportFrom").submit();	
		}
		function createExcel2(action){
			//alert(action);
			$("#reportFrom2").attr("action",action).submit();	
		}
	</script>
	
</div>
</body>

</html>