package entity;  
import java.util.ArrayList;

public class Channel {
    private String id;
    private String displayName;
    private String description;
    private String tenantId;
    private ArrayList<Member> members = new ArrayList<Member>();


    public Channel(){
        
    }
    public ArrayList<Member> getMembers() {
        return members;
    }

    public String getId() {
        return id;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName){
        this.displayName = displayName;
    }
    
    public String getDescription() {
        return description;
    }
    public String getTenantId() {
        return tenantId;
    }
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
    public Channel(String id, String displayName, String description,
           String email, String tenantId) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.tenantId = tenantId;
    }
    
    

}
