package org.littermappingtool.backend.servlets.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.littermappingtool.backend.Event;
import org.littermappingtool.backend.dao.PreparedQueries;
import org.littermappingtool.backend.utils.DateTimeUtils;
import org.littermappingtool.backend.utils.ErrorHandler;
import org.littermappingtool.backend.utils.PageUtils;

/**
 * Servlet to persist events.
 */
@SuppressWarnings("serial")
public class EventPersister extends HttpServlet {

	/** The error handler. */
	private static ErrorHandler errorHandler = new ErrorHandler(EventPersister.class.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession httpSession = req.getSession(false);

		if (httpSession == null) {

			// The session is null, redirect back to main page
			resp.sendRedirect("index.jsp");

		} else {

			try {

				String eventTitle = req.getParameter("event-title");
				String eventDescription = req.getParameter("event-description");
				String eventDate = req.getParameter("event-date");
				String eventTime = req.getParameter("event-time");
				String eventLocation = req.getParameter("event-location");
				String eventColour = req.getParameter("event-color");
				
				String city = eventLocation .substring(0, eventLocation.indexOf(","));
				String country = eventLocation.substring(eventLocation.indexOf(",") + 2, eventLocation.indexOf("(") - 1);
				double lat = Double.parseDouble(eventLocation.substring(eventLocation.indexOf("(") + 1, eventLocation.indexOf(",", eventLocation.indexOf("("))));
				double lon = Double.parseDouble(eventLocation.substring(eventLocation.indexOf(",", eventLocation.indexOf("(")) + 2, eventLocation.length() - 1));
				long time = DateTimeUtils.formatDate(eventTime, DateTimeUtils.HOUR_FORMAT).getTime();
				long date = DateTimeUtils.formatDate(eventDate, DateTimeUtils.DATE_FORMAT).getTime();
				
				Event event = new Event.Builder(date + time, lat, lon, eventTitle).setDescription(eventDescription)
						.setCity(city).setCountry(country).setColour(eventColour).build();
				
				PreparedQueries.saveEvent(event);
				
				httpSession.setAttribute("message-type", "success");
				httpSession.setAttribute("message", "New event added successfully");
				
				resp.sendRedirect("index.jsp?page=events");

			} catch (Exception e) {

				String errorMessage = "An error occured while adding event";
				errorHandler.logFullErrorEvent(errorMessage, e);
				resp.sendRedirect(PageUtils.getRedirectAddressForErrorPage(errorMessage));
			}
		}
	}
}
