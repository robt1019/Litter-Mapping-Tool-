package org.littermappingtool.backend;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;

/**
 * The MenuCommand entity.
 */
public class MenuCommand {

	/** The id. */
	@Id private Long id;

	/** The name. */
	@Index private String name;

	/** The search terms. */
	private ArrayList<String> searchTerms;

	/**
	 * Mandatory, no-args constructor
	 */
	public MenuCommand() {
	}

	/**
	 * Instantiates a new litter type.
	 *
	 * @param name the name
	 */
	public MenuCommand(String name) {
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
	public ArrayList<String> getSearchTerms() {
		return searchTerms;
	}

	/**
	 * Sets the search terms.
	 *
	 * @param searchTerms the new search terms
	 */
	public void setSearchTerms(ArrayList<String> searchTerms) {
		this.searchTerms = searchTerms;
	}
}
