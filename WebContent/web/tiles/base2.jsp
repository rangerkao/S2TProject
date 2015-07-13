<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>  
<div class="container-fluid max_height" >
	<div class="row max_height">
		<table class="col-md-12" style="height: 100%;padding: 0px;">
			<tr>
				<td bgcolor="#AFFFB0" style="max-width: 150px;width: 150px">
					<div style="height: 100%;"><tiles:insertAttribute name="menu"/></div>
				</td>
				<td >
					<div style="height: 100%;" ng-view ><tiles:insertAttribute name="main"/></div>
				</td>
			</tr>
		</table>
	</div>
</div>