<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="SubscriberCtrl as sCtrl" class="container-fluid max_height" style="vertical-align: middle; background-color: rgba(45, 41, 41, 0.36);">
	<div class="row max_height subPage" align="center">
		
		<div class="infoc col-xs-12">
			<div class="col-xs-12" >
				<label>使用者資訊{{sCtrl.custInfo.seq}}</label>
				<span class="glyphicon glyphicon-chevron-up" aria-hidden="true" ng-click="sCtrl.hideNotNecessaryClicked()" ng-show="!sCtrl.hideNotNecessary"></span>
				<span class="glyphicon glyphicon-chevron-down" aria-hidden="true" ng-click="sCtrl.hideNotNecessaryClicked()" ng-show="sCtrl.hideNotNecessary"></span>
				<a ng-href="logout"><font color="red" >登出</font></a>
			</div>
			<div class="col-xs-8" align="right">
				<label ng-repeat="item in sCtrl.radioList">
					<input type="radio" ng-model="sCtrl.selectedType"  value="{{item.id}}" ng-change="sCtrl.selectType()" ng-init="sCtrl.selectedType='main'">{{item.name}}
				</label>
				<input type="text" ng-model="sCtrl.input" ng-keydown="sCtrl.onSelectedTypeKeyDown($event)">
				<a type="button" value="search" ng-click="sCtrl.queryList()" class="btn btn-primary btn-xs">search</a>	
			</div>
			<div class="col-xs-4" align="left">
				<button data-toggle="modal" data-target="#companyModal" class="btn btn-warning btn-xs">choose company</button>
				<modal title="Choose a company" id="companyModal" modal-width="80%">
					<table class="dataTable" >
						<tr>
							<td align="center" width="10%">Id</td>
							<td align="center" width="20%">名稱</td>
							<td align="center" width="10%">香港主號</td>
							<td align="center" width="10%">Home MSISDN</td>
							<td align="center" width="10%">狀態</td>
							<td align="center" width="20%">啟用時間</td>
							<td align="center" width="20%">退租時間</td>
						</tr>
						<!-- <tr ng-repeat="item in sCtrl.IDList" ng-click="sCtrl.chooseServiceId(item.serviceId)"> -->
						<tr ng-repeat="item in sCtrl.IDList" ng-click="sCtrl.chooseItem(item)">
							<td align="center">{{item.idTaxid}}</td>
							<td align="center">{{item.name}}</td>
							<td align="center">{{item.s2tMsisdn}}</td>
							<td align="center">{{item.chtMsisdn}}</td>
							<td align="center">{{item.status}}</td>
							<td align="center">{{item.activatedDate}}</td>
							<td align="center">{{item.canceledDate}}</td>
					  	</tr>
					</table>
				</modal>	
				<input type="button" value="產生客戶總表" class="btn btn-success btn-xs"  ng-click="sCtrl.downLoadExcel()" >
				<input type="button" value="save" class="btn btn-danger btn-xs" style="margin-left: 50px;" ng-click="sCtrl.updateSubscriber()"  ng-show="sCtrl.showSave&&!sCtrl.hideNotNecessary">
			<!-- 	<button data-toggle="modal" data-target="#serviceidModal" class="btn btn-success btn-xs" >choose serviceId</button>
				<modal title="Choose a serviceid" id="serviceidModal">
					<table class="dataTable">
						<tr ng-repeat="item in sCtrl.serviceIdList" ng-click="sCtrl.chooseServiceId(item)">
							<td>{{item}}</td>
					  	</tr>
					</table>
				</modal> -->
			</div>
			<div class="col-xs-12 custInfo"  >
				<div class="col-sm-3" align="left">
					<label>S2T MSISDN　　：</label>
					<span ng-bind="sCtrl.custInfo.s2tMsisdn"></span>
				</div>
				<div class="col-sm-3" align="left">
					<label>Home　MSISDN　：</label>
					<span ng-bind="sCtrl.custInfo.chtMsisdn"></span>
				</div>
				
			
				<div class="col-sm-3" align="left">
					<label>Service ID　　：</label>
					<span ng-bind="sCtrl.custInfo.serviceId"></span>
				</div>
				
				
			
				<div class="col-sm-3" align="left">
					<label>狀態　　　　　：</label>
					<span ng-bind="sCtrl.custInfo.status" ></span>
				</div>
				<div class="col-sm-3" align="left" ng-dblclick="sCtrl.infoEditMod('agency')" ng-hide="sCtrl.hideNotNecessary">
					<label>代辦處代號　　：</label>
					<span ng-bind="sCtrl.custInfo.agency" ng-show="sCtrl.show""></span>
					<input type="text" ng-model = "sCtrl.custInfo.agency" ng-show="!sCtrl.show" ng-change="sCtrl.whenInfoCahnge('agency')">
				</div>
				<div class="col-sm-3" align="left" ng-dblclick="sCtrl.infoEditMod('name')" ng-hide="sCtrl.hideNotNecessary">
					<label>姓名　　　　　：</label>
					<span ng-bind="sCtrl.custInfo.name" ng-show="sCtrl.show"></span>
					<input id="T1" type="text" ng-model = "sCtrl.custInfo.name" ng-show="!sCtrl.show"" ng-change="sCtrl.whenInfoCahnge('name')"  >
				</div>
				<div class="col-sm-6" align="left" ng-dblclick="sCtrl.infoEditMod('idTaxid')" ng-hide="sCtrl.hideNotNecessary">
					<label>統一編號／證號：</label>
					<span ng-bind="sCtrl.custInfo.idTaxid" ng-show="sCtrl.show""></span>
					<input id="T2" ng-keydown="sCtrl.onDataKeyDownToBringData($event)" type="text" ng-model = "sCtrl.custInfo.idTaxid" ng-show="!sCtrl.show" ng-change="sCtrl.whenInfoCahnge('idTaxid')">
					<a type="button" value="帶入資料" ng-click="sCtrl.queryInfo()" class="btn btn-danger btn-xs" ng-show="!sCtrl.show">帶入資料</a>
				</div>
				
				<div class="col-sm-3" align="left" ng-dblclick="sCtrl.infoEditMod('chair')" ng-hide="sCtrl.hideNotNecessary">
					<label>負責人　　　　：</label>
					<span ng-bind="sCtrl.custInfo.chair" ng-show="sCtrl.show"></span>
					<input type="text" ng-model = "sCtrl.custInfo.chair" ng-show="!sCtrl.show" ng-change="sCtrl.whenInfoCahnge('chair')">
				</div>
				<div class="col-sm-3" align="left" ng-dblclick="sCtrl.infoEditMod('chairID')" ng-show="!sCtrl.hideNotNecessary">
					<label>負責人ＩＤ　　：</label>
					<span ng-bind="sCtrl.custInfo.chairID" ng-show="sCtrl.show"></span>
					<input type="text" ng-model = "sCtrl.custInfo.chairID" ng-show="!sCtrl.show" ng-change="sCtrl.whenInfoCahnge('chairID')">
				</div>
				
				<div class="col-sm-3" align="left" ng-dblclick="sCtrl.infoEditMod('phone')" ng-hide="sCtrl.hideNotNecessary">
					<label>聯絡電話　　　：</label>
					<span ng-bind="sCtrl.custInfo.phone" ng-show="sCtrl.show""></span>
					<input type="text" ng-model = "sCtrl.custInfo.phone" ng-show="!sCtrl.show" ng-change="sCtrl.whenInfoCahnge('phone')">
				</div>
				<div class="col-sm-3" align="left" ng-dblclick="sCtrl.infoEditMod('birthday')" ng-hide="sCtrl.hideNotNecessary">
					<label>生日(民國)　　:</label>
					<span ng-bind="sCtrl.custInfo.birthday" ng-show="sCtrl.show""></span>
					<input type="text" ng-model = "sCtrl.custInfo.birthday" ng-show="!sCtrl.show" ng-change="sCtrl.whenInfoCahnge('birthday')">
				</div>
				
				<div class="col-sm-6" align="left" ng-dblclick="sCtrl.infoEditMod('permanentAddress')" ng-hide="sCtrl.hideNotNecessary">
					<label>戶籍地址　　　：</label> 
					<span ng-bind="sCtrl.custInfo.permanentAddress" ng-show="sCtrl.show"></span>
					<input type="text" ng-model = "sCtrl.custInfo.permanentAddress" ng-show="!sCtrl.show" ng-change="sCtrl.whenInfoCahnge('permanentAddress')">
				</div>
				
				<div class="col-sm-6" align="left" ng-dblclick="sCtrl.infoEditMod('billingAddress')" ng-hide="sCtrl.hideNotNecessary">
					<label>帳單地址　　　：</label>
					<span ng-bind="sCtrl.custInfo.billingAddress" ng-show="sCtrl.show""></span>
					<input type="text" ng-model = "sCtrl.custInfo.billingAddress" ng-show="!sCtrl.show" ng-change="sCtrl.whenInfoCahnge('billingAddress')">
				</div>
				<div class="col-sm-3" align="left" ng-dblclick="sCtrl.infoEditMod('email')" ng-hide="sCtrl.hideNotNecessary">
					<label>Ｅｍａｉｌ　　：</label>
					<span ng-bind="sCtrl.custInfo.email" ng-show="sCtrl.show""></span>
					<input type="text" ng-model = "sCtrl.custInfo.email" ng-show="!sCtrl.show" ng-change="sCtrl.whenInfoCahnge('email')">
				</div>
				<div class="col-sm-3" align="left"  ng-hide="sCtrl.hideNotNecessary"> <!-- ng-dblclick="sCtrl.infoEditMod('type')" -->
					<label>類　　　　　型：</label>
					<label>
						<input type="radio" name="type" ng-model="sCtrl.custInfo.type" ng-disabled="sCtrl.show.type" ng-change="sCtrl.whenInfoCahnge('type')" value="P">個人
						<input type="radio" name="type" ng-model="sCtrl.custInfo.type" ng-disabled="sCtrl.show.type" ng-change="sCtrl.whenInfoCahnge('type')" value="E">公司
					</label>
				</div>
				
	
				
			</div>
		</div>
		<!-- <span >===============================   </span>
		<span class="glyphicon glyphicon-chevron-up" aria-hidden="true" ng-click="sCtrl.hideNotNecessary=true"></span>
		
		<div class="btn-group" role="group">
				<button class="btn btn-default btn-xs dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
					頁面選單
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
					<li role="presentation" ng-repeat="tab in sCtrl.tabs">
						<a role="menuitem" tabindex="-1"  ng-click="sCtrl.selectTab($index)">{{tab.title}}</a>
					</li>
				</ul>
			</div>
			<span class="glyphicon glyphicon-chevron-down" aria-hidden="true" ng-click="sCtrl.hideNotNecessary=false"></span>
		<span >   ===============================</span> -->
		<!--  把tag置中，在父層Div加上 text-align=center，裡面ul加上display="inline-block"-->
		<div class="detail" align="center">
			 <div class="tabs" style="text-align: center;">
				<ul class="nav nav-tabs" style="display: inline-block;">
					<li role="presentation" class="{{$index===sCtrl.selectedTab?'active':''}}" ng-repeat="tab in sCtrl.tabs">
						<a class="tab bg-info" ng-click="sCtrl.selectTab($index)" style="padding: 5px;margin: 0px;">{{tab.title}}</a>
					</li>
				</ul>
			</div> 
			<div ng-repeat="tab in sCtrl.tabs" class="tabContent" ng-show="sCtrl.selectedTab==={{$index}}">
				<div ng-include="tab.url" ></div>  
			</div>
		</div>
	</div>
