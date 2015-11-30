angular.module('MainApp')
	.controller('CRMElseCtrl',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){
		var self = this;
		
		$scope.$on('queryElse',function(event,data){
			console.log(data['s2tIMSI']);
			self.s2tMsisdn = data['s2tMsisdn'];
			self.serviceid = data['serviceid'];
			self.s2tIMSI = data['s2tIMSI'];
			self.privePlanId = data['privePlanId'];
			self.activatedDate = data['activatedDate'];
			self.canceledDate = data['canceledDate'];
			
			self.queryVLN(self.serviceid);
			self.queryAddons(self.serviceid);
			self.queryGPRS(self.s2tMsisdn);
		});
		
		self.VLNs = [];
		
		self.queryVLN = function(serviceId){
			if(!serviceId || serviceId == '')
				return;
			self.elseMsg = "";
			AjaxService.query("queryVLN",{input:serviceId})
			.success(function(data, status, headers, config) {
				if(data['error']){
					alert(data['error']);
				}else{
					self.VLNs=data['data'];
				}
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    	self.elseMsg = "finished!";
		    });
			
		};
		
		self.addons = [];
		
		self.queryAddons = function(serviceid){
			console.log("serviceid:"+serviceid);
			if(!serviceid || serviceid == '')
				return;
			self.elseMsg = "";
			AjaxService.query("queryAddonService",{input:serviceid})
			.success(function(data, status, headers, config) {
				if(data['error']){
					alert(data['error']);
				}else{
					
					self.addons=data['data'];
					/*angular.forEach(data['data'][0],function(obj,key){
						alert(obj+","+key);
					});*/
				}
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    	self.elseMsg = "finished!";
		    });
			
		};
		
		self.queryGPRS = function(s2tMsisdn){
			console.log("msisdn:"+s2tMsisdn);
			if(!s2tMsisdn || s2tMsisdn == '')
				return;
			self.elseMsg = "";
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
		    	self.elseMsg = "finished!";
		    });
			
		};
		
		
	}]);