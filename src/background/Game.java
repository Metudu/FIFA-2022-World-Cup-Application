package background;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    Random random = new Random();
    private final Team[] teams = new Team[16];
    private Team user_favourite_team;
    private final int[] fixture = new int[16];
    private final int[] quarter_finals = new int[8];
    private final int[] semi_finals = new int[4];
    private final int[] finals = new int[2];
    public Game(){
        for (int i = 0; i < fixture.length; i++) {
            fixture[i] = i;
        }
        shuffle_last_16();
        try {
            int i = 0;
            Scanner scanner = new Scanner(new File("resources/all-teams.txt"));
            while (scanner.hasNextLine()){
                String data = scanner.nextLine();
                teams[i] = new Team(data,i);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void shuffle_last_16(){
        for (int i = 0; i < fixture.length; i++) {
            int temp = fixture[i];
            int random_index = random.nextInt(fixture.length);
            fixture[i] = fixture[random_index];
            fixture[random_index] = temp;
        }
    }

    public void go_to_next_section(int[] big_array,int[] small_array){
        for(int i = 0,j = 0; i < big_array.length; i+=2,j++){
            int increment = random.nextInt(2);
            small_array[j] = big_array[i+increment];
        }
    }
    public Team[] getTeams() {
        return teams;
    }
    public Team getUser_favourite_team() {
        return user_favourite_team;
    }

    public void setUser_favourite_team(Team user_favourite_team) {
        this.user_favourite_team = user_favourite_team;
    }

    public int[] getFixture() {
        return fixture;
    }
    public int[] getQuarter_finals() {
        return quarter_finals;
    }
    public int[] getSemi_finals() {
        return semi_finals;
    }
    public int[] getFinals() {
        return finals;
    }
}
