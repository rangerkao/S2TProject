<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="MenuCtrl as mCtrl" class="max_height" >
	<div class="max_height" align="center" >
		<div class="tool">
			<div>
				<a ng-href="#/">回首頁</a>
			</div>
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
					<div>
						<a href="goSystemMenu">回系統頁</a>
					</div>
					<div>
						<a href="logout">登出</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function(){
		if(!document.getElementById("DVRS")){
			/* $("#sc").append("<div id='DVRS'>"+
					"<script src='js/DVRS/DVRS_menu.js' \x3C/script>"+
				"</div>"); */
				
			var DVRS = document.createElement("div");
			DVRS.setAttribute("id", "DVRS");
			$("#sc").after(DVRS);
			
			/* var DVRS_admin = document.createElement("script");
			//DVRS_admin.setAttribute("type","text/javascript");
			DVRS_admin.setAttribute("src", "js/DVRS/DVRS_admin.js");
			$("#DVRS").append(DVRS_admin);   */ 
			
			var DVRS_menu = document.createElement("script");
			//DVRS_menu.setAttribute("type","text/javascript");
			DVRS_menu.setAttribute("src", "js/DVRS/DVRS_menu.js");
			$("#DVRS").append(DVRS_menu); 
			
			/*  var directive = document.createElement("script");
			//directive.setAttribute("type","text/javascript");
			directive.setAttribute("src", "js/directive.js");
			$("#DVRS").append(directive);    */
		}
		
		if(!document.getElementById("directive")){
			 var directive = document.createElement("script");
				directive.setAttribute("id","directive");
				directive.setAttribute("src", "js/directive.js");
				$("#sc").append(directive);  
		} 
	});
</script> 