angular.module('MainApp',[])
	.controller('AdminCtrl',[function(){
		
	}]);
/*angular.module('MainApp',['mDirective','mService'])
	.controller('AdminCtrl',['AjaxService','$q',function(AjaxService,$q){
		var self=this;
		
		self.dataHeader=[
		                 {name:"使用者ID",col:"day",_width:"25%"},
		                 {name:"使用者帳號",col:"day",_width:"25%"},
		                 {name:"使用者密碼",col:"day",_width:"25%"},
		                 {name:"角色分類",col:"day",_width:"25%"}];
		
		$(document).ready(function () {
			self.query()
		});
		
		self.query=function(){
			ini_arrays();
			AjaxService.query('queryAdmin',{})
			.success(function(data, status, headers, config) {  
				self.dataList=data['data'];
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    	self.select(self.selectedItem);
		    });
		};
	}]);*/