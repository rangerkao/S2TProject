angular.module('MainApp')
	.controller('DataRateCtrl',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){
		
		var self = this;
		
		self.dataHeader=[{name:"資費ID",col:"pricePlanId",_width:"5%"},
		                 {name:"資費名稱",col:"pricePlanName",_width:"25%"},
		                 {name:"MCCMNC",col:"mccmnc",_width:"15%"},
		                 {name:"國家",col:"country",_width:"10%"},
		                 {name:"網路業者",col:"netWork",_width:"15%"},
		                 {name:"費率",col:"rate",_width:"5%"},
		                 {name:"計價單位(KB)",col:"chargeunit",_width:"10%"},
		                 {name:"幣別",col:"currency",_width:"5%"},
		                 {name:"每日上限",col:"dayCap",_width:"10%"}];
		self.query = function(){
			AjaxService.query('queryDataRate',{})
			.success(function(data, status, headers, config) {  
				self.dataList=data['data'];
		    }).error(function(data, status, headers, config) {   
		    	self.Error=data['error'];
		    });
		};
		
		
		self.downLoadExcel = function(){
			alert("click");
			self.buttonDis = true;
			createExcel(self.dataHeader,self.dataList,'費率表('+DateFormatString.Format(new Date())+')');
			self.buttonDis = false;
		};

		$(document).ready(function () {
			self.query();
			/*if(!self.role && self.role != null && self.role!='' && self.role != 'undefined'){
				self.query();
			}*/
		});		
	}]);