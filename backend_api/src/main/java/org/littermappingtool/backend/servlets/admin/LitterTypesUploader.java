package org.littermappingtool.backend.servlets.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.littermappingtool.backend.LitterType;
import org.littermappingtool.backend.dao.PreparedQueries;

/**
 * Simple loader for litter types
 */
@SuppressWarnings("serial")
public class LitterTypesUploader extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String[] litterTypeNames = { "metal", "plastic", "glass", "other" };

		for (String litterTypeName : litterTypeNames) {
			PreparedQueries.saveLitterType(new LitterType.Builder(litterTypeName).build());
		}
	}
}