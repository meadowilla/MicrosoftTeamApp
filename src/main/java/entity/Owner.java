package entity;
import java.util.ArrayList;

public class Owner extends Member{
    private ArrayList<Channel> channels;
    private ArrayList<Member> members;

    public Owner(String displayName, String visibleHistoryStartDateTime, String userId, String email, String roles) {
        super(displayName, visibleHistoryStartDateTime, userId, email, roles);
    }
    
    public void createChannel(Channel channel){
        if (!channels.contains(channel)){
            channels.add(channel);
        }
        System.out.println("The channel has been created");
    }
    public void addMember(Member member){
        if (!members.contains(member)){
            members.add(member);
        }
        System.out.println("The member has been added.");
    }
    
    public ArrayList<Channel> getChannels() {
        return channels;
    }
    public ArrayList<Member> getMembers() {
        return members;
    }
    
}
