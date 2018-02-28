package aegis.net.paypal.controller;
/**
 * 
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import aegis.net.paypal.model.ProductType;


import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
/**
 * @author Tareq.Nabeel
 *
 */
@Controller
public class PayPalWebController {
	
	
	private static int counter = 0;
	private static final String VIEW_INDEX = "index";
	private static final String PRODUCT_TYPE_INDEX = "productType";
	private static final String PRODUCT_CANCEL = "paypalCancel";
	private static final String PRODUCT_ORDER_SUCCESS = "paypalSuccess";
	private static final String PRODUCT_ORDER_NOTIFY = "paypalNotify";
	private static final String PRODUCT_TYPE_INDEXJS = "productType";
	private static final String PRODUCT_ORDER_IPN = "paypalIPN";
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(PayPalWebController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(ModelMap model) {

		model.addAttribute("message", "PayPal Demo");
		model.addAttribute("counter", ++counter);
		logger.debug("[welcome] counter : {}", counter);

		// Spring uses InternalResourceViewResolver and return back index.jsp
		return VIEW_INDEX;

	}

//	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
//	public String welcomeName(@PathVariable String name, ModelMap model) {
//
//		model.addAttribute("message", "Welcome " + name);
//		model.addAttribute("counter", ++counter);
//		logger.debug("[welcomeName] counter : {}", counter);
//		logger.debug("[welcomeName]: ", name);
//		
//		return VIEW_INDEX;
//
//	}
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String welcomeName(@PathVariable ("name") String name, ModelMap model, HttpServletRequest request) {

		model.addAttribute("message", "Welcome " + name);
		model.addAttribute("counter", ++counter);
		logger.debug("[welcomeName] counter : {}", counter);
		logger.debug("[welcomeName]: " + name);
		System.out.println("Logged User: " + name);
		request.getSession().setAttribute("loggedName", name);
		return VIEW_INDEX;

	}
	
	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	public String payPalDemo(ModelMap model) {

		//model.addAttribute("message", "Paypal Demo ");
		ProductType productType = new ProductType();
		//Make "Spring MVC" as default checked value
		productType.setProductSubscription("free");
		model.addAttribute("productType", productType);
		
		logger.debug("[paypal] Demo : ");
		return PRODUCT_TYPE_INDEX;

	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public String payPalProductCancel(ModelMap model) {

		logger.debug("****payPalProductCancel****");
		return PRODUCT_CANCEL;

	}
	
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String payPalProductSuccess(ModelMap model, HttpServletRequest request) {

		Enumeration in = request.getParameterNames();
		logger.debug("***payPalProductSuccess request has parameter" + in.hasMoreElements());
	    while(in.hasMoreElements()) {
	    	String paramName = in.nextElement().toString();
	    	String paramValue = request.getParameter(paramName);
	    	
	    	logger.debug( paramName + " = " + paramValue);
	    	if(paramName.equalsIgnoreCase("tx")){
	    		request.getSession().setAttribute("transactionId",paramName);
	    	}
	    }
	    payPalProductNotify(model,request);
		logger.debug("****payPalProductSuccess****");
		return PRODUCT_ORDER_SUCCESS;

	}
	
	@RequestMapping(value = "/notify", method = RequestMethod.POST)
	public String payPalProductNotify(ModelMap model, HttpServletRequest request) {

		Enumeration in = request.getParameterNames();
		String transactionID = (String)request.getSession().getAttribute("transactionId");
		logger.debug("***payPalProductNotify request has parameter" + in.hasMoreElements());
		processPDT( request, transactionID);
		logger.debug("****payPalProductNotify****");
		return PRODUCT_ORDER_NOTIFY;

	}
	
	public void processPDT( HttpServletRequest request, String transactionId) {
		
        logger.debug( "*****processPDT ****" );
        String CONTENT_TYPE = "Content-Type";
        String MIME_APP_URLENC = "application/x-www-form-urlencoded";	
        String PAY_PAL_DEBUG = "https://www.sandbox.paypal.com/cgi-bin/webscr";
        String PARAM_NAME_CMD = "cmd";
        String PARAM_VAL_CMD = "_notify-synch";
        String PARAM_NAME_AT = "at";
        String PARAM_VAL_AT = "uDfybYmgLjsJRja3RSNHcT3KNQVjFaMwCmNJaBkGeDuvoZ7nyzPX570o7CS";
        String PARAM_NAME_TX = "tx";
        String PARAM_VAL_TX = transactionId;

        //Create client for Http communication
        HttpClient httpClient = HttpClientBuilder.create().build();
	    HttpPost httppost = new HttpPost( PAY_PAL_DEBUG );
	    httppost.setHeader( CONTENT_TYPE, MIME_APP_URLENC );
     
    try {
            //Store Payment info for passing to processing service
            Map<String, String> params = new HashMap<String, String>(); 

            //Use name/value pair for building the encoded response string		
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2); 

            //Append the required command
            nameValuePairs.add( new BasicNameValuePair( PARAM_NAME_CMD, PARAM_VAL_CMD  ) );
            nameValuePairs.add( new BasicNameValuePair( PARAM_NAME_TX, PARAM_VAL_TX  ) );
            nameValuePairs.add( new BasicNameValuePair( PARAM_NAME_AT, PARAM_VAL_AT  ) );
	
//            //Process the parameters
            Enumeration<String> names = request.getParameterNames();
            while ( names.hasMoreElements() ) {
            	String param = names.nextElement();
            	String value = request.getParameter( param );
		
            	nameValuePairs.add( new BasicNameValuePair( param, value ) );
            	params.put( param, value );
            	logger.debug("*****processPDT: " + param + "=" + value );					
            }
	
            httppost.setEntity( new UrlEncodedFormEntity( nameValuePairs ) );			
		
	if ( verifyResponse( httpClient.execute( httppost ) ) ) {
					//Implement your processing logic here, I used an @Asyn annotation
                    //Remember to track completed transactions and don't process duplicates
		String responseStr = readResponse(httpClient.execute( httppost ));
		
		   
	}

   } catch ( UnsupportedEncodingException e ) {			
        e.printStackTrace();
       } catch ( ClientProtocolException e ) {
        e.printStackTrace();
   } catch ( IOException e ) {
	e.printStackTrace();
       }	 				
    }	
	
	
	private boolean verifyResponse( HttpResponse response ) throws IllegalStateException, IOException {
		
		String RESP_SUCCESS = "SUCCESS";
        InputStream is = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String responseText = reader.readLine();
        is.close();
        
       logger.debug( "RESPONSE : " + responseText );
       logger.debug( "**** RESPONSE verifyResponse PDT ****" + responseText);
       response.setStatusCode(200);
 
      return responseText.equals( RESP_SUCCESS );
 
   }
	
   private String readResponse( HttpResponse response ) throws IllegalStateException, IOException {
		
		String responseString = "";
        InputStream is = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        HttpEntity entity = response.getEntity();

        // Read the contents of an entity and return it as a String.
//        String content = EntityUtils.toString(entity);
//        logger.debug("ENTITY UTILS RESPONSE PDT : " + content);
        int statusCode = response.getStatusLine().getStatusCode();
        String message = response.getStatusLine().getReasonPhrase();
        String line;
        while ((line = reader.readLine()) != null) {
            responseString += line;
        }
        is.close();
        logger.debug( "BUFFER READ RESPONSE PDT : " + responseString );
        
      return responseString;
 
   }

	
}
