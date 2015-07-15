angular.module('MainApp',['ngRoute','mService'])
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

		
		$(document).ready(function () {
			var toolHright=50;
			$(".tool").css('height', toolHright+'px');
			var parentHeight=$(".accordion").parent().height();
			$(".accordion").css('height', (parentHeight-toolHright)+'px');
			$(".accordion").css('max-height', (parentHeight-toolHright)+'px');
			setAccordianDivHeight();
			self.select(1);			
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
			var bottomsize=50;
			var contentHeight=(containerSize-(headerSize+15)*cN-bottomsize)+"px";
			//alert("headerSize:"+headerSize+",containerSize:"+containerSize+",cN:"+cN+",contentHeight:"+contentHeight);
			$(".accordion div.content").css('height', contentHeight);
			$(".accordion div.content").css('max-height', contentHeight);
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
			$("#content"+index).siblings("DIV").slideUp("fast");
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
	.controller('SubscriberCtrl',['AjaxService','ActionService',function(AjaxService,ActionService){
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
	           {title:'費用紀錄',content:'費用紀錄',active:false,disabled:false},
	           {title:'租退記錄',content:'租退記錄',active:true,disabled:false},
	           {title:'使用記錄',content:'使用記錄',active:false,disabled:false},
	           {title:'申訴紀錄',content:'申訴紀錄',active:false,disabled:true}
	           ];
		self.selectTab=function(index){
			self.selectedTab=index;
		};
		$(document).ready({
			
		});
		
		
		self.radioList=[{id:"id",name:"ID"},{id:"name",name:"名稱"}];
		
		//查詢by id_taxid
		self.queryList=function(){
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
			
			AjaxService.query(action,{input:self.input})
			.success(function(data, status, headers, config) {
				self.IDList=[];
				angular.forEach(data['data'],function(obj){
					self.IDList.push({id:obj.idTaxid,name:obj.name});
				});
				if(self.IDList.length==0){
					alert("查無資料");
				}
				alert("success");
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		           alert(data['error']);
		    }).then(function(){
		    	ActionService.unblock();
		    });
		};
		//ID選擇
		self.selectId=function(){
			if(self.selectedId==null||self.selectedId=="")
				return;
			AjaxService.query('queryDataById',{input:self.selectedId})
			.success(function(data, status, headers, config) {	
				self.custInfo=data['data'];
				alert("success");
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		           alert(data['error']);
		    }).then(function(){
		    });
		};
		//type選擇
		self.selectType=function(){
			//alert(self.selectedType);
		};
	
	
	}]);