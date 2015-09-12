package org.littermappingtool.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * The Bin entity.
 */
@Entity
public class Bin {

    /** The id. */
    @Id private Long id;

    /** The bin type. */
    private String binType;
    
    /** The other bin type. */
    private String otherBinType;

    /** The user email. */
    private String userEmail;

    /** The latitude. */
    private Double latitude;

    /** The longitude. */
    private Double longitude;
    
    /** The photo blob ref. */
    private String photoBlobRef;

    /** The created. */
    private Long created;
        
    /**
     * Mandatory, no-args constructor.
     * ===
     * Use the provided Builder to initialize a new Bin object. 
     */
    public Bin() {}
    
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
	 * Gets the bin type.
	 *
	 * @return the bin type
	 */
	public String getBinType() {
		return binType;
	}

	/**
	 * Sets the bin type.
	 *
	 * @param binType the new bin type
	 */
	public void setBinType(String binType) {
		this.binType = binType;
	}

	/**
	 * Gets the other bin type.
	 *
	 * @return the other bin type
	 */
	public String getOtherBinType() {
		return otherBinType;
	}

	/**
	 * Sets the other bin type.
	 *
	 * @param otherBinType the new other bin type
	 */
	public void setOtherBinType(String otherBinType) {
		this.otherBinType = otherBinType;
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
	 * The Bin Builder.
	 */
	public static class Builder {
		
		// Mandatory fields
		
		/** The bin type. */
		private final String binType;
		
		/** The user email. */
		private final String userEmail;
		
		/** The latitude. */
		private final Double latitude;
		
		/** The longitude. */
		private final Double longitude;
		
		/** The created. */
		private final Long created;
		
		// Optional fields
		
		/** The other bin type. */
		private String otherBinType;
		
		/** The photo blob ref. */
		private String photoBlobRef;
		
		// Constructor & setters

		/**
		 * Instantiates a new builder.
		 *
		 * @param binType the bin type
		 * @param userEmail the user email
		 * @param latitude the latitude
		 * @param longitude the longitude
		 * @param created the created
		 */
		public Builder(String binType, String userEmail, Double latitude, Double longitude, Long created) {
			this.binType = binType;
			this.userEmail = userEmail;
			this.latitude = latitude;
			this.longitude = longitude;
			this.created = created;
		}

		public Builder setOtherBinType(String otherBinType) {
			this.otherBinType = otherBinType;
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
		 * Build the Bin object.
		 *
		 * @return the bin
		 */
		public Bin build() {
			return new Bin(this);
		}
	}
	
	/**
	 * Instantiates a new bin.
	 *
	 * @param builder the builder
	 */
	private Bin(Builder builder) {
		this.binType = builder.binType;
		this.otherBinType = builder.otherBinType;
		this.userEmail = builder.userEmail;
		this.latitude = builder.latitude;
		this.longitude = builder.longitude;
		this.created = builder.created;
		this.photoBlobRef = builder.photoBlobRef;
	}
}
