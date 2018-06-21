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
	<div>
		<font color="red">方案 1:一般用戶(失效方案);2/9:一般用戶 ;3/5:華人上網包(香港);4/6:高量／華人上網包(香港+大陸);7:Joy、GO2PLAY;10:多國上網包</font>
	</div>
	<page-table 
		table-width = "100%" 
		table-header="qosCtrl.qosHeader" 
		table-data="qosCtrl.qosList">
	</page-table>
</div>