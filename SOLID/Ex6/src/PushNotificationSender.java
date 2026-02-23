public class PushNotificationSender extends NotificationSender<Payloads.PushPayload> {
    public PushNotificationSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public void send(Payloads.PushPayload payload) {
        System.out.println(
                "PUSH -> toDevice=" + payload.deviceId + " title=" + payload.title + " message=" + payload.message);
        audit.add("push sent");
    }
}
