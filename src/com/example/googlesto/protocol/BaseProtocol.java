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
	 * ���ط�������json����
	 * 
	 * @return json����
	 */
	private void loadServer(final int index) {
		String path = com.example.googlesto.globle.GlobalContants.URL+getKey()+"?index=" + index;
		HttpUtils utils = new HttpUtils();
		// ʹ��xutils��������
		utils.send(HttpMethod.GET, path, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(BaseApplication.getApplication(), "���ش���",
						Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = (String) responseInfo.result;
				saveLocal(result, getIndex(index));// ���浽����
			}
		});
	}
	/**
	 * ��json���ݱ��浽���� 1.��json�ļ����浽�����ļ� 2.��ÿ���ļ�ժ�������浽���ݿ�
	 * 
	 * @param json
	 */
	private void saveLocal(String json, int index) {
		FileWriter writer;
		BufferedWriter bw = null;
		try {
			File dir = FileUtis.getCacheDir();
			// �ڵ�һ��дһ������ʱ��
			File file = new File(dir, getKey()+"_" + index);
			writer = new FileWriter(file);
			bw = new BufferedWriter(writer);
			// д��ʱ��
			bw.write(System.currentTimeMillis() + 1000 * 100 + "");
			bw.newLine();// ����
			bw.write(json);// ��jsonд���ļ�
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
	 * ���ر��ص�json
	 * 
	 * @param index
	 *            ����
	 * @return json
	 */
	private String loadLocal(int index) {
		//  ��������ļ��Ѿ������� �Ͳ�Ҫ��ȥ���û�����
		File dir=FileUtis.getCacheDir();// ��ȡ�������ڵ��ļ���
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
	 * ����json
	 * @param json
	 * @return
	 */
	public abstract T paserJson(String json);
	/**
	 * ˵���˹ؼ���
	 * @return
	 */
	public abstract String getKey();
}
