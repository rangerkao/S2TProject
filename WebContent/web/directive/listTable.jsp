<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	<table class="table-bordered table-hover" style="width: {{_width}}" id="{{tableId}}">
		<tr class="even_columm" >
			<td ng-repeat="head in tableHeader" align="center" style="width: ={{head._width}}">{{head.name}}</td>
		</tr>
		<tr ng-repeat="data in tableData" class="{{($odd?'even_columm':'odd_columm')}}" ng-click='onSelect(data)'>
			<td ng-repeat="head in tableHeader" align="center" style="width: ={{head._width}}">{{data[head.col]}}</td>
		</tr>
	</table>
</div>
