package gui;

import background.*;
import exceptions.ChooseTeamException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private ChoiceBox<String> favourite_box;
    @FXML
    private Button start;
    @FXML
    private ImageView imageview;
    static Game game = new Game();
    public void start_the_world_cup() throws IOException, ChooseTeamException {
        String name = favourite_box.getValue();
        if(name == null)
            throw new ChooseTeamException("You have to choose team!");
        for (int i = 0; i < game.getTeams().length; i++) {
            if(name.equals(game.getTeams()[i].getName())){
                game.setUser_favourite_team(game.getTeams()[i]);
            }
        }

        game.shuffle_last_16();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml-files/diagram.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) start.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < game.getTeams().length; i++) {
            favourite_box.getItems().add(game.getTeams()[i].getName());
        }

        favourite_box.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                imageview.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../img/img" + favourite_box.getSelectionModel().getSelectedIndex() + ".png"))));
            }
        });
    }
}
