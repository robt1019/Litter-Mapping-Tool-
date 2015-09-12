package org.littermappingtool.backend.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.littermappingtool.backend.Bin;
import org.littermappingtool.backend.BinType;
import org.littermappingtool.backend.Brand;
import org.littermappingtool.backend.Event;
import org.littermappingtool.backend.Litter;
import org.littermappingtool.backend.LitterType;

import com.googlecode.objectify.ObjectifyService;

/**
 * Datastore queries.
 */
public class PreparedQueries {
	
	/**
    * Register all queried classes before use
    */
	static {
		ObjectifyService.register(Bin.class);
		ObjectifyService.register(BinType.class);
		ObjectifyService.register(Brand.class);
        ObjectifyService.register(Litter.class);
        ObjectifyService.register(LitterType.class);
        ObjectifyService.register(Event.class);
    }
	
	// CRUD operations for litters
	
    /**
     * Get all litters.
     *
     * @return unordered list of litter items
     */
    public static List<Litter> getAllLitters() {
		return ofy().load().type(Litter.class).list();
    }
    
    /**
     * Get a specific litter.
     *
     * @param Litter the litter item
     */
    public static Litter getLitter(Long id) {
    	return ofy().load().type(Litter.class).id(id).now();
    }
    
    /**
     * Delete litter.
     *
     * @param id the litter id
     */
    public static void deleteLitter(Long id) {
    	ofy().delete().type(Litter.class).id(id).now();
	}
    
    /**
     * Save litter.
     *
     * @param litter the litter to save
     */
    public static void saveLitter(Litter litter) {
    	ofy().save().entity(litter).now();
    }
    
	// CRUD operations for bins
    
    /**
     * Get all bins.
     *
     * @return unordered list of bins
     */
    public static List<Bin> getAllBins() {
    	return ofy().load().type(Bin.class).list();
    }
    
    /**
     * Delete bin.
     *
     * @param id the bin id
     */
    public static void deleteBin(Long id) {
    	ofy().delete().type(Bin.class).id(id).now();
	}
    
    /**
     * Save bin.
     *
     * @param bin the bin to save
     */
    public static void saveBin(Bin bin) {
    	ofy().save().entity(bin).now();
    }
    
	// CRUD operations for bin types
    
	/**
	 * Gets the bin type by id.
	 *
	 * @param id the bin type id
	 * @return the bin type by its id
	 */
	public static BinType getBinTypeById(Long id) {
    	return ofy().load().type(BinType.class).id(id).now();
    }
    
    /**
	 * Get all bin types ordered by name.
	 *
	 * @return ordered list of bin types
	 */
    public static List<BinType> getAllBinTypes() {
    	return ofy().load().type(BinType.class).order("name").list();
    }
    
    /**
     * Delete bin type.
     *
     * @param id the bin type id
     */
    public static void deleteBinType(Long id) {
    	ofy().delete().type(BinType.class).id(id).now();
	}
    
    /**
     * Save bin type.
     *
     * @param binType the bin type
     */
    public static void saveBinType(BinType binType) {
    	ofy().save().entity(binType).now();
    }

	// CRUD operations for brands
    
	/**
	 * Gets the brand by id.
	 *
	 * @param id the id
	 * @return the brand by its id
	 */
	public static Brand getBrandById(Long id) {
    	return ofy().load().type(Brand.class).id(id).now();
    }
    
    /**
	 * Get all brands ordered by name.
	 *
	 * @return ordered list of brands
	 */
    public static List<Brand> getAllBrands() {
    	return ofy().load().type(Brand.class).order("name").list();
    }
    
    /**
     * Delete brand.
     *
     * @param id the brand id
     */
    public static void deleteBrand(Long id) {
    	ofy().delete().type(Brand.class).id(id).now();
	}
    
    /**
     * Save brand.
     *
     * @param brand the brand
     */
    public static void saveBrand(Brand brand) {
    	ofy().save().entity(brand).now();
    }

	// CRUD operations for litter types
    
	/**
	 * Gets the litter type by id.
	 *
	 * @param id the id
	 * @return the litter type by its id
	 */
	public static LitterType getLitterTypeById(Long id) {
    	return ofy().load().type(LitterType.class).id(id).now();
    }
    
    /**
	 * Get all litter types ordered by name.
	 *
	 * @return ordered list of litter types
	 */
    public static List<LitterType> getAllLitterTypes() {
		return ofy().load().type(LitterType.class).order("name").list();
	}
    
    /**
     * Delete litter type.
     *
     * @param id the litter type id
     */
    public static void deleteLitterType(Long id) {
    	ofy().delete().type(LitterType.class).id(id).now();
	}
    
    /**
     * Save litter type.
     *
     * @param litterType the litter type
     */
    public static void saveLitterType(LitterType litterType) {
    	ofy().save().entity(litterType).now();
    }
    
    // CRUD operations for events
    
    /**
     * Get an event by id.
     *
     * @param id the event id
     * @return the event by its id
     */
    public static Event getEventById(Long id) {
    	return ofy().load().type(Event.class).id(id).now();
    }
    
    /**
     * Get all events.
     *
     * @return unordered list events
     */
    public static List<Event> getAllEvents() {
    	return ofy().load().type(Event.class).list();
    }

    /**
     * Gets the all current events.
     *
     * @return the all current events
     */
    public static List<Event> getAllCurrentEvents() {
    	return ofy().load().type(Event.class).filter("occurrenceDate >= ", new Date().getTime()).list();
    }
    

    /**
     * Gets all past events ordered by occurrence date.
     *
     * @return list of past events ordered by occurrence date
     */
    public static List<Event> getAllPastEventsOrderedByOccurrenceDate() {
    	return ofy().load().type(Event.class).filter("occurrenceDate < ", new Date().getTime()).order("occurrenceDate").list();
    }
    
    /**
     * Count all upcoming events.
     *
     * @return the count of events
     */
    public static int countAllUpcomingEvents() {
    	return ofy().load().type(Event.class).filter("occurrenceDate >= ", new Date().getTime()).count();
    }
    
    /**
     * Count all upcoming events this month.
     *
     * @return the count of events
     */
    public static int countAllUpcomingEventsThisMonth() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
    	return ofy().load().type(Event.class).filter("occurrenceDate >= ", new Date().getTime()).filter("occurrenceDate <= ", calendar.getTimeInMillis()).count();
    }
    
    /**
     * Save event.
     *
     * @param event the event
     */
    public static void saveEvent(Event event) {
    	ofy().save().entity(event).now();
    }
    
    /**
     * Delete event.
     *
     * @param id the event id
     */
    public static void deleteEvent(Long id) {
    	ofy().delete().type(Event.class).id(id).now();
    }
}
