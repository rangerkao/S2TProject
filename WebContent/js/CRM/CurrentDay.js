angular.module('MainApp')
	.controller('CurrentDayCtrl',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){
		var self = this;
		
		$scope.$on('queryDay',function(event,data){
			self.serviceId=data['serviceId'];
		});
		
		$scope.$on('subReset',function(event,data){
			self.init();
		});

		//-------------data head ---------------------
		self.dataHeader=[{name:"累計日期",col:"day",_width:"10%"},
		  	          // {name:"IMSI",col:"imsi",_width:"10%"},
			           {name:"累計費用",col:"charge",_width:"10%"},
			           {name:"最後累計檔案ID",col:"lastFileId",_width:"10%"},
			           {name:"最後使用時間",col:"lastDataTime",_width:"10%"},
			           {name:"累計流量(byte)",col:"volume",_width:"10%"},
			           {name:"更新時間",col:"updateDate",_width:"15%"},
			           {name:"建立時間",col:"createDate",_width:"15%"},
			           {name:"國家業者",col:"mccmnc",_width:"10%"},
			           {name:"是否發送過每日警示",col:"alert",_width:"10%"}];
		
		
		self.query = function(serviceId){
			self.dataList =[];
			if(!serviceId){
				alert("No ServiceID!");
				return;
			}
			
			var df = "";
			if(self.dateFrom && self.dateFrom != '')
				df=DateFormatString.Format(self.dateFrom);

			var dt = "";
			if(self.dateTo && self.dateTo != '')
				dt=DateFormatString.Format(self.dateTo);

			self.dayMsg = "查詢中...";
			self.buttonDis = true;
			AjaxService.query('queryCurrentDay', {
							"serviceId" : serviceId,
							"from" : df,
							"to" : dt
						}).success(function(data, status, headers, config) {
							if (data['error']) {
								alert(data['error']);
							} else {
								self.dataList = data['data'];
							}
						}).error(function(data, status, headers, config) {
							alert("Error:");
							self.buttonDis = false;
							self.dayMsg = "查詢完成!";
						}).then(function() {
							self.buttonDis = false;
							self.dayMsg = "查詢完成!";
						});
					};
		self.init = function(){
			self.dataList=[];
			self.dateFrom = new Date();
			self.dateTo = new Date();
			self.dayMsg="";
		}
		$(document).ready(function () {
			self.init();
		});		
		
		
	}]);