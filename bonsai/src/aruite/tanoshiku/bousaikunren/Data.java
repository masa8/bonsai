package aruite.tanoshiku.bousaikunren;


import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Model
 * @author kevin
 *
 */
public class Data {
	
	public static final String URL = "http://totsuka-bousai.herokuapp.com/reports.json";
	
	public static final String JSON_TITLE = "title";
	public static final String JSON_LAT = "lat";
	public static final String JSON_LNG = "lng";
	public static final String JSON_DESCRIPTION = "description";
	public static final String JSON_URL = "url";
	
	public static final String KYOTEN = "Kyoten";
	public static final String LIQUEFY = "Liquefy";
	
	public String title;
	public String lat;
	public String lng;
	public String description;
	public String url;
	
	public Data(JSONObject json) throws JSONException {
		title 	 = json.getString(JSON_TITLE);
		lat    = json.getString(JSON_LAT);
		lng    = json.getString(JSON_LNG);
		description    = json.getString(JSON_DESCRIPTION);
		url 	= json.getString(JSON_URL);
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_TITLE, title);
		json.put(JSON_LAT, lat);
		json.put(JSON_LNG, lng);
		json.put(JSON_DESCRIPTION, description);
		json.put(JSON_URL, url);
		return json;
	}
}