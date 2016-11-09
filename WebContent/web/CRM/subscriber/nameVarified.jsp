<%@ page language="java" contentType="text/html; charset=BIG5"  pageEncoding="BIG5"%>
<div ng-controller="CRMNameVarified as nCtrl">
	<h4>{{tab.title}}({{nCtrl.nvMsg}})</h4>
	<input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.query()" value="�d��" ng-disabled="dCtrl.buttonDis">
	<input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.insertOrUpdate()" value="�s�W�Χ�s" ng-disabled="dCtrl.buttonDis">
	<input class="btn btn-primary btn-xs"  type="button"  ng-click="nCtrl.clear()" value="�M��"  >
	
	<table class="dataTable" style="width: 80%">
		<tr>
		</tr>
		<tr>
			<th ng-repeat="item in nCtrl.dataHeader" width="{{item._width}}">{{item.name}}</th>
		</tr>
		<tr>
			<td ng-repeat="item in nCtrl.dataInput"  ng-switch on="item.col">
				<select ng-switch-when="type" ng-model="nCtrl.bean[item.col]" >
					<option value="">�п��...</option>
					<option ng-repeat = "type in nCtrl.identityTypes" value="{{type.value}}">{{type.name}}</option>
				</select>
				<input ng-switch-default type="text"  ng-model="nCtrl.bean[item.col]" >
			</td>
		</tr>
		<tr>
			<td ng-repeat="item in nCtrl.dataHeader">{{nCtrl.data[item.col]}}</td>
		</tr>
	</table>
</div>