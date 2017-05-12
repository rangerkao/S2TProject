angular.module('MainApp')
	.controller('CRMNameVarified',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){

		var self = this;
		//this.serviceId = "9999";
				
		

		$scope.$on('queryNameVarified',function(event,data){
			
			self.init();
			
			self.serviceId = data['serviceId'];
			self.s2tMsisdn = data['s2tMsisdn'];
			self.chtMsisdn = data['chtMsisdn'];

			//alert(self.chtMsisdn+":"+self.s2tMsisdn);
			if(!self.chtMsisdn||data['priceplanId']!=139){
				self.chtMsisdn = self.s2tMsisdn;
			}
			
			self.passportName = data['passportName'];
			self.passportId = data['passportId'];
			
			var s2tNumber = self.s2tMsisdn;
			//中國號規則
			/*8525609nnnn->861841103nnnn
			8526640nnnn->861391048nnnn
			8526947nnnn->861391037nnnn*/
			$(".chinaArea input,.chinaArea button").removeAttr("disabled");
			if(s2tNumber.match(/^8525609.+/g))
				self.chinaMsisdn = s2tNumber.replace("8525609","861841103");
			else if(s2tNumber.match(/^8526640.+/g))
				self.chinaMsisdn = s2tNumber.replace("8526640","861391048");
			else if(s2tNumber.match(/^8526947.+/g))
				self.chinaMsisdn = s2tNumber.replace("8526947","861391037");
			
			if(!self.chinaMsisdn){
				alert("中國門號無法對應規則");
				$(".chinaArea input,.chinaArea button").attr("disabled","true");
			}else{
				self.query("china",self.chinaMsisdn);
			}
			
			self.queryVLN();
		});
		
		
		
		$scope.$on('subReset',function(event,data){
			self.init();
		});
		
		if(typeof String.prototype.trim=='undefined'){
			console.log("trim is undefined.");
		    String.prototype.trim = function () {
		        return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
		    }   
		}else{
			console.log("trim is defined.");
		}
		

		self.dataHeader1 =[	
			{name:"證件類型",col:"type",_width:"190px",type:"selector",colSize : "1"},
			{name:"姓名",col:"name",_width:"100px",type:"input",colSize : "1"},	           
          	{name:"證號",col:"id",_width:"100px",type:"input",colSize : "1"},
          	{name:"中華/香港號",col:"msisdn",_width:"90%",type:"lable",colSize : "1"},	           
          	{name:"當地副號",col:"vln",_width:"90%",type:"lable",colSize : "1"},
          	{name:"建立時間",col:"createTime",_width:"90%",type:"lable",colSize : "1"},
          	{name:"發送時間",col:"sendDate",_width:"90%",type:"lable",colSize : "1"},
          	];
		
		self.dataHeader2 =[	
			{name:"備註",col:"remark",_width:"100%",type:"input",colSize : "4"},
			{name:"狀態",col:"status",_width:"90%",type:"lable",colSize : "1"},
			{name:"證件認證數",col:"usedCount",_width:"90%",type:"lable",colSize : "1"}
          	];		
		
		self.identityTypes = [
			{name:"台湾居民来往大陆通行证",value:"台湾居民来往大陆通行证"},
			{name:"Passport",value:"Passport"},
			{name:"港澳居民来往内地通行证",value:"港澳居民来往内地通行证"},
			{name:"中华人民共和国二代居民身份证",value:"中华人民共和国二代居民身份证"}
			];
		
		self.sgpShow = false;
		self.nvMsg="";
		self.history = {'china':{},'sgp':{}};
		self.vlns=[];
		self.data = {china:{},sgp:{}};
		self.oData = {china:{},sgp:{}};
		self.defalutType = 0;
		self.data.china={type:self.identityTypes[0].value};
		self.data.sgp={type:'Passport',name:self.passportName,id:self.passportId};
		self.chinaMsisdn = '';
		self.currentChinaMsisdn = '';
		self.init = function (){
			self.sgpShow = false;
			self.nvMsg="";
			self.history = {};
			self.vlns=[];
			self.data = {china:{},sgp:{}};
			self.oData = {china:{},sgp:{}};
			self.defalutType = 0;
			self.data.china={type:self.identityTypes[0].value};
			self.data.sgp={type:'Passport',name:self.passportName,id:self.passportId};
			self.chinaMsisdn = '';
			self.currentChinaMsisdn = '';
		};
		
		$(document).ready(function () {
			self.init();
		});
		
		/*self.clear = function(){
			self.data={};
		};*/
		//*************    VLN  *************//
		self.queryVLN = function(){
			AjaxService.query('queryVLN',
					{	"input":self.serviceId})
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{					
					//console.log("retrun");
					//console.log(data['data']);
					if(data['data']){
						self.vlns = data['data'];
						angular.forEach(data['data'], function(value) {
							if(value.match(/^65/g)){
								self.sgp = value;
								self.sgpShow = true;
								self.query('sgp',self.sgp);
							}else if(value.match(/^86/g)){
								self.currentChinaMsisdn = value;
							}
						});					
					}
					if(!self.data){
						console.log("no VLN.");
					}					
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("Error:");
		    	self.nvMsg = "查詢完成!"
		    }).then(function(){
		    	self.nvMsg = "查詢完成!"
		    		self.buttonDis = false;
		    });
		}
		
		
		
		
		//*************    Query *************//
		self.query = function(country,currentVLN){
			if(!self.serviceId || self.serviceId==''){
				alert("No Serviceid!");
				return;
			}
			self.buttonDis = true;
			self.nvMsg = "查詢中...";
/*			self.oldData= {};
			self.data={};*/
			
			
			var ndata,oData;

			AjaxService.query('queeryNameVarifiedData',
					{	"input":self.serviceId+","+self.chtMsisdn+","+ currentVLN})
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{					
					//console.log("retrun");
					//console.log(data['data']);
					var history = [];
					if(data['data']){
						//console.log(data['data']);
						angular.forEach(data['data'], function(value) {
							  if(value.status == '1' && value.msisdn == self.chtMsisdn){
								  ndata = value;
								  //angular.copy(data, oData);
							  }
							  else{
								  //self.history.push(value);
								  history.push(value);
							  }
							});						
					}			
					//依國家填入元件
					if(country=='china'){
						self.data.china = ndata;
						//console.log(self.data.china);
						if(!self.data.china){
							self.data.china={type:self.identityTypes[0].value,vln:currentVLN,msisdn:self.chtMsisdn};
						}
						angular.copy(ndata, self.oData.china);
						self.history.china=history;
					}else if(country=='sgp'){
						self.data.sgp = ndata;
						//console.log(self.data.sgp);
						if(!self.data.sgp){
							self.data.sgp={type:'Passport',name:self.passportName,id:self.passportId,vln:currentVLN,msisdn:self.chtMsisdn};
						}
						angular.copy(ndata, self.oData.sgp);
						self.history.sgp=history;
					}
					

					//self.data= self.datas[0];
					//angular.copy(self.data, self.oldData);
					//self.history = self.datas.slice(1);
					/*angular.forEach(self.datas, function(value) {
						  if(value.status == '1')
							  self.data = value;
						  else
							  history.push(value);
						});
					angular.copy(self.data, self.oldData);
					self.history = history;
					self.data.type=self.identityTypes[0].value;
					if(self.datas.current){
						self.data = self.datas.current;
						angular.copy(self.data, self.oldData);
					}

					history = self.datas.history;
					self.history = history;*/
					//alert(Object.keys(self.data).length);
					
//					console.log("data:");
//					console.log(self.data);
//					console.log("oldData:");
//					console.log(self.oldData);
//					console.log("history:");
//					console.log(self.history);
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
		//*************    Update *************//
		
		self.update = function(country,nData,oData,currentVLN){
			//無serviceid
			if(!self.serviceId || self.serviceId==''){
				alert("No Serviceid!");
				return;
			}
			//無資料可更新
			if(!nData)
				return;		
			//無使用者姓名
			if(!nData.name){
				alert("請輸入使用者姓名！");
				return;
			}
			//無證件類型
			if(!nData.type){
				alert("請選擇證件類型！");
				return;
			}
			//無證件號碼
			if(!nData.id){
				alert("請輸入證件ID！");
				return;
			}
			//驗證ID
			if(self.errorMsg.id && self.errorMsg.id!=""){
				alert("資料有誤，請確認後再試！");
				return;
			}
			
			//無舊資料
			if(!oData||Object.keys(oData).length==0){
				alert("未認證過資料！請認證！");
				return;
			}

			//與舊資料相同
			if(oData.name == nData.name &&
					oData.id == nData.id &&
					oData.type == nData.type &&
					oData.remark == nData.remark &&
					//舊資料與最新的中華門號相同
					oData.msisdn == self.chtMsisdn &&
					oData.vln == currentVLN) {
				alert("無修改的資料！");
				return ; 
			}
			
			//已送出認證
			if((nData.sendDate && nData.sendDate.trim()!='')){
				//將不允許更新名稱
				if(oData.name != nData.name ){
					alert("不允許更新使用者名稱！請重新認證");
					return;
				}
				
				//不允許更新證件號
				if(oData.id != nData.id ){
					alert("不允許更新證件號碼！請重新認證");
					return;
				}
				
				//不允許更新證件類型
				if(oData.type != nData.type ){
					alert("不允許更新證件類型！請重新認證");
					return;
				}
				
				//不允許更新中國號
				if(oData.vln != currentVLN ){
					alert("不允許更新當地號！請重新認證");
					return;
				}

			}
			
			if(!confirm("是否確定更新？")){
				return ;
			}
			
			//填入Serviceid
			nData.serviceid = self.serviceId;
			//填入最新的中華號
			nData.msisdn = self.chtMsisdn;
			
			//填入最新的中國號
			nData.vln = currentVLN;

			self.buttonDis = true;
			self.nvMsg = "更新中...";
			AjaxService.query('updateNameVarifiedData',
					{	"input": angular.toJson(nData)
					})
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					//alert(data['data']);
					//self.clear();
					self.query(country,currentVLN);
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("Error:");
		    	self.nvMsg = "更新完成!"
		    }).then(function(){
		    	self.buttonDis = false;
		    	self.nvMsg = "更新完成!"
		    });
		};
		
		
		/****************** 認證 ****************/
		self.varify = function(country,nData,oData,currentVLN){
			
			//無serviceid
			if(!self.serviceId || self.serviceId==''){
				alert("No Serviceid!");
				return;
			}
			//無資料可更新
			if(!nData)
				return;		
			//無使用者姓名
			if(!nData.name){
				alert("請輸入使用者姓名！");
				return;
			}
			//無證件類型
			if(!nData.type){
				alert("請選擇證件類型！");
				return;
			}
			//無證件號碼
			if(!nData.id){
				alert("請輸入證件ID！");
				return;
			}
			//驗證ID
			if(self.errorMsg.id && self.errorMsg.id!=""){
				alert("資料有誤，請確認後再試！");
				return;
			}

			//有舊資料
			if(oData&&Object.keys(oData).length!=0){
				
				if(	oData.name == nData.name &&
						oData.id == nData.id &&
						oData.type == nData.type &&
						nData.vln == currentVLN){
					alert("不需重新認證的資料！");
					return ; 
				}
				
				if((!nData.sendDate|| nData.sendDate.trim()=='')){
					
					if(oData.name != nData.name){
						alert("不允許重新認證！請使用更新修改使用者名稱");
						return;
					}
	
					//不允許更新證件號
					if(oData.id != nData.id ){
						alert("不允許重新認證證件號碼！請使用修改");
						return;
					}
					
					//不允許更新證件類型
					if(oData.type != nData.type ){
						alert("不允許重新認證證件類型！請使用修改");
						return;
					}
					
					//不允許更新中國號
					if(oData.vln != currentVLN ){
						alert("不允許重新認證當地號！請使用修改");
						return;
					}
				}
			}

			if(!confirm("是否確定認證？")){
				return ;
			}
			
			nData.sendDate = '';
			
			nData.serviceid = self.serviceId;
			//填入最新的中國號
			nData.vln = currentVLN;
			//填入最新的中華號
			nData.msisdn = self.chtMsisdn;
			
			self.buttonDis = true;
			self.nvMsg = "認證中...";
			AjaxService.query('addNameVarifiedData',
					{	"input": angular.toJson(nData)
					})
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					alert(data['data']);
					//self.clear();
					self.query(country,currentVLN);
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("Error:");
		    	self.nvMsg = "認證完成!"
		    }).then(function(){
		    	self.buttonDis = false;
		    	self.nvMsg = "認證完成!"
		    });
			
			if(self.currentChinaMsisdn&& self.currentChinaMsisdn!= '' && self.chinaMsisdn!=self.currentChinaMsisdn){
				setTimeout( console.log("認證已裝上的中國號") ,1000);

				nData.vln = self.currentChinaMsisdn;
				AjaxService.query('addNameVarifiedData',
						{	"input": angular.toJson(nData)
						})
				.success(function(data, status, headers, config) {  
					if(data['error']){
						alert(data['error']);
					}else{
						alert(data['data']);
						//self.clear();
						self.query(country,currentVLN);
					}
			    }).error(function(data, status, headers, config) {   
			    	alert("Error:");
			    	self.nvMsg = "認證完成!"
			    }).then(function(){
			    	self.buttonDis = false;
			    	self.nvMsg = "認證完成!"
			    });
			}
			
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
			
			var value = self.data.china[name];
			if(!value||value =="")
				return;
			
			if(name=='id'){
				var error = false;
				//台胞證
				if(self.data.china.type == self.identityTypes[0].value){
					//純數字狀態7位或8位，12位數字帶刮號內英文2碼
					if(!value.match(/^\d{7,8}$/g) && !value.match(/^\d{10}\(\w{1}\)$/g)){
						error = true;
					}
				}else //護照
					if(self.data.china.type == self.identityTypes[1].value){
					
				}else //港澳居民通行證
					if(self.data.china.type == self.identityTypes[2].value){
						//首數字H或M，後面為8或10位數字
						if(!value.match(/^[H,M](\d{8}|\d{10})$/g)){
							error = true;
						}
				}else //第二代身份證
					if(self.data.china.type == self.identityTypes[3].value){
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