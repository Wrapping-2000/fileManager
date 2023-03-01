package sample.model;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.factory.InterfaceFactory;

/**
 * @author Wrapping
 * @version 1.00
 * @descirption 测试类，用于测试GetHdfsPath接口
 * @classname
 * @date 2022.6.22 11:03$
 */
public class  Test extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 创建一个包含按钮的APP
        Button newWindowBtn = new Button("打开新的窗口");
        // 为按钮添加事件——点击时打开新的窗口
        newWindowBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                // 创建新的stage
                InterfaceFactory interfaceFactory = new InterfaceFactory();
                System.out.println(interfaceFactory.getGetHdfsPath());
            }
        });
        StackPane mainPane = new StackPane(newWindowBtn); // 创建一个新的布局
        Scene scene = new Scene(mainPane, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
