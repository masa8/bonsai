package aruite.tanoshiku.bousaikunren;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.widget.Toast;

class JSONSerializer {

	public JSONSerializer() {
	}

	public JSONArray loadData(String str) throws IOException, JSONException {
		
		JSONArray array = null;
		
		try {
			HttpClient client = new DefaultHttpClient();
			StringBuilder uri = new StringBuilder(str);
			HttpGet httpget = new HttpGet(uri.toString());
			
			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			
			BufferedReader reader = null;
			try {

				reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder builder = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}

				array = (JSONArray) new JSONTokener(
						builder.toString()).nextValue();
			
			} catch (Exception e) {
			} finally {
				if (reader != null) {
					reader.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return array;
	}

}