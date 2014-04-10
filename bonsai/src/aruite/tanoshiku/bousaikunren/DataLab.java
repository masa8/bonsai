package aruite.tanoshiku.bousaikunren;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;


import android.content.Context;
import android.widget.Toast;


public class DataLab {

	private static DataLab sDataLab;
	
	private HashMap<String, Data> mData;
	
	private Context mContext;
	
	public static DataLab get(Context c){
		
		if ( sDataLab == null ){
			sDataLab = new DataLab(c);
			
		}
		return  sDataLab;
	}
	
	private DataLab(Context c){
		mContext = c;
		try {
			mData = new HashMap<String,Data>();
			new JsonTask(c).execute(Data.URL);
		} catch (Exception e){
				e.printStackTrace();
		}
	}
	
	public HashMap<String,Data> getData(){
		return mData;
	}
	
	
	public void setData(JSONArray array){

		for (int i = 0; i < array.length(); i++) {
			try {
				mData.put(array.getJSONObject(i).getString(Data.JSON_URL), new Data(array.getJSONObject(i)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
