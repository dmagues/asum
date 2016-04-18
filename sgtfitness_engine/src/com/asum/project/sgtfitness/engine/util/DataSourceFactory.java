package com.asum.project.sgtfitness.engine.util;

import javax.sql.DataSource;

import org.mariadb.jdbc.MariaDbDataSource;

public class DataSourceFactory {

	public static final String CONNECTIONURL = "jdbc:mysql://aa252tbwaejqy2.cybnfr6pktxx.us-west-2.rds.amazonaws.com";
	
	 public static DataSource getMySQLDataSource() {
	        
	        MariaDbDataSource mysqlDS=null;
			try {
	            
	            mysqlDS = new MariaDbDataSource(CONNECTIONURL);
	            mysqlDS.setDatabaseName("asum_project");
	            mysqlDS.setUser("asum");
	            mysqlDS.setPassword("AsumProject2016");
	            
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return mysqlDS;
	    }
}
