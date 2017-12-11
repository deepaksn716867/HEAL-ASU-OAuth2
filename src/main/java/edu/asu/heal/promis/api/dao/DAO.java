package edu.asu.heal.promis.api.dao;

import java.sql.SQLException;

public interface DAO {
	
	public ValueObject registerClient(String appType , String appVersion) throws DAOException , SQLException;
	
	public ValueObject tokenGeneration(String clientID , String clientSecret,String patientPIN) throws DAOException , SQLException;
	
	public ValueObject getAccessToken(String accessToken) throws DAOException , SQLException;

}
