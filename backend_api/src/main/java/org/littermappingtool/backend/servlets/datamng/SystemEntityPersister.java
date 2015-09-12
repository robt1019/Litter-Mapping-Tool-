package org.littermappingtool.backend.servlets.datamng;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.littermappingtool.backend.BinType;
import org.littermappingtool.backend.Brand;
import org.littermappingtool.backend.LitterType;
import org.littermappingtool.backend.dao.PreparedQueries;
import org.littermappingtool.backend.utils.ErrorHandler;
import org.littermappingtool.backend.utils.PageUtils;

/**
 * Servlet to persist system entities/data.
 */
@SuppressWarnings("serial")
public class SystemEntityPersister extends HttpServlet {

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

				String itemType = req.getParameter("item-type");
				String itemName = req.getParameter("item-name");				
				String[] searchableTerms = req.getParameterValues("items-searchable-terms");
				String[] categories = req.getParameterValues("items-categories");

				if (StringUtils.isEmpty(itemName)) {

					httpSession.setAttribute("message-type", "warning");
					httpSession.setAttribute("message", "Error: name cannot be empty");

				} else {

					if (itemType.equals("bin-type")) {

						BinType binType = new BinType.Builder(itemName).build();
						
						if (searchableTerms != null && searchableTerms.length > 0) {
							binType.setSearchTerms(Arrays.asList(searchableTerms));
						}
						
						PreparedQueries.saveBinType(binType);						
						
						httpSession.setAttribute("message-type", "success");
						httpSession.setAttribute("message", "Bin type added successfully");

					} else if (itemType.equals("brand")) {

						Brand brand = new Brand.Builder(itemName).build();
						
						if (searchableTerms != null && searchableTerms.length > 0) {
							brand.setSearchTerms(Arrays.asList(searchableTerms));
						}
						
						if (categories != null && categories.length > 0) {
							brand.setCategories(Arrays.asList(categories));
						}
						
						PreparedQueries.saveBrand(brand);
						
						httpSession.setAttribute("message-type", "success");
						httpSession.setAttribute("message", "Brand added successfully");

					} else if (itemType.equals("litter-type")) {

						LitterType litterType = new LitterType.Builder(itemName).build();
						
						if (searchableTerms != null && searchableTerms.length > 0) {
							litterType.setSearchTerms(Arrays.asList(searchableTerms));
						}
						
						PreparedQueries.saveLitterType(litterType);
						
						httpSession.setAttribute("message-type", "success");
						httpSession.setAttribute("message", "Litter type added successfully");

					} else {

						httpSession.setAttribute("message-type", "warning");
						httpSession.setAttribute("message", "Error: unknown entity type");

					}
				}

				resp.sendRedirect("index.jsp?page=manage");

			} catch (Exception e) {

				String errorMessage = "An error occured while adding an entity";
				errorHandler.logFullErrorEvent(errorMessage, e);
				resp.sendRedirect(PageUtils.getRedirectAddressForErrorPage(errorMessage));
			}
		}
	}
}
