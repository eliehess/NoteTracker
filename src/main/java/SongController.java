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
    private Button highlightButton;
    @FXML
    private Button commentButton;
    @FXML
    private Button clearButton;
    @FXML
    private AnchorPane anchor;

    private Comment comment = null;
    private boolean commentButtonSelected = false;

    private static final int COMMENT_WIDTH = 201;
    private static final int COMMENT_HEIGHT = 120;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        highlightButton.setVisible(false); //TODO: Add functionality and remove this line

        String songName = Main.getSongName();
        image.setImage(new Image(getClass().getResourceAsStream("Images/" + songName + ".png")));
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
        toggleCommentButton();
    }

    @FXML
    private void highlightButtonClicked() {

    }

    @FXML
    private void clearButtonClicked() {
        if (comment != null) comment.delete();
        clearButton.setDisable(true);
    }

    @FXML
    private void imageClicked(MouseEvent mouseEvent) {
        if (!commentButtonSelected) return;

        commentButtonClicked();
        if (comment == null)
            comment = new Comment(mouseEvent.getSceneX(), mouseEvent.getSceneY());
    }

    private void toggleCommentButton() {
        if (commentButtonSelected)
            commentButton.setStyle("");
        else
            commentButton.setStyle("-fx-background-color: linear-gradient(#666666, #222222);-fx-text-fill: white");

        commentButtonSelected = !commentButtonSelected;
    }

    private class Comment {
        double xPos;
        double yPos;
        TextArea text;
        Button doneButton;
        Button cancelButton;
        Button deleteButton;
        ImageView marker = null;
        boolean thisExists = false;
        String backupText = "";

        Comment(double xPos, double yPos) {
            this.xPos = xPos;
            this.yPos = yPos;

            text = new TextArea();
            text.setPrefSize(COMMENT_WIDTH, COMMENT_HEIGHT);
            text.relocate(xPos, yPos);
            text.setWrapText(true);
            text.setStyle("-fx-border-color: black");

            doneButton = new Button();
            doneButton.setText("Done");
            doneButton.setPrefSize((float) COMMENT_WIDTH / 3, 25);
            doneButton.relocate(xPos + (2 * (float) (COMMENT_WIDTH / 3)), yPos - 27);
            doneButton.setOnAction(e -> doneClicked());

            cancelButton = new Button();
            cancelButton.setText("Cancel");
            cancelButton.setPrefSize((float) COMMENT_WIDTH / 3, 25);
            cancelButton.relocate(xPos, yPos - 27);
            cancelButton.setOnAction(e -> cancelClicked());

            anchor.getChildren().add(text);
            anchor.getChildren().add(doneButton);
            anchor.getChildren().add(cancelButton);
        }

        void doneClicked() {
            if(text.getText().trim().equals("")) {
                cancelClicked();
                return;
            }

            text.setVisible(false);
            doneButton.setVisible(false);
            cancelButton.setVisible(false);

            backupText = text.getText();

            if(marker == null) {
                marker = new ImageView();
                marker.setImage(new Image(getClass().getResourceAsStream("Images/Comment.png")));
                marker.relocate(xPos, yPos);
                marker.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    markerClicked();
                    event.consume();
                });
                anchor.getChildren().add(marker);
            } else marker.setVisible(true);

            thisExists = true;
            clearButton.setDisable(false);
        }

        void cancelClicked() {
            if (thisExists) {
                text.setText(backupText);
                text.setVisible(false);
                doneButton.setVisible(false);
                cancelButton.setVisible(false);
                deleteButton.setVisible(false);
                marker.setVisible(true);
            } else delete();
        }

        void markerClicked() {
            marker.setVisible(false);
            text.setVisible(true);
            doneButton.setVisible(true);
            cancelButton.setVisible(true);

            if(deleteButton == null) {
                deleteButton = new Button();
                deleteButton.setText("Delete");
                deleteButton.setPrefSize((float) COMMENT_WIDTH / 3, 25);
                deleteButton.relocate(xPos + (float) COMMENT_WIDTH / 3, yPos - 27);
                deleteButton.setOnAction(e -> delete());
                anchor.getChildren().add(deleteButton);
            }
        }

        void delete() {
            anchor.getChildren().remove(text);
            anchor.getChildren().remove(marker);
            anchor.getChildren().remove(doneButton);
            anchor.getChildren().remove(cancelButton);
            anchor.getChildren().remove(deleteButton);
            comment = null;
        }
    }
}
