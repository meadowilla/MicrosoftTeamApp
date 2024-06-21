package entity;
import java.util.ArrayList;

public class Member {
    private String displayName;
    private String visibleHistoryStartDateTime;
    private String userId;
    private String email;
    private String roles;
    private ArrayList<Team> joinedTeams;
    
    public Member(String displayName, String visibleHistoryStartDateTime, String userId, String email, String roles){
        this.displayName = displayName;
        this.visibleHistoryStartDateTime = visibleHistoryStartDateTime;
        this.userId = userId;
        this.email = email;
        this.roles = roles;
    }
    public String getdisplayName() {
        return displayName;
    }
    public void setdisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getvisibleHistoryStartDateTime() {
        return visibleHistoryStartDateTime;
    }
    public void setvisibleHistoryStartDateTime(String visibleHistoryStartDateTime) {
        this.visibleHistoryStartDateTime = visibleHistoryStartDateTime;
    }
    public String getuserId() {
        return userId;
    }
    public void setuserId(String userId) {
        this.userId = userId;
    }
    public String getemail() {
        return email;
    }
    public void setemail(String email) {
        this.email = email;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getVisibleHistoryStartDateTime() {
        return visibleHistoryStartDateTime;
    }
    public void setVisibleHistoryStartDateTime(String visibleHistoryStartDateTime) {
        this.visibleHistoryStartDateTime = visibleHistoryStartDateTime;
    }
    public String getUserId() {
        return userId;
    }
    public String getEmail() {
        return email;
    }
    public String getRoles() {
        return roles;
    }
    public ArrayList<Team> getJoinedTeams() {
        return joinedTeams;
    }

    public void joinedTeams(Team team){
        joinedTeams.add(team);
    }

    
}
