package sample.factory;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Wrapping
 * @version 1.00
 * @descirption 接口的抽象类
 * @classname
 * @date 2022.6.22 3:15$
 */
public interface IFactory {

    /** 获取 获取文件hdfs路径的接口函数 */
    String getGetHdfsPath();

    /** 获取 上传文件hdfs路径的接口函数 */
    String getUploadHdfsPath(String localFilePath) throws IOException, URISyntaxException;

    /** 从hdfs文件系统下载文件的接口函数 */
    Boolean downloadFile(String hdfspath, String localpath) throws IOException;

    /** 创建暂存文件的文件夹 */
    void mkdir(String[] uuids);

    /** 从HDFS上删除文件和文件夹的接口函数 */
    void deleteHDFSFile(String[] filearray) throws IOException;
}
