<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <script type="text/javascript" defer="defer">
var dataList;
var dateChange;
var currenList;
$(document).ready(function(){
	dateChange=true;
	$(".datapicker").datepicker({
        showOn: "button",
        buttonImage: "source/icon.png",
        buttonImageOnly: true,
        buttonText: "Select date",
        dateFormat: 'yy-mm-dd'
    });
    var Today=new Date();
    var month=Today.getMonth()+1;
    
    if(month<10)
    	month="0"+month;
    
    var Day=Today.getDate();
    if(Day<10)
    	Day="0"+Day;
    
    
    
	var ds=Today.getFullYear()+ "-" + month + "-" + Day;
	$("#dateFrom").val(ds);
	$("#dateTo").val(ds);
});
	function queryCurrentDay(){
		
		/* if(!dateChange){
			queryList();
			return;
		} */
		
		var   reg=$("#imsi").val();
		reg="^"+reg+"$"
		reg=reg.replace("*","\\d+");
		reg=new RegExp(reg);
		
		if(!validate()){
			return;
		}
		$.ajax({
	      url: '<s:url action="queryCurrentDay"/>',
	      data: {	//"imsi":$("#imsi").val(),
	    	  		"imsi" : reg,
	    	  		"from":$("#dateFrom").val(),
  					"to":$("#dateTo").val()}, //parameters go here in object literal form
	      type: 'POST',
	      datatype: 'json',
	      success: function(json) {  
	    	  $("#Qmsg").html("Success");
	    	  //jQuery.parseJSON,JSON.parse(json)
	    	  //alert(json);
	    	  var list=$.parseJSON(json);
	    	  currenList=list['data'];
	    	  if(currenList!=null)
	    		  dataList=currenList.slice(0);
	    	  
	    	  var error = list['error'];
	    	  $('#Error').html(error);
	    	  },
	      error: function() { $("#Qmsg").html('something bad happened'); 
	      },
	      beforeSend:function(){
    		$("#Qmsg").html("正在查尋，請稍待...");
    		$('#Error').html("");
    		dataList=[];
    		disableButton();
          },
          complete:function(){
        	  enableButton();
        	  //pagination();
        	  /* queryList(); */
        	  pagination();
        	  dateChange=false;
          }
	    });
	}
	var tHead=[{name:"累計日期",col:"day",_width:"9%"},
	           {name:"IMSI",col:"imsi",_width:"10%"},
	           {name:"累計費用",col:"charge",_width:"9%"},
	           {name:"最後累計檔案ID",col:"lastFileId",_width:"9%"},
	           {name:"最後使用時間",col:"lastDataTime",_width:"9%"},
	           {name:"累計流量(byte)",col:"volume",_width:"9%"},
	           {name:"更新時間",col:"updateDate",_width:"9%"},
	           {name:"建立時間",col:"createDate",_width:"9%"},
	           {name:"國家業者",col:"mccmnc",_width:"9%"},
	           {name:"是否發送過每日警示",col:"alert",_width:"9%"}];
	
	function disableButton(){
		$(':button').attr('disabled', 'disabled');
	}
	function enableButton(){
		$(':button').removeAttr('disabled'); //.attr('disabled', '');
	}
	function validate(){
		var validation=true;
		if((($("#dateFrom").val()==null||$("#dateFrom").val()=="")^($("#dateTo").val()==null||$("#dateTo").val()==""))){
			alert("日期必須同時填或皆不填！")
			validation=false;
		}else
		if($("#dateFrom").val()>$("#dateTo").val()){
			alert("開始日期不可大於結束日期！")
			validation=false;
		}
		return validation;
	}
	function clearDate(){
		//alert("clear clicked");
		$("#dateFrom").val("");
		$("#dateTo").val("");
		$("#imsi").val("");
	}
	
	function queryList(){
		var   reg=$("#imsi").val();
		reg="^"+reg+"$"
		reg=reg.replace("*","\\d+");
		reg=new RegExp(reg);	
		
		dataList.splice(0,dataList.length);
		 $.each(currenList,function(i,ListItem){
			 if(reg.test(ListItem.imsi)||($("#imsi").val()==null||$("#imsi").val()=="")){
				 dataList.push(ListItem);
			 }
		}); 
		pagination();
	}
</script> --%>
<div ng-controller="CurrentDayCtrl as Ctrl" class="container-fluid max_height" style="vertical-align: middle;">
	<div class="row max_height" align="center">
		<h3>單日累計查詢</h3>
		<div class="col-xs-3" align="right">查詢期間從</div>
		<div class="col-xs-3">
			<p class="input-group">
				<input 	type="text" class="form-control" 
					datepicker-popup="yyyy-MM-dd" 
					ng-model="Ctrl.dateFrom" 
					is-open="Ctrl.fromOpened" 
					min-date="'20000101'" 
					max-date="Ctrl.maxDate" 
					init-date="Ctrl.maxDate"
					datepicker-options="dateOptions" 
					date-disabled="Ctrl.disabled(date, mode)" 
					ng-required="true"  close-text="Close" 
					ng-change="Ctrl.dateChange = true"/>
				<span class="input-group-btn">
					<button type="button" class="btn btn-default" ng-click="Ctrl.fromOpened = true">
						<i class="glyphicon glyphicon-calendar"></i>
					</button>
				</span>
			</p>
		</div>
		<div class="col-xs-1">
			到
		</div>
		<div class="col-xs-3">
			<p class="input-group">
				<input 	type="text" class="form-control" 
					datepicker-popup="yyyy-MM-dd" 
					ng-model="Ctrl.dateTo" 
					is-open="Ctrl.toOpened" 
					min-date="'20000101'" 
					max-date="Ctrl.maxDate" 
					init-date="Ctrl.maxDate"
					datepicker-options="dateOptions" 
					date-disabled="Ctrl.disabled(date, mode)" 
					ng-required="true"  close-text="Close" 
					ng-change="Ctrl.dateChange = true"/>
				<span class="input-group-btn">
					<button type="button" class="btn btn-default" ng-click="Ctrl.toOpened = true">
						<i class="glyphicon glyphicon-calendar"></i>
					</button>
				</span>
			</p>
		</div>
		<div class="col-xs-2">
		</div>
		<div class="col-xs-4" align="right"><label for="imsi">IMSI:</label></div>
		<div class="col-xs-2" align="left"><input ng-model="Ctrl.imsi" type="text" /></div>
		<div class="btn-group col-xs-6">
			<input type="button" class="btn btn-primary btn-sm" ng-click="Ctrl.query()" value="查詢">
			<input type="button" class="btn btn-primary btn-sm" ng-click="Ctrl.imsi=''" value="清除">
		</div>
		<div class="col-xs-12">
			<font size="2" color="red">(查詢IMSI時可使用"*"取代某區段號碼進行模糊查詢)</font>
		</div>
		<page-table 
			table-width="90%" 
			table-header="Ctrl.dataHeader" 
			table-data="Ctrl.dataList" 
			page-num="Ctrl.pageNum" 
			>
		</page-table>
		<div class="col-xs-12" align="left"> 
			<div ng-model="Ctrl.Error"></div>
		</div>
	</div>
</div>