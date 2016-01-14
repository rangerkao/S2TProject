<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="CRMElseCtrl as eCtrl">
	<h4>{{tab.title}}({{eCtrl.elseMsg}})</h4>
	<div class="col-xs-12" ><label>s2tIMSI:</label><label ng-bind="eCtrl.s2tIMSI"></label></div>
	<div class="col-xs-12" ><label>homeIMSI:</label><label ng-bind="eCtrl.homeIMSI"></label></div>
	<div class="col-xs-12" ng-show="sCtrl.testMode"><input type="text" ng-model="eCtrl.vlntest"><input type="button" value="vlntest" ng-click="eCtrl.queryVLN(eCtrl.vlntest)"></div>
	<div class="col-xs-12" ><label>VLN:</label><label ng-repeat="vln in eCtrl.VLNs">{{vln}},</label></div>
	<div class="col-xs-12" ><label>資費:</label><label ng-bind="eCtrl.privePlanId"></label></div>
	<div class="col-xs-12" ng-show="sCtrl.testMode"><input type="text" ng-model="eCtrl.addontest"><input type="button" value="addontest" ng-click="eCtrl.queryAddons(eCtrl.addontest)"></div>
	<div class="col-xs-12" ><label>華人上網包:</label>
		<table border="1" style="width: 80%;">
			<tr>
				<th>SERVICEID</th><th>SERVICECODE</th><th>STATUS</th><th>STARTDATE</th><th>ENDDATE</th>
			</tr>
			<tr ng-repeat="addon in eCtrl.addons">
				<td>{{addon.serviceId}}</td><td>{{addon.serviceCode}}</td><td>{{addon.status}}</td><td>{{addon.startDate}}</td><td>{{addon.endDate}}</td>
			</tr>
		</table>
	</div>
	<div class="col-xs-12"><label>數據狀態:</label><label ng-bind="eCtrl.gprsStatus"></label> </div>
	<div class="col-xs-12">
		<div><label>啟用時間：</label><label ng-bind="eCtrl.activatedDate"></label></div>
		<div><label>退租時間：</label><label ng-bind="eCtrl.canceledDate"></label></div>
	</div>
</div>