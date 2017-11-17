package ua.nure.kn155.omelchenko.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn155.omelchenko.User;
import ua.nure.kn155.omelchenko.db.DaoFactory;
import ua.nure.kn155.omelchenko.db.DatabaseException;

public class BrowseServlet extends HttpServlet {

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		browse(req, resp);
	}

	private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		Collection<User> users;
		try {
			users = DaoFactory.getInstance().getUserDao().findAll();
			 req.getSession().setAttribute("users", users);
			 req.getRequestDispatcher("/browse.jsp").forward(req, resp);
		} catch (DatabaseException | IOException e) {	
			throw new ServletException(e);
		}

	}

}
