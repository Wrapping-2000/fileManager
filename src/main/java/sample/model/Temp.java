package sample.model;

/**
 * @author Wrapping
 * @version 1.00
 * @descirption 暂存类，用于暂存hdfs路径
 * @classname
 * @date 2022.6.22 10:56$
 */
public class Temp {
    /**
     * @description Temp的默认构造函数
     * @paramaeter  []
     * @return
     * @author  Wrapping
     * @date  2022-07-19
     * @throws
     */
    public Temp(){

    }

    /** s的get方法 */
    public static String getS() {
        return s;
    }

    /** s的set方法 */
    public static void setS(String s) {
        Temp.s = s;
    }

    /** s用于存储hdfs路径 */
    private static String s;
}
