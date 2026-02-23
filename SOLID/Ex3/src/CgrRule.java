public class CgrRule implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile profile) {
        if (profile.cgr < 8.0) {
            return "CGR below 8.0";
        }
        return null;
    }
}
