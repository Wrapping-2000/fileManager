package sample.model;

import sample.factory.InterfaceFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * @author Wrapping
 * @version 1.00
 * @descirption 测试类，测试上传文件接口
 * @classname
 * @date 2022.6.26 15:42$
 */
public class TestUploadInterface {
    public static void main(String[] args) throws IOException, URISyntaxException {
        File dirFile = new File("C:\\Users\\Wrapping\\Desktop\\test");
        ArrayList dirStrArr = new ArrayList();
        if (dirFile.exists()) {
        //直接取出利用listFiles()把当前路径下的所有文件夹、文件存放到一个文件数组
            File files[] = dirFile.listFiles();
            for (File file : files) {
        //如果传递过来的参数dirFile是以文件分隔符，也就是/或者\结尾，则如此构造
                if (dirFile.getPath().endsWith(File.separator)) {
                    dirStrArr.add(dirFile.getPath() + file.getName());
                } else {
        //否则，如果没有文件分隔符，则补上一个文件分隔符，再加上文件名，才是路径
                    dirStrArr.add(dirFile.getPath() + File.separator
                            + file.getName());
                }
            }
        }
//        System.out.println(dirStrArr);
//        for(int i = 0; i < dirStrArr.size(); i ++) {
//            System.out.println(dirStrArr.get(i).toString());
//        }

        InterfaceFactory interfaceFactory = new InterfaceFactory();
        for(int i = 0; i < dirStrArr.size(); i ++) {
            String s = interfaceFactory.getUploadHdfsPath(dirStrArr.get(i).toString());
            System.out.println(s);
        }

    }

}
