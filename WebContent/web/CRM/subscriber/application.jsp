<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	{{tab.title}}({{sCtrl.appMsg}})
	<div ng-show="{{sCtrl.role=='admin'}}">
	<br>
		<label>新增項目</label>
		<select ng-model="sCtrl.appType" ng-options="type.value as type.name for type in sCtrl.appTypes"></select>
		<button ng-disabled="sCtrl.custInfo.serviceId==null" class="btn btn-info btn-xs" 
				ng-click="sCtrl.insertApp(sCtrl.appType)" >新增</button>
	</div>
	<page-table 
		table-width="50%" 
		table-header="sCtrl.appHeader" 
		table-data="sCtrl.appList" 
		>
	</page-table>
	
</div>