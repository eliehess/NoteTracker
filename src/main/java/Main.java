import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400 + 24;

    private static String songName = "";
    private static Stage stage;
    private static URL scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setResizable(false);
        stage.setMaxHeight(HEIGHT);
        stage.setMinHeight(HEIGHT);
        stage.setMaxWidth(WIDTH);
        stage.setMinWidth(WIDTH);
        scene = getClass().getResource("/Main.fxml");
        loadHome();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    static void loadHome() throws IOException {
        Parent loadingRoot = FXMLLoader.load(scene);
        stage.setTitle("NoteTracker");
        stage.setScene(new Scene(loadingRoot));
    }

    static String getSongName() {
        return songName;
    }

    static void setSongName(String songName) {
        Main.songName = songName;
    }

    static Stage getStage() {
        return stage;
    }
}
