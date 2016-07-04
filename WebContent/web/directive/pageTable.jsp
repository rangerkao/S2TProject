<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-model="tablePage" >		
	<div class="col-xs-12" align="center" style="margin-bottom: 10px;"> 
		<button type="button" name="Previous"  class="pagination btn btn-warning" ng-click='changePage("before")' style="width: 100px;padding: 3px;margin: 0px;">
			<span class="glyphicon glyphicon-chevron-left"></span> Previous
		</button>
		<input type="text" ng-model="pageNum" style="width: 25px;">
		<button type="button" name="Next" class="pagination btn btn-warning" ng-click='changePage("after")' style="width: 100px;padding: 3px;margin: 0px;"> 
			Next<span class="glyphicon glyphicon-chevron-right"></span>
		</button>
		<span style="width: 50px;">&nbsp;</span>
		<label style="margin-right: 10px" >共<span style="width: 50px;"  ng-bind="totalPage"></span>頁</label>
		<span style="width: 50px;">&nbsp;</span>
		<label>每頁筆數</label>
		<input ng-model='onePage' type="text" value="10" style="width: 50px;">
		<label>共<span style="width: 50px;" >{{tableData==null?'0':tableData.length}}</span>筆</label>
	</div>
	<div class="col-xs-12 ddT table-responsive" >
		<table class="table table-condensed " style="width:{{tableWidth}};">
			<tr class="even_columm">
				<td style="">
					<table class=" table-bordered table-hover" style="width: inherit;" >
						<tr  >
							<td ng-repeat="head in tableHeader" align="center" ng-style="{width:head._width}">{{head.name}}</td>
							<td align="center" style="width: 4%"></td>
						</tr>
					</table>
				</td>
				<td style="width:16px;">
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div style="overflow-y:scroll; ;max-height:{{tableHeight}};width: inherit">
						<table class=" table-bordered table-hover" style="width:100%;" id="PageTable" >
							<tr ng-repeat="data in dataList" class="{{($odd?'even_columm':'odd_columm')}}" ng-click='onSelect(data)'>
								<td ng-repeat="head in tableHeader" align="center" ng-style="{width:head._width}">{{data[head.col]}}</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</div> 

</div>
