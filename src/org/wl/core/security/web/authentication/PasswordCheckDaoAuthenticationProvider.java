package org.wl.core.security.web.authentication;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class PasswordCheckDaoAuthenticationProvider extends DaoAuthenticationProvider {
    private final static Log log = LogFactory.getLog(PasswordCheckDaoAuthenticationProvider.class);
    
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	    super.additionalAuthenticationChecks(userDetails, authentication);
	    log.debug("additionalAuthenticationChecks");
    }

}
