public class SmsSender extends NotificationSender<Payloads.SmsPayload> {
    public SmsSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public void send(Payloads.SmsPayload payload) {
        // Pure delivery logic.
        System.out.println("SMS -> to=" + payload.to + " body=" + payload.body);
        audit.add("sms sent");
    }
}
