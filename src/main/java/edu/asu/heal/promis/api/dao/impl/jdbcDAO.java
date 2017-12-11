package edu.asu.heal.promis.api.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import edu.asu.heal.promis.api.dao.DAO;
import edu.asu.heal.promis.api.dao.DAOException;
import edu.asu.heal.promis.api.dao.DAOFactory;
import edu.asu.heal.promis.api.dao.ValueObject;
import edu.asu.heal.promis.api.helper.APIConstants;


public abstract class jdbcDAO implements DAO{
	
	//static Logger log = LogManager.getLogger(JdbcDAO.class);
	private String __jdbcDriver;
	protected String _jdbcUser;
	protected String _jdbcPasswd;
	protected String _jdbcUrl;

	public jdbcDAO(Properties props) throws DAOException {

		// For MySQL we expect the JDBC Driver, user, password, and the URI. Maybe more in the future.
        _jdbcUrl    = props.getProperty("jdbc.url");
        _jdbcUser   = props.getProperty("jdbc.user");
        _jdbcPasswd = props.getProperty("jdbc.passwd");
        __jdbcDriver = props.getProperty("jdbc.driver");

        try {
        		Class.forName(__jdbcDriver); // ensure the driver is loaded
        }
        catch (ClassNotFoundException cnfe) {
        		throw new DAOException("*** Cannot find the JDBC driver " + __jdbcDriver, cnfe);
        }
        catch (Throwable t) {
        		throw new DAOException(t);
        }
	}

	/**
	 * We really should implement some simple wrapper and pooling YYY
	 * @return database Connection
	 * @throws DAOException
	 */
	protected Connection getConnection() throws DAOException 
	{
		try 
		{
			return DriverManager.getConnection(_jdbcUrl, _jdbcUser, _jdbcPasswd);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new DAOException("Unable to get connection to database", e);
		}
	}
	
	public ValueObject registerClient(String appType , String appVersion) throws DAOException , SQLException
	{
		ValueObject vo = new ValueObject(); // need to fill this up
		Connection connection = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String clientID = UUID.randomUUID().toString();
			String clientSecret = UUID.randomUUID().toString();
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YY HH:mm:ss z");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			String currentTimeinGMT = sdf.format(new Date());
			
			String query = DAOFactory.getDAOProperties().getProperty("sql.registerClient");
			ps = connection.prepareStatement(query);
			ps.setString(1,appType);
			ps.setString(2,appVersion);
			ps.setString(3,clientID);
			String hashedPassword = passwordHash(clientSecret);
			ps.setString(4,hashedPassword);
			ps.setString(5,currentTimeinGMT);

			//Gonna execute the query.
			int rowCount = ps.executeUpdate();
			boolean result = (rowCount >=1)? true:false;
			
			if(!result)
			{
				vo.putAttribute("result",result);
				return vo;
			}
			
			vo.putAttribute("result", result);
			vo.putAttribute(APIConstants.clientID,clientID);
			vo.putAttribute(APIConstants.clientSecret,hashedPassword);
			vo.putAttribute(APIConstants.issuedAt,currentTimeinGMT);

		} 
		catch (Throwable t) 
		{
			t.printStackTrace();
			throw new DAOException("Unable to process results from query sql.registerClient");	
		} 
		finally 
		{
			try 
			{
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connection != null) connection.close();
			} 
			catch (SQLException se) 
			{
				se.printStackTrace();
				// YYY need a logging facility, but this does not have to be rethrown
				//log.error(se);
			}
		}
		return vo;
	}
	
	public ValueObject tokenGeneration(String clientID , String clientSecret,String patientPIN) throws DAOException , SQLException
	{
		ValueObject vo = new ValueObject(); // need to fill this up
		Connection connection = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String query = DAOFactory.getDAOProperties().getProperty("sql.clientValidation");
			ps = connection.prepareStatement(query);
			ps.setString(1,clientID);
			ps.setString(2,clientSecret);
			rs = ps.executeQuery();
			
			if(!rs.next())
			{
				//Not a valid client.
				vo.putAttribute("result", false);
				vo.putAttribute("error", "client ID and Secret could not be validated");
				return vo;
			}
			
			String accessToken = UUID.randomUUID().toString();
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YY HH:mm:ss z");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			String currentTimeinGMT = sdf.format(new Date());
			
			query = DAOFactory.getDAOProperties().getProperty("sql.registerToken");
			ps = connection.prepareStatement(query);
			ps.setString(1,accessToken);
			ps.setString(2,patientPIN);
			ps.setString(3,currentTimeinGMT);
		
			//Gonna execute the query.
			int rowCount = ps.executeUpdate();
			boolean result = (rowCount >=1)? true:false;
			
			if(!result)
			{
				vo.putAttribute("result",result);
				vo.putAttribute("error","Unexpected error while inserting access token");
				return vo;
			}
			
			String tokenExpirationTime = DAOFactory.getDAOProperties().getProperty("TokenExpirationTime");
			
			vo.putAttribute("result", result);
			vo.putAttribute(APIConstants.accessToken,accessToken);
			vo.putAttribute(APIConstants.expiresIn,tokenExpirationTime);
			vo.putAttribute(APIConstants.issuedAt,currentTimeinGMT);
			
			return vo;
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			throw new DAOException("UnExpected error while processing query for access token creation");
		} 
		finally 
		{
			try 
			{
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connection != null) connection.close();
			} 
			catch (SQLException se) 
			{
				se.printStackTrace();
				// YYY need a logging facility, but this does not have to be rethrown
				//log.error(se);
			}
		}
	}
	
	public ValueObject getAccessToken(String accessToken) throws DAOException , SQLException
	{
		ValueObject vo = new ValueObject(); // need to fill this up
		Connection connection = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String query = DAOFactory.getDAOProperties().getProperty("sql.getAccessToken");
			ps = connection.prepareStatement(query);
			ps.setString(1,accessToken);
			rs = ps.executeQuery();
			
			if(!rs.next())
			{
				//Not a valid access token.
				vo.putAttribute("result", false);
				vo.putAttribute("error", "Invalid access token");
				return vo;
			}
			
			accessToken = rs.getString("access_token");
			String createdAt = rs.getString("createdAt");
			String tokenExpirationTime = DAOFactory.getDAOProperties().getProperty("TokenExpirationTime");
			
			vo.putAttribute(APIConstants.accessToken, accessToken);
			vo.putAttribute(APIConstants.expiresIn, tokenExpirationTime);
			vo.putAttribute(APIConstants.issuedAt, createdAt);
			
			return vo;
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			throw new DAOException("UnExpected error while processing query for get access token creation");
		} 
		finally 
		{
			try 
			{
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connection != null) connection.close();
			} 
			catch (SQLException se) 
			{
				se.printStackTrace();
				// YYY need a logging facility, but this does not have to be rethrown
				//log.error(se);
			}
		}
		
	}
	
	private String passwordHash(String plainPassowrd)
	{
		int workload = 12;
		String salt = BCrypt.gensalt(workload);
		String hashed_password = BCrypt.hashpw(plainPassowrd, salt);

		return(hashed_password);
	}
	
	/**
	 * 
	 * @param password_plaintext
	 * @param stored_hash
	 * @return a boolean which will compare the results.
	 */
	public static boolean checkPassword(String password_plaintext, String stored_hash) 
	{
		boolean password_verified = false;

		if(null == stored_hash || !stored_hash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

		return (password_verified);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
