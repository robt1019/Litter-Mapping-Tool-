package org.littermappingtool.backend.servlets.datamng;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.littermappingtool.backend.Litter;
import org.littermappingtool.backend.dao.PreparedQueries;
import org.littermappingtool.backend.utils.DateTimeUtils;
import org.littermappingtool.backend.utils.ErrorHandler;
import org.littermappingtool.backend.utils.PageUtils;

/**
 * Servlet to persist a litter entity (for data management).
 */
@SuppressWarnings("serial")
public class LittersPersister extends HttpServlet {

	/** The error handler. */
	private static ErrorHandler errorHandler = new ErrorHandler(SystemEntityPersister.class.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession httpSession = req.getSession(false);

		if (httpSession == null) {

			// The session is null, redirect back to main page
			resp.sendRedirect("index.jsp");

		} else {

			try {

				String userEmail = req.getParameter("user-email");
				Double latitude = Double.parseDouble(req.getParameter("latitude"));
				Double longitude = Double.parseDouble(req.getParameter("longitude"));
				String brand = req.getParameter("brands-list");
				String litterType = req.getParameter("litter-types-list");
				String eventId = req.getParameter("events-list");
				Date date = DateTimeUtils.formatDate(req.getParameter("litter-date-time"), DateTimeUtils.DATE_FORMAT);
				Litter litter = null;

				if (StringUtils.isNotEmpty(eventId)) {
					litter = new Litter.Builder(litterType, brand, userEmail, latitude, longitude, 
							date.getTime(), Long.parseLong(eventId)).build();					
				} else {
					litter = new Litter.Builder(litterType, brand, userEmail, latitude, longitude, 
							date.getTime(), null).build();										
				}
				
				PreparedQueries.saveLitter(litter);
				
				httpSession.setAttribute("message-type", "success");
				httpSession.setAttribute("message", "Litter uploaded successfully");

				resp.sendRedirect("index.jsp?page=upload-litter");

			} catch (Exception e) {

				String errorMessage = "An error occured while uploading an entity";
				errorHandler.logFullErrorEvent(errorMessage, e);
				resp.sendRedirect(PageUtils.getRedirectAddressForErrorPage(errorMessage));
			}
		}
	}
}