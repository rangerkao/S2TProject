angular.module('MainApp')
	.controller('CRMQosCtrl',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){
		
		var self = this;
		
		$scope.$on('queryQos',function(event,data){
			self.s2tMsisdn=data['s2tMsisdn'];
			self.activatedDate=data['activatedDate'];
			self.canceledDate=data['canceledDate'];
			self.queryQos();
		});
		
		$scope.$on('subReset',function(event,data){
			self.qosMsg="";
			self.qosList = [];
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
			self.queryQosList(self.s2tMsisdn,self.activatedDate,self.canceledDate);
		};
		
		self.queryQosList = function(msisdn,activeDate,cancelDate){

			if(!cancelDate)
				cancelDate='';
			
			self.qosList = [];
			if(!msisdn)
				return ;			

			var numberSize = 8;
			if(msisdn.length > numberSize){
				msisdn = msisdn.substring(msisdn.length-numberSize,msisdn.length);
			}
	
			self.qosMsg = "查詢中...";
			AjaxService.query('queryQos',{msisdn:msisdn,activeDate:activeDate,cancelDate:cancelDate})
											
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					self.qosList=data['data'];
					console.log(self.qosList);
					//alert("success");
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("error");
		    }).then(function(){
		    	self.qosMsg="查詢完成!";
			});
		};
		
	}]);