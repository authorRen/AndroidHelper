package com.rex.app_library.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.rex.app_library.base.BaseApplication;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 网络连接工具类
 *
 * Created by renzeqiang
 * on 2019/5/8
 */
public class NetworkUtils {

    private NetworkUtils() {
        throw new UnsupportedOperationException("不允许创建NetworkUtils构造器");
    }

    public static final int NETWORK_WIFI = 1;   //wifi network
    public static final int NETWORK_4G = 4;     //"4G" network
    public static final int NETWORK_3G = 3;     //"3G" network
    public static final int NETWORK_2G = 2;     //"2G" network
    public static final int NETWORK_UNKNOWN = 5;//unknown network
    public static final int NETWORK_NO = -1;    //no network

    private static final int NETWORK_TYPE_GSM = 16;
    private static final int NETWORK_TYPE_TD_SCDMA = 17;
    private static final int NETWORK_TYPE_IWLAN = 18;

    //除了判断是否有网络，还写工具方法判断网络为什么类型 2G、3G、4G、wifi此处忽略
    public static boolean checkNetworkState(Context context) {
        //得到网络信息
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            //当前网络是连接的
            return true;
        } else {
            return false;
        }
    }

    /**
     * 打开网络设置界面
     */
    public static void openWirelessSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

    /**
     * 获取活动网络信息
     * 需要权限
     * <p>{@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     */
    public static NetworkInfo getActivityNetworkInfo(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo();
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isConnected() {
        NetworkInfo info = getActivityNetworkInfo(BaseApplication.getContext());
        return info.isConnected();
    }

    /**
     * 打开或关闭移动数据
     * 需系统应用 需添加权限
     * {@code <uses-permission android:name="android.permission.MODIFY_PHONE_STATE"/>}
     */
    public static void setDataEnabled(Context context, boolean enabled) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method setDataEnabled = manager.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
            setDataEnabled.invoke(manager, enabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断网络是否是4G
     */
    public static boolean is4G(Context context) {
        NetworkInfo networkInfo = getActivityNetworkInfo(context);
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
    }

    /**
     * 判断wifi是否打开
     */
    public static boolean getWifiEnabled(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    /**
     * 获取网络运营商名称
     *
     * @return 中国移动、中国联通、中国电信
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager != null ? manager.getNetworkOperatorName() : null;
    }

    /**
     * 获取IP地址
     * <p>
     * 需添加权限
     * {@code <uses-permission android:name="android.permission.INTERNET"/>}
     * </p>
     *
     * @param useIPv4
     *            是否用IPv4
     * @return IP地址
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements();) {
                NetworkInterface ni = nis.nextElement();
                // 防止小米手机返回10.0.2.15
                if (!ni.isUp())
                    continue;
                for (Enumeration<InetAddress> addresses = ni.getInetAddresses(); addresses.hasMoreElements();) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (useIPv4) {
                            if (isIPv4)
                                return hostAddress;
                        } else {
                            if (!isIPv4) {
                                int index = hostAddress.indexOf('%');
                                return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index)
                                        .toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用SSL不信任的证书
     */
    public static void useUntrustedCertificate() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) return false;
        return connectivity.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(componentName);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
}
