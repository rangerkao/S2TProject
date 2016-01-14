angular.module('MainApp')
	.controller('CurrentDayCtrl',['AjaxService','DateFormatString','$scope',function(AjaxService,DateFormatString,$scope){
		var self = this;
		
		$scope.$on('queryDay',function(event,data){
			self.s2tIMSI=data['s2tIMSI'];
			self.dataList=[];
			self.dayMsg="";
		});

		//-------------data head ---------------------
		self.dataHeader=[{name:"累計日期",col:"day",_width:"9%"},
		  	           {name:"IMSI",col:"imsi",_width:"10%"},
			           {name:"累計費用",col:"charge",_width:"9%"},
			           {name:"最後累計檔案ID",col:"lastFileId",_width:"9%"},
			           {name:"最後使用時間",col:"lastDataTime",_width:"9%"},
			           {name:"累計流量(byte)",col:"volume",_width:"9%"},
			           {name:"更新時間",col:"updateDate",_width:"9%"},
			           {name:"建立時間",col:"createDate",_width:"9%"},
			           {name:"國家業者",col:"mccmnc",_width:"9%"},
			           {name:"是否發送過每日警示",col:"alert",_width:"9%"}];
		
		self.dataList=[];
		
		self.query = function(imsi){
			self.dataList =[];
			if(!imsi){
				alert("No IMSI!");
				return;
			}
			
			var df = "";
			if(self.dateFrom && self.dateFrom != '')
				df=DateFormatString.Format(self.dateFrom);

			var dt = "";
			if(self.dateTo && self.dateTo != '')
				dt=DateFormatString.Format(self.dateTo);
			
			console.log(imsi);
			self.dayMsg = "查詢中...";
			self.buttonDis = true;
			AjaxService.query('queryCurrentDay',
					{	"imsi" : imsi,
						"from":df,
						"to":dt})
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					self.dataList=data['data'];
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("Error:");
		    }).then(function(){
		    	self.buttonDis = false;
		    	self.dayMsg = "完成!";
		    });
		};
		
		$(document).ready(function () {
		});		
		
	}]);