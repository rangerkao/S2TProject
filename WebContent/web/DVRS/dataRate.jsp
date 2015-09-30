<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="DataRateCtrl as Ctrl" class="container-fluid max_height" style="vertical-align: middle;">
	<div class="row max_height" align="center">
		<h3>費率查詢頁面</h3>
		<div class="col-xs-12"> 
			<input type="button" class="btn btn-primary btn-sm" onclick="createExcel()" value="下載Excel"> 
		</div>
		<page-table 
			table-header="Ctrl.dataHeader" 
			table-data="Ctrl.dataList">
		</page-table>
		<div class="col-xs-12" align="left"> 
			<div ng-model="Ctrl.Error"></div>
		</div>
	</div>
</div>