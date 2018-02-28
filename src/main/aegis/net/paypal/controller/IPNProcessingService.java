package aegis.net.paypal.controller;

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class IPNProcessingService {

	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(IPNProcessingService.class);
	
	  public void processPaymentNotification (Map <String, String> params){
		  
		  String processString = "processing";
		  logger.debug("***** IPNProcessingService processPaymentNotification ***** ");
		  
	  }
}
