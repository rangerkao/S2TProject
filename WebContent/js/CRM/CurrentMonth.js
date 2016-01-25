angular.module('MainApp')
	.controller('CRMMonCtrl',['AjaxService','$scope',function(AjaxService,$scope){
		var self = this;
		
		$scope.$on('queryMonth',function(event,data){
			self.s2tIMSI=data['s2tIMSI'];
			self.dataList=[];
		});
		
		var now = new Date();
		
		self.years = [];
		for(var i=0 ; i<5 ; i++){
			self.years.push(now.getFullYear()-i);
		}
	
		self.mons = [];
		for(var i=1 ; i<=12 ; i++){
			var s = i;
			if(i<10)
				s="0"+i;
			
			self.mons.push(s);

		}
		
		self.suspends=[{name:"全部",value:""},
		               {name:"否",value:"0"},
		               {name:"是",value:"1"}];
		
		self.suspend = self.suspends[0].value;
		
		self.monthDataHeader =[	{name:"統計月份",col:"month",_width:"8%"},	           
		                  	{name:"IMSI",col:"imsi",_width:"12%"},
		                  	{name:"累計費用",col:"charge",_width:"8%"},
		                  	{name:"最後累計檔案ID",col:"lastFileId",_width:"8%"},
		                  	{name:"發送簡訊次數",col:"smsTimes",_width:"8%"},
		                  	{name:"最後使用時間",col:"lastDataTime",_width:"8%"},
		                  	{name:"累計流量(byte)",col:"volume",_width:"8%"},	           
		                  	{name:"更新時間",col:"updateDate",_width:"8%"},
		                  	{name:"建立時間",col:"createDate",_width:"8%"},
		                  	{name:"是否曾中斷數據",col:"everSuspend",_width:"8%"},
		                  	{name:"最後警示額度",col:"lastAlertThreshold",_width:"8%"},
		                  	{name:"最後警示流量(byte)",col:"lastAlertVolume",_width:"8%"}];
		self.queryCurrentMonth = function(imsi){
			self.monthDataList =[];
			if(!imsi || imsi=='')
				return;
			self.monMsg = "查詢中...";
			self.buttonDis = true;
			AjaxService.query('queryCurrentMonth',
					{	"from":self.fy+""+self.fm,
						"to":self.ty+""+self.tm,
						"imsi":	imsi,
						"suspend":self.suspend})
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					self.monthDataList=data['data'];
					//alert("success");
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("Error:");
		    	self.buttonDis = false;
		    	self.monMsg = "完成!"
		    }).then(function(){
		    	self.buttonDis = false;
		    	self.monMsg = "完成!"
		    });
		};
		
		$(document).ready(function () {
			self.fy = self.years[0];
			self.fm = self.mons[now.getMonth()];
			self.ty = self.years[0];
			self.tm = self.mons[now.getMonth()];
			
			//alert(self.fy+"/"+self.fm+"~"+self.ty+"/"+self.tm);
		});	
	}]);