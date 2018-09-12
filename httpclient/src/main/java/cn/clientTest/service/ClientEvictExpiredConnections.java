package cn.clientTest.service;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

//�ر����ӳص���Ч����
public class ClientEvictExpiredConnections {

	public static void main(String[] args) throws Exception {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		// �������������
		cm.setMaxTotal(200);
		// ����ÿ��������ַ�Ĳ�����
		cm.setDefaultMaxPerRoute(20);

		new IdleConnectionEvictor(cm).start();
	}

	public static class IdleConnectionEvictor extends Thread {

		private final HttpClientConnectionManager connMgr;

		private volatile boolean shutdown;

		public IdleConnectionEvictor(HttpClientConnectionManager connMgr) {
			this.connMgr = connMgr;
		}

		@Override
		public void run() {
			try {
				while (!shutdown) {
					synchronized (this) {
						// ÿ��5��ִ��һ�����ر�ʧЧ��http����
						wait(5000);
						// �ر�ʧЧ������
						connMgr.closeExpiredConnections();
					}
				}
			} catch (InterruptedException ex) {
				// ����
			}
		}

		public void shutdown() {
			shutdown = true;
			synchronized (this) {
				notifyAll();
			}
		}
	}

}

