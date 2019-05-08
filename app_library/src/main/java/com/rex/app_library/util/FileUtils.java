package com.rex.app_library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.rex.app_library.base.BaseApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 文件管理
 * <p>
 * Created by renzeqiang
 * on 2019/5/7
 */
public class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException("can't be instantiated");
    }

    private static final String TAG = FileUtils.class.getSimpleName();

    private static final String ROOT_PATH = Environment.getExternalStorageState()
            + File.separator
            + BaseApplication.getContext().getPackageName();

    private static final String CACHE = "/cache/";

    /**
     * 在初始化时创建APP所需要的基础文件夹
     * 在6.0以上版本时需要申请权限
     */
    public static void init(Context context) {
        createFileDir(context, CACHE);
    }

    /**
     * 创建目录
     *
     * @param context context
     * @param dirName 文件夹名称
     * @return
     */
    public static File createFileDir(Context context, String dirName) {
        String filePath;
        //如SD卡已存在，则存储；反之存在data目录下
        if (isMountedSDCard()) {
            //SD卡路径
            filePath = Environment.getExternalStorageState() + File.separator + dirName;
        } else {
            filePath = context.getCacheDir().getPath() + File.separator + dirName;
        }
        File destDir = new File(filePath);
        if (!destDir.exists()) {
            boolean isCreate = destDir.mkdirs();
            Log.i("FileUtils", filePath + "has created. " + isCreate);
        }
        return destDir;
    }

    /**
     * 删除文件(若为目录，则递归删除子目录和文件)
     *
     * @param file
     * @param delThisPath true代表删除参数指定file，false代表保留参数指定file
     */
    public static void delFile(File file, boolean delThisPath) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                int num = subFiles.length;
                //删除子目录和文件
                for (int i = 0; i < num; i++) {
                    delFile(subFiles[i], true);
                }
            }
        }
        if (delThisPath) {
            file.delete();
        }
    }

    /**
     * 获取文件大小，单位为byte(若为目录，则包括所有子目录和文件)
     *
     * @param file
     * @return
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] subFiles = file.listFiles();
                if (subFiles != null) {
                    int num = subFiles.length;
                    for (int i = 0; i < num; i++) {
                        size += getFileSize(subFiles[i]);
                    }
                }
            } else {
                size += file.length();
            }
        }
        return size;
    }

    /**
     * 保存bitmap到指定文件
     */
    public static File saveBitmapFile(Context context, String dirName, String fileName, Bitmap bitmap) {
        File file = new File(createFileDir(context, dirName) + fileName);
        if (bitmap == null) {
            return null;
        }
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 保存Bitmap到指定目录
     *
     * @param dir      目录
     * @param fileName 文件名
     * @param bitmap
     */
    public static void saveBitmap(File dir, String fileName, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        File file = new File(dir, fileName);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断某目录下文件是否存在
     *
     * @param dir      目录
     * @param fileName 文件名
     * @return
     */
    public static boolean isFileExists(File dir, String fileName) {
        return new File(dir, fileName).exists();
    }

    /**
     * 检查是否已挂载SD卡镜像(是否存在SD卡)
     */
    public static boolean isMountedSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else {
            Log.e(TAG, "SDCARD is not MOUNTED!");
            return false;
        }
    }

    /**
     * 获取SD卡剩余容量（单位Byte）
     */
    public static long gainSDFreeSize() {
        if (isMountedSDCard()) {
            //取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs statFs = new StatFs(path.getPath());
            //获取单个数据块的大小（Byte）
            int blockSize = statFs.getBlockSize();
            //空闲的数据块的数量
            int freeBlocks = statFs.getAvailableBlocks();

            //返回SD卡空闲大小
            return freeBlocks * blockSize; //单位Byte
        } else {
            return 0;
        }
    }

    /**
     * 获取SD卡总容量（单位Byte）
     */
    @SuppressWarnings("deprecation")
    public static long gainSDAllSize() {
        if (isMountedSDCard()) {
            // 取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            // 获取单个数据块的大小(Byte)
            long blockSize = sf.getBlockSize();
            // 获取所有数据块数
            long allBlocks = sf.getBlockCount();
            // 返回SD卡大小（Byte）
            return allBlocks * blockSize;
        } else {
            return 0;
        }
    }

    /**
     * 获取可用的SD卡路径（若SD卡不没有挂载则返回""）
     */

    public static String gainSDCardPath() {
        if (isMountedSDCard()) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            if (!sdcardDir.canWrite()) {
                Log.w(TAG, "SDCARD can not write !");
            }
            return sdcardDir.getPath();
        }
        return "";
    }

    /**
     * 以行为单位读取文件内容，一次读一整行，常用于读面向行的格式化文件
     *
     * @param filePath 文件路径
     */
    public static String readFileByLines(String filePath) throws IOException {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath),
                    System.getProperty("file.encoding")));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
                sb.append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return sb.toString();

    }

    /**
     * 以行为单位读取文件内容，一次读一整行，常用于读面向行的格式化文件
     *
     * @param filePath 文件路径
     * @param encoding 写文件编码
     */
    public static String readFileByLines(String filePath, String encoding)
            throws IOException {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath), encoding));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
                sb.append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return sb.toString();
    }
}
