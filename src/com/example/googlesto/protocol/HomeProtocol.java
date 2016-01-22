package com.example.googlesto.protocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

import com.example.googlesto.BaseApplication;
import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.http.HttpHelper;
import com.example.googlesto.http.HttpHelper.HttpResult;
import com.example.googlesto.tools.FileUtis;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.http.client.entity.FileUploadEntity;
import com.lidroid.xutils.util.IOUtils;

public class HomeProtocol extends BaseProtocol<List<AppInfo>> {
	
	/**
	 * 解析json数据 碰到大括号{}用JSONObject,碰到中括号[]用JSONArray
	 * 
	 * @param json
	 *            对象
	 */
	public List<AppInfo> paserJson(String json) {
		List<AppInfo> list = new ArrayList<AppInfo>();
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				long id = jsonObject2.getLong("id");
				String name = jsonObject2.getString("name");
				String packageName = jsonObject2.getString("packageName");
				String iconUrl = jsonObject2.getString("iconUrl");
				float stars = Float.parseFloat(jsonObject2.getString("stars"));
				long size = jsonObject2.getLong("size");
				String downloadUrl = jsonObject2.getString("downloadUrl");
				String des = jsonObject2.getString("des");
				AppInfo appinfo = new AppInfo(id, name, packageName, iconUrl,
						stars, size, downloadUrl, des);
				list.add(appinfo);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

	
	@Override
	public String getKey() {
		return "home";
	}

}
