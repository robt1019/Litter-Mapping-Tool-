package org.littermappingtool.backend.servlets.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.littermappingtool.backend.Event;
import org.littermappingtool.backend.dao.PreparedQueries;
import org.littermappingtool.backend.utils.ErrorHandler;
import org.littermappingtool.backend.utils.PageUtils;

/**
 * All events deleter.
 */
@SuppressWarnings("serial")
public class AllEventsDeleter extends HttpServlet {

	/** The error handler. */
	private static ErrorHandler errorHandler = new ErrorHandler(AllEventsDeleter.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession httpSession = req.getSession(false);

		if (httpSession == null) {

			// The session is null, redirect back to main page
			resp.sendRedirect("index.jsp");

		} else {

			try {

				List<Event> events = PreparedQueries.getAllEvents();
				for (Event event : events) {
					PreparedQueries.deleteEvent(event.getId());
				}
				
				httpSession.setAttribute("message-type", "success");
				httpSession.setAttribute("message", "All events deleted successfully");
				
				resp.sendRedirect("index.jsp?page=manage");

			} catch (Exception e) {

				String errorMessage = "An error occured while deleting all events";
				errorHandler.logFullErrorEvent(errorMessage, e);
				resp.sendRedirect(PageUtils.getRedirectAddressForErrorPage(errorMessage));
			}
		}
	}
}