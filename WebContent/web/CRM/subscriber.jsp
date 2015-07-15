<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="SubscriberCtrl as sCtrl" class="container-fluid max_height" style="vertical-align: middle;">
	<div class="row max_height" align="center">
		<div class="info">
			<label>使用者資訊</label>
			<div>
				<label ng-repeat="item in sCtrl.radioList">
					<input type="radio" ng-model="sCtrl.selectedType"  value="{{item.id}}" ng-change="sCtrl.selectType()" ng-init="sCtrl.selectedType='id'">{{item.name}}
				</label>
				<input type="text" ng-model="sCtrl.input">
				<input type="button" value="search" ng-click="sCtrl.queryList()">
				<select ng-change="sCtrl.selectId()" ng-model="sCtrl.selectedId" >
					<option value="" selected="selected">請選擇...</option>
					<option ng-repeat="item in sCtrl.IDList" value="{{item.id}}">{{item.name}}</option>
				</select>
			</div>
			<table border="1" class="col-xs-12">
				<!-- <tr>
					<td>Service ID:</td><td ng-bind="sCtrl.custInfo.Serviceid"></td>
					<td>中華號碼:</td><td ng-bind="sCtrl.custInfo.TWNcode"></td>
					<td>香港主號:</td><td ng-bind="sCtrl.custInfo.S2Tcode"></td>
				</tr> -->
				<tr>
					<td>姓名:</td><td ng-bind="sCtrl.custInfo['name']"></td>
					<td>統一編號/身份證字號:</td><td ng-bind="sCtrl.custInfo.idTaxid"></td>
					<td>生日：</td><td ng-bind="sCtrl.custInfo.birthday"></td>
				</tr>
				<tr>
					<td>聯絡電話：</td><td ng-bind="sCtrl.custInfo.phone"></td>
					<td>Email：</td><td colspan="3" ng-bind="sCtrl.custInfo.email"></td>
				</tr>
				<tr>
					<td>戶籍地址：</td><td colspan="2" ng-bind="sCtrl.custInfo.permanentAddress"></td>
					<td>帳單地址：</td><td colspan="2" ng-bind="sCtrl.custInfo.billingAddress"></td>
				</tr>
				<tr>
					<td>代辦處代號:</td><td ng-bind="sCtrl.custInfo.agency"></td>
					<td>其他：</td><td colspan="3" ng-bind="sCtrl.custInfo.remark"></td>
				</tr>
			</table>
		</div>
		<div class="detail">
			<div class="tabs">
				<ul class="nav nav-tabs">
					<li role="presentation" class="{{$index===sCtrl.selectedTab?'active':''}}" ng-repeat="tab in sCtrl.tabs">
						<a class="tab bg-info" ng-click="sCtrl.selectTab($index)">{{tab.title}}</a>
					</li>
				</ul>
			</div>
			<div class="tabContent" ng-show="sCtrl.selectedTab===0">
				<!-- 費用記錄 -->
				客戶費用記錄
			</div>
			<div class="tabContent" ng-show="sCtrl.selectedTab===1">
				<!-- 租退記錄 -->
				客戶租退記錄
			</div>
			<div class="tabContent" ng-show="sCtrl.selectedTab===2">
				<!-- 使用記錄 -->
				客戶使用記錄
			</div>
			<div class="tabContent" ng-show="sCtrl.selectedTab===3">
				<!-- 申訴紀錄 -->
				客戶申訴紀錄
			</div>
		</div>
	</div>
</div>