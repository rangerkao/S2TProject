<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>  

<div class="container-fluid max_height">
	<div class="row max_height" >
		<table class="col-xs-12 max_height" style="height: 99%">
			<tr>
				<td bgcolor="#AFFEFF" height="50px"><div><tiles:insertAttribute name="top"/> </div></td>
			</tr>
			<tr> 
				<td bgcolor="#AFFFD8" height="100%"><div class="max_height"><tiles:insertAttribute name="middle" /> </div></td>
			</tr>
		</table>
	</div>
</div>
