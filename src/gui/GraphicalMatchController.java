package gui;

import background.Game;
import background.Position;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GraphicalMatchController implements Initializable {
    @FXML
    private Label finalist1,finalist2,score1_label,score2_label,current_time,match_situation;
    @FXML
    private Circle ball,player;
    Game game = MainMenuController.game;
    Position position = new Position();
    int score1 = 0;
    int score2 = 0;
    int minutes = 0;
    int match_time = 0;
    int position_time = 0;
    int wait_after_shoot = 0;
    int wait_after_match = 0;
    int wait_before_match = 0;
    boolean stop_timeline = false;
    boolean before_match = true;
    Timeline shoot_timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if(position.did_the_ball_hit()){
                if(position.check_the_goal()){
                    if(position.getBall_x() >= position.getWIDTH() && !stop_timeline){
                        score1 += 1;
                        score1_label.setText(String.valueOf(score1));
                        match_situation.setText(finalist1.getText() + " has scored!");
                    }
                    else if(position.getBall_x() <= 0 && !stop_timeline){
                        score2 += 1;
                        score2_label.setText(String.valueOf(score2));
                        match_situation.setText(finalist2.getText() + " has scored!");
                    }
                    stop_timeline = true;
                }
                wait_after_shoot += 10;
                if(wait_after_shoot == 5000) {
                    wait_after_shoot = 0;
                    stop_timeline = false;
                    ball.setVisible(false);
                    player.setVisible(false);
                    match_situation.setText("");
                    shoot_timeline.stop();
                    timeline.setCycleCount(Animation.INDEFINITE);
                    timeline.play();
                }
            }
            else{
                position.shoot_the_ball();
                move_ball();
            }
        }
    }));
    Timeline position_timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            position_time += 1;
            position.move_both();
            move_player();
            move_ball();
            ball.setVisible(true);
            player.setVisible(true);

            if(position_time == 500) {
                position_time = 0;
                position_timeline.stop();
                position.do_math_of_the_shoot();
                shoot_timeline.setCycleCount(Animation.INDEFINITE);
                shoot_timeline.play();
            }
        }
    }));

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if(before_match){
                match_situation.setText("Match is about to start!");
                wait_before_match += 10;
                if(wait_before_match == 3000){
                    before_match = false;
                    match_situation.setText("");
                }
            }
            else {
                if (minutes == 90) {
                    match_situation.setText("Match has finished!");
                    wait_after_match += 10;
                    match_time -= 50;
                    if (wait_after_match == 500) {
                        timeline.stop();
                        load_results();
                    }
                } else {
                    if (position.position_or_not()) {
                        timeline.stop();
                        position.define_positions();
                        position_timeline.setCycleCount(Animation.INDEFINITE);
                        position_timeline.play();
                    }

                    match_time += 50;
                    if (match_time == 400) {
                        match_time = 0;
                        current_time.setText("Minute: " + ++minutes);
                    }
                }
            }
        }
    }));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player.setVisible(true);
        position.setPlayer_radius((float) player.getRadius());
        position.setBall_radius((float) ball.getRadius());

        ball.setVisible(false);
        player.setVisible(false);

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void move_player(){
        player.setLayoutX(position.getPlayer_x());
        player.setLayoutY(position.getPlayer_y());
    }
    public void move_ball(){
        ball.setLayoutX(position.getBall_x());
        ball.setLayoutY(position.getBall_y());

    }
    public Label getFinalist1() {
        return finalist1;
    }

    public Label getFinalist2() {
        return finalist2;
    }

    public void load_results(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml-files/results.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ResultsController controller = loader.getController();
        controller.getImageView1().setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../img/img" + game.getFinals()[0] + ".png"))));
        controller.getImageView2().setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../img/img" + game.getFinals()[1] + ".png"))));
        controller.getScore_label().setText(score1 + " - " + score2);
        if(score1 > score2) {
            controller.getResult_label().setText(game.getTeams()[game.getFinals()[0]].getName().toUpperCase() + " HAS WON THE WORLD CUP!");
            controller.getFriendshipView().setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../img/gif" + game.getFinals()[0] + ".gif"))));

            if(game.getUser_favourite_team().getId() == game.getFinals()[0])
                controller.getResult2_label().setText("Congratulations! Your favourite team has won!");
            else if(game.getUser_favourite_team().getId() == game.getFinals()[1])
                controller.getResult2_label().setText("Unfortunately, your favourite team has lost :(");
        }
        else if (score2 > score1) {
            controller.getResult_label().setText(game.getTeams()[game.getFinals()[1]].getName().toUpperCase() + " HAS WON THE WORLD CUP!");
            controller.getFriendshipView().setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../img/gif" + game.getFinals()[1] + ".gif"))));

            if(game.getUser_favourite_team().getId() == game.getFinals()[1])
                controller.getResult2_label().setText("Congratulations! Your favourite team has won!");
            else if(game.getUser_favourite_team().getId() == game.getFinals()[0])
                controller.getResult2_label().setText("Unfortunately, your favourite team has lost :(");
        }
        else{
            controller.getResult_label().setText("FRIENDSHIP HAS WON THE WORLD CUP :)");
            controller.getFriendshipView().setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../img/draw.gif"))));
        }
        Stage stage = (Stage) player.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
