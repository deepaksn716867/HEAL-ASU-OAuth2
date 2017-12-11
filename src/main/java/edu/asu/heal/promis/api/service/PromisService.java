package edu.asu.heal.promis.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.asu.heal.promis.api.errorHandler.ErrorMessage;
import edu.asu.heal.promis.api.errorHandler.NotFoundException;
import edu.asu.heal.promis.api.model.AccessToken;
import edu.asu.heal.promis.api.model.ClientRegistration;
import edu.asu.heal.promis.api.model.ModelException;
import edu.asu.heal.promis.api.model.ModelFactory;
import edu.asu.heal.promis.api.errorHandler.BadRequestCustomException;


/**
 * This is the service class , it is singleton class.
 * All of the json must be constructed here.
 * @author deepak
 */

public class PromisService {
	
	private static PromisService promisService = null;
	ObjectMapper mapper = new ObjectMapper();
	
	//Making the service a singleton.
	private PromisService(){}
	
	/**
	 * This is the getter for the singleton class.
	 * @return
	 */
	public static PromisService getPromisService()
	{
		if(promisService == null)
		{
			promisService = new PromisService();
		}
		
		return promisService;
	}
	
	public static String clientID = "";
	
	public String clientRegistration(String requestJSON)
	{
		String clientRegistrationJSON = "";
		try 
		{
			JSONObject json = new JSONObject();
			JSONParser parser = new JSONParser();
	    		try
	    		{
	    			json = (JSONObject) parser.parse(requestJSON);
	    		}
	    		catch(Exception e)
	        	{
	        		e.printStackTrace();
	        		String JsonErrorMessage = mapper.writeValueAsString(new ErrorMessage("The JSON is invalid"));
	    			//log.error("Error: The JSON is invalid" );
	    			throw new NotFoundException(Response.Status.INTERNAL_SERVER_ERROR,JsonErrorMessage);
	        	}
			ModelFactory mf = new ModelFactory();
			
			if(json.get("appType") == null)
			{
				String JsonErrorMessage = mapper.writeValueAsString(new ErrorMessage("Bad Request : paramater appType missing"));
    				throw new BadRequestCustomException(JsonErrorMessage);
			}
			
			if(json.get("appVersion") == null)
			{
				String JsonErrorMessage = mapper.writeValueAsString(new ErrorMessage("Bad Request : paramater appVersion missing"));
    				throw new BadRequestCustomException(JsonErrorMessage);
			}
			
			String appType = (String) json.get("appType");
			String appVersion = (String) json.get("appVersion");
			String patientPIN = (String) json.get("appVersion");
			
			ClientRegistration cr = mf.clientRegister(appType, appVersion);
			JSONObject jsonResponse = new JSONObject();
			clientID = cr.getClientID();
			jsonResponse.put("clientID", cr.getClientID());
			jsonResponse.put("clientSecret", cr.getClientSecret());
			jsonResponse.put("issuedAt", cr.getIssuedAt());
			jsonResponse.put("expiresAt", cr.getExpiresAt());
			
			clientRegistrationJSON = jsonResponse.toJSONString();
		}
		catch(JsonProcessingException e)
		{
			// TODO Auto-generated catch block
						//Need to throw 500 error.
						e.printStackTrace();
		}
		catch (ModelException e) 
		{
			// TODO Auto-generated catch block
			//Need to throw 500 error.
			e.printStackTrace();
		}
		
		return clientRegistrationJSON;
	}
	
