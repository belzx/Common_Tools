package cn.clientTest.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DoPOST {

	public static void main(String[] args) throws Exception {

		// ����Httpclient����
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// ����http POST����
		HttpPost httpPost = new HttpPost("http://image.baidu.com/search/index?tn=baiduimage&ct=201326592&lm=-1&cl=2&ie=gb18030&word=%CD%BC%C6%AC&fr=ala&ala=1&alatpl=others&pos=0");
		// αװ���������
		httpPost.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");

		CloseableHttpResponse response = null;
		try {
			// ִ������
			response = httpclient.execute(httpPost);
			// �жϷ���״̬�Ƿ�Ϊ200
			if (response.getStatusLine().getStatusCode() == 200) {
				// ��ȡ����ˣ���Ӧ������
				String content = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				System.out.println(content);
			}
		} finally {
			if (response != null) {
				response.close();
			}
			httpclient.close();
		}

	}

}