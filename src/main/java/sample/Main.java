package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.factory.ViewFactory;

public class Main extends Application {

    /** 主舞台 */
    public Stage primaryStage;

    /** main的get方法 */
    public Main getMain() {
        return this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        BorderPane borderPane = new BorderPane();
        //hdfs://localhost:9000/test
        //hdfs://192.168.192.132:9000
        ViewFactory viewFactory = new ViewFactory("hdfs://localhost:9000/test");
        borderPane.setCenter(viewFactory.getFileManageDisplay());
//        borderPane.setCenter(ViewFactory.getGetFileManageDisplay());
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FileOverview.fxml"));
        this.primaryStage.setTitle("Hello World");
        this.primaryStage.setScene(new Scene(borderPane));
        this.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}