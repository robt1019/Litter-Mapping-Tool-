package org.littermappingtool.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The Achievement entity.
 */
@Entity
public class Achievement {
	
    /** The id. */
    @Id private Long id;

    /** The name. */
    @Index private String name;
    
    /** The description. */
    private String description;
    
    /** The value. */
    private int value;
    
    /**
     * Mandatory, no-args constructor.
     */
    public Achievement() {}

	/**
	 * Instantiates a new achievement.
	 *
	 * @param name the name
	 * @param description the description
	 * @param value the value
	 */
	public Achievement(String name, String description, int value) {
		this.name = name;
		this.description = description;
		this.value = value;
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
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
