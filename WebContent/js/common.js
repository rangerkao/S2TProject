angular.module('MainApp',['mService'])
	.controller('MainCtrl',[function(){
		var self=this;
		
	}])
	.controller('LoginCtrl',['LoginService','$http',function(LoginService,$http){
		var self=this;
		self.click=function(){
			alert(self.user.account+":"+self.user.password);
			alert(angular.toJson(self.user));
			$http({   
		        method: 'post',   
		        url: 'login',
		        param: angular.toJson(self.user),
		        data: self.user
		    }).success(function(data, status, headers, config) {   
		        alert(data);   
		           
		    }).error(function(data, status, headers, config) {   
		           
		    });   
		};
	}])
	.factory('LoginService',['$http',function($http){
		return{
			login:function(data){
				$http.post('login',data)
				.then(
						function(response){
							alert(response.data);
						}
				);
				/*$http.post('login',data)
				.success(function(data, status, headers, config) {
				      alert(data);
				    });*/
				/*$http({   
			        method: 'GET',   
			        url: 'login'  
			    }).success(function(data, status, headers, config) { 
			    	 alert(data);
			    	 alert(status);
			    	 alert(headers);
			    	 alert(config);
			    }).error(function(data, status, headers, config) {   
			           
			    });   */
			}
		};
	}])
	.controller('SystemMenuCtrl',['AjaxService',function(AjaxService){
		var self=this;
		self.systemList=[];
		$(document).ready(function () {
			self.query();
		});
		self.query=function(){
			AjaxService.query('querySystemMenu',{})
			.success(function(data, status, headers, config) {  
				angular.forEach(data['data'],function(obj){
					self.systemList.push({name:obj.name,action:obj.action});
				});
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){

		    });
		};
	}]);