public interface EligibilityRule {
    /**
     * Evaluates a student profile.
     * 
     * @return A reason string if the student fails the rule, or null if they pass.
     */
    String evaluate(StudentProfile profile);
}
