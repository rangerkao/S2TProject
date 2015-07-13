angular.module('MainApp',['ngRoute','mService'])
	.controller('MenuCtrl',['AjaxService',function(AjaxService){
		var self=this;
		self.selectedItem=3;
		
		function ini_arrays(){
			self.adminList=[];
			self.settingList=[];
			self.searchList=[];
			self.elseList=[];
		}
		self.adminList=[{name:'name1',action:'action1'},{name:'name2',action:'action2'}];
		self.settingList=[];
		self.searchList=[];
		self.elseList=[{name:'name3',action:'action3'}];
	
		self.test="中文字測試!";

		
		$(document).ready(function () {
			var toolHright=50;
			$(".tool").css('height', toolHright+'px');
			var parentHeight=$(".accordion").parent().height();
			$(".accordion").css('height', (parentHeight-toolHright)+'px');
			$(".accordion").css('max-height', (parentHeight-toolHright)+'px');
			setAccordianDivHeight();
			self.select(3);			
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
			AjaxService.query('queryMenu',{system:'DVRS'})
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
		}
		
	}])
	.config(['$routeProvider',function($routeProvider){
		
		$routeProvider
		.when('/',{
			templateUrl:'web/DVRS/welcome.jsp'
		})
		.when('/adminLink',{
			templateUrl:'web/DVRS/admin.jsp'
		})
		.when('/dataRateLink',{
			templateUrl:'web/DVRS/dataRate.jsp'
		})
		.otherwise({
			redirectTo:'/'
		});
	}])
	.controller('AdminCtrl',['AjaxService','$q',function(AjaxService,$q){
		var self=this;
		
		self.dataHeader=[
		                 {name:"使用者ID",col:"userid",_width:"25%"},
		                 {name:"使用者帳號",col:"account",_width:"25%"},
		                 {name:"使用者密碼",col:"password",_width:"25%"},
		                 {name:"角色分類",col:"role",_width:"25%"}];
		self.test={name:"使用者ID",col:"userid",_width:"25%"};
		$(document).ready(function () {
			AjaxService.query('queryAdmin',{})
			.success(function(data, status, headers, config) {  
				self.dataList=data['data'];
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    });
		});
	}]);