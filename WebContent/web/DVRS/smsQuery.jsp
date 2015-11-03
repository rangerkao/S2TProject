<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="SmsQueryCtrl as Ctrl" class="container-fluid max_height" style="vertical-align: middle;">
	<div class="row max_height" align="center">
		<H3>超量簡訊發送查詢頁面</H3>
		<div class="col-xs-12">
				查詢期間從
				<input 	type="text" style=" height: 32px;"
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
					<button type="button" class="btn btn-default" ng-click="Ctrl.fromOpened = true">
						<i class="glyphicon glyphicon-calendar"></i>
					</button>
			到
				<input 	type="text"  style=" height: 32px;"
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
					<button type="button" class="btn btn-default" ng-click="Ctrl.toOpened = true">
						<i class="glyphicon glyphicon-calendar"></i>
					</button>
		</div>
		<div class="col-xs-12">
			<label>門號:</label>
			<input type="text" ng-model="Ctrl.MSISDN">
			<div class="btn-group" >
				<input type="button" class="btn btn-primary btn-sm" ng-click="Ctrl.query()" value="查詢">
				<input type="button" class="btn btn-primary btn-sm" ng-click="Ctrl.clearDate()" value="清除">
				<input type="button" class="btn btn-primary btn-sm" ng-click="Ctrl.createExcel('#PageTable')" value="下載Excel"> 
			</div>
		</div>
		<div class="col-xs-12"><font size="2" color="red">(查詢門號時可使用"*"取代某區段號碼進行模糊查詢)</font><label id="Qmsg" style="height: 30px;">&nbsp;</label></div>
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