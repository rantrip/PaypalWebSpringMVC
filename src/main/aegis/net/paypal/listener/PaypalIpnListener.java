/**
 * 
 */
package aegis.net.paypal.listener;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;




import org.slf4j.LoggerFactory;


/**
 * @author Tareq.Nabeel
 *
 */
@WebListener 
//public class PaypalIpnListener implements ServletRequestListener { ServletContextAttributeEvent

public class PaypalIpnListener implements ServletContextListener { 
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(PaypalIpnListener.class);
	  public PaypalIpnListener() {
	    super();
	    
	  }
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		// TODO Auto-generated method stub
		logger.debug("*** PaypalIpnListener contextInitialized: " + contextEvent.getServletContext().getServletContextName());
		String contextStr = contextEvent.getServletContext().getContextPath().toString();
		logger.debug("*** PaypalIpnListener contextInitialized: " + contextStr);
	}
	
	

	
	

}
