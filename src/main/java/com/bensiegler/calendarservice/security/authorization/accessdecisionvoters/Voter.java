package com.bensiegler.calendarservice.security.authorization.accessdecisionvoters;

import com.bensiegler.calendarservice.models.calstandard.calendarobjects.CalendarObject;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.web.FilterInvocation;

public abstract class Voter implements AccessDecisionVoter<CalendarObject> {

}
