<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	<h4>{{tab.title}}({{sCtrl.smsMsg}})</h4>
	<div class="col-xs-3" align="right">查詢期間從</div>
	<div class="col-xs-3">
		<p class="input-group">
			<input 	type="text" class="form-control" 
				datepicker-popup="yyyy-MM-dd" 
				ng-model="sCtrl.dateFrom" 
				is-open="sCtrl.fromOpened" 
				min-date="'20000101'" 
				max-date="sCtrl.maxDate" 
				init-date="sCtrl.maxDate"
				datepicker-options="dateOptions" 
				date-disabled="sCtrl.disabled(date, mode)" 
				ng-required="true"  close-text="Close" 
				ng-change="sCtrl.dateChange = true"/>
			<span class="input-group-btn">
				<button type="button" class="btn btn-default" ng-click="sCtrl.fromOpened = true">
					<i class="glyphicon glyphicon-calendar"></i>
				</button>
			</span>
		</p>
	</div>
	<div class="col-xs-1">
		到
	</div>
	<div class="col-xs-3">
		<p class="input-group">
			<input 	type="text" class="form-control" 
				datepicker-popup="yyyy-MM-dd" 
				ng-model="sCtrl.dateTo" 
				is-open="sCtrl.toOpened" 
				min-date="'20000101'" 
				max-date="sCtrl.maxDate" 
				init-date="sCtrl.maxDate"
				datepicker-options="dateOptions" 
				date-disabled="sCtrl.disabled(date, mode)" 
				ng-required="true"  close-text="Close" 
				ng-change="sCtrl.dateChange = true"/>
			<span class="input-group-btn">
				<button type="button" class="btn btn-default" ng-click="sCtrl.toOpened = true">
					<i class="glyphicon glyphicon-calendar"></i>
				</button>
			</span>
		</p>
	</div>
	<div class="col-xs-2" align="right">
		<button type="button" class="btn btn-primary" ng-click="sCtrl.querySMSwithTime()">查詢簡訊</button>
	</div>
	<page-table 
		table-width = "100%" 
		table-header="sCtrl.smsHeader" 
		table-data="sCtrl.SMSList">
	</page-table>
</div>