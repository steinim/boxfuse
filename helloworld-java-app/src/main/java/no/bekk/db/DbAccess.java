package no.bekk.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbAccess {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private static final String DATABASE_USER = System.getenv("BOXFUSE_DATABASE_USER");
	private static final String DATABASE_URL = System.getenv("BOXFUSE_DATABASE_URL");
	private static final String DATABASE_PASSWORD = System.getenv("BOXFUSE_DATABASE_PASSWORD");

	private static final Logger LOG = LoggerFactory.getLogger(DbAccess.class);
	

	public DbAccess() {
		String dbUrl =  DATABASE_URL + "&user="+ DATABASE_USER + "&password=" + DATABASE_PASSWORD;
		try {
			connect = DriverManager.getConnection(dbUrl);
			LOG.info("Connected to " + dbUrl);
		} catch (SQLException e) {
			LOG.error("Connection to " + dbUrl + " failed: ", e);
		}
	}

	public String sayHello() throws Exception {
		String query = "select * from helloworld_java_app.messages";
		try {
			statement = connect.createStatement();
			LOG.info("Running query: " + query);
			resultSet = statement.executeQuery(query);
			String message = null;
			while (resultSet.next()) {
				message = resultSet.getString("message");
				LOG.info("Got '" + message);
			}
			return message + " :)";
		} catch (Exception e) {
			LOG.error("Something went wrong while executing query: " + query, e);
			return "nothing :(";
		} finally {
			close();
		}

	}

	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}
