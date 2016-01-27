<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="SubscriberCtrl as sCtrl" class="container-fluid max_height" style="vertical-align: middle; background-color: rgba(45, 41, 41, 0.36);">
	<div class="row max_height subPage" align="center">
		<div class="info">
			<label>使用者資訊{{sCtrl.custInfo.seq}}</label>
			<a ng-href="logout"><font color="red" >登出</font></a>
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
				<modal title="Choose a company" id="companyModal" modal-width="80%">
					<table class="dataTable" >
						<tr>
							<td align="center" width="10%">Id</td>
							<td align="center" width="20%">名稱</td>
							<td align="center" width="10%">香港主號</td>
							<td align="center" width="10%">主號</td>
							<td align="center" width="10%">狀態</td>
							<td align="center" width="20%">啟用時間</td>
							<td align="center" width="20%">退租時間</td>
						</tr>
						<tr ng-repeat="item in sCtrl.IDList" ng-click="sCtrl.chooseServiceId(item.serviceId)">
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
				<a class="btn btn-success btn-xs"  ng-click="sCtrl.downLoadExcel()" ng-disabled="sCtrl.buttonDis">download excel</a>	
			<!-- 	<button data-toggle="modal" data-target="#serviceidModal" class="btn btn-success btn-xs" >choose serviceId</button>
				<modal title="Choose a serviceid" id="serviceidModal">
					<table class="dataTable">
						<tr ng-repeat="item in sCtrl.serviceIdList" ng-click="sCtrl.chooseServiceId(item)">
							<td>{{item}}</td>
					  	</tr>
					</table>
				</modal> -->
			</div>
			<div class="col-xs-12">
				<div class="col-xs-3" align="left">
					<label>S2T MSISDN:</label>
					<span ng-bind="sCtrl.custInfo.s2tMsisdn"></span>
				</div>
				<div class="col-xs-3" align="left" ng-dblclick="sCtrl.infoEditMod('name')">
					<label>姓名:</label>
					<span ng-bind="sCtrl.custInfo.name" ng-show="sCtrl.show.name"></span>
					<input type="text" ng-model = "sCtrl.custInfo.name" ng-show="!sCtrl.show.name" ng-change="sCtrl.whenInfoCahnge('name')">
				</div>
				<div class="col-xs-3" align="left" ng-dblclick="sCtrl.infoEditMod('idTaxid')">
					<label>統一編號/證號:</label>
					<span ng-bind="sCtrl.custInfo.idTaxid" ng-show="sCtrl.show.idTaxid"></span>
					<input type="text" ng-model = "sCtrl.custInfo.idTaxid" ng-show="!sCtrl.show.idTaxid" ng-change="sCtrl.whenInfoCahnge('idTaxid')">
					<input type="button" value="帶入資料" ng-click="sCtrl.queryInfo()" class="btn btn-danger btn-xs" ng-show="!sCtrl.show.idTaxid">
				</div>
				<div class="col-xs-3" align="left" ng-dblclick="sCtrl.infoEditMod('birthday')">
					<label>生日(民國):</label>
					<span ng-bind="sCtrl.custInfo.birthday" ng-show="sCtrl.show.birthday"></span>
					<input type="text" ng-model = "sCtrl.custInfo.birthday" ng-show="!sCtrl.show.birthday" ng-change="sCtrl.whenInfoCahnge('birthday')">
				</div>
			</div>
			<div class="col-xs-12">
				<div class="col-xs-3" align="left">
					<label>Home MSISDN:</label>
					<span ng-bind="sCtrl.custInfo.chtMsisdn"></span>
				</div>
				<div class="col-xs-3" align="left" ng-dblclick="sCtrl.infoEditMod('email')">
					<label>Email:</label>
					<span ng-bind="sCtrl.custInfo.email" ng-show="sCtrl.show.email"></span>
					<input type="text" ng-model = "sCtrl.custInfo.email" ng-show="!sCtrl.show.email" ng-change="sCtrl.whenInfoCahnge('email')">
				</div>
				<div class="col-xs-6" align="left" ng-dblclick="sCtrl.infoEditMod('billingAddress')">
					<label>帳單地址：</label>
					<span ng-bind="sCtrl.custInfo.billingAddress" ng-show="sCtrl.show.billingAddress"></span>
					<input type="text" ng-model = "sCtrl.custInfo.billingAddress" ng-show="!sCtrl.show.billingAddress" ng-change="sCtrl.whenInfoCahnge('billingAddress')">
				</div>
			</div>
			<div class="col-xs-12">
				<div class="col-xs-3" align="left">
					<label>Service ID：</label>
					<span ng-bind="sCtrl.custInfo.serviceId"></span>
				</div>
				<div class="col-xs-3" align="left" ng-dblclick="sCtrl.infoEditMod('phone')">
					<label>聯絡電話：</label>
					<span ng-bind="sCtrl.custInfo.phone" ng-show="sCtrl.show.phone"></span>
					<input type="text" ng-model = "sCtrl.custInfo.phone" ng-show="!sCtrl.show.phone" ng-change="sCtrl.whenInfoCahnge('phone')">
				</div>
				<div class="col-xs-6" align="left" ng-dblclick="sCtrl.infoEditMod('permanentAddress')">
					<label>戶籍地址：</label> 
					<span ng-bind="sCtrl.custInfo.permanentAddress" ng-show="sCtrl.show.permanentAddress"></span>
					<input type="text" ng-model = "sCtrl.custInfo.permanentAddress" ng-show="!sCtrl.show.permanentAddress" ng-change="sCtrl.whenInfoCahnge('permanentAddress')">
				</div>
			</div>
			<div class="col-xs-12">
				<div class="col-xs-3" align="left">
					<label>狀態：</label>
					<span ng-bind="sCtrl.custInfo.status" ></span>
				</div>
				<div class="col-xs-3" align="left" ng-dblclick="sCtrl.infoEditMod('agency')">
					<label>代辦處代號：</label>
					<span ng-bind="sCtrl.custInfo.agency" ng-show="sCtrl.show.agency"></span>
					<input type="text" ng-model = "sCtrl.custInfo.agency" ng-show="!sCtrl.show.agency" ng-change="sCtrl.whenInfoCahnge('agency')">
				</div>
				<div class="col-xs-3" align="left" ng-dblclick="sCtrl.infoEditMod('type')">
					<label>類型：</label>
					<label>
						<input type="radio" name="type" ng-model="sCtrl.custInfo.type" ng-disabled="sCtrl.show.type" ng-change="sCtrl.whenInfoCahnge('type')" value="P">個人
						<input type="radio" name="type" ng-model="sCtrl.custInfo.type" ng-disabled="sCtrl.show.type" ng-change="sCtrl.whenInfoCahnge('type')" value="E">公司
					</label>
				</div>
				<div class="col-xs-3" align="left">
					<input type="button" value="save" ng-click="sCtrl.updateSubscriber()" class="btn btn-danger btn-xs" ng-show="sCtrl.showSave">
				</div>
			</div>
			<div class="col-xs-12" ng-show="sCtrl.custInfo.type=='E'">
				<div class="col-xs-6" align="left" ng-dblclick="sCtrl.infoEditMod('chair')">
					<label>負責人：</label>
					<span ng-bind="sCtrl.custInfo.chair" ng-show="sCtrl.show.chair"></span>
					<input type="text" ng-model = "sCtrl.custInfo.chair" ng-show="!sCtrl.show.chair" ng-change="sCtrl.whenInfoCahnge('chair')">
				</div>
				<div class="col-xs-6" align="left" ng-dblclick="sCtrl.infoEditMod('chairID')">
					<label>負責人 ID：</label>
					<span ng-bind="sCtrl.custInfo.chairID" ng-show="sCtrl.show.chairID"></span>
					<input type="text" ng-model = "sCtrl.custInfo.chairID" ng-show="!sCtrl.show.chairID" ng-change="sCtrl.whenInfoCahnge('chairID')">
				</div>
			</div>
			
		</div>
		<div class="detail" >
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