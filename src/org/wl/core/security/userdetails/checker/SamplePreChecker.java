package org.wl.core.security.userdetails.checker;


import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;


/**
 * 账号类型验证
 * @author Administrator
 *
 */
public class SamplePreChecker implements UserDetailsChecker {
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public void check(UserDetails userDetails) {
    }

}
