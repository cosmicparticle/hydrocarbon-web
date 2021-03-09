package cho.carbon.hc.web.common.interceptor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import cho.carbon.util.MD5Util;

@Component
public class Md5PasswordEncoder 
implements PasswordEncoder
{

	@Override
	public String encode(CharSequence rawPassword) {
		String password = (String)rawPassword;
		  return MD5Util.encryptMD5(password.getBytes());
	}
//
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
//		MD5Util.encryptMD5(var0)
		String password = (String)rawPassword;
		 return encodedPassword.equals("{MD5}" +MD5Util.encryptMD5(password.getBytes()));
	}

}
