import java.util.List;

public class ConsolePrinter {
    void printErrors(List<String> errors) {
        if (!errors.isEmpty()) {
            System.out.println("ERROR: cannot register");
            for (String e : errors)
                System.out.println("- " + e);
            return;
        }
    }

    void printSuccess(StudentRecord record, int totalStudents, String id) {
        System.out.println("OK: created student " + id);
        System.out.println("Saved. Total students: " + totalStudents);
        System.out.println("CONFIRMATION:");
        System.out.println(record);
    }
}
