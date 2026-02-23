public class AttendanceRule implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile profile) {
        if (profile.attendancePct < 75) {
            return "attendance below 75";
        }
        return null;
    }
}
