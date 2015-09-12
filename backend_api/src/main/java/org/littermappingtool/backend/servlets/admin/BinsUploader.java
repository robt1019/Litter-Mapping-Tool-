package org.littermappingtool.backend.servlets.admin;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.littermappingtool.backend.Bin;
import org.littermappingtool.backend.dao.PreparedQueries;

/**
 * Simple loader for bins.
 */

@SuppressWarnings("serial")
public class BinsUploader extends HttpServlet {

	/** The Constant BIN_TYPE_1. */
	private static final String BIN_TYPE_1 = "landfill";
	
	/** The Constant BIN_TYPE_2. */
	private static final String BIN_TYPE_2 = "recycling";
	
	/** The Constant EMAIL. */
	private static final String EMAIL = "andrej.lanak@gmail.com";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PreparedQueries.saveBin(new Bin.Builder(BIN_TYPE_1, EMAIL, 51.453468, -2.606486, new Date().getTime()).build());
		PreparedQueries.saveBin(new Bin.Builder(BIN_TYPE_2, EMAIL, 51.453361, -2.606757, new Date().getTime()).build());
		PreparedQueries.saveBin(new Bin.Builder(BIN_TYPE_1, EMAIL, 51.453501, -2.607792, new Date().getTime()).build());
		PreparedQueries.saveBin(new Bin.Builder(BIN_TYPE_2, EMAIL, 51.453862, -2.605730, new Date().getTime()).build());
		PreparedQueries.saveBin(new Bin.Builder(BIN_TYPE_1, EMAIL, 51.453309, -2.605609, new Date().getTime()).build());
		PreparedQueries.saveBin(new Bin.Builder(BIN_TYPE_2, EMAIL, 51.452846, -2.605829, new Date().getTime()).build());
		PreparedQueries.saveBin(new Bin.Builder(BIN_TYPE_1, EMAIL, 51.452199, -2.607039, new Date().getTime()).build());
		PreparedQueries.saveBin(new Bin.Builder(BIN_TYPE_2, EMAIL, 51.452084, -2.601436, new Date().getTime()).build());
		PreparedQueries.saveBin(new Bin.Builder(BIN_TYPE_1, EMAIL, 51.452137, -2.600178, new Date().getTime()).build());
	}
}