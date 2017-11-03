package ua.nure.kn155.omelchenko.db;

public class DaoFactoryImpl extends DaoFactory {

	@Override
	public UserDao getUserDao() {
		UserDao result = null;
		try {
			Class<?> clazz = Class.forName(properties.getProperty("ua.nure.kn155.omelchenko.db.UserDao"));
			result = (UserDao) clazz.newInstance();
			result.setConnectionFactory(getConnectionFactory());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	
}
