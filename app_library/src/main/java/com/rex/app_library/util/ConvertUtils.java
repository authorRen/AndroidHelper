package com.rex.app_library.util;

import android.annotation.SuppressLint;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 类型转换类
 *
 * Created by renzeqiang
 * on 2019/5/7
 */
public class ConvertUtils {
    private static final char[] DIGIST_LOWER = {'0', '1', '2', '3', '4', '5',
    '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGIST_UPPER = {'0', '1', '2', '3', '4', '5',
    '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public ConvertUtils() {
        throw new UnsupportedOperationException("can't be instantiated");
    }

    /**
     * 十六进制字符串转换为byte数组
     *
     * @param hexString 十六进制字符串
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * char转换为byte数组
     *
     * @param c char
     * @return byte
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 16进制转化为数字
     *
     * @param ch        16进制
     * @param index     索引
     * @return          转化结果
     * @throws Exception 转化失败异常
     */
    public static int toDigit(final char ch, final int index) throws Exception{
        final int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new Exception("Illegal hexadecimal character" + ch + "at index " + index);
        }
        return digit;
    }

    /**
     * bytes数组转16进制String
     *
     * @param data      bytes数组
     * @param toDigits  DIGITS_LOWER或DIGITS_UPPER
     * @return          转化结果
     */
    public static String bytes2Hex(final byte[] data, final char[] toDigits) {
        final int length = data.length;
        final char[] out = new char[length << 1];
        //two characters from the hex value.
        for (int i = 0, j = 0; i < length; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0xF0 & data[i]];
        }
        return new String(out);
    }

    /**
     * byte数组转换为十六进制字符串
     *
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        if (bytes.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int value = bytes[i] & 0xFF;
            String hv = Integer.toHexString(value);
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
        }
        return sb.toString();
    }

    /**
     * int转换为byte数组
     *
     * @param res
     * @return
     */
    public static byte[] intToByte(int res) {
        byte[] targets = new byte[4];
        targets[0] = (byte) (res & 0xFF); //最低位
        targets[1] = (byte) ((res >> 8) & 0xFF); //次低位
        targets[2] = (byte) ((res >> 16) & 0xFF); //次高位
        targets[3] = (byte) ((res >> 24) & 0xFF); //最高位，无符号右移。
        return targets;
    }

    /**
     * byte数组转换为int
     *
     * @param res
     * @return
     */
    public static int byteToInt(byte[] res) {
        //一个byte数据左移24位变为0x??00000,再右移8位变成0x00??0000
        int targets = (res[3] & 0xff) | ((res[2] << 8) & 0xff00) | ((res[1] << 16 & 0xff0000) | ((res[0] << 24 & 0xff000000)));
        return targets;
    }

    /**
     * 保留几位小数
     */
    @SuppressLint("DefaultLocale")
    public static String saveDecimals(int cnt, double value) {
        if (cnt == 2) {
            return String.format("%.02f", value);
        } else if (cnt == 1) {
            return String.format("%.01f", value);
        } else {
            return String.format("%.0f", value);
        }
    }

    /**
     * null转String
     *
     * @param str
     * @return
     */
    public static String nullOfString(String str) {
        if (str == null) {
            str = "";
        }
        return str;
    }

    /**
     * String转Byte
     *
     * @param string
     * @return
     */
    public static byte stringToByte(String string) {
        byte b = 0;
        if (string != null) {
            try {
                b = Byte.parseByte(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    /**
     * String转Boolean
     *
     * @param str
     * @return
     */
    public static boolean stringToBoolean(String str) {
        if (str == null) {
            return false;
        } else {
            if (str.equals("1")) {
                return true;
            } else if (str.equals("0")) {
                return false;
            } else {
                try {
                    return Boolean.parseBoolean(str);
                } catch (Exception e) {
                    return false;
                }
            }
        }
    }

    /**
     * String转Int
     *
     * @param str
     * @return
     */
    public static int stringToInt(String str) {
        int i;
        if (str != null) {
            try {
                i = Integer.parseInt(str.trim());
            } catch (Exception e) {
                i = 0;
            }
        } else {
            i = 0;
        }
        return i;
    }

    /**
     * String转Short
     *
     * @param str
     * @return
     */
    public static short stringToShort(String str) {
        short i;
        if (str != null) {
            try {
                i = Short.parseShort(str.trim());
            } catch (Exception e) {
                i = 0;
            }
        } else {
            i = 0;
        }
        return i;
    }

    /**
     * String转Double
     *
     * @param str
     * @return
     */
    public static double stringToDouble(String str) {
        double i;
        if (str != null) {
            try {
                i = Double.parseDouble(str.trim());
            } catch (Exception e) {
                i = 0;
            }
        } else {
            i = 0;
        }
        return i;
    }

    /**
     * Int转String
     *
     * @param i
     * @return
     */
    public static String intToString(int i) {
        String str;
        try {
            str = String.valueOf(i);
        } catch (Exception e) {
            str = "";
        }
        return str;
    }

    /**
     * Double转Long
     *
     * @param d
     * @return
     */
    public static long doubleToLong(double d) {
        long lo = 0;
        try {
            //double转换成long前要过滤掉double类型小数点后数据
            lo = Long.parseLong(String.valueOf(d).substring(0, String.valueOf(d).lastIndexOf(".")));
        } catch (Exception e) {
            lo = 0;
        }
        return lo;
    }

    /**
     * Double转Int
     *
     * @param d
     * @return
     */
    public static int doubleToInt(double d) {
        int i;
        try {
            i = Integer.parseInt(String.valueOf(d).substring(0, String.valueOf(d).lastIndexOf(".")));
        } catch (Exception e) {
            i = 0;
        }
        return i;
    }

    /**
     * Long转Double
     *
     * @param d
     * @return
     */
    public static double longToDouble(long d) {
        double lo;
        try {
            lo = Double.parseDouble(String.valueOf(d));
        } catch (Exception e) {
            lo = 0;
        }
        return lo;
    }

    /**
     * Long转Int
     *
     * @param d
     * @return
     */
    public static int longToInt(long d) {
        int i;
        try {
            i = Integer.parseInt(String.valueOf(d));
        } catch (Exception e) {
            i = 0;
        }
        return i;
    }

    /**
     * String转long
     *
     * @param str
     * @return
     */
    public static long stringToLong(String str) {
        long lo;
        try {
            lo = Long.parseLong(str);
        } catch (Exception e) {
            lo = 0L;
        }
        return lo;
    }

    /**
     * byte[] 转为 对象
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object byteToObj(byte[] bytes) throws Exception {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        }  finally {
            if (ois != null) ois.close();
        }
    }

    /**
     * 对象 转为 byte[]
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static byte[] objToByte(Object obj) throws Exception {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return bos.toByteArray();
        } finally {
            if (oos != null) oos.close();
        }
    }

    public static void byteToBit(byte[] bytes, StringBuilder sb) {
        for (int i = 0; i < Byte.SIZE * bytes.length; i++) {
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        }
    }

    public static String byteToBit(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Byte.SIZE * bytes.length; i++)
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

    public static int convertToint(String intStr, int defValue) {
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }

    public static long convertTolong(String longStr, long defValue) {
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }

    public static float convertTofloat(String fStr, float defValue) {
        try {
            return Float.parseFloat(fStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }

    public static double convertTodouble(String dStr, double defValue) {
        try {
            return Double.parseDouble(dStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return defValue;
    }


    public static Integer convertToInteger(String intStr) {
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Long convertToLong(String longStr) {
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Float convertToFloat(String fStr) {
        try {
            return Float.parseFloat(fStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static Double convertToDouble(String dStr) {
        try {
            return Double.parseDouble(dStr);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return null;
    }

}
