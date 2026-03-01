public class StrictGrader implements Grader {
    @Override
    public int grade(Submission s, Rubric r) {
        // Stricter grading: no bonus, harsher base calculation
        int base = Math.min(60, 30 + s.code.length() % 30);
        return base;
    }
}
