package ua.org.rent.settings;

/**
 * Created with IntelliJ IDEA. User: admin Date: 17.08.13 Time: 10:05 To change
 * this template use File | Settings | File Templates.
 */
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import android.content.Context;
import android.app.Application;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import ua.org.rent.R;

public class RentAppState extends Application {

	private static RentAppState sInstance;
	private static Context mContext;


	public static RentAppState getAppInstance() {
		if (sInstance == null) {
			throw new RuntimeException("The application has not been initialized.");
		}
		return sInstance;
	}

	@Override
	public void onCreate() {

		super.onCreate();
		sInstance = this;
		mContext = this.getApplicationContext();
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showStubImage(android.R.drawable.ic_menu_upload) // resource or drawable
        .showImageForEmptyUri(android.R.drawable.ic_delete) // resource or drawable
        .showImageOnFail(android.R.drawable.stat_notify_error) // resource or drawable
		.cacheOnDisc(true)
        .build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.defaultDisplayImageOptions(options)
				.build();
		
		ImageLoader.getInstance().init(config);
		trustEveryone();


	}

	public static Context getContext() {
		return mContext;
	}

	private void trustEveryone() {
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[]{new X509TrustManager() {
					@Override
					public void checkClientTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}

					@Override
					public void checkServerTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}

					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return new X509Certificate[0];
					}
				}
			}, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cleanUserData() {

		Log.d("SYNC", "Clean Up situation");
	}
}
