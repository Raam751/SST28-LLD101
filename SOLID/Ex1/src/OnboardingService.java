import java.util.*;
import java.util.Set;

public class OnboardingService {
    private final StudentRepository db;
    private final InputParser inputParser;
    private final StudentValidator studentValidator;
    private final ConsolePrinter consolePrinter;

    public OnboardingService(StudentRepository db, Set<String> allowedPrograms) {
        this.db = db;
        this.inputParser = new InputParser();
        this.studentValidator = new StudentValidator(allowedPrograms);
        this.consolePrinter = new ConsolePrinter();
    }

    public void registerFromRawInput(String raw) {
        System.out.println("INPUT: " + raw);

        Map<String, String> parsedData = inputParser.parse(raw);

        String name = parsedData.getOrDefault("name", "");
        String email = parsedData.getOrDefault("email", "");
        String phone = parsedData.getOrDefault("phone", "");
        String program = parsedData.getOrDefault("program", "");

        List<String> errors = studentValidator.validate(parsedData);

        if (!errors.isEmpty()) {
            consolePrinter.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(db.count());
        StudentRecord rec = new StudentRecord(id, name, email, phone, program);

        db.save(rec);

        consolePrinter.printSuccess(rec, db.count(), id);
    }
}
