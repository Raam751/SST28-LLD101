public class PublicityLeadTool implements Publicity {

    @Override
    public void postAnnouncement(String message) {
        System.out.println("Announcement: \"" + message + "\"");
    }
}
