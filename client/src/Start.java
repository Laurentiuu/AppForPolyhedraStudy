import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/pane.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Poliedre");
        stage.setWidth(1160);
        stage.setScene(scene);
        stage.show();
    }
}
