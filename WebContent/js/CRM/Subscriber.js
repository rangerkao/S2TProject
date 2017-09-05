angular.module('MainApp')
	.controller('SubscriberCtrl',['AjaxService','ActionService','$rootScope',function(AjaxService,ActionService,$rootScope){
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
		
		$rootScope.isAppliacted = false;
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
					'chairId':'',
					'passportId':'',
					'passportName':''
				};
			angular.copy(self.custInfo,self.origincustInfo);
			//Customer column edit show control
			//self.showControl(true);
			//Button control
			self.showSave = false;
		};
				
		self.showControl = function(colShow){
			
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
					'chairId':false,
					
					'passportId':false,
					'passportName':false
					
				};
		};
		
		self.tabs=[
		           {url:'web/CRM/subscriber/elseInfo.jsp',title:'供裝資訊',content:'供裝資訊',active:false,disabled:false},
		           //{url:'web/CRM/subscriber/addon.jsp',title:'供裝記錄',content:'供裝記錄',active:false,disabled:false},
		           {url:'web/CRM/subscriber/QosProvision.jsp',title:'CMHK QoS查詢',content:'CMHK QoS查詢',active:false,disabled:false},
		           {url:'web/CRM/subscriber/sms.jsp',title:'系統簡訊(開通、落地、超量)',content:'系統簡訊(開通、落地、超量)',active:false,disabled:false},
		           {url:'web/CRM/subscriber/application.jsp',title:'申請書回收查詢',content:'申請書回收查詢',active:true,disabled:false},
		           //{url:'web/CRM/subscriber/bill.jsp',title:'月出帳記錄(含明細)',content:'月出帳記錄(含明細)',active:false,disabled:false},
		           //{url:'web/CRM/subscriber/using.jsp',title:'使用記錄',content:'使用記錄',active:false,disabled:false},
		           //{url:'web/CRM/subscriber/receive.jsp',title:'付款記錄',content:'付款記錄',active:false,disabled:false},
		           //{url:'web/CRM/subscriber/collection.jsp',title:'催收記錄',content:'催收記錄',active:false,disabled:false},
		           //{url:'web/CRM/subscriber/appeal.jsp',title:'申訴記錄',content:'申訴記錄',active:false,disabled:false},
		           {url:'web/CRM/subscriber/currentMonth.jsp',title:'數據用量月累計',content:'數據用量月累計',active:false,disabled:false},
		           {url:'web/CRM/subscriber/currentDay.jsp',title:'數據用量日累計',content:'數據用量日累計',active:false,disabled:false},
		           {url:'web/CRM/subscriber/dataRate.jsp',title:'各國費率表',content:'各國費率表',active:false,disabled:false},
		           {url:'web/CRM/subscriber/nameVarified.jsp',title:'實名制登記',content:'實名制登記',active:false,disabled:false},
		           {url:'web/CRM/subscriber/queryNameVarified.jsp',title:'實名制登記查詢',content:'實名制登記查詢',active:false,disabled:false}
		           ];
		
		self.radioList=[{id:"psid",name:"護照ID"},
						{id:"id",name:"ID"},
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
		
		
		self.show = true;
		//Edit column control
		self.infoEditMod = function(col){
			if($rootScope.role=='noc'||$rootScope.role=='noc')
				return;
			self.show = !self.show;
			/*if(self.infoEditable){
				self.show[col]=!self.show[col];
			};*/
		};
		self.whenInfoCahnge = function(col){
			
			if(col=='idTaxid'){
				var str = self.custInfo[col];
				if(str==''){
					self.custInfo['type']='P';
				}else{
					if(str.match('^[A-Za-z]+')){
						self.custInfo['type']='P';
					}else{
						self.custInfo['type']='E';
					}
				}
				
			}
			
			//console.log(self.custInfo[col]+":"+self.origincustInfo[col]);
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
				
				console.log(data);
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
						console.log("1"+self.session["s2t.role"]);
						if(!self.session["s2t.role"]){
							document.getElementById('logoutLink').click();
						}
						
						$rootScope.role = self.session["s2t.role"];
						//alert($rootScope.role);
						self.infoEditable = true;
						self.appEditable = true;
						/*if($rootScope.role =='admin'){
							self.infoEditable = true;
							self.appEditable = true;
						}*/
						
						if($rootScope.role =='apply_Proccesser'){
							self.showControl(false);
						}else{
							self.showControl(true);
						}
					}
					//console.log("success");
				}
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    	
		    });
		};
		
		self.onSelectedTypeKeyDown = function(event){
			if(event.keyCode ==13){
				self.queryList();
			}
		}
		
		function detectBrowser(){
			var sAgent = navigator.userAgent.toLowerCase();
			this.isIE = (sAgent.indexOf("msie")!=-1); //IE6.0-7
			this.isFF = (sAgent.indexOf("firefox")!=-1);//firefox
			this.isSa = (sAgent.indexOf("safari")!=-1);//safari
			this.isOp = (sAgent.indexOf("opera")!=-1);//opera
			this.isNN = (sAgent.indexOf("netscape")!=-1);//netscape
			this.isCh = (sAgent.indexOf("chrome")!=-1);//chrome
			this.isMa = this.isIE;//marthon
			this.isOther = (!this.isIE && !this.isFF && !this.isSa && !this.isOp && !this.isNN && !this.isSa);//unknown Browser
		}
		
		function reaponsBrowser(){
			var oBrowser = new detectBrowser();
			if (oBrowser.isIE) { 
				//alert("IE6.0/7.0(or above version)."); 
			}else{
				
			}
			if (oBrowser.isSa && !oBrowser.isCh) { 
				//alert("Safari."); 
			} 
			if (oBrowser.isOp) { 
				//alert("Opera."); 
			} 
			if (oBrowser.isCh && oBrowser.isSa) { 
				//alert("Chrom."); 
			} 
			if(oBrowser.isFF) { 
				//alert("FireFox."); 
			}
		}
		
		function getNextElement(sender){
			alert(sender.nodeName);
			var nextElmt = sender.nextSibling;
			alert(sender.nodeName);
			var i = 0;
			try{
				while (nextElmt.nodeName!="INPUT" && nextElmt.nodeName!="SELECT") {
					alert(nextElmt.nodeName);
						nextElmt = nextElmt.nextSibling;
				}
			}catch (e){
				return sender;
			}
			return nextElmt;
		}

		self.onDataKeyDownToBringData = function(event){
			if(event.keyCode == 13){
				self.queryInfo();
			}
		}
		
		
		
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
					if(data['data'].name)
						self.custInfo.name=data['data'].name;
					if(data['data'].birthday)
						self.custInfo.birthday=data['data'].birthday;
					if(data['data'].phone)
						self.custInfo.phone=data['data'].phone;
					if(data['data'].email)
						self.custInfo.email=data['data'].email;
					if(data['data'].permanentAddress)
						self.custInfo.permanentAddress=data['data'].permanentAddress;
					if(data['data'].billingAddress)
						self.custInfo.billingAddress=data['data'].billingAddress;
					if(data['data'].agency)
						self.custInfo.agency=data['data'].agency;
					if(data['data'].type)
						self.custInfo.type=data['data'].type;
					if(data['data'].seq)
						self.custInfo.seq=data['data'].seq;
					if(data['data'].chair)
						self.custInfo.chair=data['data'].chair;
					if(data['data'].chairId)
						self.custInfo.chairId=data['data'].chairId;
					
					if(data['data'].passportId)
						self.custInfo.passportId=data['data'].passportId;
					
					if(data['data'].passportName)
						self.custInfo.passportName=data['data'].passportName;
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
			else if(self.selectedType=='psid')
				action='queryListByPassPortId';
			
			self.custInfo = [];
			self.IDList=[];
			self.subReset();
			AjaxService.query(action,{input:self.input})
			.success(function(data, status, headers, config) {
				//console.log(data);
				if(data['error']){
					alert(data['error']);
					if(data['error'].indexOf("登入")){
						//document.location.href=document.host+"/S2TProject";
						document.getElementById('logoutLink').click();
					}
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
//							console.log(self.IDList[0].serviceId);
							if(self.IDList[0].serviceId && self.IDList[0].serviceId != ''){
								/*self.chooseServiceId(self.IDList[0].serviceId);*/
								self.chooseItem(self.IDList[0]);
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
		
		
		self.chooseItem = function(item){
			self.init();
			self.custInfo=item;
			$("#companyModal").modal('hide');
			
			if(self.selectedType=='chtm'&&self.input!=self.custInfo.chtMsisdn){
				alert("輸入門號與查詢結果中華號不相同，請留意資料正確性！");
			}
			
			angular.copy(self.custInfo,self.origincustInfo);
			//
			self.querySMS(self.custInfo.s2tMsisdn, self.custInfo.chtMsisdn,self.custInfo.activatedDate,self.custInfo.canceledDate);
			//
			self.queryQosList(self.custInfo.s2tMsisdn,self.custInfo.activatedDate,self.custInfo.canceledDate);
			//
			self.queryAppList(self.custInfo.serviceId);
			//
			self.queryMonth(self.custInfo.serviceId);
			//
			self.queryDay(self.custInfo.serviceId);
			//
			self.queryElse(self.custInfo.s2tMsisdn, self.custInfo.serviceId, self.custInfo.s2tIMSI, 
					self.custInfo.privePlanId, self.custInfo.activatedDate, self.custInfo.canceledDate, 
					self.custInfo.homeIMSI);
			//
			self.queryNameVarified(self.custInfo.serviceId,self.custInfo.chtMsisdn,self.custInfo.s2tMsisdn,self.custInfo.privePlanId.id,
					self.custInfo.passportName,self.custInfo.passportId,self.custInfo.nowS2tActivated, self.custInfo.canceledDate);
			//
			ActionService.unblock();
		}
		
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
//					console.log(data['data']);
					if(data['data'].length==0)
						alert("無此客戶！");
					else{
						self.custInfo=data['data'];
						angular.copy(self.custInfo,self.origincustInfo);
						//
						self.querySMS(self.custInfo.s2tMsisdn, self.custInfo.chtMsisdn,self.custInfo.activatedDate,self.custInfo.canceledDate);
						//
						self.queryQosList(self.custInfo.s2tMsisdn,activatedDate,canceledDate);
						//
						self.queryAppList(self.custInfo.serviceId);
						//
						self.queryMonth(self.custInfo.serviceId);
						//
						self.queryDay(self.custInfo.serviceId);
						//
						self.queryElse(self.custInfo.s2tMsisdn, self.custInfo.serviceId, self.custInfo.s2tIMSI, 
								self.custInfo.privePlanId, self.custInfo.activatedDate, self.custInfo.canceledDate, 
								self.custInfo.homeIMSI);
						//
						self.queryNameVarified(self.custInfo.serviceId,self.custInfo.chtMsisdn,self.custInfo.s2tMsisdn,self.custInfo.privePlanId.id,
								self.custInfo.passportName,self.custInfo.passportId,self.custInfo.nowS2tActivated, self.custInfo.canceledDate);
						//
					}
				}
					
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		          
		    }).then(function(){
		    	ActionService.unblock();
		    });
		};
		
		self.querySMS = function(s2tMsisdn,chtMsisdn,activatedDate,canceledDate){
			$rootScope.$broadcast('querySMS',{
				s2tMsisdn:s2tMsisdn,chtMsisdn:chtMsisdn,activatedDate:activatedDate,canceledDate:canceledDate
			});
		};
		self.queryQosList = function(s2tMsisdn,activatedDate,canceledDate){
			$rootScope.$broadcast('queryQos',{s2tMsisdn:s2tMsisdn,activatedDate:activatedDate,canceledDate:canceledDate});
		};
		self.queryAppList = function(serviceId){
			$rootScope.$broadcast('queryApp',{serviceId:serviceId});
		};
		self.queryMonth = function(serviceId){
			$rootScope.$broadcast('queryMonth',{serviceId:serviceId});
		};
		self.queryDay = function(serviceId){
			$rootScope.$broadcast('queryDay',{serviceId:serviceId});
		};
		self.queryElse = function(s2tMsisdn,serviceid,s2tIMSI,privePlanId,activatedDate,canceledDate,homeIMSI){
			$rootScope.$broadcast('queryElse',{s2tMsisdn:s2tMsisdn,serviceid:serviceid,s2tIMSI:s2tIMSI,
				privePlanId:privePlanId,activatedDate:activatedDate,canceledDate:canceledDate,homeIMSI:homeIMSI});
		};
		self.queryNameVarified=function(serviceId,chtMsisdn,s2tMsisdn,priceplanId,passportName,passportId,nowS2tActivated,canceledDate){
			$rootScope.$broadcast('queryNameVarified',{
				serviceId:serviceId,chtMsisdn:chtMsisdn,s2tMsisdn:s2tMsisdn,priceplanId:priceplanId,passportName:passportName,passportId:passportId,canceledDate:canceledDate,nowS2tActivated:nowS2tActivated
			});
		}
		//
		
		self.subReset = function(){
			$rootScope.$broadcast('subReset',{});
		}
		

		self.updateSubscriber = function(){
			if(!self.custInfo.serviceId){
				alert("請先進行查詢！");
				return;
			}
			
			if($rootScope.role=='apply_Proccesser' && !$rootScope.isAppliacted){
				alert("未更新申請書狀態！");
				return;
			}
			self.showControl(true);
			self.showSave = false;
			
			if(self.custInfo.type == 'P'){
				self.custInfo.chair = '';
				self.custInfo.chairId = '';
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
					'chairId':self.custInfo['chairId'],
					'passportId':self.custInfo['passportId'],
					'passportName':self.custInfo['passportName']
				};
				
			AjaxService.query('updateSubscriber',{input:angular.toJson(updateInfo)})
			.success(function(data, status, headers, config) {	
				if(data['error']){
					alert(data['error']);
					ActionService.unblock();
				}else{
					alert("success!");
					var info=data['data'];
					
					self.custInfo['seq'] = info['seq'];
					self.custInfo['serviceId'] = info['serviceId'];
					self.custInfo['name'] = info['name'];
					self.custInfo['birthday'] = info['birthday'];
					self.custInfo['idTaxid'] = info['idTaxid'];
					self.custInfo['phone'] = info['phone'];
					self.custInfo['email'] = info['email'];
					self.custInfo['permanentAddress'] = info['permanentAddress'];
					self.custInfo['billingAddress'] = info['billingAddress'];
					self.custInfo['agency'] = info['agency'];
					self.custInfo['type'] = info['type'];
					self.custInfo['chair'] = info['chair'];
					self.custInfo['chairId'] = info['chairId'];
					self.custInfo['passportId'] = info['passportId'];
					self.custInfo['passportName'] = info['passportName'];
					angular.copy(self.custInfo,self.origincustInfo);
					
				}
					
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    });
		};
		
		
		self.downLoadExcel = function(){
			self.buttonDis =true;
			createExcel2('getSubscribersExcel');
			//self.buttonDis = false;
		};
		
		self.insertApp = function(){
			var type = '供裝';
			if(!self.custInfo.serviceId){
				alert("No serviceid!");
				return;
			}
				
			self.buttonDis = true;
			AjaxService.query('insertNew',{	serviceid:self.custInfo.serviceId,type:type})				
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					alert(data['data']);
					self.queryAppList(self.custInfo.serviceId);
					//alert("success");
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("error");
		    }).then(function(){
		    	self.buttonDis = false;
		    });
		};	
		
		$(document).ready(function () {
			self.getSession();
			self.init();			
		});	
		
	}])
	.filter('hideData',['$rootScope',function($rootScope){
		return function(data){
			
			if(!data) return '';
			
			if($rootScope.role=="helen") return data;
			
			var s = '';
		
			data = ""+data;
			
			s = data.substring(0,1)+"*****"+ data.substring(data.length-1,data.length);
			/*for(var i = 0 ; i < data.length ; i++){
				s+= '*';
			}*/
			
			return s;
		};	
	}]);