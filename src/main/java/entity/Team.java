package entity;
import java.util.ArrayList;

public class Team {
    private String id;
    private String displayName;
    private String discription;
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Channel> channels = new ArrayList<>();
    
    
    public String getDiscription() {
        return discription;
    }
    public String getId() {
        return id;
    }
    public String getDisplayName() {
        return displayName;
    }
    public ArrayList<Member> getMembers() {
        return members;
    }
    public ArrayList<Channel> getChannels() {
        return channels;
    }
    public Team(){

    }
    

    
}
