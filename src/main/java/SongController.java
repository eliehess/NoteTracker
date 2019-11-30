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

    private Comment comment = null;
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
    private void highlightButtonClicked() {

    }

    @FXML
    private void clearButtonClicked() {
        if(comment != null)
            comment.hide();
    }

    @FXML
    private void imageClicked(MouseEvent mouseEvent) {
        if (!commentSelected) return;

        if(comment == null)
            comment = new Comment(200, 150, mouseEvent.getSceneX(), mouseEvent.getSceneY());
    }

    private class Comment {
        double xPos;
        double yPos;
        int width;
        int height;
        TextArea textArea;
        Button button;

        Comment(int width, int height, double xPos, double yPos) {
            this.width = width;
            this.height = height;
            this.xPos = xPos;
            this.yPos = yPos;

            textArea = new TextArea();
            anchor.getChildren().add(textArea);
            textArea.setPrefSize(width, height);
            textArea.relocate(xPos, yPos);
            textArea.setWrapText(true);

            button = new Button();
            anchor.getChildren().add(button);
            button.setText("Done");
            button.setPrefSize(50, 25);
            button.relocate(xPos + width - 50, yPos - 25);
        }

        void hide() {
            textArea.setVisible(false);
            button.setVisible(false);
        }
    }
}
