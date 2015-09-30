<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<div class="max_height"  >
	
	<div style="width: 50%;position: absolute;left: 50%;margin-left: -25%" class="div_valign_middle" align="center" >
		<span> <font size="5px" color="red" style=""><s:property value="msg"/></font></span>
		<div style="width: 50%">
			<form class="form-horizontal" role="form" method="post" action="login" >
				<div class="form-group">
					<label for="account" class="col-xs-2">Account</label>
					<div class="col-xs-10">
						<input type="text"  class="form-control" id="account" name="account" placeholder="Account" required>
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-xs-2">Password</label>
					<div class="col-xs-10">
						<input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
					</div>
				</div>
				<button class="btn btn-primary" type="submit">Login</button>
			</form>
		</div>
	</div>
</div>
