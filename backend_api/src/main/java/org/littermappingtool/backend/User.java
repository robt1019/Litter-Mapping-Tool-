package org.littermappingtool.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * The User entity.
 */
@Entity
public class User {
	
	/** The id. */
	@Id private Long id;
	
	/** The name. */
	private String name;
	
	/** The surname. */
	private String surname;
	
	/** The email. */
	private String email;
	
	/** The score. */
	private Integer score;
	
	/**
	 * Mandatory, no-args constructor.
     * ===
     * Use the provided Builder to initialize a new User object. 
	 */
	public User() {}

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
	 * Gets the surname.
	 *
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Sets the surname.
	 *
	 * @param surname the new surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * Sets the score.
	 *
	 * @param score the new score
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	
	/**
	 * The User Builder.
	 */
	public static class Builder {
		
		// Mandatory fields
		
		/** The email. */
		private String email;
		
		// Optional fields
		
		/** The name. */
		private String name;
		
		/** The surname. */
		private String surname;
		
		/** The score. */
		private Integer score;

		// Constructor & setters
		
		/**
		 * Instantiates a new builder.
		 *
		 * @param email the email
		 */
		public Builder(String email) {
			this.email = email;
		}		
		
		/**
		 * Sets the name.
		 *
		 * @param name the name
		 * @return the builder
		 */
		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		/**
		 * Sets the surname.
		 *
		 * @param surname the surname
		 * @return the builder
		 */
		public Builder setSurname(String surname) {
			this.surname = surname;
			return this;
		}

		/**
		 * Sets the score.
		 *
		 * @param score the score
		 * @return the builder
		 */
		public Builder setScore(Integer score) {
			this.score = score;
			return this;
		}
		
		/**
		 * Builds the.
		 *
		 * @return the user
		 */
		public User build() {
			return new User(this);
		}
	}
	
	/**
	 * Instantiates a new user.
	 *
	 * @param builder the builder
	 */
	private User(Builder builder) {
		this.name = builder.name;
		this.surname = builder.surname;
		this.email = builder.email;
		this.score = builder.score;
	}
}
