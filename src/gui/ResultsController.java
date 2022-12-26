package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

public class ResultsController {
    @FXML
    private Label result_label,score_label,result2_label;
    @FXML
    private ImageView imageView1,imageView2,friendshipView;
    @FXML
    private Button play_again,exit;

    public void play_again() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml-files/main-menu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) play_again.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(){
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    public Label getResult_label() {
        return result_label;
    }

    public Label getScore_label() {
        return score_label;
    }

    public ImageView getImageView1() {
        return imageView1;
    }

    public ImageView getImageView2() {
        return imageView2;
    }

    public ImageView getFriendshipView() {
        return friendshipView;
    }
    public Label getResult2_label(){
        return result2_label;
    }
}
