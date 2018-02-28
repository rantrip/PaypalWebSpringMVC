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
	String test = null;
    test = (String)request.getSession().getAttribute("loggedName");
    if(null == test){
    	test = "Guest";
    }
    out.println( test +"<br>");
%>
<H1> You paid successfully.</H1><br>
<h1><%=test %> transaction id = <%=request.getParameter("tx") %></h1>
<%
    Enumeration in = request.getParameterNames();
    out.println( in.hasMoreElements() +"<br>");
    while(in.hasMoreElements()) {
    String paramName = in.nextElement().toString();
    String paramValue = request.getParameter(paramName);
    out.println( paramName + " = " + paramValue + "<br>");
    }
%>

<form method=post action="https://www.sandbox.paypal.com/cgi-bin/webscr">
  <input type="hidden" name="cmd" value="_notify-synch">
   <input type="hidden" name="tx" value="<%=request.getParameter("tx") %>"> 
  <!-- <input type="hidden" name="tx" value="1PB4348047224333J"> -->
  <input type="hidden" name="at" value="uDfybYmgLjsJRja3RSNHcT3KNQVjFaMwCmNJaBkGeDuvoZ7nyzPX570o7CS">
  <input type="submit" value="PDT">
</form>

<form method=post action="../paypal/paypalIpnListener">
    
<input type="hidden" name="mc_gross" value="99.00" />
<input type="hidden" name="protection_eligibility" value="Ineligible" />
<input type="hidden" name="address_status" value="confirmed" />
<input type="hidden" name="payer_id" value="4UUMYVV55M85U" />
<input type="hidden" name="address_street" value="1 Main St" />
<input type="hidden" name="payment_date" value="14:37:04 Oct 25, 2017 PDT" />
<input type="hidden" name="payment_status" value="Completed" />
<input type="hidden" name="charset" value="UTF8" />
<input type="hidden" name="address_zip" value="95131" />
<input type="hidden" name="first_name" value="test" />
<input type="hidden" name="option_selection1" value="Starter" />
<input type="hidden" name="mc_fee" value="3.17" />
<input type="hidden" name="address_country_code" value="US" />
<input type="hidden" name="address_name" value="test buyer" />
<input type="hidden" name="notify_version" value="3.8" />
<input type="hidden" name="subscr_id" value="I-49YN2HG8RJMS" />
<input type="hidden" name="payer_status" value="verified" />
<input type="hidden" name="business" value="rantrip-facilitator@gmail.com" />
<input type="hidden" name="address_country" value="United States" />
<input type="hidden" name="address_city" value="SanJose" />
<input type="hidden" name="verify_sign" value="Ao68DNqlX5gVaPZlGVYk.BmBnaqJA7xd4Xv2ByHPO16mvZnH7SsVEbT8" />
<input type="hidden" name="payer_email" value="rantrip-buyer@gmail.com" />
<input type="hidden" name="option_name1" value="Paymentoptions:" />
<input type="hidden" name="txn_id" value="3F3147513P470923P" />
<input type="hidden" name="payment_type" value="instant" />
<input type="hidden" name="last_name" value="buyer" />
<input type="hidden" name="address_state" value="CA" />
<input type="hidden" name="receiver_email" value="rantripfacilitator@gmail.com" />
<input type="hidden" name="payment_fee" value="3.17" />
<input type="hidden" name="receiver_id" value="SWH4BGYHSMBZY" />
<input type="hidden" name="txn_type" value="subscr_payment" />
<input type="hidden" name="mc_currency" value="USD" />
<input type="hidden" name="residence_country" value="US" />
<input type="hidden" name="test_ipn" value="1" />
<input type="hidden" name="transaction_subject" value="" />
<input type="hidden" name="payment_gross" value="99.00" />
<input type="hidden" name="ipn_track_id" value="25ac83a8798e9" />
 <input type="submit" value="IPN">
</form>


<form method=post action="https://ipnpb.sandbox.paypal.com/cgi-bin/webscr">
	<input type="hidden" name="cmd" value="_notify-validate">
	<input type="hidden" name="mc_gross" value="99.00" />
	<input type="hidden" name="protection_eligibility" value="Ineligible" />
	<input type="hidden" name="address_status" value="confirmed" />
	<input type="hidden" name="payer_id" value="4UUMYVV55M85U" />
	<input type="hidden" name="address_street" value="1 Main St" />
	<input type="hidden" name="payment_date" value="14:37:04 Oct 25, 2017 PDT" />
	<input type="hidden" name="payment_status" value="Completed" />
	<input type="hidden" name="charset" value="UTF8" />
	<input type="hidden" name="address_zip" value="95131" />
	<input type="hidden" name="first_name" value="test" />
	<input type="hidden" name="option_selection1" value="Starter" />
	<input type="hidden" name="mc_fee" value="3.17" />
	<input type="hidden" name="address_country_code" value="US" />
	<input type="hidden" name="address_name" value="test buyer" />
	<input type="hidden" name="notify_version" value="3.8" />
	<input type="hidden" name="subscr_id" value="I-49YN2HG8RJMS" />
	<input type="hidden" name="payer_status" value="verified" />
	<input type="hidden" name="business" value="rantrip-facilitator@gmail.com" />
	<input type="hidden" name="address_country" value="United States" />
	<input type="hidden" name="address_city" value="SanJose" />
	<input type="hidden" name="verify_sign" value="Ao68DNqlX5gVaPZlGVYk.BmBnaqJA7xd4Xv2ByHPO16mvZnH7SsVEbT8" />
	<input type="hidden" name="payer_email" value="rantrip-buyer@gmail.com" />
	<input type="hidden" name="option_name1" value="Paymentoptions:" />
	<input type="hidden" name="txn_id" value="3F3147513P470923P" />
	<input type="hidden" name="payment_type" value="instant" />
	<input type="hidden" name="last_name" value="buyer" />
	<input type="hidden" name="address_state" value="CA" />
	<input type="hidden" name="receiver_email" value="rantripfacilitator@gmail.com" />
	<input type="hidden" name="payment_fee" value="3.17" />
	<input type="hidden" name="receiver_id" value="SWH4BGYHSMBZY" />
	<input type="hidden" name="txn_type" value="subscr_payment" />
	<input type="hidden" name="mc_currency" value="USD" />
	<input type="hidden" name="residence_country" value="US" />
	<input type="hidden" name="test_ipn" value="1" />
	<input type="hidden" name="transaction_subject" value="" />
	<input type="hidden" name="payment_gross" value="99.00" />
	<input type="hidden" name="ipn_track_id" value="25ac83a8798e9" />
	<input type="submit" value="IPN Paypal">
</form>
</body>
</html>