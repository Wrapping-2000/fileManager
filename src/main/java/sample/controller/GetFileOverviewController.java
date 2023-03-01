package sample.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import sample.Main;
import sample.model.DateUtil;
import sample.model.FileInfo;
import sample.model.Temp;
import sample.model.TestFile;

import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDate;

/**
 * @description: 一组获取按钮调用的接口界面
 * @ClassName：FileOverviewController.java
 * @author: 陈温鹏
 * @Data: 2022-06-21 17:30:33
 * @Version: 1.00
 */
public class GetFileOverviewController {

    /** 获取接口界面的TableView表格 */
    @FXML
    private TableView<TestFile> fileTable;

    /** 文件名列 */
    @FXML
    private TableColumn<TestFile, String> nameColumn;

    /** 修改日期列 */
    @FXML
    private TableColumn<TestFile, LocalDate> modifyDateColumn;

    /** 文件类型列 */
    @FXML
    private TableColumn<TestFile, String> typeColumn;

    /** 文件大小列 */
    @FXML
    private TableColumn<TestFile, String> sizeColumn;

    /** testFileData按照TestFile中的格式记录每一个上传到hdfs文件系统的文件属性 */
    @FXML
    private ObservableList<TestFile> testFileData = FXCollections.observableArrayList();

    /**
     * @description TestFileData的get方法
     * @paramaeter  []
     * @return  javafx.collections.ObservableList<sample.model.TestFile>
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public ObservableList<TestFile> getTestFileData() {
        return testFileData;
    }

    /** jsonArray按照格式将上传到hdfs文件系统的文件信息记录并保存 */
    @FXML
    private JSONArray jsonArray;


    /** jsonObject用来为String字符串转换为JSON提供支持 */
    @FXML
    private JSONObject jsonObject;

    /** 用户名，默认为king */
    @FXML
    private String userName;

    /** main主窗口 */
    @FXML
    private Main main;

    /** 集群地址 */
    String HDFS_WEB_ADDRESS;

    /**
     * @description GetFileOverveiwController的默认构造方法
     * @paramaeter  []
     * @return
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public GetFileOverviewController() {

    }

    /**
     * @description GetFileOverviewController的有参构造方法
     * @paramaeter  [HDFS_WEB_ADDRESS]
     * @return
     * @author  Wrapping
     * @date  2022-10-18
     * @throws
     */
    public GetFileOverviewController(String HDFS_WEB_ADDRESS) {
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
     * @description 初始化获取接口界面
     * @paramaeter  []
     * @return  void
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    @FXML
    private void initialize() {
        new Thread(() -> {
            fileTable.setItems(getTestFileData());
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", HDFS_WEB_ADDRESS);
            /* 读 JSON */
            String content = null;
            try {
                content = FileOverviewController.updateUsersFile(HDFS_WEB_ADDRESS);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            /* 展示 JSON */
            this.jsonObject = JSON.parseObject(content);
            this.userName = "king";
            this.jsonArray = (JSONArray) jsonObject.get(userName);
            for (int i = 0;i < jsonArray.size();i++) {
                FileInfo fileInfo = JSON.toJavaObject((JSONObject)jsonArray.get(i),FileInfo.class);
                TestFile testFile = new TestFile(fileInfo.getName(), DateUtil.parse(fileInfo.getModifyDate()), fileInfo.getType(), fileInfo.getSize(), fileInfo.getHdfsPath());
                this.testFileData.add(testFile);
            }
            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            modifyDateColumn.setCellValueFactory(cellData -> cellData.getValue().modifyDateProperty());
            typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
            sizeColumn.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        }).start();
    }

    /**
     * @description 获取按钮的反应函数
     * @paramaeter  []
     * @return  void
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    @FXML
    private void handleGet() {
        if(fileTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误对话框");
            alert.setHeaderText("鼠标未选中内容！");
            alert.setContentText("请选中内容！");
            alert.showAndWait();
        } else {
            TestFile testFile = fileTable.getSelectionModel().getSelectedItem();
            //调用setS方法将获取到的hdfs路径暂存到Temp类的s字符串中
            Temp.setS(testFile.hdfsPathProperty().getValue());
            Stage stage = (Stage)fileTable.getParent().getScene().getWindow();
            stage.close();
        }
    }
}
