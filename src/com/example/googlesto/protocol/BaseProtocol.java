package com.example.googlesto.protocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import android.os.SystemClock;
import android.widget.Toast;

import com.example.googlesto.BaseApplication;
import com.example.googlesto.domin.AppInfo;
import com.example.googlesto.tools.FileUtis;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.IOUtils;

public abstract class BaseProtocol<T> {
	String json=null;
	
	public T load(int index) {
	SystemClock.sleep(1500);	
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
	 * 下载服务器的json数据
	 * 
	 * @return json数据
	 */
	private void loadServer(final int index) {
		String path = com.example.googlesto.globle.GlobalContants.URL+getKey()+"?index=" + index;
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
				String result = (String) responseInfo.result;
				saveLocal(result, getIndex(index));// 保存到本地
			}
		});
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
			File file = new File(dir, getKey()+"_" + index);
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

	public int getIndex(int index) {
		return index;

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
		File file = new File(dir,getKey()+"_" + index); 
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
	/**
	 * 解析json
	 * @param json
	 * @return
	 */
	public abstract T paserJson(String json);
	/**
	 * 说明了关键字
	 * @return
	 */
	public abstract String getKey();
}
