package org.littermappingtool.backend.utils.jsp;

import java.util.List;

import org.littermappingtool.backend.Event;
import org.littermappingtool.backend.dao.PreparedQueries;

/**
 * Data loader for the events.jsp page.
 */
public class EventsDataLoader {
	
	/** The events. */
	private List<Event> allEvents;
	
	/** The total upcoming events. */
	private int totalUpcomingEvents;
	
	/** The upcoming events this month. */
	private int upcomingEventsThisMonth;

	/**
	 * Instantiates a new events data loader.
	 */
	public EventsDataLoader() {
		process();
	}
	
	/**
	 * Gets the all events.
	 *
	 * @return the all events
	 */
	public List<Event> getAllEvents() {
		return allEvents;
	}

	/**
	 * Sets the all events.
	 *
	 * @param allEvents the new all events
	 */
	public void setAllEvents(List<Event> allEvents) {
		this.allEvents = allEvents;
	}

	/**
	 * Gets the upcoming events this month.
	 *
	 * @return the upcoming events this month
	 */
	public int getUpcomingEventsThisMonth() {
		return upcomingEventsThisMonth;
	}

	/**
	 * Sets the upcoming events this month.
	 *
	 * @param upcomingEventsThisMonth the new upcoming events this month
	 */
	public void setUpcomingEventsThisMonth(int upcomingEventsThisMonth) {
		this.upcomingEventsThisMonth = upcomingEventsThisMonth;
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
		allEvents = PreparedQueries.getAllEvents();
		totalUpcomingEvents = PreparedQueries.countAllUpcomingEvents();
		upcomingEventsThisMonth = PreparedQueries.countAllUpcomingEventsThisMonth();
	}
}
