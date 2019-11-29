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
        switch (songName) {
            case "AlmaMater":
                image.setImage(new Image(getClass().getResourceAsStream("Images/AlmaMater.png")));
                break;
            case "CrazyTrain":
                image.setImage(new Image(getClass().getResourceAsStream("Images/CrazyTrain.png")));
                break;
            case "EyeOfTheTiger":
                image.setImage(new Image(getClass().getResourceAsStream("Images/EyeOfTheTiger.png")));
                break;
            case "SevenNationArmy":
                image.setImage(new Image(getClass().getResourceAsStream("Images/SevenNationArmy.png")));
                break;
            case "SweetCaroline":
                image.setImage(new Image(getClass().getResourceAsStream("Images/SweetCaroline.png")));
                break;
            case "TakeOnMe":
                image.setImage(new Image(getClass().getResourceAsStream("Images/TakeOnMe.png")));
                break;
            default:
                System.out.println("ERROR: Invalid song name");
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
