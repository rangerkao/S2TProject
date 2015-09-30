<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
    <%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
/* .ui-datepicker-calendar {
display: none;
} */
</style> 
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
var currentList;
var everQuery=false;
var dataList;
$(document).ready(function(){
	//queryCurrentMonth();
	everQuery=false;
	$(".datapicker").datepicker({
        showOn: "button",
        buttonImage: "source/icon.png",
        buttonImageOnly: true,
        buttonText: "Select date",
        dateFormat: 'yy-mm',        
    });
    var Today=new Date();
    var month=Today.getMonth()+1;
    
    if(month<10)
    	month="0"+month;
    setDateYear();
    setDateMonth();
});

function setDateYear(){
	var now = new Date();
	var y = now.getFullYear();
	var html="";
	for(var i=y;i>=1990;i--){
		html+='<option value="'+i+'" '+(i==y?'selected="selected"':'')+'>'+i+'</option>';
	}
	$("#date-year").append(html);
	$("#date-year-to").append(html);
}
function setDateMonth(){
	var now = new Date();
	var m = now.getMonth();
	var html="";
	for(var i=12;i>=1;i--){
		var j=i;
		if(j<10)
	    	j="0"+j;
		
		html+='<option value="'+j+'" '+(i==m+1?'selected="selected"':'')+'>'+j+'</option>';
	}
	$("#date-month").append(html);
	$("#date-month-to").append(html);
}

	function queryCurrentMonth(imsi){			
		$.ajax({
	      url: '<s:url action="queryCurrentMonth"/>',
	      data: {
	    	  /* "from":$("#dateFrom").val(),
				"to":$("#dateTo").val(), */
				"from":$("#date-year").val()+"-"+$("#date-month").val(),
				"to":$("#date-year-to").val()+"-"+$("#date-month-to").val(),
	    		"imsi":imsi,
	    		"suspend":$('input[name=suspend]:checked').val()
	    	  //"imsi":$("#imsi").val() 	 
	    	  }, //parameters go here in object literal form
	      type: 'POST',
	      datatype: 'json',
	      success: function(json) {  
	    	  $("#Qmsg").html("Success");
	    	  //jQuery.parseJSON,JSON.parse(json)
	    	  //alert(json);
	    	  var list=$.parseJSON(json);
	    	  currentList=list['data'];
	    	  if(currentList!=null)
	    		  dataList=currentList.slice(0);
	    	  
	    	  var error = list['error'];
	    	  $('#Error').html(error);
	    	  },
	      error: function() { $("#Qmsg").html('something bad happened'); 
	      },
	      beforeSend:function(){
	    	$("#Qmsg").html("���b�d�M�A�еy��...");
    		$('#Error').html("");
    		dataList=[];
    		disableButton();
          },
          complete:function(){
        	  enableButton();
        	  pagination();
          }
	    });
	}
	var tHead=[{name:"�έp���",col:"month",_width:"8%"},	           
	           {name:"IMSI",col:"imsi",_width:"12%"},
	           {name:"�֭p�O��",col:"charge",_width:"8%"},
	           {name:"�̫�֭p�ɮ�ID",col:"lastFileId",_width:"8%"},
	           {name:"�o�e²�T����",col:"smsTimes",_width:"8%"},
	           {name:"�̫�ϥήɶ�",col:"lastDataTime",_width:"8%"},
	           {name:"�֭p�y�q(byte)",col:"volume",_width:"8%"},	           
	           {name:"��s�ɶ�",col:"updateDate",_width:"8%"},
	           {name:"�إ߮ɶ�",col:"createDate",_width:"8%"},
	           {name:"�O�_�����_�ƾ�",col:"everSuspend",_width:"8%"},
	           {name:"�̫�ĵ���B��",col:"lastAlertThreshold",_width:"8%"},
	           {name:"�̫�ĵ�ܬy�q(byte)",col:"lastAlertVolume",_width:"8%"}];
	
	function disableButton(){
		$(':button').attr('disabled', 'disabled');
	}
	function enableButton(){
		$(':button').removeAttr('disabled'); //.attr('disabled', '');
	}
	
	function queryList(){
		
		
		var   reg=$("#imsi").val();
		reg="^"+reg+"$"
		reg=reg.replace("*","\\d+");
		reg=new RegExp(reg);	
		
		queryCurrentMonth(reg);
		
		/* if(!everQuery){
			queryCurrentMonth();
			everQuery=true;
		} */
	
		/* dataList.splice(0,dataList.length);
		 $.each(currentList,function(i,ListItem){
			 if(reg.test(ListItem.imsi)||($("#imsi").val()==null||$("#imsi").val()=="")){
				 dataList.push(ListItem);
			 }
		});  */
		pagination();
	}
	
