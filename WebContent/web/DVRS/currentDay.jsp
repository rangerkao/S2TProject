<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="CurrentDayCtrl as Ctrl" class="container-fluid max_height" style="vertical-align: middle;">
	<div class="row max_height" align="center">
		<h3>單日累計查詢</h3>
		<div class="col-xs-3" align="right">查詢期間從</div>
		<div class="col-xs-3">
			<p class="input-group">
				<input 	type="text" class="form-control" 
					datepicker-popup="yyyy-MM-dd" 
					ng-model="Ctrl.dateFrom" 
					is-open="Ctrl.fromOpened" 
					min-date="'20000101'" 
					max-date="Ctrl.maxDate" 
					init-date="Ctrl.maxDate"
					datepicker-options="dateOptions" 
					date-disabled="Ctrl.disabled(date, mode)" 
					ng-required="true"  close-text="Close" 
					ng-change="Ctrl.dateChange = true"/>
				<span class="input-group-btn">
					<button type="button" class="btn btn-default" ng-click="Ctrl.fromOpened = true">
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
					ng-model="Ctrl.dateTo" 
					is-open="Ctrl.toOpened" 
					min-date="'20000101'" 
					max-date="Ctrl.maxDate" 
					init-date="Ctrl.maxDate"
					datepicker-options="dateOptions" 
					date-disabled="Ctrl.disabled(date, mode)" 
					ng-required="true"  close-text="Close" 
					ng-change="Ctrl.dateChange = true"/>
				<span class="input-group-btn">
					<button type="button" class="btn btn-default" ng-click="Ctrl.toOpened = true">
						<i class="glyphicon glyphicon-calendar"></i>
					</button>
				</span>
			</p>
		</div>
		<div class="col-xs-2">
		</div>
		<div class="col-xs-4" align="right"><label for="imsi">IMSI:</label></div>
		<div class="col-xs-2" align="left"><input ng-model="Ctrl.imsi" type="text" /></div>
		<div class="btn-group col-xs-6">
			<input type="button" class="btn btn-primary btn-sm" ng-click="Ctrl.query()" value="查詢">
			<input type="button" class="btn btn-primary btn-sm" ng-click="Ctrl.imsi=''" value="清除">
		</div>
		<div class="col-xs-12">
			<font size="2" color="red">(查詢IMSI時可使用"*"取代某區段號碼進行模糊查詢)</font>
		</div>
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