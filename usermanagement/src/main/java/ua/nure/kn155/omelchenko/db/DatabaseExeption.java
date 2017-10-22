package ua.nure.kn155.omelchenko.db;

public class DatabaseExeption extends Exception {
	public DatabaseExeption(Throwable e) {
		super(e);
	}

	public DatabaseExeption(String string) {
		super(string);
	}

	public DatabaseExeption() {
		super();
	}
}
