angular.module('MainApp')
	.controller('CRMAppCtrl',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){
		var self = this;
		
		$scope.$on('queryApp',function(event,data){
			self.serviceId=data['serviceId'];
			self.queryAppList(self.serviceId);
		});
		
		$scope.$on('subReset',function(event,data){
			self.appMsg = "";
			self.appList = [];
		});
		
		self.appHeader = [{name:"申請書類型",col:"type",_width:"50%"},
		                  {name:"審定日",col:"applicationDate",_width:"50%"}];

		self.appTypes = [	{name:"供裝",value:"供裝"},
		                 	{name:"異動",value:"異動"},
							{name:"退租",value:"退租"}];
			
		self.appType = self.appTypes[0].value;

		self.queryAppList = function(serviceId){
			if(!serviceId)
				return ;
			self.appMsg = "查詢中...";
			self.appList = [];
			AjaxService.query('queryAppByServiceId',{	serviceid:serviceId})				
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					self.appList=data['data'];
					//alert("success");
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("error");
		    }).then(function(){
		    	self.appMsg = "查詢完成!";
		    });
		};
		
		self.insertApp = function(type){
			if(!self.serviceId)
				return;
			self.buttonDis = true;
			AjaxService.query('insertNew',{	serviceid:self.serviceId,type:type})				
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					alert(data['data']);
					self.queryAppList(self.serviceId);
					//alert("success");
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("error");
		    }).then(function(){
		    	self.buttonDis = false;
		    });
		};	
	}]);