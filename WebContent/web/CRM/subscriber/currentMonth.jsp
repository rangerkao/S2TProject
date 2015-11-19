<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller = "CRMMonCtrl as mCtrl">
	<h4>{{tab.title}}({{mCtrl.monMsg}})</h4>

	<div class="col-xs-12" align="center">
		查詢期間從
		<select ng-model="mCtrl.fy" ng-options="year for year in mCtrl.years"></select> 年
		<select ng-model="mCtrl.fm" ng-options="mon for mon in mCtrl.mons"></select>  月
		到
		<select ng-model="mCtrl.ty" ng-options="year for year in mCtrl.years"></select> 年
		<select ng-model="mCtrl.tm" ng-options="mon for mon in mCtrl.mons"></select>  月	
	</div>
	<div class="col-xs-12" align="center">
		<label>是否中斷過GPRS:</label>
		<span ng-repeat="suspend in mCtrl.suspends">
			<input type="radio" ng-model="mCtrl.suspend"  value="{{suspend.value}}">{{suspend.name}}
		</span>
	</div>
	<div class="col-xs-12" ng-show="sCtrl.testMode"><input type="text" ng-model="mCtrl.monthtes"><input type="button" value="monthtes" ng-click="mCtrl.queryCurrentMonth(mCtrl.monthtes)"></div>
	
	<div class="col-xs-12" align="center">
		<input type="button" value="查詢" ng-click="mCtrl.queryCurrentMonth(mCtrl.s2tIMSI)" class="btn btn-primary btn-xs">
	</div>
	<div class="col-xs-12" align="center">
		<font size="2" color="red">(查詢IMSI時可使用"*"取代某區段號碼進行模糊查詢)</font>
	</div>
	<page-table 
		table-width="90%" 
		table-header="mCtrl.monthDataHeader" 
		table-data="mCtrl.monthDataList" 
		>
	</page-table>
</div>