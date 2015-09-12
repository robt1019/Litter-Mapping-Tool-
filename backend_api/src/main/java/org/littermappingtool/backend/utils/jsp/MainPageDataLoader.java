package org.littermappingtool.backend.utils.jsp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.littermappingtool.backend.Litter;
import org.littermappingtool.backend.dao.PreparedQueries;

/**
 * Data loader for the home.jsp page.
 */
public class MainPageDataLoader {

	/** The Constant POINTS_NEWBIE. */
	private static final int POINTS_NEWBIE = 50;

	/** The Constant POINTS_WEEKENDER. */
	private static final int POINTS_WEEKENDER = 100;

	/** The Constant POINTS_REGULAR. */
	private static final int POINTS_REGULAR = 150;

	/** The Constant POINTS_CONTRIBUTOR. */
	private static final int POINTS_CONTRIBUTOR = 200;

	/** The total upcoming events. */
	private int totalUpcomingEvents;

	/** The leader board. */
	private List<User> leaderBoard;

	/** The current user board position. */
	private Integer currentUserBoardPosition;

	/** The current user score. */
	private Integer currentUserScore;

	/** The current user total litters. */
	private Integer currentUserTotalLitters;

	/** The current user email. */
	private String currentUserEmail;

	/** The current user weekender. */
	private boolean currentUserWeekender = false;

	/** The current user regular. */
	private boolean currentUserRegular = false;

	/** The current user total achievements. */
	private Integer currentUserTotalAchievements = 0;

	/**
	 * Instantiates a new main page data loader.
	 */
	public MainPageDataLoader(String currentUserEmail) {
		this.currentUserEmail = currentUserEmail;
		process();
	}

	/**
	 * Gets the total upcoming events.
	 *
	 * @return the total upcoming events
	 */
	public int getTotalUpcomingEvents() {
		return totalUpcomingEvents;
	}

	/**
	 * Sets the total upcoming events.
	 *
	 * @param totalUpcomingEvents the new total upcoming events
	 */
	public void setTotalUpcomingEvents(int totalUpcomingEvents) {
		this.totalUpcomingEvents = totalUpcomingEvents;
	}

	/**
	 * Gets the leader board.
	 *
	 * @return the leader board
	 */
	public List<User> getLeaderBoard() {
		return leaderBoard;
	}

	/**
	 * Sets the leader board.
	 *
	 * @param leaderBoard the new leader board
	 */
	public void setLeaderBoard(List<User> leaderBoard) {
		this.leaderBoard = leaderBoard;
	}

	/**
	 * Gets the current user board position.
	 *
	 * @return the current user board position
	 */
	public Integer getCurrentUserBoardPosition() {
		return currentUserBoardPosition;
	}

	/**
	 * Sets the current user board position.
	 *
	 * @param currentUserBoardPosition the new current user board position
	 */
	public void setCurrentUserBoardPosition(Integer currentUserBoardPosition) {
		this.currentUserBoardPosition = currentUserBoardPosition;
	}

	/**
	 * Gets the current user score.
	 *
	 * @return the current user score
	 */
	public Integer getCurrentUserScore() {
		return currentUserScore;
	}

	/**
	 * Sets the current user score.
	 *
	 * @param currentUserScore the new current user score
	 */
	public void setCurrentUserScore(Integer currentUserScore) {
		this.currentUserScore = currentUserScore;
	}

	/**
	 * Checks if is current user weekender.
	 *
	 * @return true, if is current user weekender
	 */
	public boolean isCurrentUserWeekender() {
		return currentUserWeekender;
	}

	/**
	 * Sets the current user weekender.
	 *
	 * @param currentUserWeekender the new current user weekender
	 */
	public void setCurrentUserWeekender(boolean currentUserWeekender) {
		this.currentUserWeekender = currentUserWeekender;
	}

	/**
	 * Checks if is current user regular.
	 *
	 * @return true, if is current user regular
	 */
	public boolean isCurrentUserRegular() {
		return currentUserRegular;
	}

	/**
	 * Sets the current user regular.
	 *
	 * @param currentUserRegular the new current user regular
	 */
	public void setCurrentUserRegular(boolean currentUserRegular) {
		this.currentUserRegular = currentUserRegular;
	}

	/**
	 * Gets the current user total achievements.
	 *
	 * @return the current user total achievements
	 */
	public Integer getCurrentUserTotalAchievements() {
		return currentUserTotalAchievements;
	}

