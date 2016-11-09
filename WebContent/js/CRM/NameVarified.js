angular.module('MainApp')
	.controller('CRMNameVarified',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){

		var self = this;
		//this.serviceId = "9999";
				

		$scope.$on('queryNameVarified',function(event,data){
			self.serviceId=data['serviceId'];
			self.query();
		});
		
		$scope.$on('subReset',function(event,data){
			self.init();
		});
		
		self.init = function (){
			self.nvMsg="";
			self.data={};
			self.bean={};
		};

		$(document).ready(function () {
			self.init();
		});
		
		self.dataHeader =[	
			{name:"姓名",col:"name",_width:"20%"},	           
          	{name:"證號",col:"id",_width:"20%"},
          	{name:"證件類型",col:"type",_width:"20%"},
          	{name:"備註",col:"remark",_width:"20%"},
          	{name:"建立時間",col:"createTime",_width:"20%"}
          	];
		self.dataInput =[
			{name:"姓名",col:"name",_width:"20%"},	           
          	{name:"證號",col:"id",_width:"20%"},
          	{name:"證件類型",col:"type",_width:"20%"},
          	{name:"備註",col:"remark",_width:"20%"}
          	//{name:"建立時間",col:"createTime",_width:"20%"}
          	];
		self.identityTypes = [
			{name:"台湾居民来往大陆通行证",value:"台湾居民来往大陆通行证"},
			{name:"Passport",value:"Passport"},
			{name:"港澳居民来往内地通行证",value:"港澳居民来往内地通行证"},
			{name:"中华人民共和国二代居民身份证",value:"中华人民共和国二代居民身份证"}
			];
		
		self.bean={};
		self.clear = function(){
			self.bean={};
		};
		
		self.data={};
		self.query = function(){
			self.data={};
			if(!self.serviceId || self.serviceId==''){
				alert("No Serviceid!");
				return;
			}
			self.buttonDis = true;
			self.nvMsg = "查詢中...";
			AjaxService.query('queeryNameVarifiedData',
					{	"input":self.serviceId })
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					self.data=data['data'];
					//console.log(self.dataList);
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("Error:");
		    	self.nvMsg = "查詢完成!"
		    }).then(function(){
		    	self.nvMsg = "查詢完成!"
		    		self.buttonDis = false;
		    });
		};
		
		
		self.insertOrUpdate = function(){
			if(!self.serviceId || self.serviceId==''){
				alert("No Serviceid!");
				return;
			}
			
			if(!self.bean.name){
				alert("請輸入使用者姓名！");
				return;
			}
			
			if(!self.bean.type){
				alert("請選擇證件類型！");
				return;
			}
				
			if(!self.bean.id){
				alert("請輸入證件ID！");
				return;
			}
			self.bean.serviceid = self.serviceId;
			self.buttonDis = true;
			self.nvMsg = "更新中...";
			AjaxService.query('insertOrModifiedNameVarifiedData',
					{	"input": angular.toJson(self.bean)
					})
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					alert(data['data']);
					self.clear();
					self.query();
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("Error:");
		    	self.nvMsg = "更新完成!"
		    }).then(function(){
		    	self.buttonDis = false;
		    	self.nvMsg = "更新完成!"
		    });
		};
		
	}]);