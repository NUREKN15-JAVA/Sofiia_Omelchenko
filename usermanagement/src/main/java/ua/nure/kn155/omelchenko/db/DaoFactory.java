package ua.nure.kn155.omelchenko.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {
	protected static Properties properties = new Properties();
	private static DaoFactory instance;
	private final String USER_DAO = "ua.nure.kn155.omelchenko.db.UserDao";
	protected static final String DAO_FACTORY = "dao.factory";

	public DaoFactory() {
		super();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static synchronized DaoFactory getInstance() {
		if (instance == null) {
			Class<?> factoryClass = null;
			try {	
//				String className= properties.getProperty(DAO_FACTORY);
//				if(className == null)
//					throw new RuntimeException();
				factoryClass = Class.forName("ua.nure.kn155.omelchenko.db.DaoFactoryImpl");
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
			try {
				instance = (DaoFactory) factoryClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
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
