package org.littermappingtool.backend.utils.jsp.wrappers;

import java.util.List;

import org.littermappingtool.backend.Event;
import org.littermappingtool.backend.Litter;

/**
 * The Events wrapper class.
 */
public class EventWithLitters {
	
	/** The event. */
	private Event event;
	
	/** The litters. */
	private List<Litter> litters;


	/**
	 * Instantiates a new event with litters.
	 *
	 * @param event the event
	 * @param litters the litters
	 */
	public EventWithLitters(Event event, List<Litter> litters) {
		this.event = event;
		this.litters = litters;
	}

	/**
	 * Gets the event.
	 *
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * Sets the event.
	 *
	 * @param event the new event
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * Gets the litters.
	 *
	 * @return the litters
	 */
	public List<Litter> getLitters() {
		return litters;
	}

	/**
	 * Sets the litters.
	 *
	 * @param litters the new litters
	 */
	public void setLitters(List<Litter> litters) {
		this.litters = litters;
	}
}
