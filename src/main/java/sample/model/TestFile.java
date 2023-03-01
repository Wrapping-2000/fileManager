package sample.model;
/**
 * @author Wrapping
 * @version 1.00
 * @descirption 文件规范类，制定了Json字符串存储的规范
 * @classname
 * @date 2022.6.21 19:03$
 */

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;

/**
 * @description 规范文件类，规定了Json字符串的存储方式
 * @paramaeter
 * @return
 * @author  Wrapping
 * @date  2022-07-19
 * @throws
 */
public class TestFile extends RecursiveTreeObject<TestFile> {

    /** 文件名 */
    private final StringProperty name;

    /** 修改日期 */
    private final ObjectProperty<LocalDate> modifyDate;

    /** 文件类型 */
    private final StringProperty type;

    /** 文件大小 */
    private final StringProperty size;

    /** 文件的hdfs路径 */
    private final StringProperty hdfsPath;

    /** hdfsPath的get方法 */
    public String getHdfsPath() {
        return hdfsPath.get();
    }

    /** 类似于hdfsPath的get方法 */
    public StringProperty hdfsPathProperty() {
        return hdfsPath;
    }

    /** hdfsPath的set方法 */
    public void setHdfsPath(String hdfsPath) {
        this.hdfsPath.set(hdfsPath);
    }

    /**
     * @description TestFile类的构造方法
     * @paramaeter  [name, localDate, type, size, hdfsPath]
     * @return
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public TestFile(String name,LocalDate localDate,String type,String size,String hdfsPath) {
        this.name = new SimpleStringProperty(name);
        this.modifyDate = new SimpleObjectProperty<LocalDate>(localDate);;
        this.type = new SimpleStringProperty(type);
        this.size = new SimpleStringProperty(size);
        this.hdfsPath = new SimpleStringProperty(hdfsPath);
    }

    /** name的get方法 */
    public String getName() {
        return name.get();
    }

    /** 类似于name的get方法 */
    public StringProperty nameProperty() {
        return name;
    }

    /** name的set方法 */
    public void setName(String name) {
        this.name.set(name);
    }

    /** modifyDate的get方法 */
    public LocalDate getModifyDate() {
        return modifyDate.get();
    }

    /** 类似于modifyDate的get方法 */
    public ObjectProperty<LocalDate> modifyDateProperty() {
        return modifyDate;
    }

    /** modifyDate的set方法 */
    public void setModifyDate(LocalDate modifyDate) {
        this.modifyDate.set(modifyDate);
    }

    /** type的get方法 */
    public String getType() {
        return type.get();
    }

    /** 类似于type的get方法 */
    public StringProperty typeProperty() {
        return type;
    }

    /** type的set方法 */
    public void setType(String type) {
        this.type.set(type);
    }

    /** size的get方法 */
    public String getSize() {
        return size.get();
    }

    /** 类似于size的get方法 */
    public StringProperty sizeProperty() {
        return size;
    }

    /** size的set方法 */
    public void setSize(String size) {
        this.size.set(size);
    }

}
