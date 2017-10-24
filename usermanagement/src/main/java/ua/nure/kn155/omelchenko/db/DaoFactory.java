package ua.nure.kn155.omelchenko.db;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {
	private final Properties PROPERTIES;
	private final static DaoFactory INSTANCE = new DaoFactory();
	private final String USER_DAO = "ua.nure.kn155.omelchenko.db.UserDao";
	public DaoFactory() {
		super();
		PROPERTIES = new Properties();
		try {
			PROPERTIES.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static DaoFactory getInstance() {
		return INSTANCE;
	}

	private ConnectionFactory getConnectionFactory() {
		String driver = PROPERTIES.getProperty("connection.driver");
		String url = PROPERTIES.getProperty("connection.url");
		String user = PROPERTIES.getProperty("connection.user");
		String password = PROPERTIES.getProperty("connection.password");
		return new ConnectionFactoryImpl(driver, url, user, password);
	}

	public UserDao getUserDao(){
		UserDao result = null;
		try {
			Class clazz = Class.forName(PROPERTIES.getProperty(USER_DAO));
			result = (UserDao) clazz.newInstance();
			result.setConnectionFactory(getConnectionFactory());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

}
