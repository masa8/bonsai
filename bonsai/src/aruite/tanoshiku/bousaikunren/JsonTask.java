package aruite.tanoshiku.bousaikunren;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;

public class JsonTask extends AsyncTask<String, Void, JSONArray> {

	Context mContext;
	public JsonTask(Context context){
		mContext = context;
	}
	
	@Override
	protected JSONArray doInBackground(String... url) {
		try {
			return new JSONSerializer().loadData(url[0]);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(JSONArray array) {
		
		if ( array == null ){
			return;
		}
		DataLab.get(mContext).setData(array);
				
		

	}

	
}
