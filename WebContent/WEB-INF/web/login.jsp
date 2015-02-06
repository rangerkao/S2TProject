<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="max_height">
	<div style="width: 30%;position: absolute;left: 50%;margin-left: -15%" class="div_valign_middle" align="right">
		<form class="form-horizontal" role="form" action="login" >
			<div class="form-group">
				<label for="account" class="col-xs-2">Account</label>
				<div class="col-xs-10">
					<input type="text"  class="form-control" id="account" placeholder="Account" required>
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-xs-2">Password</label>
				<div class="col-xs-10">
					<input type="password" class="form-control" id="password" placeholder="Password" required>
				</div>
			</div>
			<button type="submit" class="btn btn-primary">Login</button>
		</form>
	</div>
</div>
