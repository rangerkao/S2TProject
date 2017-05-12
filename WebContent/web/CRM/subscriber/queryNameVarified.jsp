<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div ng-controller="QueryNameVarified as qCtrl">
	<h4>{{tab.title}}({{qCtrl.nvMsg}})</h4>
	<div style="display: block;width: 80%;background-color: yellow;"><font color="red" size="4" >＊此查詢內容為資料庫所有客戶認證資訊</font></div>

	<div style="display: inline;"> 
		<input type="radio" 	ng-model = "qCtrl.type"  value="1"> service ID
		<input type="radio" 	ng-model = "qCtrl.type"  value="2"> 姓名
		<input type="radio" 	ng-model = "qCtrl.type" value="3"> 證件號碼
		<input type="radio" 	ng-model = "qCtrl.type"  value="4"> 證件類型
		<input type="radio" 	ng-model = "qCtrl.type"  value="5"> 發送認證時間("yyyy/MM/dd")
		<input type="radio" 	ng-model = "qCtrl.type"  value="6"> 中華/香港號
		<input type="radio" 	ng-model = "qCtrl.type"  value="7"> 當地副號
	</div> 
	<input type="text"  ng-model = "qCtrl.input"  ng-keydown="qCtrl.onKeyDown($event)">
	<input class="btn btn-primary btn-xs"  type="button"  ng-click="qCtrl.query()" value="查詢" ng-disabled="qCtrl.buttonDis">
	
	<table class="dataTable" style="width: 80%">
		<tr>
		</tr>
		<tr>
			<th ng-repeat="item in qCtrl.dataHeader">{{item.name}}</th>
		</tr>
		<tr>
			<th colspan="{{qCtrl.dataHeader.length}}" >{{remark.name}}</th>
		</tr>
		<!-- <tr>
			<th ng-repeat="item in qCtrl.dataHeader"  ng-switch on="item.col">
				<select ng-switch-when="type"  ng-options="type.value as type.name for type in qCtrl.identityTypes"   ng-model="qCtrl.data[item.col]"  >
					<option value="">請選擇...</option>
					<option ng-repeat = "type in nCtrl.identityTypes" value="{{type.value}}"   >{{type.name}}</option>
				</select>
				<input ng-switch-default type="text"  style="width: 120px" ng-model="qCtrl.data[item.col]"  >
			</th>
		</tr> -->
		<tr ng-repeat-start="item in qCtrl.datas"  style="background-color: rgb(200, 200, 200);border-bottom-color: black;border-bottom-style: solid;border-bottom-width: 0.5px;" >
			<td ng-repeat="header in qCtrl.dataHeader" >
				{{item[header.col]!=null?item[header.col]:""}}
			</td>
		</tr>
		<tr  ng-repeat-end style="background-color: rgb(200, 200, 200);border-bottom-color: black;border-bottom-style: solid;border-bottom-width: 2px;" >
			<td colspan="{{qCtrl.dataHeader.length}}" >
				{{item[qCtrl.remark.col]!=null?item[qCtrl.remark.col]:" "}}
			</td>
		</tr>
	</table>
</div>