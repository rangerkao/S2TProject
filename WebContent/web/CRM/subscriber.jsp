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
				<input type="button" value="search" ng-click="sCtrl.queryList()" class="btn btn-primary btn-xs">
				<!-- <select ng-change="sCtrl.selectId()" ng-model="sCtrl.selectedId" >
					<option value="" selected="selected">請選擇...</option>
					<option ng-repeat="item in sCtrl.IDList" value="{{item.idTaxid}}">{{item.name}}</option>
				</select> -->
				<button data-toggle="modal" data-target="#companyModal" class="btn btn-warning btn-xs">choose company</button>
				<modal title="Choose a company" id="companyModal">
					<table class="dataTable">
						<tr ng-repeat="item in sCtrl.IDList" ng-click="sCtrl.chooseId(item.idTaxid)">
							<td>{{item.idTaxid}}</td>
							<td>{{item.name}}</td>
							<td>{{item.permanentAddress}}</td>
					  	</tr>
					</table>
				</modal>				
				<button data-toggle="modal" data-target="#serviceidModal" class="btn btn-success btn-xs" >choose serviceId</button>
				<modal title="Choose a serviceid" id="serviceidModal">
					<table class="dataTable">
						<tr ng-repeat="item in sCtrl.serviceIdList" ng-click="sCtrl.chooseServiceId(item)">
							<td>{{item}}</td>
					  	</tr>
					</table>
				</modal>
			</div>
			<div class="col-xs-12">
				<div class="col-xs-3" align="left"><label>S2T MSISDN:</label><span ng-bind="sCtrl.custInfo.s2tMsisdn"></span></div>
				<div class="col-xs-3" align="left"><label>姓名:</label><span ng-bind="sCtrl.custInfo.name"></span></div>
				<div class="col-xs-3" align="left"><label>統一編號/證號:</label><span ng-bind="sCtrl.custInfo.idTaxid"></span></div>
				<div class="col-xs-3" align="left"><label>生日:</label><span ng-bind="sCtrl.custInfo.birthday"></span></div>
				
				<div class="col-xs-3" align="left"><label>CHT MSISDN:</label><span ng-bind="sCtrl.custInfo.chtMsisdn"></span></div>
				<div class="col-xs-3" align="left"><label>Email:</label><span ng-bind="sCtrl.custInfo.email"></span></div>
				<div class="col-xs-6" align="left"><label>帳單地址：</label><span ng-bind="sCtrl.custInfo.billingAddress"></span></div>
				
				<div class="col-xs-3" align="left"><label>Service ID：</label><span ng-bind="sCtrl.custInfo.serviceId"></span></div>
				<div class="col-xs-3" align="left"><label>聯絡電話：</label><span ng-bind="sCtrl.custInfo.phone"></span></div>
				<div class="col-xs-6" align="left"><label>戶籍地址：</label><span ng-bind="sCtrl.custInfo.permanentAddress"></span></div>
				
				<div class="col-xs-12" align="left"><label>代辦處代號:：</label><span ng-bind="sCtrl.custInfo.agency"></span></div>
			</div>

		</div>
		<div class="detail">
			<div class="tabs">
				<ul class="nav nav-tabs">
					<li role="presentation" class="{{$index===sCtrl.selectedTab?'active':''}}" ng-repeat="tab in sCtrl.tabs">
						<a class="tab bg-info" ng-click="sCtrl.selectTab($index)">{{tab.title}}</a>
					</li>
				</ul>
			</div>
			<div ng-repeat="tab in sCtrl.tabs" class="tabContent" ng-show="sCtrl.selectedTab==={{$index}}">
				<div ng-include="tab.url"></div>  
			</div>
		</div>
	</div>
</div>