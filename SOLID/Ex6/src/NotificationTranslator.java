public class NotificationTranslator {

    public static Payloads.EmailPayload toEmail(Notification n) {
        String body = n.body;
        // Truncation logic moved here from EmailSender
        if (body.length() > 40)
            body = body.substring(0, 40);
        return new Payloads.EmailPayload(n.email, n.subject, body);
    }

    public static Payloads.SmsPayload toSms(Notification n) {
        // Explicitly drops the subject because SMS does not use it.
        return new Payloads.SmsPayload(n.phone, n.body);
    }

    public static Payloads.WhatsAppPayload toWhatsApp(Notification n) {
        // Validation logic moved here from WhatsAppSender
        if (n.phone == null || !n.phone.startsWith("+")) {
            throw new IllegalArgumentException("phone must start with + and country code");
        }
        return new Payloads.WhatsAppPayload(n.phone, n.body);
    }

    public static Payloads.PushPayload toPush(Notification n) {
        // Just mock a device ID for the demo since Notification doesn't have one
        String deviceId = "device-" + n.email.hashCode();
        return new Payloads.PushPayload(deviceId, n.subject, n.body);
    }
}
