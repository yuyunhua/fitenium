package local.yunhua.fitenium.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by yuyunhua on 2015-12-30.
 */
public class ResultSaver {

    /**
     * 存放结果的根目录
     */
    private static String path = "";

    /**
     * 图片生成的序号，从10000开始
     */
    private static int seq = 10000;


    /**
     * 设置结果生成目录
     *
     * @param path
     * @return
     */
    public static boolean setPath(String path) {
        if (null == path) {
            return false;
        }


        File file = new File(path);
        /**
         * 若文件存在，且不是目录，获取文件的父目录作为结果的根目录
         */
        if (file.exists() && file.isFile()) {
            return false;
        }


        /**
         * 若文件存在，且是目录，获取全路径，并作为结果的根目录
         */
        if (file.exists() && file.isDirectory()) {
            ResultSaver.path = file.getAbsolutePath();
            return true;
        }

        /**
         * 若文件不存在，新建目录，并作为结果的根目录
         */
        if (file.mkdir()) {
            ResultSaver.path = file.getAbsolutePath();
            return true;
        }

        return false;
    }

    /**
     * 将图像文件保存到硬盘上
     *
     * @param img 要保存的图像文件
     * @return 硬盘上文件名，如果保存失败，返回null
     */
    public static String saveImg(File img) {
        seq++;
        String desPath = path + "\\" + seq + ".png";

        try {
            FileUtils.copyFile(img, new File(desPath));
        } catch (Exception e) {
            return null;
        }

        return seq + ".png";
    }

    /**
     * 将String类型的结果保存到文件，文件名是result.json，
     *
     * @param result
     * @return 结果返回-1或0，0表示写入成功，-1表示写入失败
     */
    public static int saveResult(String result) {
        String desPath = path + "\\" + "result.json";

        try {
            FileUtils.writeStringToFile(new File(desPath), result, "UTF-8");
        } catch (IOException e) {
            return -1;
        }
        return 0;
    }
}
