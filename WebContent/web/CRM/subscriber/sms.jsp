<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="CRMSMSCtrl as smsCtrl">
	<h4>{{tab.title}}({{smsCtrl.smsMsg}})</h4>
	<div class="col-xs-12" ng-show="sCtrl.testMode">
		<input type="text" ng-model="smsCtrl.smstest"><input type="button" value="smstest" 
				ng-click="eCtrl.querySMS(smsCtrl.smstest,smsCtrl.smstest,DateFormatString.Format(smsCtrl.dateFrom),DateFormatString.Format(smsCtrl.dateTo))">
	</div>
	<div class="col-xs-12" align="right">
			<div class="col-xs-2" align="right"></div>
			<div class="col-xs-2" align="right">
				請輸入查詢期間從
			</div>
			<div class="col-xs-2" align="center">
				<datepicker-directive selected-value = "smsCtrl.dateFrom" ></datepicker-directive> 
			</div>
			<div class="col-xs-1" align="center">
				<p>到</p>
			</div>
			<div class="col-xs-2" align="center">
				<datepicker-directive selected-value = "smsCtrl.dateTo" ></datepicker-directive> 
			</div>
			<div class="col-xs-3" align="left">
				<button type="button" class="btn btn-primary btn-xs" ng-click="smsCtrl.querySMSwithTime()" ng-disabled="smsCtrl.buttonDis">查詢簡訊</button>
			</div>
		</div>
	<page-table 
		table-width = "100%" 
		table-header="smsCtrl.smsHeader" 
		table-data="smsCtrl.SMSList">
	</page-table>
</div>