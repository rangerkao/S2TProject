<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="AdminCtrl as Ctrl" class="container-fluid max_height" style="vertical-align: middle;">
	<div class="row max_height" align="center">
		<h3>使用者管理頁面</h3>
		<form class="form-horizontal" role="form">
			<div class="form-group">
    			<label for="Userid" class="col-sm-4 control-label">使用者ID:</label>
    			<div class="col-sm-4">
      				<input ng-model="Ctrl.id" type="text" class="form-control" id="Userid" placeholder="輸入ID" onkeyup="clearText('Userid')">
    			</div>
    			<label id="LUserid" class="col-sm-4 control-label alert_msg">&nbsp;</label>
  			</div>
  			<div class="form-group">
    			<label for="Account" class="col-sm-4 control-label">使用者帳號:<font color="red">*</font></label>
    			<div class="col-sm-4">
      				<input ng-model="Ctrl.acc" type="text" class="form-control" id="Account" placeholder="輸入帳號" onkeyup="clearText('Account')">
    			</div>
    			<label id="LAccount" class="col-sm-4 control-label alert_msg">&nbsp;</label>
  			</div>
  			<div class="form-group">
    			<label for="Password" class="col-sm-4 control-label">使用者密碼：<font color="red">*</font></label>
    			<div class="col-sm-4">
      				<input ng-model="Ctrl.pws" type="text" class="form-control" id="Password" placeholder="輸入密碼" onkeyup="clearText('Password')">
    			</div>
    			<label id="LPassword" class="col-sm-4 control-label alert_msg">&nbsp;</label>
  			</div>
  			<div class="form-group">
    			<label for="Role" class="col-sm-4 control-label alert_msg">角色分類：<font color="red">*</font></label>
    			<div class="col-sm-4">
      				<input ng-model="Ctrl.role" type="text" class="form-control" id="Role" placeholder="輸入角色" onkeyup="clearText('Role')">
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
		<list-table 
			_width="80%" 
			table-id="dataTable" 
			table-header="Ctrl.dataHeader" 
			table-data="Ctrl.dataList"
			choose-row="Ctrl.chooseRow(data)">
		</list-table>
		<div class="col-xs-12" align="left"> 
			<div ng-model="Ctrl.Error"></div>
		</div>
	</div>
</div>
<script>
	$(document).ready(function(){
		if(!document.getElementById("DVRS_admin")){
			var DVRS_admin = document.createElement("script");
			DVRS_admin.setAttribute("id", "DVRS_admin");
			DVRS_admin.setAttribute("src", "js/DVRS/DVRS_admin.js");
			$("#DVRS").append(DVRS_admin); 
		}
	});
</script> 