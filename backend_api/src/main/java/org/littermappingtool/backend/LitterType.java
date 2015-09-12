package org.littermappingtool.backend;

import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The LitterType entity.
 */
@Entity
public class LitterType {

    /** The id. */
    @Id private Long id;

    /** The name. */
    @Index private String name;
    
    /** The search terms. */
    private List<String> searchTerms;
    
    /**
     * Mandatory, no-args constructor
     * ===
     * Use the provided Builder to initialize a new LitterType object.
     */
    public LitterType() {}
    
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
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the search terms.
     *
     * @return the search terms
     */
    public List<String> getSearchTerms() {
        return searchTerms;
    }

    /**
     * Sets the search terms.
     *
     * @param searchTerms the new search terms
     */
    public void setSearchTerms(List<String> searchTerms) {
        this.searchTerms = searchTerms;
    }
    
	/**
	 * The Litter Type Builder.
	 */
	public static class Builder {
		
		// Mandatory fields
		
		/** The name. */
		private final String name;
		
		// Optional fields
		
		/** The search terms. */
		private List<String> searchTerms;
		
		/**
		 * Instantiates a new builder.
		 *
		 * @param name the name
		 */
		public Builder(String name) {
			this.name = name;
		}

		/**
		 * Sets the search terms.
		 *
		 * @param searchTerms the search terms
		 * @return the builder
		 */
		public Builder setSearchTerms(List<String> searchTerms) {
			this.searchTerms = searchTerms;
			return this;
		}
		
		/**
		 * Builds the LitterType object.
		 *
		 * @return the litter type
		 */
		public LitterType build() {
			return new LitterType(this);
		}
	}
	
	/**
	 * Instantiates a new litter type.
	 *
	 * @param builder the builder
	 */
	private LitterType(Builder builder) {
		this.name = builder.name;
		this.searchTerms = builder.searchTerms;
	}
}
