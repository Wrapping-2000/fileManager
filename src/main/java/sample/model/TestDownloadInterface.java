package sample.model;

import sample.factory.InterfaceFactory;
import java.io.IOException;

/**
 * @author Wrapping
 * @version 1.00
 * @descirption 测试类，用于测试下载接口
 * @classname
 * @date 2022.6.30 20:19$
 */
public class TestDownloadInterface {
    public static void main(String[] args) throws IOException {
        InterfaceFactory interfaceFactory = new InterfaceFactory();
        interfaceFactory.downloadFile("hdfs://localhost:9000/test/input/UsersFile.json", "C:\\Users\\Wrapping\\Desktop\\temp\\");
    }
}
