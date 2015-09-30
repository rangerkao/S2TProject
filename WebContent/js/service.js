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
						var requestStr=null;
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
						var temp = angular.fromJson(data);
						return angular.fromJson(temp);
					}
				
				});  
			}
		};
		$httpProvider.defaults.headers.post['Content-Type']='application/x-www-form-urlencoded';
	}])
	.factory('ActionService',[function($http){
		return {
			block:function(){
				$.blockUI({ css: { 
		            border: 'none', 
		            padding: '15px', 
		            backgroundColor: '#000', 
		            '-webkit-border-radius': '10px', 
		            '-moz-border-radius': '10px', 
		            opacity: .5, 
		            color: '#fff' 
		        } });
			},
			unblock:function(){
				$.unblockUI();
			}
		};
	}]);