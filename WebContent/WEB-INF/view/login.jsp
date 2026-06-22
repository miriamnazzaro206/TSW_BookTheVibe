<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pagina di Login</title>
</head>
<body>
	<form action=LoginServlet method=post>
	<table>
	<tr><td>Inserire e-mail</td><td><input type=text name=txtEmail></td></tr>
	<tr><td>Inserire password</td><td><input type=password name=txtPwd></td></tr>
	<tr><td><input type=submit value=Login></td><td><input type=reset></td></tr>
	</table>
	</form>

</body>
</html>