	public String generateToken(String requestJSON)
	{
		String tokenRegistrationJSON = "";
		try 
		{
			JSONObject json = new JSONObject();
			JSONParser parser = new JSONParser();
	    		try
	    		{
	    			json = (JSONObject) parser.parse(requestJSON);
	    		}
	    		catch(Exception e)
	        	{
	        		e.printStackTrace();
	        		String JsonErrorMessage = mapper.writeValueAsString(new ErrorMessage("The JSON is invalid"));
	    			//log.error("Error: The JSON is invalid" );
	    			throw new NotFoundException(Response.Status.INTERNAL_SERVER_ERROR,JsonErrorMessage);
	        	}
			ModelFactory mf = new ModelFactory();
			
			if(json.get("client_id") == null)
			{
				String JsonErrorMessage = mapper.writeValueAsString(new ErrorMessage("Bad Request : paramater client_id missing"));
    				throw new BadRequestCustomException(JsonErrorMessage);
			}
			
			if(json.get("client_secret") == null)
			{
				String JsonErrorMessage = mapper.writeValueAsString(new ErrorMessage("Bad Request : paramater cleint_secret missing"));
    				throw new BadRequestCustomException(JsonErrorMessage);
			}
			
			if(json.get("patientPIN") == null)
			{
				String JsonErrorMessage = mapper.writeValueAsString(new ErrorMessage("Bad Request : paramater patientPIN missing"));
    				throw new BadRequestCustomException(JsonErrorMessage);
			}
			
			String clientID = (String) json.get("client_id");
			String clientSecret = (String) json.get("client_secret");
			
			System.out.println("client_id->"+clientID+"::client_secret->"+clientSecret);
			
			String patientPIN = (String) json.get("patientPIN");
			System.out.println("patientPIN->"+patientPIN);
			
			AccessToken ac = mf.registerAccessToken(clientID, clientSecret,patientPIN);
			JSONObject jsonResponse = new JSONObject();
			
			jsonResponse.put("access_token", ac.getAccess_token());
			jsonResponse.put("expiresIn", ac.getExpiresIn());
			
			tokenRegistrationJSON = jsonResponse.toJSONString();
		}
		catch (ModelException e) 
		{
			// TODO Auto-generated catch block
			//Need to throw 500 error.
			e.printStackTrace();
			String JsonErrorMessage = "{\"error\":\""+e.getMessage()+"\"}";
			System.out.println(JsonErrorMessage);
			throw new NotFoundException(Response.Status.INTERNAL_SERVER_ERROR,JsonErrorMessage);
		}
		catch(JsonProcessingException e)
		{
			// TODO Auto-generated catch block
						//Need to throw 500 error.
						e.printStackTrace();
						String JsonErrorMessage = "{'error':'Unexpected Error While Processing request'}";
						throw new NotFoundException(Response.Status.INTERNAL_SERVER_ERROR,JsonErrorMessage);
		}
		
		return tokenRegistrationJSON;
	}
	
	public boolean validateToken(String accessToken)
	{
		ModelFactory mf = new ModelFactory();
		AccessToken ac = null ;
		try 
		{

			ac = mf.getAccessToken(accessToken);

			if(ac == null)
			{
				return false;
			}

			String getAccessTokenTime = ac.getCreatedAt();
			long accessTokenExpiration = Long.parseLong(ac.getExpiresIn());
			
			String newAccessTokenTime = getAccessTokenTime.replaceAll("GMT","");
			
			System.out.println("newAccessToken without GMT::"+newAccessTokenTime);
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YY HH:mm:ss z");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			String currentTimeinGMT = sdf.format(new Date());
			
			String newCurrentTime = currentTimeinGMT.replaceAll("GMT","");
			
			System.out.println("newCurrentTime without GMT::"+newCurrentTime);
			
			SimpleDateFormat newsdf = new SimpleDateFormat("MM-dd-YY HH:mm:ss");
			
			Date d1 = newsdf.parse(newAccessTokenTime);
			
			Date d2 = newsdf.parse(newCurrentTime);
			
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000;
			
			System.out.println("Time diff is"+diffSeconds);
			

			if(diffSeconds > accessTokenExpiration)
			{

				String JsonErrorMessage = mapper.writeValueAsString(new ErrorMessage("Expired"));
				throw new NotFoundException(Response.Status.UNAUTHORIZED,JsonErrorMessage);
			}

		} 
		catch (ModelException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(JsonProcessingException e)
		{
			e.printStackTrace();
			String JsonErrorMessage = "{'error':'Unexpected Error While Processing request'}";
			throw new NotFoundException(Response.Status.INTERNAL_SERVER_ERROR,JsonErrorMessage);
		}
		
		return true;
	}
		
}
