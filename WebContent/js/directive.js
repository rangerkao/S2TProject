angular.module('MainApp')
	.directive('pageTable',[function(){
		return {
			templateUrl:'web/directive/pageTable.jsp',
			restrict: 'AE',
			scope:{
				
				tableHeader:'=',
				tableData:'=',
				chooseRow:'&',
				pageNum:'=',
				onePage:'=',
				tableWidth:'@'
				
			},
			link:function($scope,$element,$attrs){
				if(!$scope.pageNum){
					$scope.pageNum=Number(1);
				}else{
					$scope.pageNum=Number($scope.pageNum);
				}
					
				if(!$scope.onePage){
					$scope.onePage=Number(10);
				}else{
					$scope.onePage=Number($scope.onePage);
					alert("defind");
				}
							
				$scope.onSelect = function (data){
					//要用至入的{變數名：變數值}放入
					$scope.chooseRow({data:data});
				};
				
				$scope.changePage = function(act){
					if(act=="before"){
						if($scope.pageNum>1)
							$scope.pageNum = Number($scope.pageNum) - 1;
					}else{
						if($scope.pageNum< $scope.totalPage)
							$scope.pageNum = Number($scope.pageNum) + 1;
					};
				};
				
				$scope.$watch('tableData', function() {
					if($scope.tableData!=null){
						$scope.totalPage=Math.ceil($scope.tableData.length / $scope.onePage);
						$scope.paging();
					}
					

				});
				$scope.$watch('pageNum', function() {
					if($scope.tableData!=null)
					$scope.paging();
				});
				$scope.$watch('onePage', function() {
					if($scope.tableData!=null){
						if($scope.onePage!=0){
							$scope.totalPage=Math.ceil($scope.tableData.length / $scope.onePage);
						}else{
							$scope.totalPage=1;
						}
						if($scope.totalPage<1)
							$scope.totalPage=1;
						
						$scope.paging();
					}
				});
				
				$scope.paging = function(){
					$scope.dataList=[];
					for(var i=0;i<$scope.onePage && ($scope.pageNum-1)*$scope.onePage+i < $scope.tableData.length ;i++){
						$scope.dataList.push($scope.tableData[($scope.pageNum-1)*$scope.onePage+i]);
					}
				};
				
				
				$(document).ready(function () {
				});	
			}
		};
	}])
	.directive('listTable',[function(){
		return {
			templateUrl:'web/directive/listTable.jsp',
			restrict: 'AE',
			scope:{
				tableId:'@',
				tableHeader:'=',
				tableData:'=',
				chooseRow:'&',
				_width:'@'
			},
			link:function($scope,$element,$attrs){
				//alert($scope.tableHeader[0].name);
				$scope.onSelect = function (data){
					//要用至入的{變數名：變數值}放入
					$scope.chooseRow({data:data});
				};
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
				//alert($scope.header.name);
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