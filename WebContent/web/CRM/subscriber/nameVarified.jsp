<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div ng-controller="CRMNameVarified as nCtrl">
	<h4>{{tab.title}}({{nCtrl.nvMsg}})</h4>
	<input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.query()" value="查詢" ng-disabled="dCtrl.buttonDis">
	<input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.insertOrUpdate()" value="新增或更新" ng-disabled="dCtrl.buttonDis">
	<!-- <input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.clear()" value="清除"  > -->
	<!-- <input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.cancelSendDate()" value="移除發送時間"  > -->
	<table class="dataTable" style="width: 80%">
		<tr>
		</tr>
		<tr>
			<th ng-repeat="item in nCtrl.dataInput" width="{{item._width}}">{{item.name}}<font color="red" ng-bind="nCtrl.errorMsg[item.col]"></font></th>
			<th ng-repeat="item in nCtrl.dataCostant" width="{{item._width}}">{{item.name}}</th>
		</tr>
		<tr>
			<td ng-repeat="item in nCtrl.dataInput"  ng-switch on="item.col">
				<select ng-switch-when="type" ng-model="nCtrl.data[item.col]"  ng-options="type.value as type.name for type in nCtrl.identityTypes"   
				ng-init="nCtrl.data[item.col] = nCtrl.identityTypes[nCtrl.defalutType].value"  ng-change="nCtrl.validateInput(item.col)" >
					<!-- <option value="">請選擇...</option> -->
				<!-- 	<option ng-repeat = "type in nCtrl.identityTypes" value="{{type.value}}"   >{{type.name}}</option> -->
				</select>
				<input ng-switch-default type="text"  ng-model="nCtrl.data[item.col]" style="width: 120px" ng-change="nCtrl.validateInput(item.col)" 
							ng-blur="nCtrl.defocus(item.col)" ng-focus="nCtrl.focus(item.col)">
			</td>
			<td ng-repeat="item in nCtrl.dataCostant" >
				{{nCtrl.data[item.col]!=null?nCtrl.data[item.col]:nCtrl[item.col]}}
			</td>
			<!-- <td ng-repeat="item in nCtrl.dataCostant">
				<label>{{nCtrl.data[item.col]}}</label>
			</td> -->
		</tr>
		<tr >
			<th colspan="{{nCtrl.dataHeader.length-1}}">{{nCtrl.remark.name}}</th>
		</tr>
		<tr >
			<td colspan="{{nCtrl.dataHeader.length-1}}"><input type="text"  ng-model="nCtrl.data[nCtrl.remark.col]"  style="width: 80%"></td>
		</tr>
		<tr style="background-color: black">
			<td  colspan="{{nCtrl.dataHeader.length}}" style="color: white;" align="center">歷史資料</td>
		</tr>
		<!-- History Table -->
		<tr ng-repeat-start="item in nCtrl.history"  style="background-color: rgb(200, 200, 200);">
			<td ng-repeat="header in nCtrl.dataHeader" >
				{{item[header.col]!=null?item[header.col]:""}}
			</td>
		</tr>
		<tr ng-repeat-end style="background-color: rgb(200, 200, 200);">
			<td colspan="{{nCtrl.dataHeader.length}}" >
				{{item[nCtrl.remark.col]!=null?item[nCtrl.remark.col]:""}}
			</td>
		</tr>
	</table>
	<div>
		<br>
	</div>
	<!-- History Table -->
	<table style="width: 80%">
		<tr ng-repeat-start="item in nCtrl.history" >
			<td ng-repeat="header in dataHeader">
				{{item[header.col]!=null?item[header.col]:""}}
			</td>
		</tr>
		<tr ng-repeat-end >
			<td>
				{{item[remark.col]!=null?item[remark.col]:""}}
			</td>
		</tr>
	</table>
	<!-- <div align="left">
		<form class="form-horizontal" role="form" >
			<div class="form-group " style="width: 100%" ng-repeat="item in nCtrl.dataCostant">
				<label class="col-sm-2 control-label">{{item.name}}:</label>
				<div class="col-sm-10">
					<p class="form-control-static">{{nCtrl.data[item.col]!=null?nCtrl.data[item.col]:nCtrl[item.col]}}</p>
				</div>
			</div>
		</form>
	</div>
	Type:{{nCtrl.data["type"]}} -->
	<!-- {{nCtrl.serviceId}}
	<br>
	{{nCtrl.s2tMsisdn}}
	<br>
	{{nCtrl.chtMsisdn}}
	<br>
	{{nCtrl.chinaMsisdn}} -->
</div>