<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	<table class="table-bordered table-hover" style="width: 50%" id="table1">
		<tr class="even_columm" >
			<td ng-repeat="head in tableHeader" align="center" style="width: ={{head._width}}">{{head.name}}</td>
			<td>&nbsp;</td>
		</tr>
		<tr ng-repeat="data in tableList" class="{{($odd?'even_columm':'odd_columm')}}">
			<td ng-repeat="head in tableHeader" align="center" style="width: ={{head._width}}">{{data[head.col]}}</td>
			<td align="center"><button class='btn btn-primary btn-sm' onclick='chooseRow(this)'>選擇</button></td>
		</tr>
	</table>
</div>
