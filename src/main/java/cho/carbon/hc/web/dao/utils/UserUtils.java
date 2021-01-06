package cho.carbon.hc.web.dao.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import cho.carbon.hc.web.common.UserIdentifier;

public class UserUtils {
	/**
	 * 获得当前登录的用户对象
	 * @param userClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getCurrentUser(Class<T> userClass){
		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		
		if(authen instanceof AnonymousAuthenticationToken) {
			// 匿名用户返回空
			return null;
		}
		
		if(authen != null){
			Object user = authen.getPrincipal();
			return (T) user;
		}
		return null;
	}
	
	/**
	 * 获得当前登录的用户对象（用户对象需继承UserIdentifier方法）
	 * @return
	 */
	public static UserIdentifier getCurrentUser(){
		return getCurrentUser(UserIdentifier.class);
	}
}
