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
        comment = null;
    }

    @FXML
    private void imageClicked(MouseEvent mouseEvent) {
        if (!commentSelected) return;

        if (comment == null)
            comment = new Comment(200, 120, mouseEvent.getSceneX(), mouseEvent.getSceneY());
    }

    private class Comment {
        double xPos;
        double yPos;
        TextArea text;
        Button doneButton;
        Button cancelButton;
        ImageView marker;

        Comment(int width, int height, double xPos, double yPos) {
            this.xPos = xPos;
            this.yPos = yPos;

            text = new TextArea();
            text.setPrefSize(width, height);
            text.relocate(xPos, yPos);
            text.setWrapText(true);
            text.setStyle("-fx-border-color: black");

            doneButton = new Button();
            doneButton.setText("Done");
            doneButton.setPrefSize(50, 25);
            doneButton.relocate(xPos + width - 50, yPos - 27);
            doneButton.setOnAction(e -> doneClicked());

            cancelButton = new Button();
            cancelButton.setText("Cancel");
            cancelButton.setPrefSize(75, 25);
            cancelButton.relocate(xPos, yPos - 27);
            cancelButton.setOnAction(e -> cancelClicked());

            anchor.getChildren().add(text);
            anchor.getChildren().add(doneButton);
            anchor.getChildren().add(cancelButton);
        }

        void delete() {
            anchor.getChildren().remove(text);
            anchor.getChildren().remove(marker);
            anchor.getChildren().remove(doneButton);
            anchor.getChildren().remove(cancelButton);
        }

        void doneClicked() {
            System.out.println("done");

            text.setVisible(false);
            doneButton.setVisible(false);
            cancelButton.setVisible(false);

            marker = new ImageView();
            marker.setImage(new Image(getClass().getResourceAsStream("Images/Comment.png")));
            marker.relocate(xPos, yPos);
            anchor.getChildren().add(marker);
        }

        void cancelClicked() {
            System.out.println("cancel");
            comment = null;
        }
    }
}
