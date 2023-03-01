package sample.factory;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import sample.Main;
import sample.controller.FileOverviewController;
import sample.controller.GetFileOverviewController;
import java.io.IOException;

/**
 * @description: 界面的简单工厂模式
 * @ClassName：ViewFactory.java
 * @author: 刘伟豪
 * @Data: 2022-06-21 17:59:31
 */
public class ViewFactory {

    /** 集群地址 */
    String HDFS_WEB_ADDRESS;

    /**
     * @description ViewFactory的默认构造方法
     * @paramaeter  []
     * @return
     * @author  Wrapping
     * @date  2022-10-18
     * @throws
     */
    public ViewFactory() {

    }

    /**
     * @description ViewFactory的有参构造方法
     * @paramaeter  [HDFS_WEB_ADDRESS]
     * @return
     * @author  Wrapping
     * @date  2022-10-18
     * @throws
     */
    public ViewFactory(String HDFS_WEB_ADDRESS) {
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
     * @description 展示文件管理界面的接口
     * @paramaeter  []
     * @return  javafx.scene.layout.AnchorPane
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public AnchorPane getFileManageDisplay() {
        try {
            FileOverviewController fileOverviewController = new FileOverviewController(HDFS_WEB_ADDRESS);
            FXMLLoader loader = new FXMLLoader();
            loader.setController(fileOverviewController);
            loader.setLocation(Main.class.getResource("/fxml/FileOverview.fxml"));
            AnchorPane fileManageDisplay = (AnchorPane)loader.load();
            FileOverviewController fileOverviewController1 = (FileOverviewController)loader.getController();
            return fileManageDisplay;
        } catch (IOException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    /**
     * @description 一组获取按钮调用的界面 接口
     * @paramaeter  []
     * @return  javafx.scene.layout.AnchorPane
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public AnchorPane getGetFileManageDisplay() {
        try {
            GetFileOverviewController getFileOverviewController = new GetFileOverviewController(HDFS_WEB_ADDRESS);
            FXMLLoader loader = new FXMLLoader();
            loader.setController(getFileOverviewController);
            loader.setLocation(Main.class.getResource("/fxml/GetFileOverview.fxml"));
            AnchorPane fileManageDisplay = (AnchorPane)loader.load();
            GetFileOverviewController fileOverviewController1 = (GetFileOverviewController)loader.getController();
            return fileManageDisplay;
        } catch (IOException var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
