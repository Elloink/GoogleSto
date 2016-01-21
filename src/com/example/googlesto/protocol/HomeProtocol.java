package com.example.googlesto.protocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import android.widget.Toast;

import com.example.googlesto.BaseApplication;
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

	public void load(int index) {
		String json = loadLocal(index);
		if (json == null) {
			loadServer(index);
		}else {
			System.out.println("�����˱��ػ���");
		}
		if (json != null) {
			paserJson(json);
		}

	}

	/**
	 * ����json����
	 * 
	 * @param json
	 *            ����
	 */
	private void paserJson(String json) {

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
			File file = new File(dir, "home_" + index);
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

	/**
	 * ���ر��ص�json
	 * 
	 * @param index
	 *            ����
	 * @return json
	 */
	private String loadLocal(int index) {
		File dir = FileUtis.getCacheDir();
		// �ڵ�һ��дһ������ʱ��
		File file = new File(dir, "home_"+index);
		FileReader fr;
		BufferedReader bf;
		try {
			fr = new FileReader(file);
			 bf = new BufferedReader(fr);
			 long outofdate = Long.parseLong(bf.readLine());
			 if (System.currentTimeMillis()>outofdate) {
				return null;
			}else {
				String str=null;
				StringWriter sw = new StringWriter();
				while ((bf.readLine())!=null) {
					sw.write(str);
				}
				return sw.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	public int getIndex(int index) {
		return index;

	}

	/**
	 * ���ط�������json����
	 * 
	 * @return json����
	 */
	private void loadServer(final int index) {
		String path = "http://127.0.0.1:8090/home?index=" + index;
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
				result = (String) responseInfo.result;
				saveLocal(result, getIndex(index));//���浽����
			}
		});
	}

}
