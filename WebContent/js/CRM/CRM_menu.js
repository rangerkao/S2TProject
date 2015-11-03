angular.module('MainApp',['ngRoute','mService','ui.bootstrap','ngCookies'])
	.controller('CRMMenuCtrl',['AjaxService',function(AjaxService){
		var self=this;
		self.selectedItem=3;
		
		function ini_arrays(){
			self.adminList=[];
			self.settingList=[];
			self.searchList=[];
			self.elseList=[];
		}
		self.searchList=[];
		self.elseList=[];
		self.menuList = [{name:"查詢功能",data:[]},
		                 {name:"其他功能",data:[{name:"回系統頁",action:"goSystemMenu"},{name:"登出",action:"logout"}]}];
		

		
		$(document).ready(function () {
			/*var toolHright=50;
			$(".tool").css('height', toolHright+'px');*/
			var toolHeight=0;
			
			var parentHeight=$(".accordion").parent().height();
			$(".accordion").css('height', (parentHeight-toolHeight)+'px');
			$(".accordion").css('max-height', (parentHeight-toolHeight)+'px');
			setAccordianDivHeight();
			self.selectedItem = 1;
			self.query();
	    });
		
		//initial accordion
		function setAccordianDivHeight(){
			//container size
			var containerSize=$(".accordion").height();
			//number
			var cN = $(".accordion h4").size();
			//header Size
			var headerSize=$(".accordion h4").height();
			var bottomsize=$(".bottom").height();
			var MaxcontentHeight=(containerSize-(headerSize+15)*cN-bottomsize)+"px";
			//alert("headerSize:"+headerSize+",containerSize:"+containerSize+",cN:"+cN+",contentHeight:"+contentHeight);
			$(".accordion div.content").each(function(){
				//alert($(this).height());
				$(this).css('height', MaxcontentHeight);
			});
			
			$(".accordion div.content").css('max-height', MaxcontentHeight);
		} 
		self.query=function(){
			ini_arrays();
			AjaxService.query('queryMenu',{system:'CRM'})
			.success(function(data, status, headers, config) {  
				angular.forEach(data['data'],function(obj){
					//alert(obj);
					//alert(obj.belong);
					if(obj.belong=="adminList"){
						self.adminList.push({name:obj.name,action:obj.action});
					}
					if(obj.belong=="settingList"){
						self.settingList.push({name:obj.name,action:obj.action});
					}
					if(obj.belong=="searchList"){
						self.searchList.push({name:obj.name,action:obj.action});
						self.menuList[0]["data"].push({name:obj.name,action:"#/"+obj.action});
					}
					if(obj.belong=="elseList"){
						self.elseList.push({name:obj.name,action:obj.action});
					}
				});
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    	self.select(self.selectedItem);
		    });
		};
		self.select = function(index){
			self.selectedItem=index;
			$(".content").slideUp("fast");
			/*$("#content"+index).slideUp("fast");
			$("#content"+index).siblings("div").slideUp("fast");*/
			$("#content"+index).slideDown("fast");
		};
		
	}])
	.config(['$routeProvider',function($routeProvider){
		
		$routeProvider
		.when('/',{
			templateUrl:'web/CRM/welcome.jsp'
		})
		.when('/querySubscriber',{
			templateUrl:'web/CRM/subscriber.jsp'
		})
		.otherwise({
			redirectTo:'/'
		});
	}])
	.controller('SubscriberCtrl',['AjaxService','ActionService','$cookies',function(AjaxService,ActionService,$cookies){
		var self=this;
		self.selectedTab=0;
		self.custInfo={				
			'name':' ',
			'birthday':' ',
			'idTaxid':' ',
			'phone':' ',
			'email':' ',
			'permanentAddress':' ',
			'billingAddress':' ',
			'agency':' ',
			'remark':' ',
			'type':' ',
			'createtime':' ',
			'updatetime':' '
		};
		self.tabs=[
	           //{url:'web/CRM/subscriber/addon.jsp',title:'供裝記錄',content:'供裝記錄',active:false,disabled:false},
	           {url:'web/CRM/subscriber/QosProvision.jsp',title:'Qos查詢',content:'Qos查詢',active:false,disabled:false},
	           {url:'web/CRM/subscriber/application.jsp',title:'申請書回收查詢',content:'申請書回收查詢',active:true,disabled:false},
	           {url:'web/CRM/subscriber/sms.jsp',title:'系統簡訊(開通、落地、超量)',content:'系統簡訊(開通、落地、超量)',active:false,disabled:false},
	           {url:'web/CRM/subscriber/bill.jsp',title:'月出帳記錄(含明細)',content:'月出帳記錄(含明細)',active:false,disabled:false},
	           {url:'web/CRM/subscriber/using.jsp',title:'使用記錄',content:'使用記錄',active:false,disabled:false},
	           {url:'web/CRM/subscriber/receive.jsp',title:'付款記錄',content:'付款記錄',active:false,disabled:false},
	           {url:'web/CRM/subscriber/collection.jsp',title:'催收記錄',content:'催收記錄',active:false,disabled:false},
	           {url:'web/CRM/subscriber/appeal.jsp',title:'申訴記錄',content:'申訴記錄',active:false,disabled:false}
	           ];
		self.selectTab=function(index){
			self.selectedTab=index;
		};
		
		
		
		self.getSession = function(){
			AjaxService.query("querySession",{})
			.success(function(data, status, headers, config) {
				if(data['error']){
					alert(data['error']);
				}else{
					self.session=data['data'];
					/*angular.forEach(data['data'],function(obj){
						self.IDList.push(obj);
					});*/
					if(self.session.length==0){
						alert("查無資料");
					}else{
						self.role=self.session["s2t.role"];
					}
					//alert("success");
				}
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    	
		    });
		};
		
		$(document).ready(function () {
			self.getSession();
			
		});
		self.radioList=[{id:"id",name:"ID"},
		                {id:"name",name:"名稱"},
		                {id:"s2tm",name:"香港號"},
		                {id:"chtm",name:"中華號"}];
		
		self.serviceIdList = [];
		
		self.ServiceIdList = function(id){
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
		
		//ID選擇
		self.chooseServiceId=function(id){
			ActionService.block();
			$("#serviceidModal").modal('hide');
			AjaxService.query('queryDataByServiceId',{input:id})
			.success(function(data, status, headers, config) {	
				if(data['error']){
					alert(data['error']);
				}else{
					self.custInfo=data['data'];
					//
					self.querySMS(self.custInfo.s2tMsisdn, self.custInfo.chtMsisdn, null, null);
					//
					self.queryQosList(self.custInfo.s2tMsisdn);
					//
					self.queryAppList(self.custInfo.serviceId);
					//
					self.ServiceIdList(self.custInfo.idTaxid);
				}
					
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		          
		    }).then(function(){
		    	ActionService.unblock();
		    });
		};
		
		
		self.IDList=[];
		//查詢by id_taxid
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
			
			AjaxService.query(action,{input:self.input})
			.success(function(data, status, headers, config) {
				if(data['error']){
					alert(data['error']);
				}else{
					self.IDList=data['data'];
					/*angular.forEach(data['data'],function(obj){
						self.IDList.push(obj);
					});*/
					if(self.IDList.length==0){
						alert("查無資料");
					}else{
						if(self.IDList.length==1){
				    		self.selectedId=self.IDList[0].idTaxid;
				    		self.selectId();
				    	}else{
				    		$("#companyModal").modal('show');
				    		
				    	}
					}
					//alert("success");
				}
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		           ActionService.unblock();
		    }).then(function(){
		    	ActionService.unblock();
		    });
		};
		
		
		
		//ID選擇
		self.selectId=function(){
			if(self.selectedId==null||self.selectedId=="")
				return;
			ActionService.block();
			AjaxService.query('queryDataById',{input:self.selectedId})
			.success(function(data, status, headers, config) {	
				if(data['error']){
					alert(data['error']);
				}else{
					self.custInfo=data['data'];
					
					//
					self.querySMS(self.custInfo.s2tMsisdn, self.custInfo.chtMsisdn, null, null);
					//
					self.queryQosList(self.custInfo.s2tMsisdn);
					//
					self.queryAppList(self.custInfo.serviceId);
					//
					self.ServiceIdList(self.custInfo.idTaxid);
				}
					
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		          
		    }).then(function(){
		    	ActionService.unblock();
		    });
		};
		//type選擇
		self.selectType=function(){
			//alert(self.selectedType);
		};
		self.chooseId=function(id){
			$(".modal").modal('hide');
			self.selectedId=id;
    		self.selectId();
		};
		
		//SMS Page ***************************************************************************************
		//------------- date picker setting ----------
		self.dateFrom = new Date();
		self.fromOpened = false;
		
		self.dateTo = new Date();
		self.toOpened = false;
		
		
		self.maxDate = new Date();
		  
		self.disabled = function(date, mode) {
			 //Disable 週末
			 //return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
			return false;
		};
		
		function dateFormatString(dayTime){
			var year=dayTime.getFullYear();
			var month=dayTime.getMonth()+1;
			var day=dayTime.getDate();
			
			if(month<10){
				month = '0'+month;
			}
			
			if(day<10){
				day = '0'+day;
			}
			return year+""+month+""+day;
		};
		
		//---------------------------------------------------------
		  
		self.dateOptions = {
				formatYear: 'yy',
				startingDay: 1
		};
		
		self.smsHeader=[
		                 {name:"簡訊分類",col:"smsclass",_width:"10%"},
		                 {name:"發送號碼",col:"phoneno",_width:"10%"},
		                 {name:"發送內容",col:"content",_width:"70%"},
		                 {name:"發送時間",col:"sendTime",_width:"10%"}];
		self.SMSList = [];
	/*	startDate:dateFormatString(self.dateFrom),
		endDate:dateFormatString(self.dateTo)})*/
		self.querySMSwithTime = function(){
			if(self.custInfo.s2tMsisdn==null)
				return;
			self.querySMS(self.custInfo.s2tMsisdn,self.custInfo.chtMsisdn,dateFormatString(self.dateFrom),dateFormatString(self.dateTo));
		};
			
			
		self.querySMS = function(s2tMsisdn,chtMsisdn,startDate,endDate){
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
				self.smsMsg = "finished!";
		    }).error(function(data, status, headers, config) {   
		    	alert("error");
		    });
		};
		
		//Qos Page ***************************************************************************************
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
			self.queryQosList(self.Msisdn);
		};
		
		self.queryQosList = function(msisdn){

			if(!msisdn){
				msisdn = "";
			}

			var numberSize = 8;
			if(msisdn.length > numberSize){
				msisdn = msisdn.substring(msisdn.length-numberSize,msisdn.length);
			}
	
			AjaxService.query('queryQos',{	msisdn:msisdn})
											
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
		    });
		};
		
		
		//application Page ***************************************************************************************
		self.appHeader = [{name:"申請書類型",col:"type",_width:"50%"},
		                  {name:"審定日",col:"applicationDate",_width:"50%"}];

		self.appTypes = [	{name:"type0",value:0},
		                 	{name:"type1",value:1},
							{name:"type2",value:2}];
			
		self.appType = self.appTypes[0].value;
		
		self.queryAppList = function(serviceId){

			self.appList = [];
			AjaxService.query('queryByServiceId',{	serviceid:serviceId})				
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					self.appList=data['data'];
					//alert("success");
				}
				self.appMsg = "finished!";
		    }).error(function(data, status, headers, config) {   
		    	alert("error");
		    });
		};
		
		self.insertApp = function(type){
			if(!self.custInfo.serviceId)
				return;
			
			AjaxService.query('insertNew',{	serviceid:self.custInfo.serviceId,type:type})				
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					alert(data['data']);
					self.queryAppList(self.custInfo.serviceId);
					//alert("success");
				}
				self.appMsg = "finished!";
		    }).error(function(data, status, headers, config) {   
		    	alert("error");
		    });
		};		
	}]);