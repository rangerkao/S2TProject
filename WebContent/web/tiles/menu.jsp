<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="MenuCtrl as mCtrl" class="max_height" >
	<div class="max_height" align="center" >
		<div class="tool">
			<span>{{mCtrl.selectedItem}}</span>
			<input type="button" ng-click="mCtrl.query()" value="query">
		</div>
		<div class="accordion">
			<div>
				<h4 ng-click="mCtrl.select(0)">管理者功能</h4>
				<div id="content0" class="content {{mCtrl.selectedItem===0?'active':''}}" >
					<div ng-repeat="element in mCtrl.adminList">
						<a ng-href="#/{{element.action}}">{{element.name}}</a>
					</div>
				</div>
				<h4 ng-click="mCtrl.select(1)">設定功能</h4>
				<div id="content1" class="content {{mCtrl.selectedItem===1?'active':''}}" >
					<div ng-repeat="element in mCtrl.settingList">
						<a ng-href="#/{{element.action}}">{{element.name}}</a>
					</div>
				</div>
				<h4 ng-click="mCtrl.select(2)">查詢功能</h4>
				<div id="content2" class="content {{mCtrl.selectedItem===2?'active':''}}" >
					<div ng-repeat="element in mCtrl.searchList">
						<a ng-href="#/{{element.action}}">{{element.name}}</a>
					</div>
				</div>
				<h4 ng-click="mCtrl.select(3)">其他功能</h4>
				<div id="content3" class="content {{mCtrl.selectedItem===3?'active':''}}" >
					<div ng-repeat="element in mCtrl.elseList">
						<a ng-href="#/{{element.action}}">{{element.name}}</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
