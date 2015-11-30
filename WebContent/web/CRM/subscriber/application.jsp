<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller = "CRMAppCtrl as appCtrl">
	<h4>{{tab.title}}({{appCtrl.appMsg}})</h4>
	<div ng-show="sCtrl.role=='admin'">
	<br>
		<label>新增項目</label>
		<select ng-model="appCtrl.appType" ng-options="type.value as type.name for type in appCtrl.appTypes"></select>
		<button ng-disabled="appCtrl.custInfo.serviceId==null" class="btn btn-info btn-xs" 
				ng-click="appCtrl.insertApp(appCtrl.appType)" >新增</button>
	</div>
	<page-table 
		table-width="50%" 
		table-header="appCtrl.appHeader" 
		table-data="appCtrl.appList" 
		>
	</page-table>
	
</div>