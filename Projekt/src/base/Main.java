package base;

import dataFrame.DataFrame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import values.*;

public class Main extends Application {

    public static void main(String[] args) {

//        DataFrame df = new DataFrame("groupby.csv", new Class[]{StringV.class, DateTimeV.class, DoubleV.class, DoubleV.class});
//        System.out.println(
//                df
//                        .groupBy(new String[]{"id"})
//                        .max()
//                        .sortBy("id")
//        );

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }
}
