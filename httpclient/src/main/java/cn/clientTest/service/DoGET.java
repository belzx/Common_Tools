package cn.clientTest.service;

import java.io.File;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DoGET {

	public static void main(String[] args) throws Exception {

		// ����Httpclient����
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// ����http GET����
		HttpGet httpGet = new HttpGet("http://127.0.0.1:8088/item/536563");
		CloseableHttpResponse response = null;
		try {
			// ִ������
			response = httpclient.execute(httpGet);
			// �жϷ���״̬�Ƿ�Ϊ200
			if (response.getStatusLine().getStatusCode() == 200) {
				// ��ȡ����˷��ص�����
				String content = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				File file = null;
//				System.out.println(new File("demo.txt").exists());
				if((file = new File("./src/main/java/demo.txt")).exists() ) {
					
					String data = FileUtils.readFileToString(file, "utf-8");
					JSONObject parse = JSON.parseObject(data);
//					JSONObject parse = JSON.parseObject(data);
//					System.out.println(parseArray);
					System.out.println(parse.getJSONObject("data").getJSONObject("feedbacks"));
				}
						
//				FileUtils.writeStringToFile(new File("E:\\baidu.html"), 
//						content, "UTF-8");
//				// ����˷������ݵĳ���
//				System.out.println("���ݳ��ȣ�" + content.length());
				System.out.println(content);
				JSONObject parse = JSON.parseObject(content);
				System.out.println(parse.get("title"));
			}
		} finally {
			if (response != null) {
				response.close();
			}
			// �൱�ڹر������
			httpclient.close();
		}

	}

}