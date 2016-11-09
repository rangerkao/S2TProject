<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<div class="max_height"  >
	<div style="width: 50%;position: absolute;left: 50%;margin-left: -25%" class="div_valign_middle" align="center" >
		<span> <font size="5px" color="red" style=""><s:property value="msg"/></font></span>
		<div style="width: 100%">
			<form class="form-horizontal" role="form" method="post" action="login" >
				<div class="form-group">
					<label for="account" class="col-sm-2 control-label ">Account</label>
					<div class="col-sm-10" align="left">
						<input type="text"  class="form-control" id="account" name="account" placeholder="Account" required style="width: 300px">
					</div>
				</div>
				<div class="form-group">
					<label for="Password" class="col-sm-2 control-label ">Password</label>
					<div class="col-sm-10" align="left">
						<input type="password" class="form-control" id="password" name="password" placeholder="Password" required style="width: 300px">
					</div>
				</div>
				<button class="btn btn-primary" type="submit">Login</button>
			</form>
		</div>
	</div>
</div>
