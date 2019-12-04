import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    static final int WIDTH = 600;
    static final int HEIGHT = 400 + 24;

    private static Stage stage;
    private static URL scene;
    private static int currentSongNum = -1;
    private static List<Song> songs;

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
        populateSongs();
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
        if (currentSongNum < 0 || currentSongNum > songs.size()) return null;

        return songs.get(currentSongNum).name;
    }

    static void changeToSong(int songNum) {
        if (songNum < 0 || songNum > songs.size()) return;

        currentSongNum = songNum;

        try {
            if (songs.get(songNum).scene != null)
                stage.setScene(songs.get(songNum).scene);
            else
                stage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("/Song.fxml"))));
        } catch (IOException e) {
            System.out.println("ERROR: IOException Detected");
            e.printStackTrace();
        }
    }

    static void storeScene(Scene scene) {
        songs.get(currentSongNum).scene = scene;
    }

    private void populateSongs() {
        songs = new ArrayList<>();
        songs.add(new Song("AlmaMater"));
        songs.add(new Song("CrazyTrain"));
        songs.add(new Song("EyeOfTheTiger"));
        songs.add(new Song("SevenNationArmy"));
        songs.add(new Song("SweetCaroline"));
        songs.add(new Song("TakeOnMe"));
    }

    private static class Song {
        private Scene scene;
        private final String name;

        Song(String name) {
            this.name = name;
        }
    }
}
