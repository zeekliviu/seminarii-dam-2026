package ro.ase.dam.examen.grading;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract class GradedTest {

    @Rule
    public final TestRule gradingRule = new TestRule() {
        @Override
        public Statement apply(Statement base, Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    GradingScore.ensureHook();
                    Punctaj p = description.getAnnotation(Punctaj.class);
                    double pts = (p != null) ? p.puncte() : 0;
                    GradingScore.addPossible(pts);
                    try {
                        base.evaluate();
                        GradingScore.addEarned(pts);
                    } catch (Throwable t) {
                        GradingScore.failed(description.getMethodName(), pts);
                        throw t;
                    }
                }
            };
        }
    };
}
