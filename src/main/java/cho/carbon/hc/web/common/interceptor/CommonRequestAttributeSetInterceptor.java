package cho.carbon.hc.web.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class CommonRequestAttributeSetInterceptor implements HandlerInterceptor{
	
	 Logger   logger = LoggerFactory.getLogger(CommonRequestAttributeSetInterceptor.class);	
	
	 @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		long resStamp = System.currentTimeMillis();
		request.setAttribute("RES_STAMP", resStamp);
		String basePath = null;
		
		basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		request.setAttribute("basePath", basePath);
 
		return true;
	}
	 
	 
	 
	 

}