</script>
</head>
<body>
<div class="container-fluid max_height" style="vertical-align: middle;">
	<div class="row max_height" align="center">
		<h3>�C��֭p����</h3>
		<!-- <div class="col-xs-4" align="right">�d�ߴ����q</div>
		<div class="col-xs-8" align="left">
			<input type="text"  disabled="disabled" id="dateFrom" class="datapicker" style="height: 25px;text-align: center;position:relative;top: -5px " onchange="everQuery=false">
			��
			<input type="text" disabled="disabled" id="dateTo" class="datapicker" style="height: 25px;text-align: center;position:relative;top: -5px" onchange="everQuery=false">
		</div> -->
		<div class="col-xs-4" align="right">�d�ߴ����q</div>
		<div class="col-xs-8" align="left"> 
			<select id="date-year"></select>&nbsp;&nbsp;�~&nbsp;&nbsp;&nbsp;&nbsp;<select id="date-month"></select>&nbsp;&nbsp;��&nbsp;&nbsp;
			&nbsp;&nbsp;��&nbsp;&nbsp;<select id="date-year-to"></select>&nbsp;&nbsp;�~&nbsp;&nbsp;&nbsp;&nbsp;<select id="date-month-to"></select>&nbsp;&nbsp;��&nbsp;&nbsp;
		</div>
		<div class="col-xs-4" align="right"><label>IMSI:</label></div>
		<div class="col-xs-2" align="left"><input type="text" id="imsi"></div>
		<div class="col-xs-6" align="left"><input type="button" value="�d��" onclick="queryList()" class="btn btn-primary btn-sm"></div>
		<div class="col-xs-4" align="right"><label>�O�_���_�LGPRS:</label></div>
		<div class="col-xs-2" align="left">
			<input type="radio" name="suspend" value="">����
			<input type="radio" name="suspend" value="0">�_
			<input type="radio" name="suspend" value="1">�O
		</div>
		<div class="col-xs-6" align="left">
			<font size="2" color="red">(�d��IMSI�ɥi�ϥ�"*"���N�Y�Ϭq���X�i��ҽk�d��)</font>
			<label id="Qmsg" style="height: 30px;">&nbsp;</label>
		</div>
		<div class="col-xs-12"> 
			<button type="button" name="Previous"  class="pagination btn btn-warning"><span class="glyphicon glyphicon-chevron-left"></span> Previous</button>
			<label id="nowPage"></label>
			<button type="button" name="Next" class="pagination btn btn-warning"> <span class="glyphicon glyphicon-chevron-right"></span> Next</button>
			<label id="totalPage" style="margin-right: 10px"></label>
			<label>�C������</label>
			<input id="rown" type="text" value="10" width="5px">
			<input type="button" onclick="pagination()" class="btn btn-primary btn-sm" style="margin: 20px"  value="���s����">
		</div>
		<div class="col-xs-12"> 
			<div id="page_contain"></div>
		</div>
		<div class="col-xs-12" align="left"> 
			<div id="Error"></div>
		</div>
	</div>
</div>
</body>
</html>