import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MainController {

    @FXML
    private void image1Click() throws IOException {
        Main.setSongName("AlmaMater");
        Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/Song.fxml"))));
    }

    @FXML
    private void image2Click() throws IOException {
        Main.setSongName("CrazyTrain");
        Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/Song.fxml"))));
    }

    @FXML
    private void image3Click() throws IOException {
        Main.setSongName("EyeOfTheTiger");
        Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/Song.fxml"))));
    }

    @FXML
    private void image4Click() throws IOException {
        Main.setSongName("SevenNationArmy");
        Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/Song.fxml"))));
    }

    @FXML
    private void image5Click() throws IOException {
        Main.setSongName("SweetCaroline");
        Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/Song.fxml"))));
    }

    @FXML
    private void image6Click() throws IOException {
        Main.setSongName("TakeOnMe");
        Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/Song.fxml"))));
    }
}
