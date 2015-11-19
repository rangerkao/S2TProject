<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller = "CRMQosCtrl as qosCtrl">
	<h4>{{tab.title}}({{qosCtrl.qosMsg}})</h4>
	<div class="col-xs-12" ng-show="sCtrl.testMode">
		<input type="text" ng-model="qosCtrl.qostest"><input type="button" value="qostest" 
				ng-click="qosCtrl.queryQosList(qosCtrl.qostest)">
	</div>
	<!-- <div class="col-xs-5" align="right"><label for="msisdn">MSISDN:</label></div>
	<div class="col-xs-7" align="left"><input type="text" id="msisdn" ng-model="sCtrl.Msisdn" /></div>
	<div class="col-xs-12" align="center">		
		<div class="btn-group" >
			<input type="button" class="btn btn-primary btn-sm" ng-click="sCtrl.queryQos()" value="查詢">
		</div>
	</div> -->
	<page-table 
		tableWidth = "80%" 
		table-header="qosCtrl.qosHeader" 
		table-data="qosCtrl.qosList">
	</page-table>
</div>