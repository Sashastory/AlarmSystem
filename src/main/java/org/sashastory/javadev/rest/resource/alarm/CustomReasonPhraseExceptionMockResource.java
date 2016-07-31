package org.sashastory.javadev.rest.resource.alarm;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.sashastory.javadev.rest.errorhandling.CustomReasonPhraseException;
import org.sashastory.javadev.rest.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/mocked-custom-reason-phrase-exception")
public class CustomReasonPhraseExceptionMockResource {
	
	@Autowired
	private AlarmService alarmService;
	
	@GET
	public void testReasonChangedInResponse() throws CustomReasonPhraseException{
		alarmService.generateCustomReasonPhraseException();
	}
}
