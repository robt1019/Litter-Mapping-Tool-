package org.littermappingtool.backend.utils.jsp;

import java.util.List;

import org.littermappingtool.backend.Brand;
import org.littermappingtool.backend.Event;
import org.littermappingtool.backend.LitterType;
import org.littermappingtool.backend.dao.PreparedQueries;

/**
 * Data loader for the upload-litter.jsp page.
 */
public class UploadLitterDataLoader {
	
	/** The brands. */
	private List<Brand> brands;
	
	/** The litter types. */
	private List<LitterType> litterTypes;
	
	/** The events. */
	private List<Event> events;
	
	/**
	 * Instantiates a new upload litter data loader.
	 */
	public UploadLitterDataLoader() {
		process();
	}
	
	/**
	 * Gets the brands.
	 *
	 * @return the brands
	 */
	public List<Brand> getBrands() {
		return brands;
	}

	/**
	 * Sets the brands.
	 *
	 * @param brands the new brands
	 */
	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	/**
	 * Gets the litter types.
	 *
	 * @return the litter types
	 */
	public List<LitterType> getLitterTypes() {
		return litterTypes;
	}

	/**
	 * Sets the litter types.
	 *
	 * @param litterTypes the new litter types
	 */
	public void setLitterTypes(List<LitterType> litterTypes) {
		this.litterTypes = litterTypes;
	}

	/**
	 * Gets the events.
	 *
	 * @return the events
	 */
	public List<Event> getEvents() {
		return events;
	}

	/**
	 * Sets the events.
	 *
	 * @param events the new events
	 */
	public void setEvents(List<Event> events) {
		this.events = events;
	}

	/**
	 * Process.
	 */
	private void process() {
		
		// Query all items
		events = PreparedQueries.getAllCurrentEvents();
		brands = PreparedQueries.getAllBrands();
		litterTypes = PreparedQueries.getAllLitterTypes();
	}
}
