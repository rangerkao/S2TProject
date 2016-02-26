<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="DataRateCtrl as dCtrl" class="container-fluid max_height" style="vertical-align: middle;">
	<div class="row max_height" align="center">
		<h4>{{tab.title}}({{dCtrl.rateMsg}})</h4>
		<select ng-change="dCtrl.conditionSelected()" ng-model="dCtrl.selectedPricePlanId">
			<option value="" >All</option>
			<option ng-repeat="v in dCtrl.pricePlanId" value="{{v}}">{{v}}</option>
		</select>
		<select ng-change="dCtrl.conditionSelected()" ng-model="dCtrl.selectedCountry">
			<option value="">All</option>
			<option ng-repeat="v in dCtrl.country" value="{{v}}">{{v}}</option>
		</select>
		<button class="btn btn-success btn-xs" ng-click="dCtrl.downLoadExcel()" ng-disabled="dCtrl.buttonDis">匯出</button>
		<page-table 
			table-width="90%" 
			table-header="dCtrl.dataHeader" 
			table-data="dCtrl.dataList"
			table-name="datarate">
		</page-table>
	</div>
</div>