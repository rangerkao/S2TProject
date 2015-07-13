<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
 <script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
var dataList;
var adminList;

	$(document).ready(function(){
		queryAdmin();
	});
		
		function queryAdmin(){
		
			  $.ajax({
			      url: '<s:url action="queryAdmin"/>',
			      data: {}, //parameters go here in object literal form
			      type: 'POST',
			      datatype: 'json',
			      success: function(json) {  
			    	  $("#Qmsg").html("Success");
			    	  //jQuery.parseJSON,JSON.parse(json)
			    	  //alert(json);
			    	  var list=$.parseJSON(json);
			    	   $("#table1 tr:gt(0)").remove();//移除>0之後讀tr
			    	  	adminList=list['data']; 
			    	  	if(adminList!=null)
							dataList=adminList.slice(0);
						
						var error = list['error'];
				    	  $('#Error').html(error);
				    	  
			    	     $.each(dataList,function(i,admin){  
	                      var _tr = $(	"<tr align='center'>"+
	                      					"<td >"+admin.userid+"</td>"+
	                      					"<td>"+admin.account+"</td>"+
	                      					"<td>"+admin.password+"</td>"+
	                      					"<td>"+admin.role+"</td>"+
	                      					"<td><button class='btn btn-primary btn-sm' onclick='chooseRow(this)'>選擇</button></td>"+
	                      				"</tr>");  
	                      
	                    $("#table1").append(_tr); }); 
			    	    $("#table1 tr:odd").addClass("odd_columm");//奇數欄位樣式
			    	    $("#table1 tr:even").addClass("even_columm"); 
			    	    
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
		          }
			    }); 
			 $("#Qmsg").html("&nbsp;");
		}
		//將被選擇的table欄位放入編輯區
		function chooseRow(bu){
			var row =bu.parentNode.parentNode //this 指向 button =(parent)> cell =(parent)> row
			//alert(row.cells[0].innerText);
			$("#Userid").val(row.cells[0].innerText);
			$("#Account").val(row.cells[1].innerText);
			$("#Password").val(row.cells[2].innerText);
			$("#Role").val(row.cells[3].innerText);
		}
		//新增資料
		function updateAdmin(mod,String){
			
			if(confirm("確認要"+String+"資料？")){
				if(!validateForm(mod)){return}
				$.ajax({
				      url: '<s:url action="updateAdmin"/>',
				      data: { "admin.userid":$("#Userid").val(),
					    	  "admin.account":$("#Account").val(),
					    	  "admin.password":$("#Password").val(),
					    	  "admin.role":$("#Role").val(),
					    	  "mod":mod}, //parameters go here in object literal form
				      type: 'POST',
				      datatype: 'json',
				      success: function(json) { 
				    	  	//alert(json);  
				    	  	if(json=='success'){
				    	  		$("#Qmsg").html("Success");
				    	  		queryAdmin();
				    	  	}else{
				    	  		$("#Qmsg").html(json);
				    	  		}
				    	  	
				    	  	var error = list['error'];
					    	  $('#Error').html(error);
				      },
				      error: function(json) { $("#Qmsg").html('something bad happened');
				      },
			    	  beforeSend:function(){
			    		  $("#Qmsg").html("正在更新，請稍待...");
			    		  $('#Error').html("");
			    			disableButton();
			          },
			          complete:function(){
			        	  enableButton();
			          }
				    });
			}
		}
		function disableButton(){
			$(':button').attr('disabled', 'disabled');
		}
		function enableButton(){
			$(':button').removeAttr('disabled'); //.attr('disabled', '');
		}
		
		//驗證帳號是否已存在
		var exist;
		function validateText(val){
			exist=false;
			$.each(adminList,function(i,admin){
				if(admin.account==val)	exist=true;
			});
		}
		var validation;
		function validateForm(mod){
			validation=true;//預設驗證通過

			if(mod!='del'&& $("#Userid").val()==''){
				$("#LUserid").html("使用者ID為必填");
				validation=false;
			}
			if($("#Account").val()==''){
				$("#LAccount").html("使用者帳號為必填");
				validation=false;
			}
			if(mod!='del'&& $("#Password").val()==''){
				$("#LPassword").html("使用者密碼為必填");
				validation=false;
			}
			if(mod!='del'&& $("#Role").val()==''){
				$("#LRole").html("使用者角色為必填");
				validation=false;
			}
			
			validateText($("#Account").val());
			
			if(mod=='add'&& exist){
				$("#LAccount").html("此帳號已存在，無法新增");
				validation=false;
			}
				
			if(mod!='add'&& !exist){
				$("#LAccount").html("此帳號不存在，無法進行修改刪除");
				validation=false;
			}
			
			return validation;
		}
		function clearText(item){
			$("#L"+item).html("&nbsp;");
		}
