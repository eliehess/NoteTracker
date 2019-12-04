import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
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
    @FXML
    private GridPane featureDevGrid;
    @FXML
    private Label featureDevLabel;

    private static final int COMMENT_WIDTH = 201;
    private static final int COMMENT_HEIGHT = 120;

    private List<Comment> comments = new LinkedList<>();
    private boolean commentButtonSelected = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    private void toggleCommentButton() {
        if (commentButtonSelected)
            commentButton.setStyle("");
        else
            commentButton.setStyle("-fx-background-color: linear-gradient(#99cfff, #66a0ff);");

        commentButtonSelected = !commentButtonSelected;
    }

    @FXML
    private void highlightButtonClicked() {
        if(commentButtonSelected)
            toggleCommentButton();

        featureDevLabel.setText("Highlighting is still in development");
        featureDevGrid.setVisible(true);
        comments.forEach(Comment::hideMarker);
    }

    @FXML
    private void clearButtonClicked() {
        if(commentButtonSelected)
            toggleCommentButton();

        backButton.setDisable(true);
        highlightButton.setDisable(true);
        commentButton.setDisable(true);
        clearButton.setDisable(true);
        clearConfirmGrid.setVisible(true);

        comments.forEach(Comment::hideMarker);
    }

    @FXML
    private void imageClicked(MouseEvent mouseEvent) {
        if (!commentButtonSelected)
            return;

        toggleCommentButton();
        comments.add(new Comment(mouseEvent.getSceneX(), mouseEvent.getSceneY()));
    }

    @FXML
    private void noClear() {
        backButton.setDisable(false);
        highlightButton.setDisable(false);
        commentButton.setDisable(false);
        clearConfirmGrid.setVisible(false);
        comments.forEach(Comment::showMarker);
        updateClearButton();
    }

    @FXML
    private void yesClear() {
        comments = new LinkedList<>();

        backButton.setDisable(false);
        highlightButton.setDisable(false);
        commentButton.setDisable(false);
        clearButton.setDisable(false);
        clearConfirmGrid.setVisible(false);
        updateClearButton();
    }

    @FXML
    private void featureDevOK() {
        featureDevGrid.setVisible(false);
        comments.forEach(Comment::showMarker);
    }

    private void updateClearButton() {
        clearButton.setDisable(comments.isEmpty());
    }

    private class Comment {
        double realXPos;
        double realYPos;
        boolean thisExists = false;
        String backupText = "";

        TextArea text;
        Button doneButton;
        Button cancelButton;
        Button deleteButton;
        ImageView marker;

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
            deleteButton.setOnAction(e -> deleteThis());
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
            highlightButton.setDisable(true);
            commentButton.setDisable(true);
            clearButton.setDisable(true);

            comments.forEach(Comment::hideMarker);
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

            backupText = text.getText();

            text.setVisible(false);
            doneButton.setVisible(false);
            cancelButton.setVisible(false);
            deleteButton.setVisible(false);
            marker.setVisible(true);

            thisExists = true;
            backButton.setDisable(false);
            highlightButton.setDisable(false);
            commentButton.setDisable(false);
            updateClearButton();

            comments.forEach(Comment::showMarker);
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
                highlightButton.setDisable(false);
                commentButton.setDisable(false);
                updateClearButton();
            } else deleteThis();
            comments.forEach(Comment::showMarker);
        }

        void markerClicked() {
            backButton.setDisable(true);
            highlightButton.setDisable(true);
            commentButton.setDisable(true);
            clearButton.setDisable(true);

            marker.setVisible(false);
            text.setVisible(true);
            doneButton.setVisible(true);
            cancelButton.setVisible(true);
            deleteButton.setVisible(true);

            comments.forEach(Comment::hideMarker);
        }

        void deleteThis() {
            anchor.getChildren().remove(text);
            anchor.getChildren().remove(marker);
            anchor.getChildren().remove(doneButton);
            anchor.getChildren().remove(cancelButton);
            anchor.getChildren().remove(deleteButton);
            comments.remove(this);
            backButton.setDisable(false);
            highlightButton.setDisable(false);
            commentButton.setDisable(false);
            updateClearButton();

            comments.forEach(Comment::showMarker);
        }
    }
}
