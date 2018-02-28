package aegis.net.paypal.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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

import aegis.net.paypal.controller.IPNProcessingService;

@WebServlet(name = "IpnListenerServlet", urlPatterns = { "/ipnvalue" }) 
public class IpnListenerServlet extends HttpServlet{

	private static final long serialVersionUID = 2L;
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(IpnListenerServlet.class);
	
	
	private final static String CONTENT_TYPE = "Content-Type";
    private final static String MIME_APP_URLENC = "application/x-www-form-urlencoded";	
    private final static String PAY_PAL_DEBUG = "https://www.sandbox.paypal.com/cgi-bin/webscr";	
    private final static String PAY_PAL_PROD = "https://www.paypal.com/cgi-bin/webscr";
   // private final static String PAY_PAL_DEBUG = "https://ipnpb.sandbox.paypal.com/cgi-bin/webscr";

    private final static String PARAM_NAME_CMD = "cmd";
    private final static String PARAM_VAL_CMD = "_notify-validate";

    private final static String RESP_VERIFIED = "VERIFIED";

    private final IPNProcessingService ipnService = new IPNProcessingService() ;	

    private final String paypalUrl="";	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("GET called - Message Recieved");
		System.out.println("GET called - Message Recieved");
		resp.setStatus(200);
		PrintWriter out = resp.getWriter();
	      out.println("<h1>" + "SERVLET INVOKED" + "</h1>");
	      
	      
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		logger.debug("IPN received");
        //req.getRequestDispatcher("/paypalIpnListener").forward(req, resp);
		System.out.println("IPN received");
		PrintWriter out = resp.getWriter();
	      out.println("<h1>" + "IPN RECIEVED" + "</h1>");
	      //req.setCharacterEncoding("UTF-8");
	     //resp.setCharacterEncoding("UTF-8");
	      //processIPN(req);
	     // resp.setStatus(200);
	}
	
	
	public void processIPN( HttpServletRequest request ) {
		
        logger.debug( "*****PayPalIPNController POST Process IPN ****" );		

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
            
            HttpResponse response = httpClient.execute(httppost);

        	int responseCode = response.getStatusLine().getStatusCode();

        	System.out.println("\nSending 'POST' request to URL : " + httppost);
        	System.out.println("Post parameters : " + nameValuePairs);
        	System.out.println("Response Code : " + responseCode);
		
//	if ( verifyResponse( httpClient.execute( httppost ) ) ) {
//					//Implement your processing logic here, I used an @Asyn annotation
//                    //Remember to track completed transactions and don't process duplicates
//                    ipnService.processPaymentNotification( params );
//	}

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
