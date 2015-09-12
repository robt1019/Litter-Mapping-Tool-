package org.littermappingtool.backend;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The Event entity.
 */
@Entity
public class Event {
	
	/** The id. */
	@Id private Long id;
	
	/** The occurrence date. */
	@Index private Long occurrenceDate;
	
	/** The latitude. */
	private Double latitude;
	
	/** The longitude. */
	private Double longitude;
	
	/** The title. */
	private String title;
	
	/** The description. */
	private String description;
	
	/** The location name (e.g. park, street). */
	private String location;
	
	/** The city. */
	private String city;
	
	/** The country. */
	private String country;
	
	/** The colour (for calendar items). */
	private String colour;
	
	/** The litters. */
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	private List<Key<Litter>> litters = new ArrayList<Key<Litter>>();

	/**
	 * Mandatory, no-args constructor.
     * ===
     * Use the provided Builder to initialize a new Event object. 
	 */
	public Event() {}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the occurrence date.
	 *
	 * @return the occurrence date
	 */
	public Long getOccurrenceDate() {
		return occurrenceDate;
	}

	/**
	 * Sets the occurrence date.
	 *
	 * @param occurrenceDate the new occurrence date
	 */
	public void setOccurrenceDate(Long occurrenceDate) {
		this.occurrenceDate = occurrenceDate;
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude the new latitude
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 *
	 * @param longitude the new longitude
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Gets the colour.
	 *
	 * @return the colour
	 */
	public String getColour() {
		return colour;
	}

	/**
	 * Sets the colour.
	 *
	 * @param colour the new colour
	 */
	public void setColour(String colour) {
		this.colour = colour;
	}
	
	/**
	 * Gets the litters.
	 *
	 * @return the litters
	 */
	public List<Key<Litter>> getLitters() {
		return litters;
	}

	/**
	 * Sets the litters.
	 *
	 * @param litters the new litters
	 */
	public void setLitters(List<Key<Litter>> litters) {
		this.litters = litters;
	}

	/**
	 * The Event Builder.
	 */
	public static class Builder {
		
		// Mandatory fields
		
		/** The occurrence date. */
		private final Long occurrenceDate;
		
		/** The latitude. */
		private final Double latitude;
		
		/** The longitude. */
		private final Double longitude;
		
		/** The title. */
		private final String title;
		
		// Optional fields
		
		/** The location. */
		private String location;
		
		/** The city. */
		private String city;
		
		/** The country. */
		private String country;
		
		/** The description. */
		private String description;
		
		/** The colour. */
		private String colour;
		
		/** The litters. */
		@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
		private List<Key<Litter>> litters = new ArrayList<Key<Litter>>();
		
		// Constructor & setters
		
		/**
		 * Instantiates a new builder.
		 *
		 * @param occurrenceDate the occurrence date
		 * @param latitude the latitude
		 * @param longitude the longitude
		 * @param title the title
		 */
		public Builder(Long occurrenceDate, Double latitude, Double longitude, String title) {
			this.occurrenceDate = occurrenceDate;
			this.latitude = latitude;
			this.longitude = longitude;
			this.title = title;
		}
		
		/**
		 * Sets the location.
		 *
		 * @param location the location
		 * @return the builder
		 */
		public Builder setLocation(String location) {
			this.location = location;
			return this;
		}
		
		/**
		 * Sets the city.
		 *
		 * @param city the city
		 * @return the builder
		 */
		public Builder setCity(String city) {
			this.city = city;
			return this;
		}
		
		/**
		 * Sets the country.
		 *
		 * @param country the country
		 * @return the builder
		 */
		public Builder setCountry(String country) {
			this.country = country;
			return this;
		}
		
		/**
		 * Sets the description.
		 *
		 * @param description the description
		 * @return the builder
		 */
		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}
		
		/**
		 * Sets the colour.
		 *
		 * @param colour the colour
		 * @return the builder
		 */
		public Builder setColour(String colour) {
			this.colour = colour;
			return this;
		}
		
		/**
		 * Sets the litters.
		 *
		 * @param litters the litters
		 * @return the builder
		 */
		public Builder setLitters(List<Key<Litter>> litters) {
			this.litters = litters;
			return this;
		}
		
		/**
		 * Build the Event object.
		 *
		 * @return the event
		 */
		public Event build() {
			return new Event(this);
		}
	}
	
	/**
	 * Instantiates a new event.
	 *
	 * @param builder the builder
	 */
	private Event(Builder builder) {
		this.occurrenceDate = builder.occurrenceDate;
		this.latitude = builder.latitude;
		this.longitude = builder.longitude;
		this.title = builder.title;
		this.location = builder.location;
		this.city = builder.city;
		this.country = builder.country;
		this.description = builder.description;
		this.colour = builder.colour;
		this.litters = builder.litters;
	}
}
