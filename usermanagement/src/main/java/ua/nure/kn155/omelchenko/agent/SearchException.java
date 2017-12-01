package ua.nure.kn155.omelchenko.agent;

import ua.nure.kn155.omelchenko.db.DatabaseException;

public class SearchException extends Exception {

	public SearchException() {
		super();
	}
	
	public SearchException(DatabaseException e) {
		super(e);
	}

	public SearchException(Throwable cause) {
		super(cause);
	}

	public SearchException(String message) {
		super(message);
	}

	public SearchException(String message, Throwable cause) {
		super(message, cause);
	}
}
