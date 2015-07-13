angular.module('MainApp')
	.directive('pageTable',[function(){
		return {
			templateUrl:'web/directive/pageTable.jsp',
			restrict: 'AE',
			scope:{
				name:'@',
				tableHeader:'=',
				tableList:'='
			},
			link:function($scope,$element,$attrs){
				alert($scope.tableHeader[0].name);
				alert($scope.name);
			}
		};
	}])
	.directive('pageTest',[function(){
		return {
			 template: '<div>Hello, {{header.name}}!{{name}}</div>',
			restrict: 'AE',
			scope:{
				name:'@',
				header:'='
			},
			link:function($scope,$element,$attrs){
				alert($scope.header.name);
			}
		};
	}])
	;