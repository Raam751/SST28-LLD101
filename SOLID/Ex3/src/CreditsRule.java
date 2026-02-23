public class CreditsRule implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile profile) {
        if (profile.earnedCredits < 20) {
            return "credits below 20";
        }
        return null;
    }
}
