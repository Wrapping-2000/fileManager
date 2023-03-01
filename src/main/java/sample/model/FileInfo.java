package sample.model;

/**
 * @author Wrapping
 * @version 1.00
 * @descirption 文件信息类
 * @classname FileInfo
 * @date 2022.6.21 21:01$
 */
public class FileInfo {
    /** 文件名 */
    private String name;

    /** 修改日期 */
    private String modifyDate;

    /** 文件类型 */
    private String type;

    /** 文件大小 */
    private String size;

    /** 文件的hdfs路径 */
    private String hdfsPath;

    /**
     * @description 重写toString函数
     * @paramaeter  []
     * @return  java.lang.String
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    @Override
    public String toString() {
        return "Fileinfo{" +
                "name='" + name + '\'' +
                ", modifyDate=" + modifyDate +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", hdfsPath='" + hdfsPath + '\'' +
                '}';
    }

    /**
     * @description FileInfo类的构造方法
     * @paramaeter  [name, modifyDate, type, size, hdfsPath]
     * @return
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public FileInfo(String name, String modifyDate, String type, String size, String hdfsPath) {
        this.name = name;
        this.modifyDate = modifyDate;
        this.type = type;
        this.size = size;
        this.hdfsPath = hdfsPath;
    }

    /** name的get方法 */
    public String getName() {
        return name;
    }

    /** name的set方法 */
    public void setName(String name) {
        this.name = name;
    }

    /** modifyDate的get方法 */
    public String getModifyDate() {
        return modifyDate;
    }

    /** modifyDate的set方法 */
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    /** type的get方法 */
    public String getType() {
        return type;
    }

    /** type的set方法 */
    public void setType(String type) {
        this.type = type;
    }

    /** size的get方法 */
    public String getSize() {
        return size;
    }

    /** size的set方法 */
    public void setSize(String size) {
        this.size = size;
    }

    /** hdfsPath的get方法 */
    public String getHdfsPath() {
        return hdfsPath;
    }

    /** hdfsPath的set方法 */
    public void setHdfsPath(String hdfsPath) {
        this.hdfsPath = hdfsPath;
    }
}
