<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="MenuCtrl as mCtrl" class="max_height" >
	<div class="max_height" align="center" >
		<div class="tool">
		</div>
		<div class="accordion">
			<div>
				<h4 ng-click="mCtrl.select(0)">查詢功能</h4>
				<div id="content0" class="content {{mCtrl.selectedItem===0?'active':''}}" >
					<div ng-repeat="element in mCtrl.searchList">
						<a ng-href="#/{{element.action}}">{{element.name}}</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function(){
		if(!document.getElementById("CRM")){
			/* $("#sc").append("<div id='DVRS'>"+
					"<script src='js/DVRS/DVRS_menu.js' \x3C/script>"+
				"</div>"); */
				
			var CRM = document.createElement("div");
				CRM.setAttribute("id", "CRM");
			$("#sc").after(CRM);
			
			var CRM_menu = document.createElement("script");
			//DVRS_menu.setAttribute("type","text/javascript");
			CRM_menu.setAttribute("src", "js/CRM/CRM_menu.js");
			$("#CRM").append(CRM_menu); 
		}
	});
</script> 
<script>
	$(document).ready(function(){
			if(!document.getElementById("directive")){
				 var directive = document.createElement("script");
					directive.setAttribute("id","directive");
					directive.setAttribute("src", "js/directive.js");
					$("#DVRS").append(directive);  
			} 
	});
</script>