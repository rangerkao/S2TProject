<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="NumberChangeCtrl as Ctrl" class="container-fluid max_height" style="vertical-align: middle;">
	<div class="row max_height" align="center">
		<h3>換號記錄查詢查詢</h3>
		<div class="col-xs-5" align="right"><label>IMSI:</label></div>
		<div class="col-xs-7" align="left"><input type="text" ng-model="Ctrl.imsi"></div>
		<div class="col-xs-12" align="center"><input type="button" value="查詢" ng-click="Ctrl.query()" class="btn btn-primary btn-sm"></div>
		<div class="col-xs-12"><font size="2" color="red">(查詢IMSI時可使用"*"取代某區段號碼進行模糊查詢)</font></div>
		<page-table 
			table-width="90%" 
			table-header="Ctrl.dataHeader" 
			table-data="Ctrl.dataList" 
			page-num="Ctrl.pageNum" 
			>
		</page-table>
		<div class="col-xs-12" align="left"> 
			<div ng-model="Ctrl.Error"></div>
		</div>
	</div>
</div>
