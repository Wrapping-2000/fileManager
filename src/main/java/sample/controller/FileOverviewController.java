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
import javafx.stage.FileChooser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import sample.model.DateUtil;
import sample.model.FileInfo;
import sample.model.TestFile;
import sample.Main;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;

/**
 * @description: 文件管理模块主界面
 * @ClassName：FileOverviewController.java
 * @author: Wrapping
 * @Data: 2022-06-21 17:30:33
 * @Version: 1.00
 */
public class FileOverviewController {

    //定义字符串最大的长度
    public static final int WRITE_READ_UTF_MAX_LENGTH = 2048;

    /** 文件管理模块的TableView表格 */
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
     * @description FileOverveiwController的默认构造方法
     * @paramaeter  []
     * @return
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public FileOverviewController() {

    }

    /**
     * @description FileOverviewController的有参构造方法
     * @paramaeter  [HDFS_WEB_ADDRESS]
     * @return
     * @author  Wrapping
     * @date  2022-10-18
     * @throws
     */
    public FileOverviewController(String HDFS_WEB_ADDRESS) {
        this.HDFS_WEB_ADDRESS = HDFS_WEB_ADDRESS;
        /* 判断是否存在spark-logs文件夹，如果不存在，则创建出来，3组需求 */
        String dirPath = HDFS_WEB_ADDRESS + "/spark-logs";
        if(!isDirExist(dirPath)) {
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
     * @description 实时更新json文件
     * @paramaeter  [hdfsPath]
     * @return  java.lang.String
     * @author  Wrapping
     * @date  2022-10-05
     * @throws IOException, URISyntaxException
     */
    public static String updateUsersFile(String hdfsWebAddress) throws IOException, URISyntaxException {
        /* 如果存在json，则删除hdfs文件系统上的旧UsersFile.json */
        Configuration conf = new Configuration();
        FileSystem fs = null;
        fs = FileSystem.get(URI.create(hdfsWebAddress + "/input/UsersFile.json"), conf);
        if(fs.exists(new Path(hdfsWebAddress + "/input/UsersFile.json"))) {
            fs.delete(new Path(hdfsWebAddress + "/input/UsersFile.json"), true);//第二个参数表示是否递归删除
        }
        /* 在hdfs上创建新的UsersFile.json */
        FSDataOutputStream outputStream = fs.create(new Path(hdfsWebAddress + "/input/UsersFile.json"));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        /* 将指定的HDFS_WEB_ADDRESS内的所有文件都读取并记录 */
        FileSystem fs1 = FileSystem.get(new URI(hdfsWebAddress + "/input/"), conf);
        FileStatus[] fileStatus = fs1.listStatus(new Path(hdfsWebAddress + "/input/"));
        if(fileStatus == null || fileStatus.length == 0) {
            throw new FileNotFoundException("Cannot access" + hdfsWebAddress + "/input" + ":No such file or dictionary.");
        }
        String content = "";
        String content_ = "";
        for(int i = 0; i < fileStatus.length; i++){
            /* hdfs路径 */
            String hdfsPath = fileStatus[i].getPath().toString();
            /* 文件名 */
            String fileName = fileStatus[i].getPath().getName();
            /* 修改日期 */
            Date date = new Date(fileStatus[i].getModificationTime());
            LocalDate localDate = DateUtil.dateToLocalDate(date);
            String modifyDate = DateUtil.localDateToString(localDate);
            /* 文件大小 */
            double fileSize_B = fs.getContentSummary(fileStatus[i].getPath()).getLength();
            String fileSize = fileSize_B/1024 + " KB";
            /* 文件类型 */
            String fileType = null;
            if(fileName.indexOf(".") != -1) {
                fileType = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()).toLowerCase();
            }else{
                fileType = "暂存文件夹";
            }
            content_ = content_ + "{\"modifyDate\":" + "\"" + modifyDate + "\"" + ",\"size\":" + "\"" + fileSize + "\"" + ",\"name\":" + "\"" + fileName + "\"" + ",\"hdfsPath\":" + "\"" + hdfsPath + "\"" + ",\"type\":" + "\"" + fileType + "\"" + "},";
        }
        /* 要加入的记录 */
        if(content_.length() != 0) {
            String content1 = content_.substring(0, content_.length() - 1);//去掉最后面那个逗号
            String content2 = "{\"king\":[" + content1 + "]}";
            content = content2;
            /*写入新建的json*/
            //如果超过限定长度，将进行截取多次写出
            if(content2.length() > WRITE_READ_UTF_MAX_LENGTH) {
                for (int i = 1; i < content2.length()/WRITE_READ_UTF_MAX_LENGTH + 2; i++) {
                    outputStreamWriter.write(content2.substring(WRITE_READ_UTF_MAX_LENGTH*(i-1),WRITE_READ_UTF_MAX_LENGTH*i<content2.length()?WRITE_READ_UTF_MAX_LENGTH*i:content2.length()));
                }
            }else {
                //长度在0-65535默认写出
                outputStreamWriter.write(content2);
            }
            outputStreamWriter.close();
        }
        return content;
    }

    /**
     * @description 初始化文件管理界面
     * @paramaeter  []
     * @return  void
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    @FXML
    private void initialize() {
        new Thread(() -> {
            this.fileTable.setItems(getTestFileData());
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", HDFS_WEB_ADDRESS);
            /* 读 JSON */
            String content = null;
            try {
                content = updateUsersFile(HDFS_WEB_ADDRESS);
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
     * @description 清空fileTable列表,重新读入
     * @paramaeter  []
     * @return  void
     * @author  Wrapping
     * @date  2022-10-05
     * @throws
     */
    private void reReadIn() {
        /* 如果下面的几个不为空，则清空，确保其为空 */
        if(!this.jsonArray.isEmpty()) {
            this.jsonArray.clear();
        }
        if(!this.fileTable.getItems().isEmpty()) {
            this.fileTable.getItems().clear();
        }
        if(!this.testFileData.isEmpty()) {
            this.testFileData.clear();
        }
        /* 重新读入 */
        fileTable.setItems(getTestFileData());
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", HDFS_WEB_ADDRESS);
        /* 读 JSON */
        String content = null;
        try {
            content = updateUsersFile(HDFS_WEB_ADDRESS);
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
    }

    /**
     * @description 刷新按钮的反应函数
     * @paramaeter  []
     * @return  void
     * @author  Wrapping
     * @date  2022-07-21
     * @throws
     */
    @FXML
    private void handleUpdate() {
        reReadIn();
    }

    /**
     * @description 删除HDFS路径上的指定文件和文件夹，接口函数调用
     * @paramaeter  [filearray]
     * @return  void
     * @author  Wrapping
     * @date  2022-08-10
     * @throws IOException
     */
    public void deleteHDFSFile(String[] filearray){
        for (int i = 0; i < filearray.length; i++) {
            String s = HDFS_WEB_ADDRESS + "/input/" + filearray[i];
            Configuration configuration = new Configuration();
            FileSystem fs = null;
            try {
                fs = FileSystem.get(URI.create(s), configuration);
                fs.delete(new Path(s),true); //第二个参数表示是否递归删除
                fs.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            /* 更新json */
            try {
                updateUsersFile(HDFS_WEB_ADDRESS);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @description 删除按钮的反应函数
     * @paramaeter  []
     * @return  void
     * @author  Wrapping
     * @date  2022-07-19
     * @throws IOException
     */
    @FXML
    private void handleDelete(){
        /* 删除HDFS文件系统上选中的文件 */
        TestFile testFile = fileTable.getSelectionModel().getSelectedItem();
        if(fileTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误对话框");
            alert.setHeaderText("鼠标未选中内容！");
            alert.setContentText("请选中内容后再删除！");
            alert.showAndWait();
        } else {
            String s = testFile.hdfsPathProperty().getValue();
            Configuration configuration = new Configuration();
            FileSystem fs = null;
            try {
                fs = FileSystem.get(URI.create(s), configuration);
                fs.delete(new Path(s),true);
                fs.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            /* 清空fileTable，重新读入 */
            reReadIn();
        }
    }

    /**
     * @description 获取按钮的反应函数
     * @paramaeter  []
     * @return  java.lang.String
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    @FXML
    private String handleGet() {
        if(fileTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误对话框");
            alert.setHeaderText("鼠标未选中内容！");
            alert.setContentText("请选中内容！");
            alert.showAndWait();
            return null;
        } else {
            TestFile testFile = fileTable.getSelectionModel().getSelectedItem();
            return testFile.hdfsPathProperty().getValue();
        }
    }

    /**
     * @description 上传按钮的反应函数
     * @paramaeter  []
     * @return  java.lang.String
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    @FXML
    private String handleUpLoad() throws IOException, URISyntaxException {
        String localFilePath = openLocalFile();
        return getUploadHdfsPath(localFilePath);
    }

    /**
     * @description 打开本地文件选择界面
     * @paramaeter  []
     * @return  java.lang.String
     * @author  Wrapping
     * @date  2022-06-13
     * @throws
     */
    public String openLocalFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.setTitle("打开本地文件");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("all files", "*.*"),
                new FileChooser.ExtensionFilter("exe file", "*.exe"),
                new FileChooser.ExtensionFilter("jar file", "*.jar"),
                new FileChooser.ExtensionFilter("txt file", "*.txt"),
                new FileChooser.ExtensionFilter("xml file", "*.xml"),
                new FileChooser.ExtensionFilter("doc file", "*.doc"),
                new FileChooser.ExtensionFilter("docx file", "*.docx"),
                new FileChooser.ExtensionFilter("bash file", "*.sh"),
                new FileChooser.ExtensionFilter("xlsx file", "*.xlsx"),
                new FileChooser.ExtensionFilter("xls file", "*.xls")
                );
        return fileChooser.showOpenDialog(fileTable.getParent().getScene().getWindow()).getAbsolutePath();
    }

    /**
     * @description 获取上传到hdfs文件系统的hdfs文件路径
     * @paramaeter  [localpath]
     * @return  java.lang.String
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public String getUploadHdfsPath(String localFilePath) {
        File file = new File(localFilePath);
        String fileName = file.getName();
        String hdfsPath = HDFS_WEB_ADDRESS + "/input/" + fileName;
        try{
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", HDFS_WEB_ADDRESS);
            FileSystem fs = null;
            fs = FileSystem.get(conf);
            fs.copyFromLocalFile(new Path(localFilePath), new Path( HDFS_WEB_ADDRESS + "/input/"));
            updateUsersFile(HDFS_WEB_ADDRESS);
            /* 清空fileTable，重新读入 */
            //reReadIn();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return hdfsPath;
    }

    /**
     * @description 将hdfs文件系统上的文件下载到本地指定路径下的文件夹
     * @paramaeter  [hdfsPath, localpath]
     * @return  java.lang.Boolean
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public Boolean downloadFile(String hdfsPath, String localPath) throws IOException {
        if(!hdfsPath.isEmpty()){
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
            FSDataInputStream inputStream = fs.open(new Path(hdfsPath));
            String localPath_ = "";
            String fileName = hdfsPath.substring(hdfsPath.lastIndexOf("/") + 1);
            localPath_ = localPath + fileName;
            File file = new File(localPath_);
            if (!file.exists()) {
                file.createNewFile();
            }
            IOUtils.copyBytes(inputStream, new FileOutputStream(file), 8192, true);
            return true;
        }else{
            System.out.println("File: " + hdfsPath + " does not exist.");
            return false;
        }
    }

    /**
     * @description 判断指定路径的文件夹是否存在
     * @paramaeter  [dirPath]
     * @return  java.lang.Boolean
     * @author  Wrapping
     * @date  2022-10-30
     * @throws
     */
    public static Boolean isDirExist(String dirPath) {
        Configuration conf = new Configuration();
        FileSystem fs = null;
        try {
            fs = FileSystem.get(URI.create(dirPath), conf);
            if(fs.exists(new Path(dirPath))) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