	/**
	 * Sets the current user total achievements.
	 *
	 * @param currentUserTotalAchievements the new current user total achievements
	 */
	public void setCurrentUserTotalAchievements(Integer currentUserTotalAchievements) {
		this.currentUserTotalAchievements = currentUserTotalAchievements;
	}

	/**
	 * Gets the current user total litters.
	 *
	 * @return the current user total litters
	 */
	public Integer getCurrentUserTotalLitters() {
		return currentUserTotalLitters;
	}

	/**
	 * Sets the current user total litters.
	 *
	 * @param currentUserTotalLitters the new current user total litters
	 */
	public void setCurrentUserTotalLitters(Integer currentUserTotalLitters) {
		this.currentUserTotalLitters = currentUserTotalLitters;
	}

	/**
	 * Gets the current user email.
	 *
	 * @return the current user email
	 */
	public String getCurrentUserEmail() {
		return currentUserEmail;
	}

	/**
	 * Sets the current user email.
	 *
	 * @param currentUserEmail the new current user email
	 */
	public void setCurrentUserEmail(String currentUserEmail) {
		this.currentUserEmail = currentUserEmail;
	}

	/**
	 * Process.
	 */
	private void process() {
		totalUpcomingEvents = PreparedQueries.countAllUpcomingEvents();
		List<Litter> litters = PreparedQueries.getAllLitters();

		Map<String, User> map = new HashMap<String, User>();
		for (Litter litter : litters) {
			String userEmail = litter.getUserEmail();
			if (!map.containsKey(userEmail)) {
				User user = new User(userEmail.substring(0, userEmail.indexOf("@")), userEmail, 1);
				if (isWeekend(new Date(litter.getCreated()))) {
					user.setWeekenderAchievementUnlocked(true);
				}
				if (litter.getEventId() != null) {
					user.getUserEvents().add(litter.getEventId());
				}
				map.put(userEmail, user);
			} else {
				User user = map.get(userEmail);
				if (isWeekend(new Date(litter.getCreated()))) {
					user.setWeekenderAchievementUnlocked(true);
				}
				if (litter.getEventId() != null) {
					user.getUserEvents().add(litter.getEventId());
				}
				user.setPoints(user.getPoints() + 1);
				map.put(userEmail, user);
			}
		}

		// Loop each user to set total score / achievements for current user
		leaderBoard = new ArrayList<User>();
		currentUserBoardPosition = 0;
		currentUserScore = 0;

		for (User user : map.values()) {

			int points = user.getPoints();

			if (user.getPoints() >= 10) {
				points += POINTS_NEWBIE;
				user.setNewbieAchievementUnlocked(true);
			}
			if (user.isWeekenderAchievementUnlocked()) {
				points += POINTS_WEEKENDER;
			}
			if (user.getUserEvents().size() >= 3) {
				points += POINTS_REGULAR;
				user.setRegularAchievementUnlocked(true);
			}
			if (user.getPoints() >= 200) {
				points += POINTS_CONTRIBUTOR;
				user.setContributorAchievementUnlocked(true);
			}

			if (user.getEmail().equals(currentUserEmail)) {
				currentUserTotalLitters = user.getPoints();
				currentUserScore = points;
				if (user.isNewbieAchievementUnlocked()) {
					currentUserTotalAchievements++;
				}
				if (user.isWeekenderAchievementUnlocked()) {
					setCurrentUserWeekender(true);
					currentUserTotalAchievements++;
				}
				if (user.isRegularAchievementUnlocked()) {
					setCurrentUserRegular(true);
					currentUserTotalAchievements++;
				}
				if (user.isContributorAchievementUnlocked()) {
					currentUserTotalAchievements++;
				}
			}

			user.setTotalPoints(points);
			leaderBoard.add(user);
		}

		// Sort the leader board by points
		Collections.sort(leaderBoard, new UsersComparator());

		int position = 0;
		for (User user : leaderBoard) {
			position++;
			if (user.getEmail().equals(currentUserEmail)) {				
				currentUserBoardPosition = position;
			}
		}
	}

