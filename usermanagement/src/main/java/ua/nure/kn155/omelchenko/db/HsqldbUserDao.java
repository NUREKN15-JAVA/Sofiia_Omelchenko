package ua.nure.kn155.omelchenko.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.kn155.omelchenko.User;

class HsqldbUserDao implements UserDao {

	private static final String INSERT_QUERY = "INSERT INTO users (firstname,lastname,dateofbirth) VALUES (?,?,?)";
	private static final String DELETE_QUERY = "DELETE FROM users WHERE id=?";
	private static final String UPDATE_QUERY = "UPDATE users SET firstname=?,lastname=?,dateofbirth=? WHERE id=?";

	private ConnectionFactory connectionFactory;

	public HsqldbUserDao() {
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public HsqldbUserDao(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
		// TODO Auto-generated constructor stub
	}

	@Override
	public User create(User user) throws DatabaseExeption {
		try {
			Connection connection = connectionFactory.createConnection();
			User resultUser = new User();
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
			int n = statement.executeUpdate();
			if (n != 1) {
				throw new DatabaseExeption("Wrong number of inserted rows: " + n);
			}
			CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
			ResultSet keys = callableStatement.executeQuery();
			if (keys.next()) {
				resultUser = new User(user);
				resultUser.setId(new Long(keys.getLong(1)));
			}
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();
			return resultUser;
		} catch (DatabaseExeption e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseExeption(e);
		}

	}

	@Override
	public void update(User user) throws DatabaseExeption {
		try {
		Connection connection = connectionFactory.createConnection();
		PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
		statement.setString(1, user.getFirstName());
		statement.setString(2, user.getLastName());
		statement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
		statement.setLong(4, user.getId());
		statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(User user) throws DatabaseExeption {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
			statement.setLong(1, user.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User find(Long id) throws DatabaseExeption {
		try {
			Connection connection = connectionFactory.createConnection();
			String queryStr = "SELECT id, firstname, lastname, dateofbirth FROM users";
			ResultSet resultSet = connection.createStatement().executeQuery(queryStr);
			while (resultSet.next()) {
				if (resultSet.getLong(1) == id) {
					User user = new User();
					user.setId(resultSet.getLong(1));
					user.setFirstName(resultSet.getString(2));
					user.setLastName(resultSet.getString(3));
					user.setDateOfBirthd(resultSet.getDate(4));
					return user;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<User> findAll() throws DatabaseExeption {
		Collection<User> result = new LinkedList<User>();
		try {
			Connection connection = connectionFactory.createConnection();
			String queryStr = "SELECT id, firstname, lastname, dateofbirth FROM users";
			ResultSet resultSet = connection.createStatement().executeQuery(queryStr);
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getLong(1));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirthd(resultSet.getDate(4));
				result.add(user);
			}
		} catch (DatabaseExeption e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseExeption(e);
		}

		return result;
	}

}
