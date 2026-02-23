public class Payloads {
    public static class EmailPayload {
        public final String to;
        public final String subject;
        public final String body;

        public EmailPayload(String to, String subject, String body) {
            this.to = to;
            this.subject = subject;
            this.body = body;
        }
    }

    public static class SmsPayload {
        public final String to;
        public final String body;

        public SmsPayload(String to, String body) {
            this.to = to;
            this.body = body;
        }
    }

    public static class WhatsAppPayload {
        public final String to;
        public final String body;

        public WhatsAppPayload(String to, String body) {
            this.to = to;
            this.body = body;
        }
    }

    public static class PushPayload {
        public final String deviceId;
        public final String title;
        public final String message;

        public PushPayload(String deviceId, String title, String message) {
            this.deviceId = deviceId;
            this.title = title;
            this.message = message;
        }
    }
}
