angular.module('MainApp')
	.controller('CRMQosCtrl',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){
		
		var self = this;
		
		$scope.$on('queryQos',function(event,data){
			self.s2tMsisdn=data['s2tMsisdn'];
			self.queryQos();
		});
		self.qosHeader = [{name:"provision ID",col:"provisionID",_width:"20%"},
					      {name:"IMSI",col:"imsi",_width:"20%"},
					      {name:"門號",col:"msisdn",_width:"15%"},
					      {name:"動作",col:"action",_width:"5%"},
					      {name:"方案",col:"plan",_width:"5%"},
					      {name:"連線結果",col:"returnCode",_width:"10%"},
					      {name:"供裝結果",col:"resultCode",_width:"10%"},
					      {name:"供裝時間",col:"createTime",_width:"15%"}];
		
		self.qosList = [];
		
		self.queryQos = function(){
			self.queryQosList(self.s2tMsisdn);
		};
		
		self.queryQosList = function(msisdn){

			if(!msisdn)
				return ;			

			var numberSize = 8;
			if(msisdn.length > numberSize){
				msisdn = msisdn.substring(msisdn.length-numberSize,msisdn.length);
			}
	
			self.qosMsg = "";
			AjaxService.query('queryQos',{msisdn:msisdn})
											
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					self.qosList=data['data'];
					//alert("success");
				}
				self.qosMsg = "finished!";
		    }).error(function(data, status, headers, config) {   
		    	alert("error");
		    }).then(function(){
		    	self.qosMsg="finished";
			});
		};
		
	}]);