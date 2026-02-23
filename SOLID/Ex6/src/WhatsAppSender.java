public class WhatsAppSender extends NotificationSender<Payloads.WhatsAppPayload> {
    public WhatsAppSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public void send(Payloads.WhatsAppPayload payload) {
        // Pure delivery logic. No validation thrown from here.
        System.out.println("WA -> to=" + payload.to + " body=" + payload.body);
        audit.add("wa sent");
    }
}
