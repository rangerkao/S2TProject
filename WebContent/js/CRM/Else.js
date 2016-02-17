angular.module('MainApp')
	.controller('CRMElseCtrl',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){
		var self = this;
		
		$scope.$on('queryElse',function(event,data){			
			console.log(data['s2tIMSI']);
			self.s2tMsisdn = data['s2tMsisdn'];
			self.serviceid = data['serviceid'];
			self.s2tIMSI = data['s2tIMSI'];
			self.homeIMSI = data['homeIMSI'];
			self.privePlanId = data['privePlanId'];
			self.activatedDate = data['activatedDate'];
			self.canceledDate = data['canceledDate'];
			
			self.queryVLN(self.serviceid);
			self.queryAddons(self.serviceid);
			self.queryGPRS(self.s2tMsisdn);
		});
		
		self.VLNs = [];
		
		self.queryVLN = function(serviceId){
			self.VLNs = [];
			self.VLN = '';
			if(!serviceId || serviceId == '')
				return;
			self.elseMsg = "查詢中...";
			AjaxService.query("queryVLN",{input:serviceId})
			.success(function(data, status, headers, config) {
				if(data['error']){
					alert(data['error']);
				}else{
					console.log(data['data']);
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
		
		self.addons = [];
		
		self.queryAddons = function(serviceid){
			console.log("serviceid:"+serviceid);
			self.addons = [];
			if(!serviceid || serviceid == '')
				return;
			self.elseMsg = "查詢中...";
			AjaxService.query("queryAddonService",{input:serviceid})
			.success(function(data, status, headers, config) {
				if(data['error']){
					alert(data['error']);
				}else{
					
					self.addons=data['data'];
					console.log(self.addons);
					console.log(self.addons.length);
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
		
		self.queryGPRS = function(s2tMsisdn){
			console.log("msisdn:"+s2tMsisdn);
			self.gprsStatus="";
			if(!s2tMsisdn || s2tMsisdn == '')
				return;
			self.elseMsg = "查詢中...";
			AjaxService.query("getGPRSStatus",{input:s2tMsisdn})
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
		
		
	}]);