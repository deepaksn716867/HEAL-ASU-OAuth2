package edu.asu.heal.promis.api.model;

import edu.asu.heal.promis.api.dao.DAOFactory;
import edu.asu.heal.promis.api.dao.ValueObject;
import edu.asu.heal.promis.api.helper.APIConstants;

/**
 * This class is the model factory , which is wrapper for the DAO layer.
 * This class converts all the instantiated DAO objects back to respective models.
 * @author deepak
 *
 */
public class ModelFactory {
	
	public ModelFactory()
	{
		
	}
	
	public ClientRegistration clientRegister(String appType , String appVersion) throws ModelException
	{
		ClientRegistration cr = null;
		try
		{
			ValueObject vo = DAOFactory.getTheDAO().registerClient(appType,appVersion);
			
			if(!(Boolean)(vo.getAttribute("result")))
			{
				throw new ModelException("Cannot create Model Object ClientRegistration");
				//Need to log here.
			}
			
			String clientID = (String) (vo.getAttribute(APIConstants.clientID) == null ? "" : (String) vo.getAttribute(APIConstants.clientID));
			String clientSecret = (String) (vo.getAttribute(APIConstants.clientSecret) == null ? "" : (String) vo.getAttribute(APIConstants.clientSecret));
			String expiresAt = (String) (vo.getAttribute(APIConstants.expiresAt) == null ? "" : vo.getAttribute(APIConstants.expiresAt));
			String issuedAt = (String) (vo.getAttribute(APIConstants.issuedAt) == null ? "" : vo.getAttribute(APIConstants.issuedAt));
			
			cr = new ClientRegistration();
			cr.setClientID(clientID);
			cr.setAppType(appType);
			cr.setAppVersion(appVersion);
			cr.setClientSecret(clientSecret);
			cr.setExpiresAt(expiresAt);
			cr.setIssuedAt(issuedAt);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ModelException("Unexpected error while creating model object ClientRegistration");
			//Need to log the errors 
		}
		
		return cr;
	}
	
	public AccessToken registerAccessToken(String clientID , String clientSecret, String patientPIN) throws ModelException
	{
		AccessToken ac = null;
		try
		{
			ValueObject vo = DAOFactory.getTheDAO().tokenGeneration(clientID, clientSecret,patientPIN);
			
			if(vo.getAttribute("result") != null && !(Boolean)(vo.getAttribute("result")))
			{
				if(vo.getAttribute("error") != null)
				{
					throw new ModelException((String)vo.getAttribute("error"));
				}
				return ac;
			}
			
			String accessToken = (String) (vo.getAttribute(APIConstants.accessToken) == null ? "" : (String) vo.getAttribute(APIConstants.accessToken));
			String expirationTime = (String) (vo.getAttribute(APIConstants.expiresIn) == null ? "" : (String) vo.getAttribute(APIConstants.expiresIn));
			String createdAt = (String) (vo.getAttribute(APIConstants.issuedAt) == null ? "" : (String) vo.getAttribute(APIConstants.issuedAt));
			
			ac = new AccessToken();
			ac.setAccess_token(accessToken);
			ac.setCreatedAt(createdAt);
			ac.setExpiresIn(expirationTime);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(e.getMessage() == null || e.getMessage().isEmpty())
				throw new ModelException("Unexpected error while creating model object Access Token");
			else
				throw new ModelException(e.getMessage());
			//Need to log the errors 
		}
		
		return ac;
	}
	
	public AccessToken getAccessToken(String accessToken) throws ModelException
	{
		AccessToken ac = null;
		try
		{
			ValueObject vo = DAOFactory.getTheDAO().getAccessToken(accessToken);
			
			if(vo.getAttribute("result") != null && !(Boolean)(vo.getAttribute("result")))
			{
				return ac;
			}
			
			String expirationTime = (String) (vo.getAttribute(APIConstants.expiresIn) == null ? "" : (String) vo.getAttribute(APIConstants.expiresIn));
			String createdAt = (String) (vo.getAttribute(APIConstants.issuedAt) == null ? "" : (String) vo.getAttribute(APIConstants.issuedAt));
			
			ac = new AccessToken();
			ac.setAccess_token(accessToken);
			ac.setCreatedAt(createdAt);
			ac.setExpiresIn(expirationTime);
			
			return ac;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ModelException("Cannot create Model Access Token");
		}
		
	}

}
