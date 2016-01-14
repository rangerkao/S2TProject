<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript">
<!--

//-->
$(document).ready(function(){
	document.location.href="CRMSystem";
});
</script>
<div ng-controller="SystemMenuCtrl as smCtrl" align="center">
	<div style="width: 50%;position: absolute;left: 50%;margin-left: -25%" class="div_valign_middle">
		<!-- <a href="DVRSSystem" class="btn btn-primary btn-lg btn-block">DVRS</a> -->
		<!-- <a ng-repeat="item in smCtrl.systemList" href="{{item.action}}" class="btn btn-primary btn-lg btn-block" ng-bind="item.name"></a> -->
		<a id="CRMSystem" href="CRMSystem" class="btn btn-primary btn-lg btn-block">CRM</a>
	</div>
</div>
