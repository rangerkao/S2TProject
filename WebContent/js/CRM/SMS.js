angular.module('MainApp')
	.controller('CRMSMSCtrl',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){
		var self = this;	
		
		$scope.$on('querySMS',function(event,data){
			self.s2tMsisdn=data['s2tMsisdn'];
			self.chtMsisdn=data['chtMsisdn'];
			self.querySMS(self.s2tMsisdn,self.chtMsisdn,null,null);
		});
		
		$scope.$on('subReset',function(event,data){
			self.smsMsg="";
			self.SMSList = [];
		});
		
		self.smsHeader=[
		                 {name:"簡訊分類",col:"smsclass",_width:"10%"},
		                 {name:"發送號碼",col:"phoneno",_width:"10%"},
		                 {name:"發送內容",col:"content",_width:"70%"},
		                 {name:"發送時間",col:"sendTime",_width:"10%"}];
		self.SMSList = [];
		self.querySMSwithTime = function(){
			if(self.s2tMsisdn==null)
				return;
			self.dateTo.setDate(self.dateTo.getDate()+1);
			self.querySMS(self.s2tMsisdn,self.chtMsisdn,DateFormatString.Format(self.dateFrom),DateFormatString.Format(self.dateTo));
		};

		self.dateFrom = new Date();
		self.dateTo = new Date();

		self.querySMS = function(s2tMsisdn,chtMsisdn,startDate,endDate){
			
			if(!s2tMsisdn && !chtMsisdn)
				return;
			self.smsMsg = "查詢中...";
			self.buttonDis = true;
			//alert("s2tMsisdn:"+s2tMsisdn+",chtMsisdn:"+chtMsisdn+",startDate:"+startDate+",endDate:"+endDate);
			AjaxService.query('querySMS',{	s2tMsisdn:s2tMsisdn,
											chtMsisdn:chtMsisdn,
											startDate:startDate,
											endDate:endDate})
											
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					self.SMSList=data['data'];
					//alert("success");
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("error");
		    }).then(function(){
		    	self.buttonDis = false;
		    	self.smsMsg = "查詢完成!";
			});;
		};
	}]);