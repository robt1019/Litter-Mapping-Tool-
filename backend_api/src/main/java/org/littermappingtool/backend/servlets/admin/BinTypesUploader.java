package org.littermappingtool.backend.servlets.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.littermappingtool.backend.BinType;
import org.littermappingtool.backend.dao.PreparedQueries;

/**
 * Simple loader for bin types.
 */

@SuppressWarnings("serial")
public class BinTypesUploader extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String[] binTypeNames = { "recycling", "landfill" };

		for (String binTypeName : binTypeNames) {
			PreparedQueries.saveBinType(new BinType.Builder(binTypeName).build());
		}
	}
}