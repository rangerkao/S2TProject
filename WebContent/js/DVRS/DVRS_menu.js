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
			//reg=new RegExp(reg);
			self.dataList =[];
			self.Error=[];
			//if(self.dateChange){
				AjaxService.query('queryCurrentDay',
						{	"imsi" : self.imsi,
							"from":dateFormatString(self.dateFrom),
							"to":dateFormatString(self.dateTo)})
				.success(function(data, status, headers, config) {  
					if(!data['data'])
						self.dataList=data['data'];
					if(!data['error'])
						self.Error=data['error'];
			    }).error(function(data, status, headers, config) {   
			    	alert("AJAX Error:");
			    });
			/*}else{
				alert("self.dateChang :"+self.dateChang);
				if(self.dataList!='unddefine' && self.dataList.length>0){
					self.dataList.splice(0,self.dataList.length);
					 $.each(self.currenList,function(i,ListItem){
						 if(reg.test(ListItem.imsi)||($("#imsi").val()==null||$("#imsi").val()=="")){
							 self.dataList.push(ListItem);
						 }
					}); 
				}
			}*/
		};
		
		$(document).ready(function () {
		});		
	}]);