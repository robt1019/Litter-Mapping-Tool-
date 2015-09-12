package org.littermappingtool.backend;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.List;

/**
 * The Category entity.
 */
public class Category {

	/** The id. */
	@Id private Long id;

	/** The name. */
	@Index private String name;

	/** The search terms. */
	private List<String> searchTerms;

	/**
	 * Mandatory, no-args constructor
	 */
	public Category() {
	}

	/**
	 * Instantiates a new brand.
	 *
	 * @param name the name
	 */
	public Category(String name) {
		this.name = name;
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
}
