angular.module('mService',[])
	.factory('AjaxService',['$http',function($http){
		var datas={data:'test'};
		return {
			query:function(url,params){
				return $http({
					method:'POST',
					url:url,
					data:params,
					headers:{
						'Content-Type':'application/x-www-form-urlencoded',
						'Accept': 'application/json'},
					transformRequest:function(data,headers){
						var requestStr;
						for(var key in data){
							if(requestStr){
								requestStr += '&' + key + '=' +data[key];
							}else{
								requestStr = key + '=' + data[key];
							}
						}
						return requestStr;
					},
					transformResponse:function(data,headersGetter){
						//轉兩次才會成為Object
						return angular.fromJson(angular.fromJson(data));
					}
				
				});  
			}
		};
		$httpProvider.defaults.headers.post['Content-Type']='application/x-www-form-urlencoded';
	}]);