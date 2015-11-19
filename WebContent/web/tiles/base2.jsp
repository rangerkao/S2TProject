<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>  
<div class="container-fluid max_height" >
	<div class="row max_height">
		<%-- <table class="col-md-12" style="height: 100%;padding: 0px;">
			<tr>
				<td bgcolor="#AFFFB0" style="max-width: 150px;width: 150px">
					<div style="height: 100%;"><tiles:insertAttribute name="menu"/></div>
				</td>
				<td >
					<div style="height: 100%;" ng-view ><tiles:insertAttribute name="main"/></div>
				</td>
			</tr>
		</table> --%>
		<table class="col-md-12 max_height" style="height: 99%;">
			<tr>
				<td width="10%">
					<table style="height: 100%;width: 100%; bgcolor=#404040; ">
						<tr>
							<td bgcolor="#404040" height="50px"><div><tiles:insertAttribute name="top"/> </div></td>
						</tr>
						<tr> 
							<td width="10%"><div style="height: 100%;"><tiles:insertAttribute name="menu"/></div></td>
						</tr>
					</table>
				</td>
				<td height="100%"  ng-view><div class="max_height"><tiles:insertAttribute name="middle" /> </div></td>
			</tr>
		</table>
	</div>
</div>