<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller = "CRMAppCtrl as appCtrl">
	<h4>{{tab.title}}({{appCtrl.appMsg}})</h4>
	<div ng-show="role=='admin'||role=='apply_Proccesser'">
		<label>新增項目</label>
		<select ng-model="appCtrl.appType" ng-options="type.value as type.name for type in appCtrl.appTypes"></select>
		<button ng-disabled="appCtrl.serviceId==null || appCtrl.buttonDis==true" class="btn btn-info btn-xs" 
				ng-click="appCtrl.insertApp(appCtrl.appType)" >新增</button>
	</div>
	<span datepicker-directive selected-value = "appCtrl.date" ></span> 
	<input type="button" value="查詢" ng-click="appCtrl.query()">
	<page-table 
		table-width="50%" 
		table-header="appCtrl.appHeader" 
		table-data="appCtrl.appList" 
		>
	</page-table>
	
</div>