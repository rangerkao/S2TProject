<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="CurrentDayCtrl as dCtrl" class="container-fluid max_height" style="vertical-align: middle;">
	<div class="row max_height" align="center">
		<h4>{{tab.title}}({{dCtrl.dayMsg}})</h4>
		<div class="col-xs-3" align="right">查詢期間從</div>
		<div class="col-xs-3">
			<datepicker-directive selected-value = "dCtrl.dateFrom" ></datepicker-directive> 
		</div>
		<div class="col-xs-1">
			到
		</div>
		<div class="col-xs-3">
			<datepicker-directive selected-value = "dCtrl.dateTo" ></datepicker-directive> 
		</div>
		<div class="btn-group col-xs-2">
			<input type="button" class="btn btn-primary btn-xm" ng-click="dCtrl.query(dCtrl.s2tIMSI)" value="查詢">
		</div>
		<div class="col-xs-12" ng-show="sCtrl.testMode"><input type="text" ng-model="dCtrl.daytest"><input type="button" value="dayTest" ng-click="dCtrl.query(dCtrl.daytest)"></div>
		
		<page-table 
			table-width="90%" 
			table-header="dCtrl.dataHeader" 
			table-data="dCtrl.dataList" 
			page-num="dCtrl.pageNum" 
			>
		</page-table>
	</div>
</div>