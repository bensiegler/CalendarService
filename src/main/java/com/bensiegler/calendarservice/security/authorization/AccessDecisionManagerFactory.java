package com.bensiegler.calendarservice.security.authorization;

import com.bensiegler.calendarservice.security.authorization.accessdecisionvoters.DeleteVoter;
import com.bensiegler.calendarservice.security.authorization.accessdecisionvoters.EditVoter;
import com.bensiegler.calendarservice.security.authorization.accessdecisionvoters.ShareVoter;
import com.bensiegler.calendarservice.security.authorization.accessdecisionvoters.ViewVoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class AccessDecisionManagerFactory {

    private final static List<AccessDecisionVoter<?>> defaultVoters = Arrays.asList(
            new WebExpressionVoter(),
            new RoleVoter(),
            new AuthenticatedVoter()
    );

    @Autowired
    DeleteVoter deleteVoter;

    @Autowired
    EditVoter editVoter;

    @Autowired
    ShareVoter shareVoter;

    @Autowired
    ViewVoter viewVoter;

    @Bean
    public AccessDecisionManager defaultManager() {
        return new UnanimousBased(defaultVoters);
    }

    @Bean
    public AccessDecisionManager deleteAccessDecisionManager() {
        return filterPlusDefault(deleteVoter);
    }

    @Bean
    public AccessDecisionManager editAccessDecisionManager() {
        return filterPlusDefault(editVoter);
    }

    @Bean
    public AccessDecisionManager shareAccessDecisionManager() {
        return filterPlusDefault(shareVoter);
    }

    @Bean
    public AccessDecisionManager viewAccessDecisionManager() {
        return filterPlusDefault(viewVoter);
    }

    //////////////HELPER METHODS//////////////
    private AccessDecisionManager filterPlusDefault(AccessDecisionVoter<?> extraVoter) {
        List<AccessDecisionVoter<?>> accessDecisionVoters = new ArrayList<>(defaultVoters);
        accessDecisionVoters.add(extraVoter);

        return new UnanimousBased(accessDecisionVoters);
    }

    private AccessDecisionManager multipleFiltersPlusDefault(List<AccessDecisionVoter<?>> extraVoters) {
        List<AccessDecisionVoter<?>> accessDecisionVoters = new ArrayList<>(defaultVoters);
        accessDecisionVoters.addAll(extraVoters);

        return new UnanimousBased(accessDecisionVoters);
    }

}
