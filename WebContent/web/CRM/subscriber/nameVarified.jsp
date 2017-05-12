<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div ng-controller="CRMNameVarified as nCtrl">
	<h4>{{tab.title}}({{nCtrl.nvMsg}})</h4>
	<table class="dataTable" style="width: 80%;">
		<tr class="chinaArea">
			<th colspan="{{nCtrl.dataHeader1.length}}"  bgcolor="#ff9933">中國</th>
		</tr>
		<tr class="chinaArea">
			<th ng-repeat="item in nCtrl.dataHeader1" colspan="{{item.colSize}}">{{item.name}}<font color="red" ng-bind="nCtrl.errorMsg[item.col]"></font></th>
		</tr>
		<tr class="chinaArea">
			<td ng-repeat="item in nCtrl.dataHeader1"  ng-switch on="item.type" colspan="{{item.colSize}}">
				<select ng-switch-when="selector" ng-model="nCtrl.data.china[item.col]"  ng-options="type.value as type.name for type in nCtrl.identityTypes"  ng-focus="nCtrl.focus('id')"  ng-change="nCtrl.defocus('id')" size="{{item._width}}" ></select>
				<input ng-switch-when="input" ng-model="nCtrl.data.china[item.col]"  ng-blur="nCtrl.defocus(item.col)" ng-focus="nCtrl.focus(item.col)" style="width: 90%"	>
				<div ng-switch-default >{{nCtrl.data.china[item.col]}}</div>
			</td>
		</tr>
		<tr class="chinaArea">
			<th ng-repeat="item in nCtrl.dataHeader2" colspan="{{item.colSize}}">{{item.name}}<font color="red" ng-bind="nCtrl.errorMsg[item.col]"></font></th>
			<th></th>
		</tr>
		<tr class="chinaArea">
			<td ng-repeat="item in nCtrl.dataHeader2"  ng-switch on="item.type" colspan="{{item.colSize}}">
				<select ng-switch-when="selector" ng-model="nCtrl.data.china[item.col]"  ng-options="type.value as type.name for type in nCtrl.identityTypes"   size="{{item._width}}"></select>
				<input ng-switch-when="input" ng-model="nCtrl.data.china[item.col]"  ng-blur="nCtrl.defocus(item.col)" ng-focus="nCtrl.focus(item.col)"  style="width: 90%" >
				<div ng-switch-default >{{nCtrl.data.china[item.col]}}</div>
			</td>
			<td colspan="1" align="right">
				<!-- <input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.query()" value="查詢" ng-disabled="nCtrl.buttonDis"> -->
				<input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.update('china',nCtrl.data.china,nCtrl.oData.china,nCtrl.chinaMsisdn)" value="更新" >
				<!-- <input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.clear()" value="清除"  > -->
				<input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.varify('china',nCtrl.data.china,nCtrl.oData.china,nCtrl.chinaMsisdn)" value="認證"  >
			</td>
		</tr>
		<tr ng-hide = "!nCtrl.sgpShow"  class="sgpArea">
			<th colspan="{{nCtrl.dataHeader1.length}}"  bgcolor="#ff9933">新加坡</th>
		</tr>
		<tr ng-hide = "!nCtrl.sgpShow"  class="sgpArea">
			<th ng-repeat="item in nCtrl.dataHeader1" colspan="{{item.colSize}}">{{item.name}}<font color="red" ng-bind="nCtrl.errorMsg[item.col]"></font></th>
		</tr>
		<tr ng-hide = "!nCtrl.sgpShow"  class="sgpArea">
			<td ng-repeat="item in nCtrl.dataHeader1"  colspan="{{item.colSize}}">
				<div>{{nCtrl.data.sgp[item.col]}}</div>
			</td>
		</tr>
		
		<tr ng-hide = "!nCtrl.sgpShow" class="sgpArea">
			<th ng-repeat="item in nCtrl.dataHeader2" colspan="{{item.colSize}}">{{item.name}}<font color="red" ng-bind="nCtrl.errorMsg[item.col]"></font></th>
			<th></th>
		</tr>
		<tr ng-hide = "!nCtrl.sgpShow" class="sgpArea">
			<td ng-repeat="item in nCtrl.dataHeader2"  ng-switch on="item.type"  colspan="{{item.colSize}}">
				<input ng-switch-when="input" ng-model="nCtrl.data.sgp[item.col]"  style="width: {{item._width}}" >
				<div ng-switch-default >{{nCtrl.data.sgp[item.col]}}</div>
			</td>
			<td colspan="1" align="right">
				<!-- <input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.query()" value="查詢" ng-disabled="nCtrl.buttonDis"> -->
				<input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.update('sgp',nCtrl.data.sgp,nCtrl.oData.sgp,nCtrl.sgp)" value="更新" >
				<!-- <input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.clear()" value="清除"  > -->
				<input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.varify('sgp',nCtrl.data.sgp,nCtrl.oData.sgp,nCtrl.sgp)" value="認證"  >
			</td>
		</tr>
		
		<tr style="background-color: black">
			<td  colspan="{{nCtrl.dataHeader1.length}}" style="color: white;" align="center">歷史資料</td>
		</tr>
		
		<tr ng-repeat-start="item in nCtrl.history.china"  style="background-color: rgb(200, 200, 200);border-bottom-color: black;border-bottom-style: solid;border-bottom-width: 0.5px;">
			<td ng-repeat="header in nCtrl.dataHeader1" >{{item[header.col]}}</td>
		</tr>
		<tr ng-repeat-end style="background-color: rgb(200, 200, 200);border-bottom-color: black;border-bottom-style: solid;border-bottom-width: 2px;">
			<td ng-repeat="header in nCtrl.dataHeader2"  colspan="{{header.colSize}}">{{item[header.col]}}</td>
			<td></td>
		</tr>
		
		<tr ng-repeat-start="item in nCtrl.history.sgp"  style="background-color: rgb(200, 200, 200);border-bottom-color: black;border-bottom-style: solid;border-bottom-width: 0.5px;">
			<td ng-repeat="header in nCtrl.dataHeader1" >{{item[header.col]}}</td>
		</tr>
		<tr ng-repeat-end style="background-color: rgb(200, 200, 200);border-bottom-color: black;border-bottom-style: solid;border-bottom-width: 2px;">
			<td ng-repeat="header in nCtrl.dataHeader2"  colspan="{{header.colSize}}">{{item[header.col]}}</td>
			<td></td>
		</tr>
		
	</table>
