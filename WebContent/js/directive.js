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
	.directive('modal', function () {
	    return {
	    	templateUrl:'web/directive/modal.jsp',
	        restrict: 'E',
	        transclude: true,
	        replace:true,
	        scope:false,
	        link: function postLink(scope, element, attrs) {
	          scope.title = attrs.title;

	        /*  scope.$watch(attrs.visible, function(value){
	            if(value == true)
	              $(element).modal('show');
	            else
	              $(element).modal('hide');
	          });

	          $(element).on('shown.bs.modal', function(){
	            scope.$apply(function(){
	              scope.$parent[attrs.visible] = true;
	            });
	          });

	          $(element).on('hidden.bs.modal', function(){
	            scope.$apply(function(){
	              scope.$parent[attrs.visible] = false;
	            });
	          });*/
	        }
	    };
	});