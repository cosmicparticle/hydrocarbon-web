package cho.carbon.hc.web;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CommonAdminUserServiceImpl implements UserDetailsService{

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		ArrayList<GrantedAuthority> l = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority ga = new SimpleGrantedAuthority("ROLE_ADMIN");
		l.add(ga);
		return new User("admin", "21232f297a57a5a743894a0e4a801fc3", l);
	}

}
