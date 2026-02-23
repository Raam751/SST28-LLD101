public class Demo06 {
    public static void main(String[] args) {
        System.out.println("=== Notification Demo ===");
        AuditLog audit = new AuditLog();

        Notification n = new Notification("Welcome", "Hello and welcome to SST!", "riya@sst.edu", "9876543210");

        NotificationSender<Payloads.EmailPayload> email = new EmailSender(audit);
        NotificationSender<Payloads.SmsPayload> sms = new SmsSender(audit);
        NotificationSender<Payloads.WhatsAppPayload> wa = new WhatsAppSender(audit);
        NotificationSender<Payloads.PushPayload> push = new PushNotificationSender(audit);

        email.send(NotificationTranslator.toEmail(n));
        sms.send(NotificationTranslator.toSms(n));
        push.send(NotificationTranslator.toPush(n));

        try {
            wa.send(NotificationTranslator.toWhatsApp(n));
        } catch (RuntimeException ex) {
            System.out.println("WA ERROR: " + ex.getMessage());
            audit.add("WA failed");
        }

        System.out.println("AUDIT entries=" + audit.size());
    }
}
