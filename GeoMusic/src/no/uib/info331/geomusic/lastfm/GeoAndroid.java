package no.uib.info331.geomusic.lastfm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Caller;
import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.ResponseBuilder;
import de.umass.lastfm.Result;
import de.umass.util.MapUtilities;

public class GeoAndroid {

	/**
	 * This inner class represents a Metro, which is composed of its name and the name of its country.
	 *
	 * @see Geo#getMetros(String, String)
	 */
	public static class Metro {
		private String name;
		private String country;

		public Metro(String name, String country) {
			this.name = name;
			this.country = country;
		}

		public String getName() {
			return name;
		}

		public String getCountry() {
			return country;
		}
	}

	private GeoAndroid() {
	}

	/**
	 * 
	 * Get all events in a specific location by country or city name.<br/> This method returns <em>all</em> events by subsequently calling
	 * {@link #getEvents(String, String, int, String)} and concatenating the single results into one list.<br/> Pay attention if you use this
	 * method as it may produce a lot of network traffic and therefore may consume a long time.
	 *
	 * @param location Specifies a location to retrieve events for
	 * @param distance Find events within a specified radius (in kilometres)
	 * @param apiKey A Last.fm API key.
	 * @return a list containing all events
	 */
	public static Collection<Event> getAllEvents(String location, String distance, String apiKey) {
		Collection<Event> events = null;
		int page = 1, total;
		do {
			PaginatedResult<Event> result = getEvents(location, distance, page, apiKey);
			total = result.getTotalPages();
			Collection<Event> pageResults = result.getPageResults();
			if (events == null) {
				// events is initialized here to initialize it with the right size and avoid array copying later on
				events = new ArrayList<Event>(total * pageResults.size());
			}
			for (Event artist : pageResults) {
				events.add(artist);
			}
			page++;
		} while (page <= total);
		return events;
	}

	/**
	 * Get all events in a specific location by country or city name.<br/> This method only returns the first page of a possibly paginated
	 * result. To retrieve all pages get the total number of pages via {@link de.umass.lastfm.PaginatedResult#getTotalPages()} and subsequently
	 * call {@link #getEvents(String, String, int, String)} with the successive page numbers.
	 *
	 * @param location Specifies a location to retrieve events for
	 * @param distance Find events within a specified radius (in kilometres)
	 * @param apiKey A Last.fm API key.
	 * @return a {@link PaginatedResult} containing a list of events
	 */
	public static PaginatedResult<Event> getEvents(String location, String distance, String apiKey) {
		return getEvents(location, distance, 1, apiKey);
	}

	/**
	 * Get all events in a specific location by country or city name.<br/> This method only returns the specified page of a paginated result.
	 *
	 * @param location Specifies a location to retrieve events for
	 * @param distance Find events within a specified radius (in kilometres)
	 * @param page A page number for pagination
	 * @param apiKey A Last.fm API key.
	 * @return a {@link PaginatedResult} containing a list of events
	 */
	public static PaginatedResult<Event> getEvents(String location, String distance, int page, String apiKey) {
		return getEvents(location, distance, page, -1, apiKey);
	}

	public static PaginatedResult<Event> getEvents(String location, String distance, int page, int limit, String apiKey) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("page", String.valueOf(page));
		MapUtilities.nullSafePut(params, "location", location);
		MapUtilities.nullSafePut(params, "distance", distance);
		MapUtilities.nullSafePut(params, "limit", limit);
		Result result = Caller.getInstance().call("geo.getEvents", apiKey, params);
		return ResponseBuilder.buildPaginatedResult(result, Event.class);
	}

	/**
	 * Get all events in a specific location by latitude/longitude.<br/> This method only returns the specified page of a paginated result.
	 *
	 * @param latitude Latitude
	 * @param longitude Longitude
	 * @param page A page number for pagination
	 * @param apiKey A Last.fm API key.
	 * @return a {@link PaginatedResult} containing a list of events
	 */
	public static PaginatedResult<Event> getEvents(double latitude, double longitude, int page, String apiKey) {
		return getEvents(latitude, longitude, page, -1, apiKey);
	}

	public static PaginatedResult<Event> getEvents(double latitude, double longitude, int page, int limit, String apiKey) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("page", String.valueOf(page));
		params.put("lat", String.valueOf(latitude));
		params.put("long", String.valueOf(longitude));
		MapUtilities.nullSafePut(params, "limit", limit);
		Caller apiCaller = Caller.getInstance();
		apiCaller.setCache(null);
		Result result = apiCaller.call("geo.getEvents", apiKey, params); 
		return ResponseBuilder.buildPaginatedResult(result, Event.class);
	}

	public static PaginatedResult<Event> getEvents(double latitude, double longitude, String distance, String apiKey) {
		return getEvents(latitude, longitude, distance, -1, -1, apiKey);
	}

	/**
	 * Get all events within the specified distance of the location specified by latitude/longitude.<br/>
	 * This method only returns the specified page of a paginated result.
	 *
	 * @param latitude Latitude
	 * @param longitude Longitude
	 * @param distance Find events within a specified radius (in kilometres)
	 * @param page A page number for pagination
	 * @param limit The maximum number of items returned per page
	 * @param apiKey A Last.fm API key.
	 * @return a {@link PaginatedResult} containing a list of events
	 */
	public static PaginatedResult<Event> getEvents(double latitude, double longitude, String distance, int page, int limit, String apiKey) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("lat", String.valueOf(latitude));
		params.put("long", String.valueOf(longitude));
		params.put("distance", distance);
		MapUtilities.nullSafePut(params, "page", page);
		MapUtilities.nullSafePut(params, "limit", limit);
		Caller apiCaller = Caller.getInstance();
		apiCaller.setCache(null);
		Result result = apiCaller.call("geo.getEvents", apiKey, params);
		return ResponseBuilder.buildPaginatedResult(result, Event.class);
	}
}
