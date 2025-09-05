package ibradecode.chatme;

public class StoryModel {
    private String id;
    private String story;
    private String storyType;
    private long timestamp;
    private String uid;
    private String url;
    private String username;

    public StoryModel() {}

    public StoryModel(String id, String story, String storyType, long timestamp, String uid, String url, String username) {
        this.id = id;
        this.story = story;
        this.storyType = storyType;
        this.timestamp = timestamp;
        this.uid = uid;
        this.url = url;
        this.username = username;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStory() { return story; }
    public void setStory(String story) { this.story = story; }
    public String getStoryType() { return storyType; }
    public void setStoryType(String storyType) { this.storyType = storyType; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
