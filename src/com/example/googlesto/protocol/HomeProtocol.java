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

public class HomeProtocol {
	String result = null;
	String json=null;
	public List<AppInfo> load(int index) {
		if (json == null) {
			loadServer(index);
		}
	    json = loadLocal(index);
		if (json != null) {
			return paserJson(json);
		} else {
			return null;
		}

	}

	/**
	 * 解析json数据 碰到大括号{}用JSONObject,碰到中括号[]用JSONArray
	 * 
	 * @param json
	 *            对象
	 */
	private List<AppInfo> paserJson(String json) {
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

	/**
	 * 把json数据保存到本地 1.把json文件保存到本地文件 2.把每个文件摘出来保存到数据库
	 * 
	 * @param json
	 */
	private void saveLocal(String json, int index) {
		FileWriter writer;
		BufferedWriter bw = null;
		try {
			File dir = FileUtis.getCacheDir();
			// 在第一行写一个过期时间
			File file = new File(dir, "home_" + index);
			writer = new FileWriter(file);
			bw = new BufferedWriter(writer);
			// 写入时间
			bw.write(System.currentTimeMillis() + 1000 * 100 + "");
			bw.newLine();// 换行
			bw.write(json);// 把json写入文件
			bw.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bw);
		}

	}

	/**
	 * 加载本地的json
	 * 
	 * @param index
	 *            索引
	 * @return json
	 */
	private String loadLocal(int index) {
		//  如果发现文件已经过期了 就不要再去复用缓存了
		File dir=FileUtis.getCacheDir();// 获取缓存所在的文件夹
		File file = new File(dir,"home_" + index); 
		try {
			FileReader fr=new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			long outOfDate = Long.parseLong(br.readLine());
//			if(System.currentTimeMillis()>outOfDate){
//				return null;
//			}else{
				String str=null;
				StringWriter sw=new StringWriter();
				while((str=br.readLine())!=null){
				
					sw.write(str);
				}
				return sw.toString();
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public int getIndex(int index) {
		return index;

	}

	/**
	 * 下载服务器的json数据
	 * 
	 * @return json数据
	 */
	private void loadServer(final int index) {
		String path = "http://127.0.0.1:8090/home?index=" + index;
		HttpUtils utils = new HttpUtils();
		// 使用xutils发送请求
		utils.send(HttpMethod.GET, path, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(BaseApplication.getApplication(), "下载错误",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				result = (String) responseInfo.result;
				saveLocal(result, getIndex(index));// 保存到本地
			}
		});
	}

}
