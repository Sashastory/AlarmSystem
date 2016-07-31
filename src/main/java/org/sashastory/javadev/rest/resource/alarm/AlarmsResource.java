package org.sashastory.javadev.rest.resource.alarm;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.sashastory.javadev.rest.errorhandling.AppException;
import org.sashastory.javadev.rest.service.AlarmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Component
@Path("/alarms")
public class AlarmsResource {
	
	@Autowired
	private AlarmService alarmService;
	
	/*
	 * *********************************** CREATE ***********************************
	 */

	/**
	 * Adds a new resource (alarm) from the given json format (at least type,
	 * alarm date and user id elements are required at the DB level)
	 * 
	 * @param alarm
	 * @return
	 * @throws AppException
	 */
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response createAlarm(Alarm alarm) throws AppException {
		Long createAlarmId = alarmService.createAlarm(alarm);
		return Response.status(Response.Status.CREATED)
						.entity("A new alarm has been created")
						.header("Location",
								"http://localhost:8080/rest-jersey-spring/alarms/"
								+ String.valueOf(createAlarmId)).build();
	}
	
	/**
	 * Adds a new alarm (resource) from "form" 
	 * 
	 * @param type
	 * @param date
	 * @param description
	 * @param userId
	 * @param severity
	 * @return
	 * @throws AppException
	 */
	
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces({ MediaType.TEXT_HTML })
	public Response createAlarmFromApplicationFormURLencoded(
			@FormParam("type") String type,
			@FormParam("date") Date date,
			@FormParam("description") String description,
			@FormParam("usedId") Long userId,
			@FormParam("severity") String severity) throws AppException {

		Alarm alarm = new Alarm(type, date, description, userId,
				severity);
		Long createAlarmId = alarmService.createAlarm(alarm);

		return Response
				.status(Response.Status.CREATED)// 201
				.entity("A new alarm/resource has been created at /rest-jersey-spring/alarms/"
						+ createAlarmId)
				.header("Location",
						"http://localhost:8080/rest-jersey-spring/alarms/"
								+ String.valueOf(createAlarmId)).build();
	}
	
	/**
	 * A list of resources (here alarms) provided in json format will be added
	 * to the database.
	 * 
	 * @param alarms
	 * @return
	 * @throws AppException
	 */
	
	@POST
	@Path("list")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response createAlarms(List<Alarm> alarms) throws AppException {
		alarmService.createAlarms(alarms);
		return Response.status(Response.Status.CREATED) // 201
				.entity("List of alarms was successfully created").build();
	}
	
	/*
	 * *********************************** READ ***********************************
	 */
	/**
	 * Returns all resources (alarms) from the database
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws AppException
	 */
	
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Alarm> getAlarms() throws IOException, AppException {
		List<Alarm>alarms = alarmService.getAlarms();
		return alarms;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAlarmById(@PathParam("id") Long id, @QueryParam("detailed") boolean detailed)
			throws IOException,	AppException {
		Alarm alarmById = alarmService.getAlarmById(id);
		return Response.status(200)
				.entity(alarmById, detailed ? new Annotation[]{AlarmDetailedView.Factory.get()} : new Annotation[0])
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}	

	/*
	 * *********************************** UPDATE ***********************************
	 */

	/**
	 * The method offers both Creation and Update resource functionality. If
	 * there is no resource yet at the specified location, then an alarm
	 * creation is executed and if there is then the resource will be full
	 * updated.
	 * 
	 * @param id
	 * @param alarm
	 * @return
	 * @throws AppException
	 */
	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response putAlarmById(@PathParam("id") Long id, Alarm alarm)
			throws AppException {

		Alarm alarmById = alarmService.verifyAlarmExistenceById(id);

		if (alarmById == null) {
			// resource not existent yet, and should be created under the
			// specified URI
			Long createAlarmId = alarmService.createAlarm(alarm);
			return Response
					.status(Response.Status.CREATED)
					// 201
					.entity("A new alarm has been created AT THE LOCATION you specified")
					.header("Location",
							"http://localhost:8080/rest-jersey-spring/alarms/"
									+ String.valueOf(createAlarmId)).build();
		} else {
			// resource is existent and a full update should occur
			alarmService.updateAlarm(alarm);
			return Response
					.status(Response.Status.OK)
					// 200
					.entity("The alarm you specified has been fully updated created AT THE LOCATION you specified")
					.header("Location",
							"http://localhost:8080/rest-jersey-spring/alarms/"
									+ String.valueOf(id)).build();
		}
	}

	/*
	 * *********************************** DELETE ***********************************
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.TEXT_HTML })
	public Response deleteAlarmById(@PathParam("id") Long id) {
		alarmService.deleteAlarmById(id);
		return Response.status(Response.Status.NO_CONTENT)// 204
				.entity("Alarm successfully removed from database").build();
	}

	@DELETE
	@Produces({ MediaType.TEXT_HTML })
	public Response deleteAlarms() {
		alarmService.deleteAlarms();
		return Response.status(Response.Status.NO_CONTENT)// 204
				.entity("All alarms have been successfully removed").build();
	}

	public void setalarmService(AlarmService alarmService) {
		this.alarmService = alarmService;
	}

}