<!-- 
	<table class="dataTable" style="width: 80%;" >
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
					<option value="">請選擇...</option>
					<option ng-repeat = "type in nCtrl.identityTypes" value="{{type.value}}"   >{{type.name}}</option>
				</select>
				<input ng-switch-default type="text"  ng-model="nCtrl.data[item.col]" style="width: 120px" ng-change="nCtrl.validateInput(item.col)" 
							ng-blur="nCtrl.defocus(item.col)" ng-focus="nCtrl.focus(item.col)" >
			</td>
			<td ng-repeat="item in nCtrl.dataCostant" >{{nCtrl.data[item.col]!=null?nCtrl.data[item.col]:nCtrl[item.col]}}	</td>
			<td ng-repeat="item in nCtrl.dataCostant">
				<label>{{nCtrl.data[item.col]}}</label>
			</td>
		</tr>
		<tr >
			<th colspan="{{nCtrl.dataInput.length+nCtrl.dataCostant.length-nCtrl.extra.length}}" >{{nCtrl.remark.name}}</th>
			<th ng-repeat="item in nCtrl.extra" width="{{item._width}}">{{item.name}}</th>
		</tr>
		<tr >
			<td colspan="{{nCtrl.dataInput.length+nCtrl.dataCostant.length-nCtrl.extra.length}}" ><input type="text"  ng-model="nCtrl.data[nCtrl.remark.col]"  style="width: 100%"></td>
			<td ng-repeat="item in nCtrl.extra" >{{nCtrl.data[item.col]}}</td>
		</tr>
		<tr style="background-color: black">
			<td  colspan="{{nCtrl.dataHeader.length}}" style="color: white;" align="center">歷史資料</td>
		</tr>
		History Table
		<tr ng-repeat-start="item in nCtrl.history"  style="background-color: rgb(200, 200, 200);border-bottom-color: black;border-bottom-style: solid;border-bottom-width: 0.5px;">
			<td ng-repeat="header in nCtrl.dataHeader" >
				{{item[header.col]!=null?item[header.col]:""}}
			</td>
			<td ng-repeat="header in nCtrl.dataInput" >{{item[header.col]}}</td>
			<td ng-repeat="header in nCtrl.dataCostant" >{{item[header.col]}}	</td>
		</tr>
		<tr ng-repeat-end style="background-color: rgb(200, 200, 200);border-bottom-color: black;border-bottom-style: solid;border-bottom-width: 2px;">
			<td colspan="{{nCtrl.dataHeader.length}}" >
				{{item[nCtrl.remark.col]!=null?item[nCtrl.remark.col]:""}}
			</td>
			<td colspan="{{nCtrl.dataInput.length+nCtrl.dataCostant.length-nCtrl.extra.length}}" >{{item[nCtrl.remark.col]}}</td>
			<td ng-repeat="header in nCtrl.extra" >{{item[header.col]}}</td>
		</tr>
	</table>
	<div>
		<br>
	</div> -->
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