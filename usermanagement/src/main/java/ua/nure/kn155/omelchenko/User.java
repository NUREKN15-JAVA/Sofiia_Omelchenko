package ua.nure.kn155.omelchenko;

import java.util.Calendar;
import java.util.Date;

public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;

	public User(User user) {
		id = user.getId();
		firstName = user.getFirstName();
		lastName = user.getLastName();
		dateOfBirth = user.getDateOfBirthd();
	}

	public User() {
	}

	public User(Long id, String firstName, String lastName, Date dateOfBirth) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public User(String firstName, String lastName, Date dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

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
		return dateOfBirth;
	}

	public void setDateOfBirthd(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Object getFullName() throws IllegalStateException {
		if (getFirstName() == null || getLastName() == null)
			throw new IllegalStateException();
		return new StringBuilder().append(getLastName()).append(", ").append(getFirstName()).toString();
	}

	public int getAge() throws IllegalStateException {
		if (dateOfBirth == null) {
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

	@Override
	public int hashCode() {
		if (this.getId() == null)
			return 0;
		return this.getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (this.getId() == null && ((User) obj).getId() == null)
			return true;
		return this.getId().equals(((User) obj).getId());
	}

}
