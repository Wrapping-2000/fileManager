package sample.model;

import sample.factory.InterfaceFactory;
import sample.factory.ViewFactory;

/**
 * @author Wrapping
 * @version 1.00
 * @descirption
 * @classname
 * @date 2022.10.18 8:38$
 */
public class TestWebAddressParamPassing {

    /** 集群地址 */
    //String HDFS_WEB_ADDRESS = "hdfs://192.168.192.132:9000";

    /** 本地测试地址 */
    //String HDFS_WEB_ADDRESS = "hdfs://localhost:9000/test";
    public static void main(String[] args) {
        InterfaceFactory interfaceFactory = new InterfaceFactory("hdfs://192.168.192.132:9000");
        String[] uuids = {"aaaa", "bbbb", "cccc"};
        interfaceFactory.mkdir(uuids);
    }
}
