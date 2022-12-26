package background;
public class Team {
    private final String name;
    private final int id;
    public Team(String name,int id){
        this.name = name;
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public int getId() { return id; }
}