package com.example.googlesto.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.googlesto.domin.AppInfo;

public class GameProtocol extends BaseProtocol<List<AppInfo>> {

	@Override
	public List<AppInfo> paserJson(String json) {
		List<AppInfo> list = new ArrayList<AppInfo>();
		try {
			JSONArray jsonArray=new JSONArray(json);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject object=jsonArray.getJSONObject(i);
				long id=object.getLong("id");
				String name=object.getString("name");
				String packageName=object.getString("packageName");
				String iconUrl = object.getString("iconUrl");
				float stars=Float.parseFloat(object.getString("stars"));
				long size=object.getLong("size");
				String downloadUrl = object.getString("downloadUrl");
				String des = object.getString("des");
				AppInfo info=new AppInfo(id, name, packageName, iconUrl, stars, size, downloadUrl, des);
				list.add(info);
			}
			return  list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getKey() {
		return "game";
	}

}
