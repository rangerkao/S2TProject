angular.module('MainApp')
	.controller('QueryNameVarified',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){

		var self = this;
		//this.serviceId = "9999";
				
/*
		$scope.$on('queryNameVarified',function(event,data){
			self.serviceId = data['serviceId'];
			self.chtMsisdn = data['chtMsisdn'];
			self.s2tMsisdn = data['s2tMsisdn'];
			
			var s2tNumber = self.s2tMsisdn;
			//中國號規則
			8525609nnnn->861841103nnnn
			8526640nnnn->861391048nnnn
			8526947nnnn->861391037nnnn
			
			if(s2tNumber.match(/^8525609.+/g))
				self.chinaMsisdn = s2tNumber.replace("8525609","861841103");
			if(s2tNumber.match(/^8526640.+/g))
				self.chinaMsisdn = s2tNumber.replace("8526640","861391048");
			if(s2tNumber.match(/^8526947.+/g))
				self.chinaMsisdn = s2tNumber.replace("8526947","861391037");
			
			if(!self.chinaMsisdn)
				alert("中國門號無法對應規則");
			self.query();
		});
		
		$scope.$on('subReset',function(event,data){
			self.init();
		});*/
		
		self.init = function (){
			self.nvMsg="";
			self.data={};
		};

		self.datas=[];
		self.oldDatas=[];
		self.data={};
		
		self.dataHeader =[	
			{name:"證件類型",col:"type",_width:"20%"},
			{name:"姓名",col:"name",_width:"10%"},	           
          	{name:"證號",col:"id",_width:"10%"},
          	//{name:"備註",col:"remark",_width:"10%"},
          	{name:"中華/香港號",col:"msisdn",_width:"15%"},	           
          	{name:"當地副號",col:"vln",_width:"15%"},
          	{name:"建立時間",col:"createTime",_width:"15%"},
          	{name:"發送時間",col:"sendDate",_width:"10%"},
          	{name:"狀態",col:"status",_width:"5%"}
          	];
		self.dataInput =[
			{name:"證件類型",col:"type",_width:"20%"},
			{name:"姓名",col:"name",_width:"10%"},	           
          	{name:"證號",col:"id",_width:"10%"},
          	//{name:"建立時間",col:"createTime",_width:"20%"}
          	];
		
		
		self.dataCostant = [
			{name:"中華/香港號",col:"msisdn",_width:"15%"},	           
          	{name:"當地副號",col:"vln",_width:"15%"},
			{name:"建立時間",col:"createTime",_width:"15%"},
			{name:"發送時間",col:"sendDate",_width:"10%"},
			{name:"狀態",col:"status",_width:"5%"}
		]
		
		self.remark = {name:"備註",col:"remark",_width:"20%"};
		
		
		self.defalutType = 0;
		self.identityTypes = [
			{name:"台湾居民来往大陆通行证",value:"台湾居民来往大陆通行证"},
			{name:"Passport",value:"Passport"},
			{name:"港澳居民来往内地通行证",value:"港澳居民来往内地通行证"},
			{name:"中华人民共和国二代居民身份证",value:"中华人民共和国二代居民身份证"}
			];
		

		//*************    Query *************//
		self.query = function(){
			
			if(!self.input || self.input == ''){
				alert("請輸入查詢資料！")
				return;
			}
				
			if(!self.type || self.type == ''){
				alert("請選擇查詢條件！")
				return;
			}
			
			self.buttonDis = true;
			self.nvMsg = "查詢中...";
			self.datas=[];
			self.oldDatas=[];
			AjaxService.query('queeryNameVarifiedData',
					{	"input": self.input,"type":self.type })
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					self.datas = data['data'];
//					console.log("Query Page");
					//console.log(self.datas);
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("Error:");
		    	self.nvMsg = "查詢完成!"
		    }).then(function(){
		    	self.nvMsg = "查詢完成!"
		    		self.buttonDis = false;
		    });
		};
		self.onKeyDown = function(obj){
/*			console.log(obj);
			console.log(self.data[obj])*/
			if(self.data[obj].length>5){
				self.pushDate();
			}
		};
		/*self.selected = function(obj){
			console.log(obj)
			console.log(self.data[obj]);
		};*/
		self.pushDate = function(){
			var log = [];
			self.datas = [];
			angular.forEach(self.oldDatas, function(data, key1) {
				angular.forEach(self.dataHeader, function(header, key2) {
					var str1 = data[header.col];
					var str2 = self.data[header.col];
					var patt = new RegExp(str2);
					if(patt.test(str1)){
						self.datas.push(data);
					}
				},log);
			}, log);
		};
		
		self.onKeyDown = function(event){
			if(event.keyCode ==13){
				self.query();
			}
		}
		
		$(document).ready(function () {
			//self.query();
		});
	}]);