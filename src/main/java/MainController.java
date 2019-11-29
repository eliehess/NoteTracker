import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MainController {

    @FXML
    private void image1Click() throws IOException {
        Main.setSongName("AlmaMater");
        System.out.println("1");
        Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/Song.fxml"))));
    }

    @FXML
    private void image2Click() throws IOException {
        Main.setSongName("CrazyTrain");
        System.out.println("2");
        Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/Song.fxml"))));
    }

    @FXML
    private void image3Click() throws IOException {
        Main.setSongName("EyeOfTheTiger");
        System.out.println("3");
        Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/Song.fxml"))));
    }

    @FXML
    private void image4Click() throws IOException {
        Main.setSongName("SevenNationArmy");
        System.out.println("4");
        Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/Song.fxml"))));
    }

    @FXML
    private void image5Click() throws IOException {
        Main.setSongName("SweetCaroline");
        System.out.println("5");
        Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/Song.fxml"))));
    }

    @FXML
    private void image6Click() throws IOException {
        Main.setSongName("TakeOnMe");
        System.out.println("6");
        Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/Song.fxml"))));
    }
}
