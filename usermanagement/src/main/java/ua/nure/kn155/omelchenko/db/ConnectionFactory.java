package ua.nure.kn155.omelchenko.db;

import java.sql.Connection;

public interface ConnectionFactory {
	Connection createConnection() throws DatabaseExeption;
}
