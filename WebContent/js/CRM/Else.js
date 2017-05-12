angular.module('MainApp')
	.controller('CRMElseCtrl',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){
		var self = this;
		//初始化
		self.init = function(){
			self.elseMsg = "";
			self.VLNs = [];
			self.VLN = '';
			self.addons = [];
			self.USPackets = [];
			self.gprsStatus="";
			self.s2tMsisdn = "";
			self.serviceid = "";
			self.s2tIMSI = "";
			self.homeIMSI = "";
			self.privePlanId = "";
			self.activatedDate = "";
			self.canceledDate = "";
		};
		//載入後動作
		$(document).ready(function () {
			self.init();
			//alert(self.fy+"/"+self.fm+"~"+self.ty+"/"+self.tm);
		});	
		
		//外部觸發
		$scope.$on('queryElse',function(event,data){			
			self.s2tMsisdn = data['s2tMsisdn'];
			self.serviceid = data['serviceid'];
			self.s2tIMSI = data['s2tIMSI'];
			self.homeIMSI = data['homeIMSI'];
			self.privePlanId = data['privePlanId'];
			self.activatedDate = data['activatedDate'];
			self.canceledDate = data['canceledDate'];
			self.queryVLN(self.serviceid);
			self.queryAddons(self.serviceid);
			self.queryGPRS(self.serviceid);
			self.queryUSPacket(self.serviceid)
		});
		
		$scope.$on('subReset',function(event,data){
			self.init();
		});
		
			
		//查詢VLN
		self.queryVLN = function(serviceId){
			if(!serviceId || serviceId == '')
				return;
			self.elseMsg = "查詢中...";
			AjaxService.query("queryVLNStatus",{input:serviceId})
			.success(function(data, status, headers, config) {
				if(data['error']){
					alert(data['error']);
				}else{
//					console.log(data['data']);
					self.VLNs=data['data'];
						for(var i = 0 ;i<self.VLNs.length;i++){
							self.VLN += self.VLNs[i]+',';
						}
						if(self.VLN.length>0)
							self.VLN = self.VLN.substring(0,self.VLN.length-1);					
				}
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    	self.elseMsg = "查詢完成!";
		    });
			
		};
		
		//查詢華人上網包
		self.queryAddons = function(serviceid){
			if(!serviceid || serviceid == '')
				return;
			self.elseMsg = "查詢中...";
			AjaxService.query("queryAddonService",{input:serviceid})
			.success(function(data, status, headers, config) {
				if(data['error']){
					alert(data['error']);
				}else{
					
					self.addons=data['data'];
//					console.log(self.addons);
//					console.log(self.addons.length);
					/*angular.forEach(data['data'][0],function(obj,key){
						alert(obj+","+key);
					});*/
				}
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    	self.elseMsg = "查詢完成!";
		    });
			
		};
		
		//查詢GPRS狀態
		self.queryGPRS = function(serviceid){
			if(!serviceid || serviceid == '')
				return;
			self.elseMsg = "查詢中...";
			AjaxService.query("getGPRSStatus",{input:serviceid})
			.success(function(data, status, headers, config) {
				if(data['error']){
					alert(data['error']);
				}else{
					self.gprsStatus=data['data'];
				}
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    	self.elseMsg = "查詢完成!";
		    });
		};
		
		//20160823
		//查詢美國流量包狀態
		self.queryUSPacket = function(serviceid){
			if(!serviceid || serviceid == '')
				return;
			self.elseMsg = "查詢中...";
			AjaxService.query("queryUSPacket",{input:serviceid})
			.success(function(data, status, headers, config) {
				if(data['error']){
					alert(data['error']);
				}else{
					
					self.USPackets=data['data'];
//					console.log(self.USPackets);
//					console.log(self.USPackets.length);
					/*angular.forEach(data['data'][0],function(obj,key){
						alert(obj+","+key);
					});*/
				}
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    	self.elseMsg = "查詢完成!";
		    });
			
		};
		
	}]);