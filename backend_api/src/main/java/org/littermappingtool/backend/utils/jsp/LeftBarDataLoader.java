package org.littermappingtool.backend.utils.jsp;

import org.littermappingtool.backend.dao.PreparedQueries;

/**
 * Data loader for the left-sidebar.jsp page.
 */
public class LeftBarDataLoader {
	
	/** The total upcoming events. */
	private int totalUpcomingEvents;
	
	/**
	 * Instantiates a new left bar data loader.
	 */
	public LeftBarDataLoader() {
		process();
	}
	
	/**
	 * Gets the total upcoming events.
	 *
	 * @return the total upcoming events
	 */
	public int getTotalUpcomingEvents() {
		return totalUpcomingEvents;
	}

	/**
	 * Sets the total upcoming events.
	 *
	 * @param totalUpcomingEvents the new total upcoming events
	 */
	public void setTotalUpcomingEvents(int totalUpcomingEvents) {
		this.totalUpcomingEvents = totalUpcomingEvents;
	}

	/**
	 * Process.
	 */
	private void process() {
		totalUpcomingEvents = PreparedQueries.countAllUpcomingEvents();
	}
}
