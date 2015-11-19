angular.module('MainApp')
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
	}]);
	