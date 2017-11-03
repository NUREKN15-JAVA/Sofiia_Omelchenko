package ua.nure.kn155.omelchenko.db;

public class DatabaseException extends Exception {
	public DatabaseException(Throwable e) {
		super(e);
	}

	public DatabaseException(String string) {
		super(string);
	}

	public DatabaseException() {
		super();
	}
}
