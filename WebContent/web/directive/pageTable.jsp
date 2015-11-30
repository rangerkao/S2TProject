<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-model="tablePage" >		
	<div class="col-xs-12" align="center"> 
		<button type="button" name="Previous"  class="pagination btn btn-warning" ng-click='changePage("before")' style="width: 100px;">
			<span class="glyphicon glyphicon-chevron-left"></span> Previous
		</button>
		<input type="text" ng-model="pageNum" style="width: 25px;">
		<button type="button" name="Next" class="pagination btn btn-warning" ng-click='changePage("after")' style="width: 100px;"> 
			Next<span class="glyphicon glyphicon-chevron-right"></span>
		</button>
		<span style="width: 50px;">&nbsp;</span>
		<label style="margin-right: 10px" >共<span style="width: 50px;"  ng-bind="totalPage"></span>頁</label>
		<span style="width: 50px;">&nbsp;</span>
		<label>每頁筆數</label>
		<input ng-model='onePage' type="text" value="10" style="width: 50px;">
	</div>
	<div class="col-xs-12" style="height: {{tableHeight}}; overflow: auto; ">
		<table class="table-bordered table-hover" style="width:{{tableWidth}};" id="PageTable" >
			<tr class="even_columm" >
				<td ng-repeat="head in tableHeader" align="center" style="width: {{head._width}}">{{head.name}}</td>
			</tr>
			<tr ng-repeat="data in dataList" class="{{($odd?'even_columm':'odd_columm')}}" ng-click='onSelect(data)'>
				<td ng-repeat="head in tableHeader" align="center" style="width: ={{head._width}}">{{data[head.col]}}</td>
			</tr>
		</table>
	</div>
	
</div>
