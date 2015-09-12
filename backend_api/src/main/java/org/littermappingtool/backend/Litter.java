package org.littermappingtool.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * The Litter entity.
 */
@Entity
public class Litter {

	/** The id. */
	@Id private Long id;

	/** The litter type. */
	private String litterType;
	
	/** The other litter type. */
	private String otherLitterType;

	/** The brand. */
	private String brand;
	
	/** The other brand name. */
	private String otherBrandName;

	/** The user email. */
	private String userEmail;

	/** The latitude. */
	private Double latitude;

	/** The longitude. */
	private Double longitude;

	/** The photo blob ref. */
	private String photoBlobRef;

	/** The audio blob ref. */
	private String audioBlobRef;

	/** The created. */
	private Long created;

	/** The event id. */
	private Long eventId;

	/**
	 * Mandatory, no-args constructor. 
	 * === 
	 * Use the provided Builder to initialize a new Litter object.
	 */
	public Litter() {}

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
	 * Gets the litter type.
	 *
	 * @return the litter type
	 */
	public String getLitterType() {
		return litterType;
	}

	/**
	 * Sets the litter type.
	 *
	 * @param litterType the new litter type
	 */
	public void setLitterType(String litterType) {
		this.litterType = litterType;
	}

	/**
	 * Gets the other litter type.
	 *
	 * @return the other litter type
	 */
	public String getOtherLitterType() {
		return otherLitterType;
	}

	/**
	 * Sets the other litter type.
	 *
	 * @param otherLitterType the new other litter type
	 */
	public void setOtherLitterType(String otherLitterType) {
		this.otherLitterType = otherLitterType;
	}

	/**
	 * Gets the brand.
	 *
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * Sets the brand.
	 *
	 * @param brand the new brand
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	/**
	 * Gets the other brand name.
	 *
	 * @return the other brand name
	 */
	public String getOtherBrandName() {
		return otherBrandName;
	}

	/**
	 * Sets the other brand name.
	 *
	 * @param otherBrandName the new other brand name
	 */
	public void setOtherBrandName(String otherBrandName) {
		this.otherBrandName = otherBrandName;
	}

	/**
	 * Gets the user email.
	 *
	 * @return the user email
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * Sets the user email.
	 *
	 * @param userEmail the new user email
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
	 * Gets the photo blob ref.
	 *
	 * @return the photo blob ref
	 */
	public String getPhotoBlobRef() {
		return photoBlobRef;
	}

	/**
	 * Sets the photo blob ref.
	 *
	 * @param photoBlobRef the new photo blob ref
	 */
	public void setPhotoBlobRef(String photoBlobRef) {
		this.photoBlobRef = photoBlobRef;
	}

	/**
	 * Gets the audio blob ref.
	 *
	 * @return the audio blob ref
	 */
	public String getAudioBlobRef() {
		return audioBlobRef;
	}

	/**
	 * Sets the audio blob ref.
	 *
	 * @param audioBlobRef the new audio blob ref
	 */
	public void setAudioBlobRef(String audioBlobRef) {
		this.audioBlobRef = audioBlobRef;
	}

	/**
	 * Gets the created.
	 *
	 * @return the created
	 */
	public Long getCreated() {
		return created;
	}

	/**
	 * Sets the created.
	 *
	 * @param created the new created
	 */
	public void setCreated(Long created) {
		this.created = created;
	}

	/**
	 * Gets the event id.
	 *
	 * @return the event id
	 */
	public Long getEventId() {
		return eventId;
	}

	/**
	 * Sets the event id.
	 *
	 * @param eventId the new event id
	 */
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	/**
	 * The Litter Builder.
	 */
	public static class Builder {

		// Mandatory fields

		/** The litter type. */
		private final String litterType;

		/** The brand. */
		private final String brand;

		/** The user email. */
		private final String userEmail;

		/** The latitude. */
		private final Double latitude;

		/** The longitude. */
		private final Double longitude;

		/** The created. */
		private final Long created;
		
		/** The event id. */
		private final Long eventId;

		// Optional fields
		
		/** The other litter type. */
		private String otherLitterType;
		
		/** The other brand name. */
		private String otherBrandName;

		/** The photo blob ref. */
		private String photoBlobRef;

		/** The audio blob ref. */
		private String audioBlobRef;

		// Constructor & setters

		/**
		 * Instantiates a new builder.
		 *
		 * @param litterType the litter type
		 * @param brand the brand
		 * @param userEmail the user email
		 * @param latitude the latitude
		 * @param longitude the longitude
		 * @param created the created
		 * @param eventId the event id
		 */
		public Builder(String litterType, String brand, String userEmail, Double latitude, Double longitude, Long created, Long eventId) {
			this.litterType = litterType;
			this.brand = brand;
			this.userEmail = userEmail;
			this.latitude = latitude;
			this.longitude = longitude;
			this.created = created;
			this.eventId = eventId;
		}
		
		/**
		 * Sets the other litter type.
		 *
		 * @param otherLitterType the other litter type
		 * @return the builder
		 */
		public Builder setOtherLitterType(String otherLitterType) {
			this.otherLitterType = otherLitterType;
			return this;
		}
		
		/**
		 * Sets the other brand name.
		 *
		 * @param otherBrandName the other brand name
		 * @return the builder
		 */
		public Builder setOtherBrandName(String otherBrandName) {
			this.otherBrandName = otherBrandName;
			return this;
		}

		/**
		 * Sets the photo blob ref.
		 *
		 * @param photoBlobRef the photo blob ref
		 * @return the builder
		 */
		public Builder setPhotoBlobRef(String photoBlobRef) {
			this.photoBlobRef = photoBlobRef;
			return this;
		}

		/**
		 * Sets the audio blob ref.
		 *
		 * @param audioBlobRef the audio blob ref
		 * @return the builder
		 */
		public Builder setAudioBlobRef(String audioBlobRef) {
			this.audioBlobRef = audioBlobRef;
			return this;
		}

		/**
		 * Builds the Litter object.
		 *
		 * @return the litter
		 */
		public Litter build() {
			return new Litter(this);
		}
	}

	/**
	 * Instantiates a new litter.
	 *
	 * @param builder the builder
	 */
	private Litter(Builder builder) {
		this.litterType = builder.litterType;
		this.otherLitterType = builder.otherLitterType;
		this.brand = builder.brand;
		this.otherBrandName = builder.otherBrandName;
		this.userEmail = builder.userEmail;
		this.latitude = builder.latitude;
		this.longitude = builder.longitude;
		this.photoBlobRef = builder.photoBlobRef;
		this.audioBlobRef = builder.audioBlobRef;
		this.created = builder.created;
		this.eventId = builder.eventId;
	}
}
