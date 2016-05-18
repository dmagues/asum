package com.asum.project.sgtfitness.engine.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.mariadb.jdbc.MariaDbDataSource;

public class DataSourceFactory {

	
	private static final String CONNECTIONURL = "jdbc:mysql://localhost:3306";
	private static MariaDbDataSource mysqlDS=null;
	
	 public static DataSource getMySQLDataSource() {
	        
	        
			try {
	            
				if (mysqlDS== null)
				{
					mysqlDS = new MariaDbDataSource(CONNECTIONURL);
		            mysqlDS.setDatabaseName("asum_project");
		            mysqlDS.setUser("root");
		            mysqlDS.setPassword("");	
				}
				return mysqlDS;
				
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return mysqlDS;
	    }
	 
	 	private static Connection conn;
		
		public static Connection getConnection() throws SQLException
		{
			if (conn==null || conn.isClosed())
			{
				DataSource ds = DataSourceFactory.getMySQLDataSource();
				try {
					conn = ds.getConnection();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				 
			return conn;
		}
}
