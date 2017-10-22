package ua.nure.kn155.omelchenko.db;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {
	private final Properties PROPERTIES;
	private final static DaoFactory INSTANCE = new DaoFactory();

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

	public UserDao getUserDao() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class clazz = Class.forName(PROPERTIES.getProperty("ua.nure.kn155.omelchenko.db.UserDao"));
		UserDao result = (UserDao) clazz.newInstance();
		result.setConnectionFactory(getConnectionFactory());
		return result;
	}

}
