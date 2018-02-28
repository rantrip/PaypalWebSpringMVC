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
    out.println("<p>hello</p>");
String test = "Ranjana";
out.println( test +"<br>");
%>
<H1> You paid successfully.</H1><br>
<h1>Ranjana transaction id = <%=request.getParameter("tx") %></h1>
<form method=post action="https://www.paypal.com/cgi-bin/webscr">
  <input type="hidden" name="cmd" value="_notify-synch">
  <input type="hidden" name="tx" value="<%=(String)session.getAttribute("transaction_id")%>">
  <input type="hidden" name="at" value="<%=(String)session.getAttribute("identiyToken")%>">
  <input type="submit" value="PDT">
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

<form method=post action="https://www.paypal.com/cgi-bin/webscr">
  <input type="hidden" name="cmd" value="_profile-ipn-notify">
  <input type="submit" value="IPN">
</form>

<form method=post action="https://ipnpb.sandbox.paypal.com/cgi-bin/webscr">
<input type="hidden" name="cmd" value="_notify-validate">
  <input type="submit" value="IPN">
</form>

</body>
</html>