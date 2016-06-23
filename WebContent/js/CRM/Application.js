angular.module('MainApp')
	.controller('CRMAppCtrl',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){
		var self = this;
		self.date = new Date();
		$scope.$on('queryApp',function(event,data){
			self.serviceId=data['serviceId'];
			self.queryAppList(self.serviceId);
		});
		
		$scope.$on('subReset',function(event,data){
			self.appMsg = "";
			self.appList = [];
		});
		
		self.appHeader = [{name:"ServiceId",col:"serviceid",_width:"30%"},
		                  {name:"申請書類型",col:"type",_width:"30%"},
		                  {name:"審定日",col:"applicationDate",_width:"40%"}];

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
		self.query = function(){
			
			var y = self.date.getFullYear().toString();
			   
    	    for(var i =y.length ;i<4;i++){
    	    	y="0"+y;
    	    }
    	    var m = (self.date.getMonth()+1).toString();
    	    for(var i =m.length ;i<2;i++){
    	    	m="0"+m;
    	    }
    	    var d = self.date.getDate().toString();
    	    for(var i =d.length ;i<2;i++){
    	    	d="0"+d;
    	    }
    	   /* var h = today.getHours().toString();
    	    for(var i =h.length ;i<2;i++){
    	    	h="0"+h;
    	    }
    	    var mi = today.getMinutes().toString();
    	    for(var i =mi.length ;i<2;i++){
    	    	mi="0"+mi;
    	    }
    	    var s = today.getSeconds().toString();
    	    for(var i =s.length ;i<2;i++){
    	    	s="0"+s;
    	    }*/
    	    
    	    var req_time = y+m+d;
    	    
    	    //alert(req_time);
    	    self.appList = [];
			AjaxService.query('queryAppByDate',{	verifiedDate:req_time})				
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					//alert(data['data']);
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