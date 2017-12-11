package edu.asu.heal.promis.api.dao.impl;

import java.util.Properties;

import edu.asu.heal.promis.api.dao.DAOException;

public class MySQLDAO extends jdbcDAO{
	
	public MySQLDAO(Properties prop) throws DAOException
	{
		super(prop);
	}

}
