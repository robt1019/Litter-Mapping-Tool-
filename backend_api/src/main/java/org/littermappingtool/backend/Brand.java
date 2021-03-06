package org.littermappingtool.backend;

import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The Brand entity.
 */
@Entity
public class Brand {

	/** The id. */
	@Id private Long id;

	/** The name. */
	@Index private String name;

	/** The search terms. */
	private List<String> searchTerms;
	
	/** The categories. */
	private List<String> categories;

	/**
	 * Mandatory, no-args constructor
     * ===
     * Use the provided Builder to initialize a new Brand object.
	 */
	public Brand() {
	}

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
	 * Gets the categories.
	 *
	 * @return the categories
	 */
	public List<String> getCategories() {
		return categories;
	}

	/**
	 * Sets the categories.
	 *
	 * @param categories the new categories
	 */
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
	/**
	 * The Brand Builder.
	 */
	public static class Builder {
		
		// Mandatory fields
		
		/** The name. */
		private final String name;
		
		// Optional fields
		
		/** The search terms. */
		private List<String> searchTerms;
		
		/** The categories. */
		private List<String> categories;

		// Constructor & setters
		
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
		 * Sets the categories.
		 *
		 * @param categories the categories
		 * @return the builder
		 */
		public Builder setCategories(List<String> categories) {
			this.categories = categories;
			return this;
		}
		
		/**
		 * Builds the Brand object.
		 *
		 * @return the brand
		 */
		public Brand build() {
			return new Brand(this);
		}
	}
	
	/**
	 * Instantiates a new brand.
	 *
	 * @param builder the builder
	 */
	private Brand(Builder builder) {
		this.name = builder.name;
		this.searchTerms = builder.searchTerms;
		this.categories = builder.categories;
	}
}