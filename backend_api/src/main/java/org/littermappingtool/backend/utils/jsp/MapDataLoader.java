package org.littermappingtool.backend.utils.jsp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.littermappingtool.backend.Bin;
import org.littermappingtool.backend.Event;
import org.littermappingtool.backend.Litter;
import org.littermappingtool.backend.dao.PreparedQueries;
import org.littermappingtool.backend.utils.jsp.wrappers.EventWithLitters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Data loader for the map.jsp page
 */
public class MapDataLoader {
	
	/** List of bins */
	private List<Bin> bins;

	/** List of litters */
	private List<Litter> litters;
	
	/** The time line event json. */
	private String timeLineEventsJson;
	
	/** The none event litters json. */
	private String noneEventLittersJson;

	/** Constructor for Map Data Loader */
	public MapDataLoader() {
		process();
	}

	/**
	 * Gets the bins.
	 *
	 * @return the bins
	 */
	public List<Bin> getBins() {
		return bins;
	}

	/**
	 * Sets the bins.
	 *
	 * @param bins the new bins
	 */
	public void setBins(List<Bin> bins) {
		this.bins = bins;
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
	 * Gets the time line events json.
	 *
	 * @return the time line events json
	 */
	public String getTimeLineEventsJson() {
		return timeLineEventsJson;
	}

	/**
	 * Sets the time line events json.
	 *
	 * @param timeLineEventsJson the new time line events json
	 */
	public void setTimeLineEventsJson(String timeLineEventsJson) {
		this.timeLineEventsJson = timeLineEventsJson;
	}
	
	/**
	 * Gets the none event litters json.
	 *
	 * @return the none event litters json
	 */
	public String getNoneEventLittersJson() {
		return noneEventLittersJson;
	}

	/**
	 * Sets the none event litters json.
	 *
	 * @param noneEventLittersJson the new none event litters json
	 */
	public void setNoneEventLittersJson(String noneEventLittersJson) {
		this.noneEventLittersJson = noneEventLittersJson;
	}

	/** Main process method */
	private void process() {
		
		// Get and validate bins
		List<Bin> allBins = PreparedQueries.getAllBins();
		bins = new ArrayList<Bin>();
		
		for (Bin bin : allBins) {
			if (bin.getLatitude() != null && bin.getLongitude() != null) {
				bins.add(bin);
			}
		}

		// Create map of unique events and assign them their litters
		List<Litter> noneEventLitters = new ArrayList<Litter>();
		Map<Long, EventWithLitters> eventsHashMap = new HashMap<Long, EventWithLitters>();
		litters = PreparedQueries.getAllLitters();
		
		for (Litter litter : litters) {
			if (litter.getLatitude() != null && litter.getLongitude() != null) {
				if (litter.getEventId() != null) {
					// Litter item persisted during an event
					Event event = PreparedQueries.getEventById(litter.getEventId());
					Long eventId = event.getId();
					if (!eventsHashMap.containsKey(eventId)) {
						List<Litter> eventLitters = new ArrayList<Litter>();
						eventLitters.add(litter);
						eventsHashMap.put(eventId, new EventWithLitters(event, eventLitters));
					} else {
						List<Litter> eventLitters = eventsHashMap.get(eventId).getLitters();
						eventLitters.add(litter);
						eventsHashMap.put(eventId, new EventWithLitters(event, eventLitters));
					}
				} else {
					// Litter item persisted during an individual walk
					noneEventLitters.add(litter);
				}
			}
		}
		
		// Sort the events map by event occurrence date
		EventsComparator comparator =  new EventsComparator(eventsHashMap);
		Map<Long, EventWithLitters> eventsTreeMap = new TreeMap<Long, EventWithLitters>(comparator);
		eventsTreeMap.putAll(eventsHashMap);
		
		// Create a sorted map, where events are grouped under a relevant city 
		Map<String, List<EventWithLitters>> citiesTreeMap = new TreeMap<String, List<EventWithLitters>>();
		for (EventWithLitters eventWithLitters : eventsTreeMap.values()) {
			String city = eventWithLitters.getEvent().getCity();
			if (!citiesTreeMap.containsKey(city)) {
				List<EventWithLitters> eventsWithLitters = new ArrayList<EventWithLitters>();
				eventsWithLitters.add(eventWithLitters);
				citiesTreeMap.put(city, eventsWithLitters);
			} else {
				List<EventWithLitters> eventsWithLitters = citiesTreeMap.get(city);
				eventsWithLitters.add(eventWithLitters);
				citiesTreeMap.put(city, eventsWithLitters);				
			}
		}
		
		// Create JSON object for events time line
		Gson gson = new GsonBuilder().create();
		timeLineEventsJson = gson.toJson(citiesTreeMap);
		noneEventLittersJson = gson.toJson(noneEventLitters);
	}
	
	/**
	 * The Events Comparator.
	 */
	class EventsComparator implements Comparator<Long> {

	    /** The map. */
    	Map<Long, EventWithLitters> map;
	    
	    /**
    	 * Instantiates a new events comparator.
    	 *
    	 * @param map the map
    	 */
    	public EventsComparator(Map<Long, EventWithLitters> map) {
	        this.map = map;
	    }

    	@Override
    	public int compare(Long a, Long b) {
	        if (map.get(a).getEvent().getOccurrenceDate() >= map.get(b).getEvent().getOccurrenceDate()) {
	            return -1;
	        } else {
	            return 1;
	        }
	    }
	}
}