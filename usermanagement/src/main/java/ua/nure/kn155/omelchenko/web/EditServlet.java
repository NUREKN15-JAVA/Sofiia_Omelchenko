package ua.nure.kn155.omelchenko.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import ua.nure.kn155.omelchenko.User;
import ua.nure.kn155.omelchenko.db.DaoFactory;
import ua.nure.kn155.omelchenko.db.DatabaseException;

/**
 * Servlet for editing a user's entry from the database
 * 
 */
public class EditServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		super.service(req, resp);
		if (req.getParameter("okButton") != null) {
			doOk(req, resp);
		} else if (req.getParameter("cancelButton") != null) {
			doCancel(req, resp);
		} else {
			showPage(req, resp);
		}
	}

	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/edit.jsp").forward(req, resp);
	}

	private void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/browse").forward(req, resp);
	}

	private void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = null;
		try {
			user = getUser(req);
		} catch (Exception e1) {
			req.setAttribute("error", e1.getMessage());
			showPage(req, resp);
			return;
		}
		processUser(user);
		req.getRequestDispatcher("/browse").forward(req, resp);

	}

	private User getUser(HttpServletRequest req) {
		User user = new User();
		String idStr = req.getParameter("id");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String dateStr = req.getParameter("dateOfBirth");

		try {
			if (firstName == null)
				throw new ValidationException("Empty first name");
		} catch (ValidationException e1) {
			e1.printStackTrace();
		}
		try {
			if (lastName == null)
				throw new ValidationException("Empty last name");
		} catch (ValidationException e1) {
			e1.printStackTrace();
		}
		try {
			if (dateStr == null)
				throw new ValidationException("Empty date");
		} catch (ValidationException e1) {
			e1.printStackTrace();
		}
		if (idStr != null) {
			user.setId(new Long(idStr));
		}

		user.setFirstName(firstName);
		user.setLastName(lastName);
		try {
			user.setDateOfBirthd(DateFormat.getDateInstance().parse(dateStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return user;
	}

	protected void processUser(User user) {
		try {
			DaoFactory.getInstance().getUserDao().update(user);
		} catch (DatabaseException e) {
			e.printStackTrace();
			return;
		}

	}

}
