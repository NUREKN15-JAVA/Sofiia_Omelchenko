package ua.nure.kn155.omelchenko.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn155.omelchenko.User;

public class DetailsServlet extends EditServlet {
	@Override
	protected void showPage(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.getRequestDispatcher("/details.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void processUser(User user){
	}
}
