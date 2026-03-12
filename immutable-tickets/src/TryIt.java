import com.example.tickets.*;
import java.util.List;

/**
 * Demonstrates immutability and Builder pattern.
 */
public class TryIt {

    public static void main(String[] args) {
        TicketService svc = new TicketService();

        // 1. Create a ticket using the Builder (via service)
        IncidentTicket original = svc.createTicket("INC-001", "raam@vit.ac.in", "Login page is broken");
        System.out.println("=== Original Ticket ===");
        System.out.println(original);

        // 2. "Update" by escalating — original stays unchanged, returns a new ticket
        IncidentTicket escalated = svc.escalateToCritical(original);
        System.out.println("\n=== After Escalation ===");
        System.out.println("Escalated: " + escalated);
        System.out.println("Original : " + original);
        System.out.println("Same object? " + (original == escalated));

        // 3. Assign — again, original stays the same
        IncidentTicket assigned = svc.assign(escalated, "dev@vit.ac.in");
        System.out.println("\n=== After Assignment ===");
        System.out.println(assigned);

        // 4. Prove tags list is not mutable from outside
        List<String> tags = assigned.getTags();
        System.out.println("\n=== Immutability Proof ===");
        try {
            tags.add("HACKED");
            System.out.println("BUG: tags list was modified!");
        } catch (UnsupportedOperationException e) {
            System.out.println("Tags list is immutable — cannot add from outside!");
        }

        // 5. Build directly with Builder
        IncidentTicket custom = new IncidentTicket.Builder("INC-002", "staff@vit.ac.in", "Wi-Fi issue")
                .priority("LOW")
                .description("Wi-Fi drops every 10 minutes in hostel block C")
                .source("EMAIL")
                .slaMinutes(120)
                .customerVisible(true)
                .addTag("NETWORK")
                .addTag("HOSTEL")
                .build();
        System.out.println("\n=== Custom Ticket ===");
        System.out.println(custom);

        // 6. Validation demo — bad id should fail
        System.out.println("\n=== Validation Demo ===");
        try {
            new IncidentTicket.Builder("bad id!!", "x@y.com", "test").build();
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
