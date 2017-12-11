package edu.asu.heal.promis.api.endpoints;

/**
 *       Copyright 2010 Newcastle University
 *
 *          http://research.ncl.ac.uk/smart/
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.JSONHttpServletRequestWrapper;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.OAuthServerRegistrationRequest;
import org.apache.oltu.oauth2.ext.dynamicreg.server.response.OAuthServerRegistrationResponse;
//import org.apache.oltu.oauth2.integration.CommonExt;

import edu.asu.heal.promis.api.demo.ServerContent;
import edu.asu.heal.promis.api.service.PromisService;

/**
 *
 *
 *
 */
@Path("/register")
public class RegistrationEndpoint {

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response register(String requestJSON,
							@QueryParam("patientPIN") String patientPIN,
							@QueryParam("appType") String appType,
							@QueryParam("appVersion") String appVersion
							) throws OAuthSystemException {

		OAuthServerRegistrationRequest oauthRequest = null;
		System.out.println("The request for register is receievd");
		String clientRegistrationJSON = PromisService.getPromisService().clientRegistration(requestJSON);
		System.out.println(clientRegistrationJSON);
		
		return Response.status(Response.Status.CREATED).entity(clientRegistrationJSON).build();

	}
}