package background;

import java.util.Random;

public class Position implements MoveInitializer{
    Random random = new Random();
    private final int WIDTH = 768;
    private int which_team_has_position;
    private float player_x;
    private float player_y;
    private float player_radius;
    private float ball_radius;
    private float ball_x;
    private float ball_y;
    private int border_x;
    private int border_y;
    private float vertical_speed;
    private float horizontal_speed;
    private float vertical_road_to_border;
    private float horizontal_road_to_border;
    private float angle;

    public Position(){}
    public boolean position_or_not(){
        final int position_chance = 200;
        int temp = random.nextInt(201);
        return temp == position_chance;
    }

    public void define_positions(){
        final float player_speed = 0.4F;
        which_team_has_position = random.nextInt(2);
        player_y = random.nextInt(427) + 42;
        border_y = random.nextInt(447) + 65;

        if(which_team_has_position == 1) {
            player_x = random.nextInt(37) + 407;
            border_x = WIDTH;
        }
        else{
            player_x = random.nextInt(37) + 324;
            border_x = 0;
        }

        horizontal_road_to_border = Math.abs(player_x - border_x);
        vertical_road_to_border = Math.abs(player_y - border_y);
        float hypotenuse = ball_radius + player_radius;

        if (border_x > player_x){ // 1 - 4. region
            if (border_y > player_y){ // 4. region
                angle = - (float)(Math.atan(vertical_road_to_border / horizontal_road_to_border));
                ball_x = player_x + (float)(hypotenuse * Math.cos(angle));
                ball_y = player_y - (float)(hypotenuse * Math.sin(angle));
            } else if (player_y > border_y) { // 1. region
                angle = (float)(Math.atan(vertical_road_to_border / horizontal_road_to_border));
                ball_x = player_x + (float)(hypotenuse * Math.cos(angle));
                ball_y = player_y - (float)(hypotenuse * Math.sin(angle));
            }
        }
        else if (player_x > border_x) { // 2 - 3. region
            if(border_y > player_y){ // 3. region
                angle = (float) Math.toRadians(180) + (float)(Math.atan(vertical_road_to_border / horizontal_road_to_border));
                ball_x = player_x + (float)(hypotenuse * Math.cos(angle));
                ball_y = player_y - (float)(hypotenuse * Math.sin(angle));
            } else if (player_y > border_y) { // 2. region
                angle = (float)Math.toRadians(360) - (float) Math.toRadians(180) - (float)(Math.atan(vertical_road_to_border / horizontal_road_to_border));
                ball_x = player_x + (float)(hypotenuse * Math.cos(angle));
                ball_y = player_y - (float)(hypotenuse * Math.sin(angle));
            }
        }

        vertical_speed = (float) Math.sin(angle) * player_speed;
        horizontal_speed = (float) Math.cos(angle) * player_speed;
    }
    @Override
    public void move_both(){
        player_x += horizontal_speed;
        player_y -= vertical_speed;
        ball_x += horizontal_speed;
        ball_y -= vertical_speed;
    }

    public void do_math_of_the_shoot(){
        final float ball_speed = 1.5F;
        // y 96 400
        // goal-post 208 - 303
        border_y = random.nextInt(400 - 96) + 96;
        if(which_team_has_position == 1)
            border_x = WIDTH;
        else
            border_x = 0;
        horizontal_road_to_border = Math.abs(ball_x - border_x);
        vertical_road_to_border = Math.abs(ball_y - border_y);

        if (border_x > player_x){ // 1 - 4. region
            if (border_y > player_y){ // 4. region
                angle = - (float)(Math.atan(vertical_road_to_border / horizontal_road_to_border));
            } else if (player_y > border_y) { // 1. region
                angle = (float)(Math.atan(vertical_road_to_border / horizontal_road_to_border));
            }
        }
        else if (player_x > border_x) { // 2 - 3. region
            if(border_y > player_y){ // 3. region
                angle = (float) Math.toRadians(180) + (float)(Math.atan(vertical_road_to_border / horizontal_road_to_border));
            } else if (player_y > border_y) { // 2. region
                angle = (float)Math.toRadians(360) - (float) Math.toRadians(180) - (float)(Math.atan(vertical_road_to_border / horizontal_road_to_border));
            }
        }

        vertical_speed = (float) Math.sin(angle) * ball_speed;
        horizontal_speed = (float) Math.cos(angle) * ball_speed;
    }

    public void shoot_the_ball(){
        ball_x += horizontal_speed;
        ball_y -= vertical_speed;
    }

    public boolean did_the_ball_hit(){
        return ball_x <= 0 || ball_x >= WIDTH;
    }
    public boolean check_the_goal(){
        final int goal_post_top = 208;
        final int goal_post_bottom = 303;
        return ball_y - ball_radius / 6 >= goal_post_top && ball_y + ball_radius / 6 <= goal_post_bottom;
    }

    public void setPlayer_radius(float player_radius) {
        this.player_radius = player_radius;
    }

    public void setBall_radius(float ball_radius) {
        this.ball_radius = ball_radius;
    }

    public float getPlayer_x() {
        return player_x;
    }

    public float getPlayer_y() {
        return player_y;
    }

    public float getBall_x() {
        return ball_x;
    }

    public float getBall_y() {
        return ball_y;
    }

    public int getWIDTH() {
        return WIDTH;
    }
}
