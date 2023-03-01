package sample.factory;

/**
 * @author Wrapping
 * @version 1.00
 * @descirption filemanager的简单工厂模式
 * @classname
 * @date 2022.6.22 3:13$
 */

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import sample.controller.FileOverviewController;
import sample.model.Temp;
import java.io.IOException;

/**
 * @description 简单工厂模式的接口实现类
 * @paramaeter
 * @return
 * @author  Wrapping
 * @date  2022-07-19
 * @throws
 */
public class InterfaceFactory implements IFactory{

    /** 集群地址 */
    String HDFS_WEB_ADDRESS;

    /**
     * @description InterfaceFactory的默认构造方法
     * @paramaeter  []
     * @return
     * @author  Wrapping
     * @date  2022-10-18
     * @throws
     */
    public InterfaceFactory() {

    }

    /**
     * @description InterfaceFactory的有参构造方法
     * @paramaeter  [HDFS_WEB_ADDRESS]
     * @return
     * @author  Wrapping
     * @date  2022-10-18
     * @throws
     */
    public InterfaceFactory(String HDFS_WEB_ADDRESS) {
        this.HDFS_WEB_ADDRESS = HDFS_WEB_ADDRESS;
        /* 判断是否存在spark-logs文件夹，如果不存在，则创建出来，3组需求 */
        String dirPath = HDFS_WEB_ADDRESS + "/spark-logs";
        if(!FileOverviewController.isDirExist(dirPath)) {
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", HDFS_WEB_ADDRESS);
            FileSystem fs = null;
            try {
                fs = FileSystem.get(conf);
                fs.mkdirs(new Path(dirPath));
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @description 一组获取按钮调用的界面接口
     * @paramaeter  []
     * @return  java.lang.String
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    @Override
    public String getGetHdfsPath() {
        Stage stage = new Stage();
        ViewFactory viewFactory = new ViewFactory(HDFS_WEB_ADDRESS);
        Scene secondScene = new Scene(viewFactory.getGetFileManageDisplay());
        stage.setScene(secondScene);
        stage.showAndWait();
        return Temp.getS();
    }

    /**
     * @description 获取 上传文件hdfs路径的接口函数实现
     * @paramaeter  [localpath]
     * @return  java.lang.String
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    @Override
    public String getUploadHdfsPath(String localFilePath) {
        FileOverviewController fileOverviewController = new FileOverviewController(HDFS_WEB_ADDRESS);
        return fileOverviewController.getUploadHdfsPath(localFilePath);
    }

    /**
     * @description 从hdfs文件系统下载文件的接口函数实现
     * @paramaeter  [hdfspath, localpath]
     * @return  java.lang.Boolean
     * @author  Wrapping
     * @date  2022-07-19
     * @throws IOException
     */
    @Override
    public Boolean downloadFile(String hdfspath, String localpath) throws IOException {
        FileOverviewController fileOverviewController = new FileOverviewController(HDFS_WEB_ADDRESS);
        return fileOverviewController.downloadFile(hdfspath, localpath);
    }


    /**
     * @description 创建暂存文件夹的接口函数实现
     * @paramaeter  [uuids]
     * @return  void
     * @author  Wrapping
     * @date  2022-07-25
     * @throws
     */
    @Override
    public void mkdir(String[] uuids) {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", HDFS_WEB_ADDRESS);
        FileSystem fs = null;
        for(int i = 0; i < uuids.length; i++)
        {
            try {
                fs = FileSystem.get(conf);
                fs.mkdirs(new Path(HDFS_WEB_ADDRESS + "/input/" + uuids[i]));
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @description 删除HDFS上的文件和文件夹的接口实现
     * @paramaeter  [filearray]
     * @return  void
     * @author  Wrapping
     * @date  2022-08-10
     * @throws
     */
    @Override
    public void deleteHDFSFile(String[] filearray){
        FileOverviewController fileOverviewController = new FileOverviewController(HDFS_WEB_ADDRESS);
        fileOverviewController.deleteHDFSFile(filearray);
    }
}
