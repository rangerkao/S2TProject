<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="CRMElseCtrl as eCtrl">
	<h4>{{tab.title}}({{eCtrl.elseMsg}})</h4>
	<div align="left">
		<form class="form-horizontal" role="form" >
			<div class="form-group elseInfoList" style="width: 100%">
				<label class="col-sm-2 control-label">s2t IMSI:</label>
				<div class="col-sm-10">
					<p class="form-control-static">{{eCtrl.s2tIMSI}}</p>
				</div>
			</div>
	  		<div class="form-group elseInfoList" style="width: 100%">
				<label class="col-sm-2 control-label">home IMSI:</label>
				<div class="col-sm-10">
					<p class="form-control-static">{{eCtrl.homeIMSI}}</p>
				</div>
			</div>
			<div class="form-group elseInfoList" style="width: 100%">
				<label class="col-sm-2 control-label">VLN:</label>
				<div class="col-sm-10">
					<div class="col-xs-12" ng-show="sCtrl.testMode"><input type="text" ng-model="eCtrl.vlntest"><input type="button" value="vlntest" ng-click="eCtrl.queryVLN(eCtrl.vlntest)"></div>
					<p class="form-control-static">{{eCtrl.VLN}}</p>
				</div>
			</div>
			<div class="form-group elseInfoList" style="width: 100%">
				<label class="col-sm-2 control-label">資費:</label>
				<div class="col-sm-10">
					<div class="form-control-static" ng-show="eCtrl.privePlanId!=null">
						{{eCtrl.privePlanId.aliases}}({{eCtrl.privePlanId.id}})
					</div>
				</div>
			</div>
			<div class="form-group elseInfoList" style="width: 100%">
				<label class="col-sm-2 control-label">說明:</label>
				<div class="col-sm-10">
					<div class="form-control-static" ng-show="eCtrl.privePlanId!=null">
						{{eCtrl.privePlanId.production}}:{{eCtrl.privePlanId.desc}}
					</div>
				</div>
			</div>
			
			<div class="form-group elseInfoList" style="width: 100%">
				<label class="col-sm-2 control-label">啟用時間:</label>
				<div class="col-sm-10">
					<p class="form-control-static">{{eCtrl.activatedDate}}</p>
				</div>
			</div>
			<div class="form-group elseInfoList" style="width: 100%">
				<label class="col-sm-2 control-label">退租時間:</label>
				<div class="col-sm-10">
					<p class="form-control-static">{{eCtrl.canceledDate}}</p>
				</div>
			</div>
			<div class="form-group elseInfoList" style="width: 100%">
				<label class="col-sm-2 control-label">數據狀態:</label>
				<div class="col-sm-10">
					<p class="form-control-static" ng-show="eCtrl.gprsStatus!=null">{{eCtrl.gprsStatus==0?'關閉':'開啟'}}({{eCtrl.gprsStatus}})</p>
				</div>
			</div>
			<div class="form-group elseInfoList" style="width: 100%">
				<label class="col-sm-2 control-label">華人上網包:</label>
				<div class="col-sm-10">
					<div class="col-xs-12" ng-show="sCtrl.testMode"><input type="text" ng-model="eCtrl.addontest"><input type="button" value="addontest" ng-click="eCtrl.queryAddons(eCtrl.addontest)"></div>
					<p ng-show="eCtrl.addons.length==0" class="form-control-static">無</p>
					<div class="form-control-static">
						<table border="1" style="width: 80%;" ng-show="eCtrl.addons.length>0">					
							<tr>
								<th>SERVICEID</th><th>SERVICECODE</th><th>STATUS</th><th>STARTDATE</th><th>ENDDATE</th>
							</tr>
							<tr ng-repeat="addon in eCtrl.addons">
								<td>{{addon.serviceId}}</td><td>{{addon.serviceCode}}</td><td>{{addon.status}}</td><td>{{addon.startDate}}</td><td>{{addon.endDate}}</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="form-group elseInfoList" style="width: 100%">
				<label class="col-sm-2 control-label">美國流量包:</label>
				<div class="col-sm-10">
					<div class="col-xs-12" ng-show="sCtrl.testMode"><input type="text" ng-model="eCtrl.USPackettest"><input type="button" value="USPackettest" ng-click="eCtrl.queryUSPacket(eCtrl.USPackettest)"></div>
					<p ng-show="eCtrl.USPackets.length==0" class="form-control-static">無</p>
					<div class="form-control-static">
						<table border="1" style="width: 80%;" ng-show="eCtrl.USPackets.length>0">					
							<tr>
								<th>STARTDATE</th><th>ENDDATE</th><th>CREATETIME</th><th>CANCELTIME</th><th>ALERTED</th>
							</tr>
							<tr ng-repeat="USPacket in eCtrl.USPackets">
								<td>{{USPacket.startDate}}</td><td>{{USPacket.endDate}}</td><td>{{USPacket.createTime}}</td><td>{{USPacket.cancelTime}}</td><td>{{USPacket.alerted}}</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>