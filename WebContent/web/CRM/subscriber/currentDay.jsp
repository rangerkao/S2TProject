<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="CurrentDayCtrl as dCtrl" class="container-fluid max_height" style="vertical-align: middle;">
	<div class="row max_height" align="center">
		<h4>{{tab.title}}({{dCtrl.dayMsg}})</h4>
		<div class="col-xs-12" align="right">
			<div class="col-xs-2" align="right"></div>
			<div class="col-xs-2" align="right">
				請輸入查詢期間從
			</div>
			<div class="col-xs-2" align="center">
				<span class="datepicker-directive" selected-value = "dCtrl.dateFrom" ></span>
			</div>
			<div class="col-xs-1" align="center">
				<p>到</p>
			</div>
			<div class="col-xs-2" align="center">
				<span datepicker-directive selected-value = "dCtrl.dateTo" ></span> 
			</div>
			<div class="col-xs-3" align="left">
				<input type="button" class="btn btn-primary btn-xs" ng-click="dCtrl.query(dCtrl.s2tIMSI)" value="查詢" ng-disabled="dCtrl.buttonDis">
			</div>
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