<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product to purchase</title>
</head>
<body>
 <form:form method="POST" commandName="productType" action="../payPalProduct"> 

<%-- <form:form method="POST" commandName="productType" action="../payPalProductJS"> --%>
<table border='0'>
<tr> 
<td>Select the subscription for the product : 
</td>
</tr>
<tr>
<td>
<form:radiobutton path="productSubscription" value="0"/>Open/Free (Limited access to Touchstone) 
<br>
<form:radiobutton path="productSubscription" value="99"/>Started/Monthly (pay $99 for a month access - Renew every month)
<br> 
<form:radiobutton path="productSubscription" value="999"/>Started/Yearly (pay $999 USD for 12 months of access) 
<br>
<form:radiobutton path="productSubscription" value="6500"/>Project/Yearly (pay $6,500 USD for 12 months of access) 
<br>
<form:radiobutton path="productSubscription" value="25000"/>Enterprise/Yearly (pay $25,000 USD for 12 months of access)
 </td>
</tr>
<tr>
<td colspan="3"><input type="submit" /></td>
</tr>
</table>
</form:form>
</body>
</html>