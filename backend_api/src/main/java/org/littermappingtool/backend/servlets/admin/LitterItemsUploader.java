package org.littermappingtool.backend.servlets.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.littermappingtool.backend.Event;
import org.littermappingtool.backend.Litter;
import org.littermappingtool.backend.dao.PreparedQueries;
import org.littermappingtool.backend.utils.DateTimeUtils;

import com.google.appengine.api.search.GeoPoint;

/**
 * Simple loader for litter items
 */
@SuppressWarnings("serial")
public class LitterItemsUploader extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String[] litterTypes = { "plastic", "bottle", "glass", "bag" };

		String[] brands = { "ASDA", "Boots", "Burger King", "Cafe Nero",
				"Costa Coffee", "Domino's Pizza", "Greggs", "KFC", "McDonald's", "Marks & Spencer",
				"Morrisons", "Pizza Hut", "Pret a Manger", "Sainsbury's", "Starbucks", 
				"Subway", "Tesco", "Waitrose" };

		// EVENT-1: Bristol (Brandon Hill)

		List<GeoPoint> pointsEvent1 = new ArrayList<>();

		pointsEvent1.add(new GeoPoint(51.4518190, -2.6078984));
		pointsEvent1.add(new GeoPoint(51.4518223, -2.6078528));
		pointsEvent1.add(new GeoPoint(51.4518558, -2.6077884));
		pointsEvent1.add(new GeoPoint(51.4517755, -2.6077965));
		pointsEvent1.add(new GeoPoint(51.4518190, -2.6076356));
		pointsEvent1.add(new GeoPoint(51.4519243, -2.6076221));
		pointsEvent1.add(new GeoPoint(51.4519460, -2.6075444));
		pointsEvent1.add(new GeoPoint(51.4518792, -2.6075122));
		pointsEvent1.add(new GeoPoint(51.4519143, -2.6073888));
		pointsEvent1.add(new GeoPoint(51.4520112, -2.6077697));
		pointsEvent1.add(new GeoPoint(51.4519644, -2.6073754));
		pointsEvent1.add(new GeoPoint(51.4520731, -2.6074237));
		pointsEvent1.add(new GeoPoint(51.4521516, -2.6075926));
		pointsEvent1.add(new GeoPoint(51.4521499, -2.6077804));
		pointsEvent1.add(new GeoPoint(51.4522536, -2.6078796));
		pointsEvent1.add(new GeoPoint(51.4522519, -2.6076463));
		pointsEvent1.add(new GeoPoint(51.4523388, -2.6078823));
		pointsEvent1.add(new GeoPoint(51.4523138, -2.6080647));
		pointsEvent1.add(new GeoPoint(51.4524725, -2.6082498));
		pointsEvent1.add(new GeoPoint(51.4523940, -2.6074907));
		pointsEvent1.add(new GeoPoint(51.4522118, -2.6071125));
		pointsEvent1.add(new GeoPoint(51.4522619, -2.6068926));
		pointsEvent1.add(new GeoPoint(51.4523806, -2.6069784));
		pointsEvent1.add(new GeoPoint(51.4524291, -2.6068470));
		pointsEvent1.add(new GeoPoint(51.4525528, -2.6069489));
		pointsEvent1.add(new GeoPoint(51.4524224, -2.6066646));
		pointsEvent1.add(new GeoPoint(51.4525260, -2.6067317));
		pointsEvent1.add(new GeoPoint(51.4519945, -2.6072037));
		pointsEvent1.add(new GeoPoint(51.4519778, -2.6071045));
		pointsEvent1.add(new GeoPoint(51.4519026, -2.6070817));
		pointsEvent1.add(new GeoPoint(51.4519393, -2.6069663));
		pointsEvent1.add(new GeoPoint(51.4519711, -2.6067705));
		pointsEvent1.add(new GeoPoint(51.4520421, -2.6068604));
		pointsEvent1.add(new GeoPoint(51.4520187, -2.6069731));
		pointsEvent1.add(new GeoPoint(51.4519686, -2.6067437));
		pointsEvent1.add(new GeoPoint(51.4518650, -2.6064889));
		pointsEvent1.add(new GeoPoint(51.4518432, -2.6066431));
		pointsEvent1.add(new GeoPoint(51.4520304, -2.6067196));
		pointsEvent1.add(new GeoPoint(51.4519419, -2.6062502));
		pointsEvent1.add(new GeoPoint(51.4519184, -2.6061241));
		pointsEvent1.add(new GeoPoint(51.4517312, -2.6061000));
		pointsEvent1.add(new GeoPoint(51.4516853, -2.6061577));
		pointsEvent1.add(new GeoPoint(51.4516017, -2.6059699));
		
		Event event1 = new Event.Builder(DateTimeUtils.subtractDays(new Date(), 3).getTime(), 51.4524224, -2.6066646, "Event1").setLocation("Brandon Hill").setCity("Bristol").setCountry("United Kingdom").build();
		PreparedQueries.saveEvent(event1);		

		for (GeoPoint point : pointsEvent1) {			
			String litterType = litterTypes[new Random().nextInt(litterTypes.length - 1) + 1];
			String brand = brands[new Random().nextInt(brands.length - 1) + 1];
			PreparedQueries.saveLitter(new Litter.Builder(litterType, brand, "andrej@email.com", point.getLatitude(), point.getLongitude(), DateTimeUtils.subtractDays(new Date(), 3).getTime(), event1.getId()).build());
		}
		
		// EVENT-2: Bristol (College Green)

		List<GeoPoint> pointsEvent2 = new ArrayList<>();

		pointsEvent2.add(new GeoPoint(51.451978, -2.601484));
		pointsEvent2.add(new GeoPoint(51.452023, -2.601122));
		pointsEvent2.add(new GeoPoint(51.452228, -2.601551));
		pointsEvent2.add(new GeoPoint(51.452267, -2.601028));
		pointsEvent2.add(new GeoPoint(51.452160, -2.600795));
		pointsEvent2.add(new GeoPoint(51.452176, -2.600749));
		pointsEvent2.add(new GeoPoint(51.452160, -2.600733));
		pointsEvent2.add(new GeoPoint(51.452176, -2.600636));
		pointsEvent2.add(new GeoPoint(51.452260, -2.600652));
		pointsEvent2.add(new GeoPoint(51.452253, -2.600569));
		pointsEvent2.add(new GeoPoint(51.452181, -2.600532));
		pointsEvent2.add(new GeoPoint(51.452212, -2.600459));
		pointsEvent2.add(new GeoPoint(51.452243, -2.600239));
		pointsEvent2.add(new GeoPoint(51.452303, -2.600164));
		pointsEvent2.add(new GeoPoint(51.452063, -2.600738));
		pointsEvent2.add(new GeoPoint(51.452038, -2.600714));
		pointsEvent2.add(new GeoPoint(51.452024, -2.600636));
		pointsEvent2.add(new GeoPoint(51.452069, -2.600647));
		pointsEvent2.add(new GeoPoint(51.452078, -2.600669));
		pointsEvent2.add(new GeoPoint(51.452096, -2.600612));
		pointsEvent2.add(new GeoPoint(51.452061, -2.600583));
		pointsEvent2.add(new GeoPoint(51.452078, -2.600542));
		pointsEvent2.add(new GeoPoint(51.452053, -2.600526));
		pointsEvent2.add(new GeoPoint(51.452084, -2.600481));
		pointsEvent2.add(new GeoPoint(51.452130, -2.600435));
		pointsEvent2.add(new GeoPoint(51.452121, -2.600416));
		pointsEvent2.add(new GeoPoint(51.452131, -2.600301));
		pointsEvent2.add(new GeoPoint(51.452166, -2.600135));
		pointsEvent2.add(new GeoPoint(51.452377, -2.599909));
		pointsEvent2.add(new GeoPoint(51.452402, -2.600036));
		pointsEvent2.add(new GeoPoint(51.452425, -2.600226));
		pointsEvent2.add(new GeoPoint(51.452553, -2.600314));
		pointsEvent2.add(new GeoPoint(51.452588, -2.600551));
		pointsEvent2.add(new GeoPoint(51.452678, -2.600604));
		pointsEvent2.add(new GeoPoint(51.452720, -2.600685));
		pointsEvent2.add(new GeoPoint(51.452755, -2.600741));
		pointsEvent2.add(new GeoPoint(51.452755, -2.600778));
		pointsEvent2.add(new GeoPoint(51.452817, -2.600870));
		pointsEvent2.add(new GeoPoint(51.452636, -2.600703));
		pointsEvent2.add(new GeoPoint(51.452643, -2.600725));
		pointsEvent2.add(new GeoPoint(51.452621, -2.600749));
		pointsEvent2.add(new GeoPoint(51.452613, -2.600795));
		pointsEvent2.add(new GeoPoint(51.452654, -2.600821));
		pointsEvent2.add(new GeoPoint(51.452688, -2.600827));
		pointsEvent2.add(new GeoPoint(51.452646, -2.600888));
		pointsEvent2.add(new GeoPoint(51.452649, -2.600937));
		pointsEvent2.add(new GeoPoint(51.452678, -2.600966));
		pointsEvent2.add(new GeoPoint(51.452723, -2.600907));
		pointsEvent2.add(new GeoPoint(51.452678, -2.600966));
		pointsEvent2.add(new GeoPoint(51.452701, -2.600974));

		Event event2 = new Event.Builder(DateTimeUtils.subtractDays(new Date(), 1).getTime(), 51.452755, -2.600778, "Event2").setLocation("College Green").setCity("Bristol").setCountry("United Kingdom").build();
		PreparedQueries.saveEvent(event2);
		
		for (GeoPoint point : pointsEvent2) {
			String litterType = litterTypes[new Random().nextInt(litterTypes.length - 1)];
			String brand = brands[new Random().nextInt(brands.length - 1)];
			Litter litter = new Litter.Builder(litterType, brand, "andrej@email.com", point.getLatitude(), point.getLongitude(), DateTimeUtils.subtractDays(new Date(), 1).getTime(), event2.getId()).build();
			PreparedQueries.saveLitter(litter);
		}
		
		// EVENT-3: Chippenham
		
		List<GeoPoint> pointsEvent3 = new ArrayList<>();
		
		pointsEvent3.add(new GeoPoint(51.459511, -2.111666));
		pointsEvent3.add(new GeoPoint(51.459358, -2.111440));
		pointsEvent3.add(new GeoPoint(51.459585, -2.110807));

		Event event3 = new Event.Builder(DateTimeUtils.subtractDays(new Date(), 7).getTime(), 51.460019, -2.111719, "Event3").setCity("Chippenham").setCountry("United Kingdom").build();
		PreparedQueries.saveEvent(event3);
		
		for (GeoPoint point : pointsEvent3) {
			String litterType = litterTypes[new Random().nextInt(litterTypes.length - 1) + 1];
			String brand = brands[new Random().nextInt(brands.length - 1) + 1];
			Litter litter = new Litter.Builder(litterType, brand, "andrej@email.com", point.getLatitude(), point.getLongitude(), DateTimeUtils.subtractDays(new Date(), 7).getTime(), event3.getId()).build();
			PreparedQueries.saveLitter(litter);
		}
	}
}