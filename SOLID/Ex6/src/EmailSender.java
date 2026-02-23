public class EmailSender extends NotificationSender<Payloads.EmailPayload> {
    public EmailSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public void send(Payloads.EmailPayload payload) {
        // Pure delivery logic. No truncation.
        System.out.println("EMAIL -> to=" + payload.to + " subject=" + payload.subject + " body=" + payload.body);
        audit.add("email sent");
    }
}
