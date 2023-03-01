package sample.model;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * @author Wrapping
 * @version 1.00
 * @descirption 测试将文件上传到hdfs文件系统的测试类
 * @classname
 * @date 2022.7.26 11:57$
 */
public class testUploadFunction {



    public static void main(String[] args) throws IOException {
        /** 本地测试地址 */
        String LOCAL_PATH = "hdfs://localhost:9000/test";
        String localJsonFilePath = "D:\\UsersFile.json";
        /* 上传本地D盘下的UsersFile.json */
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS",LOCAL_PATH);
        FileSystem fs2 = FileSystem.get(conf);
        fs2.copyFromLocalFile(new Path(localJsonFilePath),new Path( LOCAL_PATH + "/input/"));
    }
}
