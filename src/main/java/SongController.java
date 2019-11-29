import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SongController implements Initializable {

    public ImageView image;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String songName = Main.getSongName();
        if(songName.equals("AlmaMater")) {
            image.setImage(new Image(getClass().getResourceAsStream("Images/AlmaMater.png")));
        } else if(songName.equals("CrazyTrain")) {
            image.setImage(new Image(getClass().getResourceAsStream("Images/CrazyTrain.png")));
        } else {
            System.out.println("ow");
        }
    }

    @FXML
    private void back() {
        try {
            Main.loadHome();
        } catch (IOException e) {
            System.out.println("ERROR: IOException Detected");
        }
    }
}
