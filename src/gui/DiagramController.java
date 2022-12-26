package gui;

import background.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DiagramController implements Initializable {
    @FXML
    private Label label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14, label15, label16,quarter1,quarter2,quarter3,quarter4,quarter5,quarter6,quarter7,quarter8,semi1,semi2,semi3,semi4,final1,final2,user_team_info;
    @FXML
    private Button shuffle_button,quarter_button,semi_button,finals_button,finish_button,retry;
    private final ArrayList<Label> last_16_list = new ArrayList<>();
    private final ArrayList<Label> quarter_finals = new ArrayList<>();
    private final ArrayList<Label> semi_finals = new ArrayList<>();
    private final ArrayList<Label> finals = new ArrayList<>();
    Game game = MainMenuController.game;
    boolean could_user_team_pass_the_qualifying = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        last_16_list.add(label1);
        last_16_list.add(label2);
        last_16_list.add(label3);
        last_16_list.add(label4);
        last_16_list.add(label5);
        last_16_list.add(label6);
        last_16_list.add(label7);
        last_16_list.add(label8);
        last_16_list.add(label9);
        last_16_list.add(label10);
        last_16_list.add(label11);
        last_16_list.add(label12);
        last_16_list.add(label13);
        last_16_list.add(label14);
        last_16_list.add(label15);
        last_16_list.add(label16);

        quarter_finals.add(quarter1);
        quarter_finals.add(quarter2);
        quarter_finals.add(quarter3);
        quarter_finals.add(quarter4);
        quarter_finals.add(quarter5);
        quarter_finals.add(quarter6);
        quarter_finals.add(quarter7);
        quarter_finals.add(quarter8);

        semi_finals.add(semi1);
        semi_finals.add(semi2);
        semi_finals.add(semi3);
        semi_finals.add(semi4);

        finals.add(final1);
        finals.add(final2);

        print_labels(game.getFixture(), last_16_list);
    }
    public void shuffle() {
        game.shuffle_last_16();
        print_labels(game.getFixture(), last_16_list);
    }
    public void go_to_quarter_finals(){
        shuffle_button.setVisible(false);
        game.go_to_next_section(game.getFixture(), game.getQuarter_finals());
        print_labels(game.getQuarter_finals(), quarter_finals);
        quarter_button.setVisible(false);
        semi_button.setVisible(true);
        check_user_team(game.getQuarter_finals(), "quarter finals");
    }
    public void go_to_semi_finals(){
        semi_button.setVisible(false);
        game.go_to_next_section(game.getQuarter_finals(), game.getSemi_finals());
        print_labels(game.getSemi_finals(), semi_finals);
        check_user_team(game.getSemi_finals(),"semi finals");
        finals_button.setVisible(true);
    }
    public void go_to_finals(){
        finals_button.setVisible(false);
        game.go_to_next_section(game.getSemi_finals(), game.getFinals());
        print_labels(game.getFinals(), finals);
        finish_button.setVisible(true);
        check_user_team(game.getFinals(),"finals");
    }
    public void finals() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml-files/final-match.fxml"));
        Parent root = loader.load();
        GraphicalMatchController controller = loader.getController();
        controller.getFinalist1().setText(game.getTeams()[game.getFinals()[0]].getName());
        controller.getFinalist2().setText(game.getTeams()[game.getFinals()[1]].getName());
        Stage stage = (Stage) finals_button.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void print_labels(int[] array, ArrayList<Label> label_list) {
        for (int i = 0; i < array.length; i++) {
            label_list.get(i).setText(game.getTeams()[array[i]].getName());
        }
    }
    public void check_user_team(int[] array,String level){
        for(int team:array){
            if(game.getUser_favourite_team().getId() == team){
                could_user_team_pass_the_qualifying = true;
                break;
            }
        }
        if (could_user_team_pass_the_qualifying){
            user_team_info.setText(game.getUser_favourite_team().getName() + " get through to " + level + "!");
        }
        else {
            user_team_info.setText(game.getUser_favourite_team().getName() + " has eliminated :(");
            retry.setVisible(true);
        }
        could_user_team_pass_the_qualifying = false;
    }

    public void try_again() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml-files/main-menu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) retry.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
