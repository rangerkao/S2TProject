<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	<h4>{{tab.title}}({{sCtrl.qosMsg}})</h4>
	<!-- <div class="col-xs-5" align="right"><label for="msisdn">MSISDN:</label></div>
	<div class="col-xs-7" align="left"><input type="text" id="msisdn" ng-model="sCtrl.Msisdn" /></div>
	<div class="col-xs-12" align="center">		
		<div class="btn-group" >
			<input type="button" class="btn btn-primary btn-sm" ng-click="sCtrl.queryQos()" value="查詢">
		</div>
	</div> -->
	<page-table 
		tableWidth = "80%" 
		table-header="sCtrl.qosHeader" 
		table-data="sCtrl.qosList">
	</page-table>
	<div class="col-xs-12" align="left"> 
		<div ng-model="sCtrl.Error"></div>
	</div>
</div>