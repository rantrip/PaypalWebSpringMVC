package aegis.net.paypal.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import aegis.net.paypal.controller.IPNProcessingService;
@Controller
@RequestMapping("/paypalIpnListener")
public class PayPalIPNController {
	
	private final static String CONTENT_TYPE = "Content-Type";
    private final static String MIME_APP_URLENC = "application/x-www-form-urlencoded";	
    //private final static String PAY_PAL_DEBUG = "https://www.sandbox.paypal.com/cgi-bin/webscr";	
    private final static String PAY_PAL_PROD = "https://www.paypal.com/cgi-bin/webscr";
    private final static String PAY_PAL_DEBUG = "https://ipnpb.sandbox.paypal.com/cgi-bin/webscr";

    private final static String PARAM_NAME_CMD = "cmd";
    private final static String PARAM_VAL_CMD = "_notify-validate";

    private final static String RESP_VERIFIED = "VERIFIED";

    private final IPNProcessingService ipnService;	

    private final String paypalUrl;	

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(PayPalIPNController.class);
    @Autowired
	//public PayPalIPNController ( IPNProcessingService ipnService, @Value( "${paypal.debug:true}") boolean debug ) {
    public PayPalIPNController ( IPNProcessingService ipnService) {
            this.ipnService = ipnService;
            //paypalUrl = debug ? PAY_PAL_DEBUG : PAY_PAL_PROD;
            paypalUrl = PAY_PAL_DEBUG;
    }

@RequestMapping(method=RequestMethod.POST )
public void processIPN( HttpServletRequest request ) {
	
        logger.debug( "*****PayPalIPNController POST Process IPN ****" );		

        //Create client for Http communication
        HttpClient httpClient = HttpClientBuilder.create().build();
	    HttpPost httppost = new HttpPost( paypalUrl );
	    httppost.setHeader( CONTENT_TYPE, MIME_APP_URLENC );
     
    try {
            //Store Payment info for passing to processing service
            Map<String, String> params = new HashMap<String, String>(); 

            //Use name/value pair for building the encoded response string		
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2); 

            //Append the required command
            nameValuePairs.add( new BasicNameValuePair( PARAM_NAME_CMD, PARAM_VAL_CMD  ) );
	
            //Process the parameters
            Enumeration<String> names = request.getParameterNames();
            while ( names.hasMoreElements() ) {
            	String param = names.nextElement();
            	String value = request.getParameter( param );
		
            	nameValuePairs.add( new BasicNameValuePair( param, value ) );
            	params.put( param, value );
            	logger.debug("*****PayPalIPNController: " + param + "=" + value );					
            }
	
            httppost.setEntity( new UrlEncodedFormEntity( nameValuePairs ) );			
		
	if ( verifyResponse( httpClient.execute( httppost ) ) ) {
					 //Remember to track completed transactions and don't process duplicates
                    ipnService.processPaymentNotification( params );
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
	
           InputStream is = response.getEntity().getContent();
           BufferedReader reader = new BufferedReader(new InputStreamReader(is));
           String responseText = reader.readLine();
           is.close();

          logger.debug( "RESPONSE : " + responseText );
          logger.debug( "*****PayPalIPNController RESPONSE verifyResponse IPN ****" + responseText);
          response.setStatusCode(200);
    
         return responseText.equals( RESP_VERIFIED );
    
}

}
