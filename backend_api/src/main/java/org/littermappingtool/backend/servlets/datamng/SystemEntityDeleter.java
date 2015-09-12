package org.littermappingtool.backend.servlets.datamng;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.littermappingtool.backend.dao.PreparedQueries;
import org.littermappingtool.backend.utils.ErrorHandler;
import org.littermappingtool.backend.utils.PageUtils;

/**
 * Servlet to delete system entities/data.
 */
@SuppressWarnings("serial")
public class SystemEntityDeleter extends HttpServlet {

	/** The error handler. */
	private static ErrorHandler errorHandler = new ErrorHandler(SystemEntityDeleter.class.getName());

	/** Handler for a particular HTTP post request. */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession httpSession = req.getSession(false);

		if (httpSession == null) {

			// The session is null, redirect back to main page
			resp.sendRedirect("index.jsp");

		} else {

			try {

				long itemId = Long.parseLong(req.getParameter("item-id"));
				String itemType = req.getParameter("item-type");

				if (itemType.equals("bin-type")) {

					PreparedQueries.deleteBinType(itemId);
					httpSession.setAttribute("message-type", "success");
					httpSession.setAttribute("message", "Bin type deleted successfully");

				} else if (itemType.equals("brand")) {

					PreparedQueries.deleteBrand(itemId);
					httpSession.setAttribute("message-type", "success");
					httpSession.setAttribute("message", "Brand deleted successfully");

				} else if (itemType.equals("litter-type")) {

					PreparedQueries.deleteLitterType(itemId);
					httpSession.setAttribute("message-type", "success");
					httpSession.setAttribute("message", "Litter type deleted successfully");

				} else {

					httpSession.setAttribute("message-type", "warning");
					httpSession.setAttribute("message", "Error: unknown entity type");

				}

				resp.sendRedirect("index.jsp?page=manage");

			} catch (Exception e) {

				String errorMessage = "An error occured while deleting an entity";
				errorHandler.logFullErrorEvent(errorMessage, e);
				resp.sendRedirect(PageUtils.getRedirectAddressForErrorPage(errorMessage));
			}
		}
	}
}
