package aegis.net.paypal.controller;



import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import aegis.net.paypal.model.*;


import java.io.BufferedReader;
import java.io.DataInputStream;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
@Controller
@RequestMapping("/payPalProductJS")

public class PayPalProductJSController {
	
	
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(PayPalProductJSController.class);
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(
			@ModelAttribute("productType") ProductType productType,
			BindingResult result, SessionStatus status, HttpServletRequest request) {
		
		   String prodType =  productType.getProductSubscription();
		   String viewType = "productType";
		   logger.debug("withing PayPalProductController processSubmit");
			status.setComplete();
			if(prodType.equalsIgnoreCase("99")){
			   viewType =  "paypalSubscription";
		   }
		   else if(prodType.equalsIgnoreCase("999") || prodType.equalsIgnoreCase("6500") || prodType.equalsIgnoreCase("25000")){
			   logger.debug("PayPalProductController processSubmit: " + prodType);
			   request.getSession().setAttribute("prodType",prodType);			   
			    viewType = "paypalBuyNow_javascript";
		   }
		    return viewType;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model){
		
		ProductType productType = new ProductType();
		//Make "Spring MVC" as default checked value
		productType.setProductSubscription("free");
		model.addAttribute("productType", productType);
		logger.debug("withing PayPalProductController initForm");
		return "productType";
	}
	
	
  
}
