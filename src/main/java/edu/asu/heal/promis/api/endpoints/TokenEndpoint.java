package edu.asu.heal.promis.api.endpoints;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.asu.heal.promis.api.demo.TestContent;
import edu.asu.heal.promis.api.errorHandler.BadRequestCustomException;
import edu.asu.heal.promis.api.errorHandler.ErrorMessage;
import edu.asu.heal.promis.api.service.PromisService;
import edu.asu.heal.promis.api.errorHandler.NotFoundException;;

/**
 *
 *
 *
 */
@Path("/token")
public class TokenEndpoint {
	public static String accessToken = "";
	ObjectMapper mapper = new ObjectMapper();

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response authorize(String requestJSON,
							@QueryParam("grant_type") String grantType) throws OAuthSystemException {
		
		try 
		{
			
			
			if(grantType == null || grantType.isEmpty() || !grantType.equals("client_credential"))
			{
				String JsonErrorMessage = mapper.writeValueAsString(new ErrorMessage("Bad Request : Incorrect Grant Type : HEAL ASU OAUTH only supports client_credential"));
				throw new BadRequestCustomException(JsonErrorMessage);
			}
			
			String tokenRegistrationJSON = PromisService.getPromisService().generateToken(requestJSON);
			
			System.out.println(tokenRegistrationJSON);
			
			return Response.status(Response.Status.CREATED).entity(tokenRegistrationJSON).build();
			
		
		}
		catch(JsonProcessingException e)
		{
						e.printStackTrace();
						String jsonErrorMessage = "{'error':'Unexpected Error While processing request'}";
						throw new NotFoundException(Response.Status.INTERNAL_SERVER_ERROR,jsonErrorMessage);
		}
	}
	

	@GET
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
	public Response authorizeGet(@Context HttpServletRequest request) throws OAuthSystemException {
		System.out.println("The request for token GET is received::");
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

		OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
				.setAccessToken(oauthIssuerImpl.accessToken()).setExpiresIn("900").buildJSONMessage();

		return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
	}
	
	static String extractPostRequestBody(HttpServletRequest request) {
	    if ("POST".equalsIgnoreCase(request.getMethod())) {
	        Scanner s = null;
	        try {
	            s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return s.hasNext() ? s.next() : "";
	    }
	    return "";
	}

}