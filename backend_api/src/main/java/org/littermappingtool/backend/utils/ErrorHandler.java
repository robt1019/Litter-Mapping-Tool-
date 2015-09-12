package org.littermappingtool.backend.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The ErrorHandler.
 */
public class ErrorHandler {

	/** The log. */
	private static Logger logger;

	/** The class name. */
	private String className;

	/**
	 * Instantiates a new error handler.
	 *
	 * @param className the class name
	 */
	public ErrorHandler(String className) {
		this.className = className;
		ErrorHandler.logger = Logger.getLogger(className);
		sendEmail();
	}

	/**
	 * Log error event.
	 *
	 * @param message the message
	 */
	public void logErrorEvent(String message) {
		logger.warning(className + ": " + message);
	}	

	/**
	 * Log full error event (with full exception).
	 *
	 * @param message the message
	 * @param e the exception
	 */
	public void logFullErrorEvent(String message, Exception e) {
		logger.log(Level.SEVERE, e.getMessage(), e);
	}

	/**
	 * Send email.
	 */
	private void sendEmail() {}
}
