import javafx.fxml.FXML;

public class MainController {

    @FXML
    private void image1Click() {
        Main.changeToSong(0);
    }

    @FXML
    private void image2Click() {
        Main.changeToSong(1);
    }

    @FXML
    private void image3Click() {
        Main.changeToSong(2);
    }

    @FXML
    private void image4Click() {
        Main.changeToSong(3);
    }

    @FXML
    private void image5Click() {
        Main.changeToSong(4);
    }

    @FXML
    private void image6Click() {
        Main.changeToSong(5);
    }
}
