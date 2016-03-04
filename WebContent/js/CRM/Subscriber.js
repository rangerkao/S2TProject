angular.module('MainApp')
	.controller('SubscriberCtrl',['AjaxService','ActionService','$scope',function(AjaxService,ActionService,$scope){
		var self=this;
		self.testMode = false;
		self.hideNotNecessary = false;
		self.hideNotNecessaryClicked = function(){
			self.hideNotNecessary= !self.hideNotNecessary;
			if(self.hideNotNecessary){
				$(".ddT").css("height",($(".ddT").height()+110)+"px")
			}else{
				$(".ddT").css("height",($(".ddT").height()-110)+"px")
			}
		};
		
		self.origincustInfo={};
		//initial
		self.init = function(){
			//Customer Info
			self.custInfo={				
					'name':'',
					'birthday':'',
					'idTaxid':'',
					'phone':'',
					'email':'',
					'permanentAddress':'',
					'billingAddress':'',
					'agency':'',
					'remark':'',
					'type':'P',
					'createtime':'',
					'updatetime':'',
					'chair':'',
					'chairID':''
				};
			angular.copy(self.custInfo,self.origincustInfo);
			//Customer column edit show control
			self.showControl();
			//Button control
			self.showSave = false;
		};
				
		self.showControl = function(){
			self.show={				
					'name':true,
					'birthday':true,
					'idTaxid':true,
					'phone':true,
					'email':true,
					'permanentAddress':true,
					'billingAddress':true,
					'agency':true,
					'remark':true,
					'type':true,
					'createtime':true,
					'updatetime':true,
					
					'chair':true,
					'chairID':true
				};
			//When change control
			self.infoCahnge={				
					'name':false,
					'birthday':false,
					'idTaxid':false,
					'phone':false,
					'email':false,
					'permanentAddress':false,
					'billingAddress':false,
					'agency':false,
					'remark':false,
					'type':false,
					'createtime':false,
					'updatetime':false,
					
					'chair':false,
					'chairID':false
				};
		};
		
		self.tabs=[
		           {url:'web/CRM/subscriber/elseInfo.jsp',title:'供裝資訊',content:'供裝資訊',active:false,disabled:false},
		           //{url:'web/CRM/subscriber/addon.jsp',title:'供裝記錄',content:'供裝記錄',active:false,disabled:false},
		           {url:'web/CRM/subscriber/QosProvision.jsp',title:'CMHK QoS查詢',content:'CMHK QoS查詢',active:false,disabled:false},
		           {url:'web/CRM/subscriber/application.jsp',title:'申請書回收查詢',content:'申請書回收查詢',active:true,disabled:false},
		           {url:'web/CRM/subscriber/sms.jsp',title:'系統簡訊(開通、落地、超量)',content:'系統簡訊(開通、落地、超量)',active:false,disabled:false},
		           //{url:'web/CRM/subscriber/bill.jsp',title:'月出帳記錄(含明細)',content:'月出帳記錄(含明細)',active:false,disabled:false},
		           //{url:'web/CRM/subscriber/using.jsp',title:'使用記錄',content:'使用記錄',active:false,disabled:false},
		           //{url:'web/CRM/subscriber/receive.jsp',title:'付款記錄',content:'付款記錄',active:false,disabled:false},
		           //{url:'web/CRM/subscriber/collection.jsp',title:'催收記錄',content:'催收記錄',active:false,disabled:false},
		           //{url:'web/CRM/subscriber/appeal.jsp',title:'申訴記錄',content:'申訴記錄',active:false,disabled:false},
		           {url:'web/CRM/subscriber/currentMonth.jsp',title:'數據用量月累計',content:'數據用量月累計',active:false,disabled:false},
		           {url:'web/CRM/subscriber/currentDay.jsp',title:'數據用量日累計',content:'數據用量日累計',active:false,disabled:false},
		           {url:'web/CRM/subscriber/dataRate.jsp',title:'各國費率表',content:'各國費率表',active:false,disabled:false}
		           ];
		
		self.radioList=[{id:"id",name:"ID"},
		                {id:"name",name:"名稱"},
		                {id:"s2tm",name:"香港號"},
		                {id:"main",name:"Home MSISDN"},
		                {id:"vln",name:"VLN"},
		                {id:"imsi",name:"IMSI"}];
				
		self.selectedTab = 0;
		self.infoEditable = false;
		self.appEditable = false;
		self.serviceIdList = [];
		self.IDList = [];
		
		//Edit column control
		self.infoEditMod = function(col){
			if(self.infoEditable){
				self.show[col]=!self.show[col];
			};
		};
		self.whenInfoCahnge = function(col){
			
			if(col=='idTaxid'){
				var str = self.custInfo[col];
				if(str==''){
					self.show['type']=false;
					self.custInfo['type']='P';
				}else{
					self.show['type']=true;
					if(str.match('^[A-Za-z]+')){
						self.custInfo['type']='P';
					}else{
						self.custInfo['type']='E';
					}
				}
				
			}
			
			
			if(self.custInfo[col]!=self.origincustInfo[col]){
				self.infoCahnge[col]=true;
			}else{
				self.infoCahnge[col]=false;
			
			}

			self.showSave = false;
			angular.forEach(self.infoCahnge,function(value,key){
				if(value){
					self.showSave = true;
				}
			});
			
			
		};
		
		self.selectTab=function(index){
			self.selectedTab=index;
		};
		
		//type選擇
		self.selectType=function(){
			//alert(self.selectedType);
		};
		
		self.chooseId=function(id){
			$(".modal").modal('hide');
    		self.chooseServiceId(id);
		};
		
		self.getSession = function(){
			AjaxService.query("querySession",{})
			.success(function(data, status, headers, config) {
				if(data['error']){
					alert(data['error']);
					ActionService.unblock();
				}else{
					self.session=data['data'];
					/*angular.forEach(data['data'],function(obj){
						self.IDList.push(obj);
					});*/
					if(self.session.length==0){
						alert("查無Session資料");
					}else{
						self.role=self.session["s2t.role"];
						if(self.role =='admin'){
							self.infoEditable = true;
							self.appEditable = true;
						}
					}
					//console.log("success");
				}
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    	
		    });
		};
		
		self.ServiceIdList = function(id){
			if(!id)
				return;
			
			AjaxService.query("queryServiceIdList",{input:id})
			.success(function(data, status, headers, config) {
				if(data['error']){
					alert(data['error']);
				}else{
					self.serviceIdList=data['data'];
				}
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    });
		};
		
		//帶入Info 資料
		self.queryInfo=function(){
			if(!self.custInfo.idTaxid || self.custInfo.idTaxid ==""){
				alert("請輸入統編/證號");
				return;
			}
			AjaxService.query('queryDataById',{input:self.custInfo.idTaxid})
			.success(function(data, status, headers, config) {	
				if(data['error']){
					alert(data['error']);
				}else{
					self.custInfo.name=data['data'].name;
					self.custInfo.birthday=data['data'].birthday;
					self.custInfo.phone=data['data'].phone;
					self.custInfo.email=data['data'].email;
					self.custInfo.permanentAddress=data['data'].permanentAddress;
					self.custInfo.billingAddress=data['data'].billingAddress;
					self.custInfo.agency=data['data'].agency;
					self.custInfo.type=data['data'].type;
					self.custInfo.seq=data['data'].seq;
					self.custInfo.chair=data['data'].chair;
					self.custInfo.chairIDdata['data'].chairIDdata;
				};
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    });
		};
		
		
		
		//查詢
		self.queryList=function(){
			self.IDList=[];
			if(self.input==null||self.input==""){
				alert("Please input data!");
				return;
			}
			ActionService.block();
			var action ="";
			if(self.selectedType=='id')
				action='queryListById';
			else if(self.selectedType=='name')
				action='queryListByName';
			else if(self.selectedType=='s2tm')
				action='queryListByS2tMisidn';
			else if(self.selectedType=='chtm')
				action='queryListByChtMsisdn';
			else if(self.selectedType=='main')
				action='queryListByMainMsisdn';
			else if(self.selectedType=='vln')
				action='queryListByVLN';
			else if(self.selectedType=='imsi')
				action='queryListByS2tIMSI';
			
			self.custInfo = [];
			self.IDList=[];
			self.subReset();
			AjaxService.query(action,{input:self.input})
			.success(function(data, status, headers, config) {
				console.log(data);
				if(data['error']){
					alert(data['error']);
					ActionService.unblock();
				}else{
					self.IDList=data['data'];
					/*angular.forEach(data['data'],function(obj){
						self.IDList.push(obj);
					});*/
					if(self.IDList.length==0){
						alert("查無資料");
						ActionService.unblock();
					}else{
						if(self.IDList.length==1){
							console.log(self.IDList[0].serviceId);
							if(self.IDList[0].serviceId && self.IDList[0].serviceId != ''){
								self.chooseServiceId(self.IDList[0].serviceId);
							}else{
								alert("客戶已退租");
								ActionService.unblock();
							}
				    	}else{
				    		$("#companyModal").modal('show');
				    		ActionService.unblock();
				    	}
					}
					//console.log("success");
				}
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		           ActionService.unblock();
		    }).then(function(){
		    	
		    });
		};
		//ID選擇
		self.chooseServiceId=function(id){
			self.init();
			ActionService.block();
			$("#companyModal").modal('hide');
			AjaxService.query('queryDataByServiceId',{input:id})
			.success(function(data, status, headers, config) {	
				if(data['error']){
					alert(data['error']);
				}else{
					console.log(data['data']);
					if(data['data'].length==0)
						alert("無此客戶！");
					else{
						self.custInfo=data['data'];
						angular.copy(self.custInfo,self.origincustInfo);
						//
						self.querySMS(self.custInfo.s2tMsisdn, self.custInfo.chtMsisdn);
						//
						self.queryQosList(self.custInfo.s2tMsisdn);
						//
						self.queryAppList(self.custInfo.serviceId);
						//
						self.queryMonth(self.custInfo.s2tIMSI);
						//
						self.queryDay(self.custInfo.s2tIMSI);
						//
						self.queryElse(self.custInfo.s2tMsisdn, self.custInfo.serviceId, self.custInfo.s2tIMSI, 
								self.custInfo.privePlanId, self.custInfo.activatedDate, self.custInfo.canceledDate, 
								self.custInfo.homeIMSI);
					}
				}
					
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		          
		    }).then(function(){
		    	ActionService.unblock();
		    });
		};
		
		self.querySMS = function(s2tMsisdn,chtMsisdn){
			$scope.$broadcast('querySMS',{s2tMsisdn:s2tMsisdn,chtMsisdn:chtMsisdn});
		};
		self.queryQosList = function(s2tMsisdn){
			$scope.$broadcast('queryQos',{s2tMsisdn:s2tMsisdn});
		};
		self.queryAppList = function(serviceId){
			$scope.$broadcast('queryApp',{serviceId:serviceId});
		};
		self.queryMonth = function(s2tIMSI){
			$scope.$broadcast('queryMonth',{s2tIMSI:s2tIMSI});
		};
		self.queryDay = function(s2tIMSI){
			$scope.$broadcast('queryDay',{s2tIMSI:s2tIMSI});
		};
		self.queryElse = function(s2tMsisdn,serviceid,s2tIMSI,privePlanId,activatedDate,canceledDate,homeIMSI){
			$scope.$broadcast('queryElse',{s2tMsisdn:s2tMsisdn,serviceid:serviceid,s2tIMSI:s2tIMSI,
				privePlanId:privePlanId,activatedDate:activatedDate,canceledDate:canceledDate,homeIMSI:homeIMSI});
		};
		
		self.subReset = function(){
			$scope.$broadcast('subReset',{});
		}
		

		self.updateSubscriber = function(){
			if(!self.custInfo.serviceId){
				alert("請先進行查詢！");
				return;
			}
			self.showControl();
			self.showSave = false;
			
			if(self.custInfo.type == 'P'){
				self.custInfo.chair = '';
				self.custInfo.chairID = '';
			}
			
			var updateInfo={		
					'seq':self.custInfo['seq'],
					'serviceId':self.custInfo['serviceId'],
					'name':self.custInfo['name'],
					'birthday':self.custInfo['birthday'],
					'idTaxid':self.custInfo['idTaxid'],
					'phone':self.custInfo['phone'],
					'email':self.custInfo['email'],
					'permanentAddress':self.custInfo['permanentAddress'],
					'billingAddress':self.custInfo['billingAddress'],
					'agency':self.custInfo['agency'],
					'type':self.custInfo['type'],
					'chair':self.custInfo['chair'],
					'chairID':self.custInfo['chairID']
				};
				
			AjaxService.query('updateSubscriber',{input:angular.toJson(updateInfo)})
			.success(function(data, status, headers, config) {	
				if(data['error']){
					alert(data['error']);
					ActionService.unblock();
				}else{
					alert("success!");
				}
					
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    });
		};
		self.downLoadExcel = function(){
			self.buttonDis =true;
			createExcel2('createSubscribersExcel');
			//self.buttonDis = false;
		};
		
		$(document).ready(function () {
			self.getSession();
			self.init();
			
		});	
		
	}]);