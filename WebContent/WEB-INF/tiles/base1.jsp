<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
</head>
<body>
<div>
	<table>
		<tr>
			<td><div><tiles:insertAttribute name="top" /> </div></td>
		</tr>
		<tr>
			<td><div><tiles:insertAttribute name="middle" /> </div></td>
		</tr>
		<tr>
			<td><div><tiles:insertAttribute name="footer" /> </div></td>
		</tr>
	</table>
</div>
</body>
</html>