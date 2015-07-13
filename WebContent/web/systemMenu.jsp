<div ng-controller="SystemMenuCtrl as smCtrl" align="center">
	<div style="width: 50%;position: absolute;left: 50%;margin-left: -25%" class="div_valign_middle">
		<!-- <a href="DVRSSystem" class="btn btn-primary btn-lg btn-block">DVRS</a> -->
		<a ng-repeat="item in smCtrl.systemList" href="{{item.action}}" class="btn btn-primary btn-lg btn-block" ng-bind="item.name"></a>
	</div>
</div>
