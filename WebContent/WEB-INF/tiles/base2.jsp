<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>  
<div class="container-fluid max_height">
	<div class="max_height row">
		<table class="max_height col-md-12">
			<tr>
				<td bgcolor="#AFFFB0" width="150px"><div class="max_height"><tiles:insertAttribute name="menu"/> </div></td>
				<td ><div class="max_height"><tiles:insertAttribute name="main" /> </div></td>
			</tr>
		</table>
	</div>
</div>
