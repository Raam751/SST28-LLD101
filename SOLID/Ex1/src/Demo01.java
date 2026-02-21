import java.util.Set;

public class Demo01 {
    public static void main(String[] args) {
        System.out.println("=== Student Onboarding Demo ===");
        FakeDb db = new FakeDb();
        Set<String> allowedPrograms = Set.of("CSE", "AI", "SWE");
        OnboardingService svc = new OnboardingService(db, allowedPrograms);

        System.out.println("--- Test Case 1: Valid Input ---");
        String validRaw = "name=Riya;email=riya@sst.edu;phone=9876543210;program=CSE";
        svc.registerFromRawInput(validRaw);

        System.out.println("\n--- Test Case 2: Invalid Input (Stretch Goal) ---");
        String invalidRaw = "name=;email=invalidEmail;phone=abc;program=XYZ";
        svc.registerFromRawInput(invalidRaw);

        System.out.println("\n-- DB DUMP --");
        System.out.print(TextTable.render3(db));
    }
}
