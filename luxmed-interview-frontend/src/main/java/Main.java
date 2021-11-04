import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    public void start(Stage stage) throws Exception {
        System.out.println(getClass().getResource("fxml/luxmed-interview.fxml"));
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("fxml/luxmed-interview.fxml"))));
        Scene scene = new Scene(root, 1024, 800);
        scene.getStylesheets().add((Objects.requireNonNull(getClass().getResource("css/global.css"))).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("InterviewTask");
        stage.setResizable(false);
//        stage.getIcons().add(new Image("images/icon.png"));
        stage.show();

    }
}