</div>
<!-- <script>
	$(document).ready(function(){
		if(!document.getElementById("CRM")){
			/* $("#sc").append("<div id='DVRS'>"+
					"<script src='js/DVRS/DVRS_menu.js' \x3C/script>"+
				"</div>"); */
				
			var CRM = document.createElement("div");
				CRM.setAttribute("id", "CRM");
			$("#sc").after(CRM);
			
			var Menu = document.createElement("script");
			//DVRS_menu.setAttribute("type","text/javascript");
			Menu.setAttribute("src", "js/CRM/Menu.js");
			$("#CRM").append(Menu);  
			
			/* var SMS = document.createElement("script");
			//DVRS_menu.setAttribute("type","text/javascript");
			SMS.setAttribute("src", "js/CRM/SMS.js");
			$("#CRM").append(SMS);  */
			
			/* var Subscriber = document.createElement("script");
			//DVRS_menu.setAttribute("type","text/javascript");
			Subscriber.setAttribute("src", "js/CRM/Subscriber.js");
			$("#CRM").append(Subscriber);  */
		}
		
		if(!document.getElementById("directive")){
			 var directive = document.createElement("script");
				directive.setAttribute("id","directive");
				directive.setAttribute("src", "js/directive.js");
				$("#sc").append(directive);  
		} 
	});
</script>  -->