package org.littermappingtool.backend.servlets.dataimport;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.littermappingtool.backend.Bin;
import org.littermappingtool.backend.dao.PreparedQueries;
import org.littermappingtool.backend.servlets.datamng.SystemEntityPersister;
import org.littermappingtool.backend.utils.DateTimeUtils;
import org.littermappingtool.backend.utils.ErrorHandler;
import org.littermappingtool.backend.utils.PageUtils;

import au.com.bytecode.opencsv.CSVReader;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Servlet to process a CSV file with bin data.
 */
@SuppressWarnings("serial")
public class SuccessfulUploadServlet extends HttpServlet {
	
	/** The error handler. */
	private static ErrorHandler errorHandler = new ErrorHandler(SystemEntityPersister.class.getName());

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		HttpSession httpSession = req.getSession(false);

		if (userService == null || httpSession == null) {
			// The user service or http session is null, redirect back to main page
			resp.sendRedirect("/index.jsp");

		} else {

			try {
				User user = userService.getCurrentUser();
				String blobKey = req.getParameter("blob-key");
	
				BlobstoreInputStream is = new BlobstoreInputStream(new BlobKey(blobKey));
				CSVReader csvReader = new CSVReader(new InputStreamReader(is));
				List<String[]> rows = csvReader.readAll();
				csvReader.close();
	
				for (String[] row : rows) {
					Date dateCreated = DateTimeUtils.formatDate(row[3], DateTimeUtils.DATE_TIME_FORMAT);
					Bin bin = new Bin.Builder(row[0], user.getEmail(), Double.parseDouble(row[1]), Double.parseDouble(row[2]), dateCreated.getTime()).build();
					PreparedQueries.saveBin(bin);
				}
				
				httpSession.setAttribute("message-type", "success");
				httpSession.setAttribute("message", "CSV file uploaded successfully");
	
				resp.sendRedirect("index.jsp?page=import");
				
			} catch (Exception e) {

				String errorMessage = "An error occured while processing CSV data";
				errorHandler.logFullErrorEvent(errorMessage, e);
				resp.sendRedirect(PageUtils.getRedirectAddressForErrorPage(errorMessage));
			}
		}
	}
}