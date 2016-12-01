angular.module('MainApp')
	.controller('CRMNameVarified',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){

		var self = this;
		//this.serviceId = "9999";
				

		$scope.$on('queryNameVarified',function(event,data){
			self.serviceId = data['serviceId'];
			self.chtMsisdn = data['chtMsisdn'];
			self.s2tMsisdn = data['s2tMsisdn'];
			
			var s2tNumber = self.s2tMsisdn;
			//中國號規則
			/*8525609nnnn->861841103nnnn
			8526640nnnn->861391048nnnn
			8526947nnnn->861391037nnnn*/
			
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
		});
		
		self.init = function (){
			self.nvMsg="";
			self.data={};
		};

		$(document).ready(function () {
			self.init();
		});
		self.data={};
		self.oldData={};
		
		self.dataHeader =[	
			{name:"證件類型",col:"type",_width:"20%"},
			{name:"姓名",col:"name",_width:"10%"},	           
          	{name:"證號",col:"id",_width:"10%"},
          	//{name:"備註",col:"remark",_width:"10%"},
          	{name:"中華號",col:"chtMsisdn",_width:"15%"},	           
          	{name:"中國號",col:"chinaMsisdn",_width:"15%"},
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
			{name:"中華號",col:"chtMsisdn",_width:"15%"},	           
          	{name:"中國號",col:"chinaMsisdn",_width:"15%"},
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
		
		self.clear = function(){
			self.data={};
		};
		
		self.query = function(){
			if(!self.serviceId || self.serviceId==''){
				alert("No Serviceid!");
				return;
			}
			self.buttonDis = true;
			self.nvMsg = "查詢中...";
			self.data={};
			self.data.type=self.identityTypes[0].value;
			AjaxService.query('queeryNameVarifiedData',
					{	"input":self.serviceId })
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					var history = [];
					self.datas = data['data'];
					//self.data= self.datas[0];
					//angular.copy(self.data, self.oldData);
					//self.history = self.datas.slice(1);
					angular.forEach(self.datas, function(value) {
						  if(value.status == '1')
							  self.data = value;
						  else
							  history.push(value);
						});
					angular.copy(self.data, self.oldData);
					self.history = history;
					
					console.log("datas");
					console.log(self.datas);
					console.log("data:");
					console.log(self.data);
					console.log("history:");
					console.log(self.history);
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
		
		//console.log(self.oldData);
		self.insertOrUpdate = function(){
			
			
			if(!self.serviceId || self.serviceId==''){
				alert("No Serviceid!");
				return;
			}
			
			if(!self.data.name){
				alert("請輸入使用者姓名！");
				return;
			}
			
			if(!self.data.type){
				alert("請選擇證件類型！");
				return;
			}
				
			if(!self.data.id){
				alert("請輸入證件ID！");
				return;
			}
			//驗證ID
			if(self.errorMsg.id && self.errorMsg.id!=""){
				alert("資料有誤，請確認後再試！");
				return;
			}

			
			if(self.oldData.name == self.data.name &&
					self.oldData.id == self.data.id &&
					self.oldData.type == self.data.type &&
					self.oldData.remark == self.data.remark &&
					self.data.chinaMsisdn == self.chinaMsisdn){
				alert("無修改的資料！");
				return ; 
			}

			
			
			if(self.oldData.name || 	self.oldData.id ||self.oldData.type || self.oldData.remark){
				if(!confirm("已存在資料，是否確定覆蓋？"))
				return ;
			}
			
			self.data.serviceid = self.serviceId;
			self.data.chtMsisdn = self.chtMsisdn;
			self.data.chinaMsisdn = self.chinaMsisdn;
			
			self.buttonDis = true;
			self.nvMsg = "更新中...";
			AjaxService.query('insertOrModifiedNameVarifiedData',
					{	"input": angular.toJson(self.data)
					})
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					//alert(data['data']);
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
		
		self.cancelSendDate = function(){
			if(!self.serviceId || self.serviceId==''){
				alert("No Serviceid!");
				return;
			}

			self.data.serviceid = self.serviceId;
			self.buttonDis = true;
			self.nvMsg = "更新中...";
			AjaxService.query('cancelNameVarifiedDataSendDate',
					{	"input": self.serviceId
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
		    	self.nvMsg = "移除完成!"
		    }).then(function(){
		    	self.buttonDis = false;
		    	self.nvMsg = "移除完成!"
		    });
		};

		self.validateInput = function (name){
			/*var value = self.data[name];
			
			if(name=='id'){
				if(self.data.type == self.identityTypes[0].value){
					//純數字狀態不可超過8位
					if(value.match(/^\d+$/g)&&value.length>8){
						console.log("over");
						value = value.substring(0,8);
					}
					
				}
			}
			self.data[name] = value;*/
		}
		
		self.errorMsg={};
		self.defocus = function(name){
			var value = self.data[name];
			if(!value||value =="")
				return;
			
			if(name=='id'){
				var error = false;
				//台胞證
				if(self.data.type == self.identityTypes[0].value){
					//純數字狀態7位或8位，12位數字帶刮號內英文2碼
					if(!value.match(/^\d{7,8}$/g) && !value.match(/^\d{11,12}\(\w+\)$/g)){
						error = true;
					}
				}else //護照
					if(self.data.type == self.identityTypes[1].value){
					
				}else //港澳居民通行證
					if(self.data.type == self.identityTypes[2].value){
						//首數字H或M，後面為8或10位數字
						if(!value.match(/^[H,M](\d{8}|\d{10})$/g)){
							error = true;
						}
				}else //第二代身份證
					if(self.data.type == self.identityTypes[3].value){
						//15或18位數字
						if(!value.match(/^\d{15}|\d{18}$/g)){
							error = true;
						}
				}
				
				if(error)
					self.errorMsg[name] = "格式錯誤";

			}
		}
		
		self.focus = function(name){
			self.errorMsg[name] = "";
		}
	}]);