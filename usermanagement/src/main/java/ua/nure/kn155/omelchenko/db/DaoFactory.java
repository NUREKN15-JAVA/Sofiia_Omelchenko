package ua.nure.kn155.omelchenko.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {
	protected static Properties properties;
	private static DaoFactory instance;
	private static final String USER_DAO = "ua.nure.kn155.omelchenko.db.UserDao";
	private static final String DAO_FACTORY = "dao.factory";

	protected DaoFactory() {
	}

	static {
		properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("settings.properties"));

			// properties.load(DaoFactory.class.getClass().getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static synchronized DaoFactory getInstance() {
		if (instance == null) {
			try {
				Class<?> factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
				instance = (DaoFactory) factoryClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}

	public static void init(Properties prop) {
		properties = prop;
		instance = null;
	}

	protected ConnectionFactory getConnectionFactory() {
		return new ConnectionFactoryImpl(properties);
	}

	public abstract UserDao getUserDao();

}