</script>
</head> 
<body>--%>
<div ng-controller="AdminCtrl as aCtrl" class="container-fluid max_height" style="vertical-align: middle;">
	<div class="row max_height" align="center">
		<h3>使用者管理頁面</h3>
		<form class="form-horizontal" role="form">
			<div class="form-group">
    			<label for="Userid" class="col-sm-4 control-label">使用者ID:</label>
    			<div class="col-sm-4">
      				<input type="text" class="form-control" id="Userid" placeholder="輸入ID" onkeyup="clearText('Userid')">
    			</div>
    			<label id="LUserid" class="col-sm-4 control-label alert_msg">&nbsp;</label>
  			</div>
  			<div class="form-group">
    			<label for="Account" class="col-sm-4 control-label">使用者帳號:<font color="red">*</font></label>
    			<div class="col-sm-4">
      				<input type="text" class="form-control" id="Account" placeholder="輸入帳號" onkeyup="clearText('Account')">
    			</div>
    			<label id="LAccount" class="col-sm-4 control-label alert_msg">&nbsp;</label>
  			</div>
  			<div class="form-group">
    			<label for="Password" class="col-sm-4 control-label">使用者密碼：<font color="red">*</font></label>
    			<div class="col-sm-4">
      				<input type="text" class="form-control" id="Password" placeholder="輸入密碼" onkeyup="clearText('Password')">
    			</div>
    			<label id="LPassword" class="col-sm-4 control-label alert_msg">&nbsp;</label>
  			</div>
  			<div class="form-group">
    			<label for="Role" class="col-sm-4 control-label alert_msg">角色分類：<font color="red">*</font></label>
    			<div class="col-sm-4">
      				<input type="text" class="form-control" id="Role" placeholder="輸入角色" onkeyup="clearText('Role')">
    			</div>
    			<label id="LRole" class="col-sm-4 control-label alert_msg">&nbsp;</label>
  			</div>
  			<div class="form-group">
  				<label class="col-sm-12" id="Msg" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
  			</div>
			<div class="form-group" align="center">
				<div class="btn-group">
			    	<input type="button" class="btn btn-primary btn-sm" onclick="this.form.reset()" value="清除" >
					<input type="button" class="btn btn-primary btn-sm" onclick="updateAdmin('add','新增')" value="新增">
					<input type="button" class="btn btn-primary btn-sm" onclick="updateAdmin('mod','修改')" value="修改">
					<input type="button" class="btn btn-primary btn-sm" onclick="updateAdmin('del','刪除')" value="刪除">
					<input type="button" class="btn btn-primary btn-sm" onclick="queryAdmin()" value="查詢">
				</div>
			</div>
		</form>
		<!-- remark 參數開頭不能以data開頭 -->
		<page-table 
			name="admin" 
			table-header="aCtrl.dataHeader" 
			table-list="aCtrl.dataList">
		</page-table>
		<div class="col-xs-12" align="left"> 
			<div id="Error"></div>
		</div>
		
	</div>
</div>
<!-- <script>
	$(document).ready(function(){
			var directive = document.createElement("script");
			//DVRS_menu.setAttribute("type","text/javascript");
			directive.setAttribute("src", "js/directive.js");
			$("#DVRS").append(directive); 
	});
</script>  -->
<!-- </body>
</html> -->