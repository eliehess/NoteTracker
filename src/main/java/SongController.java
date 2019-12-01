import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SongController implements Initializable {

    @FXML
    private ImageView background;
    @FXML
    private Button backButton;
    @FXML
    private Button highlightButton;
    @FXML
    private Button commentButton;
    @FXML
    private Button clearButton;
    @FXML
    private AnchorPane anchor;
    @FXML
    private GridPane clearConfirmGrid;

    private Comment comment = null;
    private boolean commentButtonSelected = false;

    private static final int COMMENT_WIDTH = 201;
    private static final int COMMENT_HEIGHT = 120;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        highlightButton.setVisible(false); //TODO: Add functionality and remove this line

        String songName = Main.getSongName();
        if (songName != null)
            background.setImage(new Image(getClass().getResourceAsStream("Images/" + songName + ".png")));
    }

    @FXML
    private void back() {
        Main.storeScene(background.getScene());

        try {
            Main.loadHome();
        } catch (IOException e) {
            System.out.println("ERROR: IOException Detected");
            e.printStackTrace();
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
        backButton.setDisable(true);
        commentButton.setDisable(true);
        clearButton.setDisable(true);
        clearConfirmGrid.setVisible(true);

        if (comment != null)
            comment.hideMarker();
    }

    @FXML
    private void imageClicked(MouseEvent mouseEvent) {
        if (!commentButtonSelected) return;

        commentButtonClicked();
        if (comment == null)
            comment = new Comment(mouseEvent.getSceneX(), mouseEvent.getSceneY());
    }

    @FXML
    private void noClear() {
        backButton.setDisable(false);
        commentButton.setDisable(false);
        clearConfirmGrid.setVisible(false);
        if (comment != null)
            comment.showMarker();
        updateClearButton();
    }

    @FXML
    private void yesClear() {
        if (comment != null) comment.delete();

        backButton.setDisable(false);
        commentButton.setDisable(false);
        clearButton.setDisable(false);
        clearConfirmGrid.setVisible(false);
        updateClearButton();
    }

    private void toggleCommentButton() {
        if (commentButtonSelected)
            commentButton.setStyle("");
        else
            commentButton.setStyle("-fx-background-color: linear-gradient(#99cfff, #66a0ff);");

        commentButtonSelected = !commentButtonSelected;
    }

    private void updateClearButton() {
        clearButton.setDisable(comment == null);
    }

    private class Comment {
        double realXPos;
        double realYPos;
        TextArea text;
        Button doneButton;
        Button cancelButton;
        Button deleteButton;
        ImageView marker;
        boolean thisExists = false;
        String backupText = "";

        Comment(double xPos, double yPos) {
            this.realXPos = xPos;
            this.realYPos = yPos;

            if (xPos > Main.WIDTH - (COMMENT_WIDTH * 1.5))
                xPos = Main.WIDTH - (COMMENT_WIDTH * 1.5);
            if (yPos > Main.HEIGHT - (COMMENT_HEIGHT * 1.5))
                yPos = Main.HEIGHT - (COMMENT_HEIGHT * 1.5);

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

            deleteButton = new Button();
            deleteButton.setText("Delete");
            deleteButton.setPrefSize((float) COMMENT_WIDTH / 3, 25);
            deleteButton.relocate(xPos + (float) COMMENT_WIDTH / 3, yPos - 27);
            deleteButton.setOnAction(e -> delete());
            deleteButton.setVisible(false);

            marker = new ImageView();
            marker.setImage(new Image(getClass().getResourceAsStream("Images/Comment.png")));
            marker.relocate(realXPos - 7, realYPos - 25);
            marker.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                markerClicked();
                event.consume();
            });
            marker.setVisible(false);

            anchor.getChildren().add(text);
            anchor.getChildren().add(doneButton);
            anchor.getChildren().add(cancelButton);
            anchor.getChildren().add(deleteButton);
            anchor.getChildren().add(marker);

            backButton.setDisable(true);
            commentButton.setDisable(true);
            clearButton.setDisable(true);
        }

        void hideMarker() {
            marker.setVisible(false);
        }

        void showMarker() {
            marker.setVisible(true);
        }

        void doneClicked() {
            if (text.getText().trim().equals("")) {
                cancelClicked();
                return;
            }

            text.setVisible(false);
            doneButton.setVisible(false);
            cancelButton.setVisible(false);
            deleteButton.setVisible(false);
            marker.setVisible(true);

            backupText = text.getText();

            thisExists = true;
            backButton.setDisable(false);
            commentButton.setDisable(false);
            updateClearButton();
        }

        void cancelClicked() {
            if (thisExists) {
                text.setText(backupText);
                text.setVisible(false);
                doneButton.setVisible(false);
                cancelButton.setVisible(false);
                deleteButton.setVisible(false);
                marker.setVisible(true);
                backButton.setDisable(false);
                commentButton.setDisable(false);
                updateClearButton();
            } else delete();
        }

        void markerClicked() {
            backButton.setDisable(true);
            commentButton.setDisable(true);
            clearButton.setDisable(true);

            marker.setVisible(false);
            text.setVisible(true);
            doneButton.setVisible(true);
            cancelButton.setVisible(true);
            deleteButton.setVisible(true);
        }

        void delete() {
            anchor.getChildren().remove(text);
            anchor.getChildren().remove(marker);
            anchor.getChildren().remove(doneButton);
            anchor.getChildren().remove(cancelButton);
            anchor.getChildren().remove(deleteButton);
            comment = null;
            backButton.setDisable(false);
            commentButton.setDisable(false);
            updateClearButton();
        }
    }
}
