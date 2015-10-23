<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="CurrentMonthCtrl as Ctrl" class="container-fluid max_height" style="vertical-align: middle;">
	<div class="row max_height" align="center">
		<h3>單月累計查詢</h3>
		<div class="col-xs-12" align="center">
			查詢期間從
			<select ng-model="Ctrl.fy" ng-options="year for year in Ctrl.years"></select> 年
			<select ng-model="Ctrl.fm" ng-options="mon for mon in Ctrl.mons"></select>  月
			到
			<select ng-model="Ctrl.ty" ng-options="year for year in Ctrl.years"></select> 年
			<select ng-model="Ctrl.tm" ng-options="mon for mon in Ctrl.mons"></select>  月	
		</div>
		<div class="col-xs-12" align="center">
			<label>是否中斷過GPRS:</label>
			<span ng-repeat="suspend in Ctrl.suspends">
				<input type="radio" ng-model="Ctrl.suspend"  value="{{suspend.value}}">{{suspend.name}}
			</span>
		</div>
		<div class="col-xs-12" align="center">
			<label>IMSI:</label><input type="text" ng-model="Ctrl.imsi">
			<input type="button" value="查詢" ng-click="Ctrl.query()" class="btn btn-primary btn-xs">
		</div>
		<div class="col-xs-12" align="center">
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