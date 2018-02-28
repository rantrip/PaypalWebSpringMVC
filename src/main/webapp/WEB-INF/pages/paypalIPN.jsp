<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Enumeration"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
    out.println("<p>hello IPN</p>");
String test = "Ranjana";
out.println( test +"<br>");
%>
<form method=post action="../ipn_value">
  <input type="submit" value="IPN">
</form>
<%
    Enumeration in = request.getParameterNames();
    out.println( in.hasMoreElements() +"<br>");
    while(in.hasMoreElements()) {
    String paramName = in.nextElement().toString();
    String paramValue = request.getParameter(paramName);
    out.println( paramName + " = " + paramValue + "<br>");
    }
%>

</body>
</html>