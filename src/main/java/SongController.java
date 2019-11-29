import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SongController implements Initializable {

    @FXML
    private ImageView image;
    @FXML
    private Button commentButton;
    @FXML
    private AnchorPane anchor;

    private TextArea commentBox = null;
    private boolean commentSelected = false;

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

    @FXML
    private void commentButtonClicked() {
        if (commentSelected)
            commentButton.setStyle("");
        else
            commentButton.setStyle("-fx-background-color: linear-gradient(#666666, #222222);-fx-text-fill: white");
        commentSelected = !commentSelected;
    }

    @FXML
    private void imageClicked(MouseEvent mouseEvent) {
        if (!commentSelected) return;

        if(commentBox == null) {
            commentBox = new TextArea();
            anchor.getChildren().add(commentBox);
            commentBox.setVisible(true);
            commentBox.setPrefSize(200, 150);
        }

        commentBox.relocate(mouseEvent.getSceneX(), mouseEvent.getSceneY());
    }
}
