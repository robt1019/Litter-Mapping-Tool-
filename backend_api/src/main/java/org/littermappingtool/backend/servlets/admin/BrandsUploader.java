package org.littermappingtool.backend.servlets.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.littermappingtool.backend.Brand;
import org.littermappingtool.backend.dao.PreparedQueries;

/**
 * Simple loader for brands.
 */

@SuppressWarnings("serial")
public class BrandsUploader extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String[] brandNames = { "ASDA", "Boots", "Burger King", "Cafe Nero",
				"Costa Coffee", "Domino's Pizza", "Greggs", "KFC", "McDonald's", "Marks & Spencer",
				"Morrisons", "Pizza Hut", "Pret a Manger", "Sainsbury's", "Starbucks", 
				"Subway", "Tesco", "Waitrose", "Boost", "Bourneville", "Buttons", "Cadbury Caramel",
				"Chomp", "Creme Egg/Twisted", "Crunchie", "Dairy Milk", "Double Decker", "Flake", "Freddo",
				"Fruit & Nut", "Fudge", "Halls", "Mini Rolls", "Picnic", "Roses", "Time Out",
				"Twirl", "Wishes", "Wispa", "Kinder Bueno", "Tic Tac", "Doritos", "Maoam",
				 "Discos", "Hula Hoops", "McCoys", "NikNaks", "Skips",  "Bounty", "Galaxy",
				"Galaxy Ripple", "M&M's", "Maltesers", "Mars Caramel", "Mars", "Milky Way", "Minstrels",
				"Skittles", "Snickers", "Starburst", "Topic", "Tracker", "Twix",
				 "Aero", "Breakaway", "Drifter", "Kit Kat", "Lion", "Milky Bar", "Mint/Bubbles",
				"Munchies", "Polo Mints", "Quality Street", "Rolo", "Smarties", "Toffee Crisp", "Yorkie",
				 "Drumsticks", "Love hearts", "Rainbow drops", "Refreshers",
				 "Extra Strong Mints", "Soft Mints",  "Frazzles", "French Fries", "Quavers", "Walkers", "Wotsits",
				"Bubblicious", "Dentyne", "Stimorol", "Trident", "Mentos", "Airwaves", "Big Red",
				"Extra", "Freedent", "Hubba Bubba", "Juicy Fruit", "Orbit", "Double Mint", "Wrigley's spearmint",
				"Drench", "Fruit Shoot", "J2O", "Tango", "Irn Bru", "Rubicon", "Coca-Cola", "Cherry Coke",
				"Diet Coke", "Dr Pepper", "Fanta", "Five Alive", "Lilt", "Oasis", "Powerade", "Relentless",
				"Sprite", "Coke Zero", "Emerge stimulation drink", "Volvic", "Evian", "V energy drink",
				"Lucozade", "Ribena", "Monster Energy", "Pepsi", "Mountain Dew", "Mountain Dew Energy",
				"Pepsi Max", "Diet Pepsi", "7Up", "Tropicana", "Red Bull", "Rockstar energy drink", "Schweppes lemonade",
				"Schweppes tonic water", "Capri Sun",
				"Becks", "Budweiser", "Carling", "Carlsberg", "Fosters", "Heineken", "Smirnoff",
				"Strongbow", "Stella", "Tennents", "WKD", "Cutters Choice", "Dunhill", "Lucky Strike",
				"Pall Mall", "Embassy", "Davidoff", "Drum", "Gold Leaf", "Golden Virginia", "John Players",
				"Lambert & Butler", "Regal", "Richmond", "Superkings", "Windsor Blue",
				"Amber Leaf", "Benson & Hedges", "Berkeley", "Camel", "Lucky Stripe", "Mayfair", "Old Holborn",
				"Silk Cut", "Sterling", "Winston",
				"Chesterfield", "Marlboro", "Rothman's" };

		for (String brandName : brandNames) {
			PreparedQueries.saveBrand(new Brand.Builder(brandName).build());
		}
	}
}