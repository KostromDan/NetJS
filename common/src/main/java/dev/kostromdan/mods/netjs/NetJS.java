package dev.kostromdan.mods.netjs;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class NetJS {
	public static final String MOD_ID = "netjs";

	static { // bypass pastebin security shit.
		TrustManager[] trustAllCertificates = new TrustManager[]{
				new X509TrustManager() {
					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return null; // Not relevant.
					}

					@Override
					public void checkClientTrusted(X509Certificate[] certs, String authType) {
						// Do nothing. Just allow them all.
					}

					@Override
					public void checkServerTrusted(X509Certificate[] certs, String authType) {
						// Do nothing. Just allow them all.
					}
				}
		};

		HostnameVerifier trustAllHostnames = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true; // Just allow them all.
			}
		};

		try {
			System.setProperty("jsse.enableSNIExtension", "false");
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCertificates, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(trustAllHostnames);
		} catch (GeneralSecurityException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
}