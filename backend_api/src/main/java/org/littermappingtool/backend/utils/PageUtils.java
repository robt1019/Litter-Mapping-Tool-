package org.littermappingtool.backend.utils;

import static org.littermappingtool.backend.utils.FixedValues.MAX_LENGTH_ERROR_DESC;

/**
 * Various utils for JSP pages
 */
public class PageUtils {

    /**
     * Gets the page title.
     *
     * @param pageName the page name
     * @return the page title
     */
    public static String getPageTitle(String pageName) {
        String title = "Litter Mapping Tool";
        switch (pageName) {
            case "":				title += " - Home page";					break;
            case "home":			title += " - Home page";					break;
            case "map":				title += " - Map";							break;
            case "events":			title += " - Events";						break;
            case "import":			title += " - Import";						break;
            case "manage":			title += " - Manage";						break;
            case "upload-litter":	title += " - Upload new litter item";		break;
            case "upload-bin":		title += " - Upload new bin";				break;
            case "error":			title += " - 500 - Error in processing";	break;
            default:				title += " - 404 - Page not found";			break;
        }
        return title;
    }
    
	/**
	 * Gets the redirect address for error page.
	 *
	 * @param errorMessage the error message
	 * @return the redirect address for error page
	 */
	public static String getRedirectAddressForErrorPage(String errorMessage) {
		return "/index.jsp?page=error&error-text="
				+ (errorMessage.length() > MAX_LENGTH_ERROR_DESC ? errorMessage.substring(0, MAX_LENGTH_ERROR_DESC) + " ..." : errorMessage);
	}
}
