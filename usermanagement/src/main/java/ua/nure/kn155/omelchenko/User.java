package ua.nure.kn155.omelchenko;

import java.util.Calendar;
import java.util.Date;

public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirthd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirthd() {
		return dateOfBirthd;
	}

	public void setDateOfBirthd(Date dateOfBirthd) {
		this.dateOfBirthd = dateOfBirthd;
	}

	public Object getFullName() throws IllegalStateException {
		if (getFirstName() == null || getLastName() == null)
			throw new IllegalStateException();
		return new StringBuilder().append(getLastName()).append(", ").append(getFirstName()).toString();
	}

	public int getAge() throws IllegalStateException {
		if (dateOfBirthd == null) {
			throw new IllegalStateException();
		}
		Calendar instance = Calendar.getInstance();
		instance.setTime(new Date());
		int currentYear = instance.get(Calendar.YEAR);
		instance.setTime(getDateOfBirthd());
		int year = instance.get(Calendar.YEAR);
		if (year > currentYear) {
			throw new IllegalStateException();
		}
		return currentYear - year;
	}

}