	/**
	 * Checks if is weekend.
	 *
	 * @param date the date
	 * @return true, if is weekend
	 */
	private boolean isWeekend(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The Class User.
	 */
	public class User {

		/** The user name. */
		private String userName;

		/** The email. */
		private String email;

		/** The points. */
		private int points;

		/** The total points. */
		private int totalPoints;

		/** The newbie achievement unlocked. */
		private boolean newbieAchievementUnlocked = false;

		/** The weekender achievement unlocked. */
		private boolean weekenderAchievementUnlocked = false;

		/** The regular achievement unlocked. */
		private boolean regularAchievementUnlocked = false;

		/** The contributor achievement unlocked. */
		private boolean contributorAchievementUnlocked = false;

		/** The users events. */
		private Set<Long> userEvents = new HashSet<Long>();

		/**
		 * Instantiates a new user.
		 *
		 * @param userName the user name
		 * @param email the email
		 * @param points the points
		 */
		public User(String userName, String email, int points) {
			this.userName = userName;
			this.email = email;
			this.points = points;
		}

		/**
		 * Gets the user name.
		 *
		 * @return the user name
		 */
		public String getUserName() {
			return userName;
		}

		/**
		 * Sets the user name.
		 *
		 * @param userName the new user name
		 */
		public void setUserName(String userName) {
			this.userName = userName;
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
		 * Gets the points.
		 *
		 * @return the points
		 */
		public int getPoints() {
			return points;
		}

		/**
		 * Sets the points.
		 *
		 * @param points the new points
		 */
		public void setPoints(int points) {
			this.points = points;
		}

		/**
		 * Gets the total points.
		 *
		 * @return the total points
		 */
		public int getTotalPoints() {
			return totalPoints;
		}

		/**
		 * Sets the total points.
		 *
		 * @param totalPoints the new total points
		 */
		public void setTotalPoints(int totalPoints) {
			this.totalPoints = totalPoints;
		}

		/**
		 * Checks if is newbie achievement unlocked.
		 *
		 * @return true, if is newbie achievement unlocked
		 */
		public boolean isNewbieAchievementUnlocked() {
			return newbieAchievementUnlocked;
		}

		/**
		 * Sets the newbie achievement unlocked.
		 *
		 * @param newbieAchievementUnlocked the new newbie achievement unlocked
		 */
		public void setNewbieAchievementUnlocked(boolean newbieAchievementUnlocked) {
			this.newbieAchievementUnlocked = newbieAchievementUnlocked;
		}

		/**
		 * Checks if is weekender achievement unlocked.
		 *
		 * @return true, if is weekender achievement unlocked
		 */
		public boolean isWeekenderAchievementUnlocked() {
			return weekenderAchievementUnlocked;
		}

		/**
		 * Sets the weekender achievement unlocked.
		 *
		 * @param weekenderAchievementUnlocked the new weekender achievement unlocked
		 */
		public void setWeekenderAchievementUnlocked(boolean weekenderAchievementUnlocked) {
			this.weekenderAchievementUnlocked = weekenderAchievementUnlocked;
		}

		/**
		 * Checks if is regular achievement unlocked.
		 *
		 * @return true, if is regular achievement unlocked
		 */
		public boolean isRegularAchievementUnlocked() {
			return regularAchievementUnlocked;
		}

		/**
		 * Sets the regular achievement unlocked.
		 *
		 * @param regularAchievementUnlocked the new regular achievement unlocked
		 */
		public void setRegularAchievementUnlocked(boolean regularAchievementUnlocked) {
			this.regularAchievementUnlocked = regularAchievementUnlocked;
		}

		/**
		 * Checks if is contributor achievement unlocked.
		 *
		 * @return true, if is contributor achievement unlocked
		 */
		public boolean isContributorAchievementUnlocked() {
			return contributorAchievementUnlocked;
		}

		/**
		 * Sets the contributor achievement unlocked.
		 *
		 * @param contributorAchievementUnlocked the new contributor achievement unlocked
		 */
		public void setContributorAchievementUnlocked(boolean contributorAchievementUnlocked) {
			this.contributorAchievementUnlocked = contributorAchievementUnlocked;
		}

		/**
		 * Gets the user events.
		 *
		 * @return the users events
		 */
		public Set<Long> getUserEvents() {
			return userEvents;
		}

		/**
		 * Sets the user events.
		 *
		 * @param userEvents the new users events
		 */
		public void setUserEvents(Set<Long> userEvents) {
			this.userEvents = userEvents;
		}
	}

	/**
	 * The Users Comparator.
	 */
	class UsersComparator implements Comparator<User> {

		@Override
		public int compare(User a, User b) {
			if (a.totalPoints >= b.totalPoints) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}
