package aegis.net.paypal.controller;



import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import aegis.net.paypal.model.*;

@Controller
@RequestMapping("/payPalProduct")

public class PayPalProductController {
	
	
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(PayPalProductController.class);
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
			    viewType = "paypalBuyNow";
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
