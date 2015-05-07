<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myapp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sim2Travel 維運管理系統(New)</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
<!-- JQuery UI CSS-->
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<!-- AngularJS -->
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.8/angular.min.js"></script>
<!-- CommonCss -->
<link rel="stylesheet"  type="text/css" href="<%=request.getContextPath()%>/css/common.css">
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
<!-- JQuery UI JS-->
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script type="text/javascript">
$(".accordion").find("h3").click(function(){
	$(this).next("div").siblings("div").slideUp("slow");
	$(this).next("div").slideToggle("slow");
});

$(window).resize(function(){ 
	setAccordianHeight();
});
$(".accordion").next("div").slideUp("slow");
setAccordianHeight();
function setAccordianHeight(){
	var cN = +$(".accordion").find("div").size();
	var containerSize=+$(".accordion").height();
	
	var headerSize=+$(".accordion").find("h3").height();
	var bottomsize=+$(".accordion").find("h1").height();
	var contentHeight=(containerSize-headerSize*cN-bottomsize-15)+"px";
	//alert("headerSize:"+headerSize+",containerSize:"+containerSize+",cN:"+cN+",contentHeight:"+contentHeight);
	$(".accordion").find("div").css('height', contentHeight);
}

</script>
</body>
</html>