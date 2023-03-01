package sample.model;

import sample.factory.InterfaceFactory;
import java.io.IOException;

/**
 * @author Wrapping
 * @version 1.00
 * @descirption 测试下载接口
 * @classname
 * @date 2022.8.10 22:12$
 */
public class TestDeleteHDFSFileInterface {
    public static void main(String[] args) throws IOException {
        InterfaceFactory interfaceFactory = new InterfaceFactory("hdfs://localhost:9000/test");
        String[] filearray = {"bbbb", "cccc"};
        interfaceFactory.deleteHDFSFile(filearray);
    }
}
