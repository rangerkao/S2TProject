angular.module('MainApp')
	.controller('DataRateCtrl',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){
		
		var self = this;
		
		self.dataHeader=[{name:"資費ID",col:"pricePlanId",_width:"5%"},
		                 {name:"資費名稱",col:"pricePlanName",_width:"25%"},
		                 {name:"MCCMNC",col:"mccmnc",_width:"10%"},
		                 {name:"國家",col:"country",_width:"10%"},
		                 {name:"網路業者",col:"netWork",_width:"10%"},
		                 {name:"費率",col:"rate",_width:"5%"},
		                 {name:"計價單位(KB)",col:"chargeunit",_width:"10%"},
		                 {name:"幣別",col:"currency",_width:"5%"},
		                 {name:"每日收費上限",col:"dayCap",_width:"10%"},
		                 {name:"啟用時間",col:"startTime",_width:"10%"}];
		self.query = function(){
			AjaxService.query('queryDataRate',{})
			.success(function(data, status, headers, config) { 
				console.log("data rate result:"+data);
				if(data['error']){
					
				}else{
					self.data =data['data'];
					self.dataList=data['data'];
					self.proccessSearch();
				}
				
		    }).error(function(data, status, headers, config) {   
		    	self.Error="ERROR"
		    }).then(function(){
		    	self.rateMsg = '查詢完成!';
		    });
		};
		
		self.country=[];
		self.pricePlanId=[];
		self.proccessSearch = function(){
			var log = [];
			var country=[];
			var pricePlanId=[];
			angular.forEach(self.data, function(value, key) {
				if($.inArray(value["country"], country)==-1){
					country.push(value["country"]);
				}
				if($.inArray(value["pricePlanId"], pricePlanId)==-1){
					pricePlanId.push(value["pricePlanId"]);
				}
			}, log);
			
			country.sort();
			pricePlanId.sort();
			//console.log(log);
			self.country=country;
			self.pricePlanId=pricePlanId;
		}
		self.selectedCountry ="";
		self.selectedPricePlanId="";
		self.conditionSelected=function(){
			var log = [];
			self.dataList=[];
			angular.forEach(self.data, function(value, key) {
				if((self.selectedCountry==value["country"]||self.selectedCountry=="")&&
						(self.selectedPricePlanId==value["pricePlanId"]||self.selectedPricePlanId=="")){
					self.dataList.push(value);
				}
			}, log);
		}
		
		self.downLoadExcel = function(){
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