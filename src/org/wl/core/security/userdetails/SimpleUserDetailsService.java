package org.wl.core.security.userdetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wl.core.security.domain.WlUser;
import org.wl.core.security.service.WlUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("userDetailsService")
public class SimpleUserDetailsService implements UserDetailsService {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Autowired
    private WlUserService userService;
    
    private final static Log log = LogFactory.getLog(UserDetailsService.class);

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    	log.debug("loadUserByUsername:"+username);
		SimpleUser simpleUser = null;
		Map<String,Object>map = new HashMap<String,Object>();
		map.put("name", username);
		List<WlUser> usersLst = this.userService.getWlUsers(map);
		
		if(usersLst == null ) {
			throw new UsernameNotFoundException(username);
		}
		simpleUser = new SimpleUser(usersLst.get(0));
		return simpleUser;
    }

}
