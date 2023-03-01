package sample.model;

import sample.factory.InterfaceFactory;

/**
 * @author Wrapping
 * @version 1.00
 * @descirption 测试类，用于测试创建目录窗口
 * @classname
 * @date 2022.7.25 22:32$
 */
public class testMkdirInterface {
    public static void main(String[] args) {
        InterfaceFactory interfaceFactory = new InterfaceFactory();
        String[] uuids = {"aaa", "bbb", "ccc"};
        interfaceFactory.mkdir(uuids);
    }
}
