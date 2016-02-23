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
				$scope.tableHeight = ($(".tabContent").height())+"px";
				if(!$scope.tableWidth)
					$scope.tableWidth = '50%';
				
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
	        scope:{
	        	modalWidth:'@'
	        },
	        link: function postLink(scope, element, attrs) {
	          scope.title = attrs.title;

	          if(!scope.modalWidth){
	        	  scpoe.modalWidth = '50%';
	          }
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
	}).directive('datepickerDirective',[function(){
		return {
			templateUrl:'web/directive/datePicker.jsp',
			restrict: 'AEC',
			scope:{
				selectedValue:'='
			},
			link:function($scope,$element,$attrs){
				/*$scope.dt = new Date();
				$scope.format = 'yyyy/MM/dd';
				$scope.status = {
						opened: false
				};
				$scope.open = function($event) {
					$scope.status.opened = true;
				};
				$scope.maxDate = new Date(2020, 5, 22);
				$scope.minDate = new Date(1911, 1, 1);
				$scope.dateOptions = {
						formatYear: 'yy',
						startingDay: 1
				};
				$scope.disabled = function(date, mode) {
					return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
				};			
				 $scope.today = function() {
					 $scope.dt = new Date();
				 };
				 $scope.today();*/

				
				$scope.dateOpened = false;

				$scope.maxDate = new Date();
				  
				$scope.disabled = function(date, mode) {
					 //Disable 週末
					 //return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
					return false;
				};
				
				function dateFormatString(dayTime){
					var year=dayTime.getFullYear();
					var month=dayTime.getMonth()+1;
					var day=dayTime.getDate();
					
					if(month<10){
						month = '0'+month;
					}
					
					if(day<10){
						day = '0'+day;
					}
					return year+""+month+""+day;
				};
				
				//---------------------------------------------------------
				  
				$scope.dateOptions = {
						formatYear: 'yy',
						startingDay: 1
				};
			}
		};
	}]);