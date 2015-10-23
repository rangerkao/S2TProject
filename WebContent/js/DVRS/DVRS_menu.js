angular.module('MainApp',['ngRoute','mService','ui.bootstrap'])
	.controller('MenuCtrl',['AjaxService',function(AjaxService){
		var self=this;
		self.selectedItem=3;
		
		function ini_arrays(){
			self.adminList=[];
			self.settingList=[];
			self.searchList=[];
			self.elseList=[];
		}
		self.adminList=[{name:'name1',action:'action1'},{name:'name2',action:'action2'}];
		self.settingList=[];
		self.searchList=[];
		self.elseList=[{name:'name3',action:'action3'}];
		$(document).ready(function () {
			var toolHright=50;
			$(".tool").css('height', toolHright+'px');
			var parentHeight=$(".accordion").parent().height();
			$(".accordion").css('height', (parentHeight-toolHright)+'px');
			$(".accordion").css('max-height', (parentHeight-toolHright)+'px');
			setAccordianDivHeight();
			self.select(3);			
			self.query();
	    });
		
		//initial accordion
		function setAccordianDivHeight(){
			//container size
			var containerSize=$(".accordion").height();
			//number
			var cN = $(".accordion h4").size();
			//header Size
			var headerSize=$(".accordion h4").height();
			var bottomsize=50;
			var contentHeight=(containerSize-(headerSize+15)*cN-bottomsize)+"px";
			//alert("headerSize:"+headerSize+",containerSize:"+containerSize+",cN:"+cN+",contentHeight:"+contentHeight);
			$(".accordion div.content").css('height', contentHeight);
			$(".accordion div.content").css('max-height', contentHeight);
		} 
		self.query=function(){
			ini_arrays();
			AjaxService.query('queryMenu',{system:'DVRS'})
			.success(function(data, status, headers, config) {  
				angular.forEach(data['data'],function(obj){
					if(obj.belong=="adminList"){
						self.adminList.push({name:obj.name,action:obj.action});
					}
					if(obj.belong=="settingList"){
						self.settingList.push({name:obj.name,action:obj.action});
					}
					if(obj.belong=="searchList"){
						self.searchList.push({name:obj.name,action:obj.action});
					}
					if(obj.belong=="elseList"){
						self.elseList.push({name:obj.name,action:obj.action});
					}
				});
		    }).error(function(data, status, headers, config) {   
		           alert("error");
		    }).then(function(){
		    	self.select(self.selectedItem);
		    });
		};
		self.select = function(index){
			self.selectedItem=index;
			$("#content"+index).siblings("DIV").slideUp("fast");
			$("#content"+index).slideDown("fast");
		};
		
	}])
	.config(['$routeProvider',function($routeProvider){
		$routeProvider
		.when('/',{
			templateUrl:'web/DVRS/welcome.jsp'
		})
		.when('/adminLink',{
			templateUrl:'web/DVRS/admin.jsp'
		})
		.when('/dataRateLink',{
			templateUrl:'web/DVRS/dataRate.jsp'
		})
		.when('/currentMonthLink',{
			templateUrl:'web/DVRS/currentMonth.jsp'
		})
		.when('/currentDayLink',{
			templateUrl:'web/DVRS/currentDay.jsp'
		})
		.otherwise({
			redirectTo:'/'
		});
	}]).controller('AdminCtrl',['AjaxService','$q',function(AjaxService,$q){
		var self=this;

		self.dataHeader=[
		                 {name:"使用者ID",col:"userid",_width:"25%"},
		                 {name:"使用者帳號",col:"account",_width:"25%"},
		                 {name:"使用者密碼",col:"password",_width:"25%"},
		                 {name:"角色分類",col:"role",_width:"25%"}];
		
		self.query = function(){
			AjaxService.query('queryAdmin',{})
			.success(function(data, status, headers, config) {  
				self.dataList=data['data'];
		    }).error(function(data, status, headers, config) {   
		    	self.Error=data['error'];
		    });
		};
		$(document).ready(function () {
			self.query();
		});

		self.chooseRow = function(obj){
			self.id = obj.userid;
			self.acc = obj.account;
			self.pws = obj.password;
			self.role = obj.role;
		};
		
	}]).controller('DataRateCtrl',['AjaxService','$q',function(AjaxService,$q){
		var self=this;

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
		
		
		$(document).ready(function () {
			self.query();
		});		
	}]).controller('CurrentDayCtrl',['AjaxService','$q',function(AjaxService,$q){
		
		var self=this;

		self.pageNum=1;
		
		//------------- date picker setting ------------------
		self.dateFrom = new Date();
		self.fromOpened = false;
		
		self.dateTo = new Date();
		self.toOpened = false;
		
		
		self.maxDate = new Date();
		  
		self.disabled = function(date, mode) {
			 //Disable 週末
			 //return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
			return false;
		};
		  
		self.dateOptions = {
				formatYear: 'yy',
				startingDay: 1
		};
		  
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
		
		//---------------- data query ---------------
		function validate(){
			var validation=true;
			if(((self.dateFrom==null||self.dateFrom=="")^(self.dateTo==null||self.dateTo==""))){
				alert("日期必須同時填或皆不填！");
				validation=false;
			}else
			if(self.dateFrom>self.dateTo){
				alert("開始日期不可大於結束日期！");
				validation=false;
			}
			return validation;
		}
		
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
			return year+'-'+month+'-'+day;
		};
		
		//self.dateChange = true;
		
		self.query = function(){
			if(!self.imsi)
				self.imsi="";
			self.dataList =[];
			AjaxService.query('queryCurrentDay',
					{	"imsi" : self.imsi,
						"from":dateFormatString(self.dateFrom),
						"to":dateFormatString(self.dateTo)})
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					
					self.dataList=data['data'];

					//alert("success");
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("Error:");
		    });
		};
		
		$(document).ready(function () {
		});		
	}]).controller('CurrentMonthCtrl',['AjaxService','$q',function(AjaxService,$q){
		var self=this;
	
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
		
		self.query = function(){
			alert("imsi:"+self.imsi+",suspend:"+self.suspend);
		};
		self.dataHeader =[	{name:"統計月份",col:"month",_width:"8%"},	           
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
		self.query = function(){

			self.dataList =[];
			AjaxService.query('queryCurrentMonth',
					{	"from":self.fy+""+self.fm,
						"to":self.ty+""+self.tm,
						"imsi":	self.imsi,
						"suspend":self.suspend})
			.success(function(data, status, headers, config) {  
				if(data['error']){
					alert(data['error']);
				}else{
					self.dataList=data['data'];
					//alert("success");
				}
		    }).error(function(data, status, headers, config) {   
		    	alert("Error:");
		    });
		};
		
		$(document).ready(function () {
			self.fy = self.years[0];
			self.fm = self.mons[now.getMonth()];
			self.ty = self.years[0];
			alert(self.mons[now.getMonth()]);
			self.tm = self.mons[now.getMonth()];
			
			//alert(self.fy+"/"+self.fm+"~"+self.ty+"/"+self.tm);
		});	

	}]);