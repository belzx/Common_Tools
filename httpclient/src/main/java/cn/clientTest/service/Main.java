package cn.clientTest.service;
//���ߣ�������
//���ӣ�https://www.zhihu.com/question/30626103/answer/83157368
//��Դ��֪��
//����Ȩ���������С���ҵת������ϵ���߻����Ȩ������ҵת����ע��������

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStreamImpl;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;

public class Main
{
	static String SendGet(String url)
	{
		// ����һ���ַ��������洢��ҳ����
		String result = "";
		// ����һ�������ַ�������
		BufferedReader in = null;
		try
		{
			// ��stringת��url����
			URL realUrl = new URL(url);
			// ��ʼ��һ�����ӵ��Ǹ�url������
			URLConnection connection = realUrl.openConnection();
			// ��ʼʵ�ʵ�����
			connection.connect();
			// ��ʼ�� BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			// ������ʱ�洢ץȡ����ÿһ�е�����
			String line;
			while ((line = in.readLine()) != null)
			{
				// ����ץȡ����ÿһ�в�����洢��result����
				result += line;
			}
		} catch (Exception e)
		{
			System.out.println("����GET��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally���ر�������
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
		return result;
	}

	static String RegexString(String targetStr, String patternStr)
	{
		// ����һ����ʽģ�壬����ʹ��������ʽ����������Ҫץ������
		// �൱�����������ƥ��ĵط��ͻ����ȥ
		Pattern pattern = Pattern.compile(patternStr);
		// ����һ��matcher������ƥ��
		Matcher matcher = pattern.matcher(targetStr);
		// ����ҵ���
		if (matcher.find())
		{
			// ��ӡ�����
			
			String string = matcher.group();
			return "http:"+string.substring(string.indexOf("=")+1).trim();
		}
		return "Nothing";
	}

	public static void main(String[] args)
	{
//		System.out.println(RegexString());
		// ���弴�����ʵ�����
		String url = "http://www.baidu.com";
		// �������Ӳ���ȡҳ������
		String result = SendGet(url);
		System.out.println(result);
		//src=//www.baidu.com/img/gs.gif
		// ʹ������ƥ��ͼƬ��src����
		String imgSrc = RegexString(result, "src=//.+?\\s");
		// ��ӡ���
		System.out.println(imgSrc);
		//����
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(imgSrc);
		// αװ���������
		httpPost.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpPost);
			if(response != null && response.getStatusLine().getStatusCode() == 200) {
				File file = new File("C:\\Users\\lx\\Desktop\\baidu.png");
				if(!file.exists()) {
					file.createNewFile();
				}
				FileImageOutputStream imageOutput = new FileImageOutputStream(file);
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				byte[] bytes = new byte[1024*8];
				int length = 0;
				while((length = content.read(bytes)) >=0) {
					imageOutput.write(bytes, 0, length);
				}
			}else {
				throw new RuntimeException();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}