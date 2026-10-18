package dev.nm.examples;
import org.junit.Test;

/** Executes the tutorial programs, whose checks validate numerical results. */
public class TutorialExamplesTest {
    @Test public void cholesky() { CholeskyExample.main(new String[0]); }
    @Test public void rootFinding() throws Exception { RootFindingExample.main(new String[0]); }
    @Test public void interpolation() { InterpolationExample.main(new String[0]); }
    @Test public void normalProbabilities() { NormalProbabilityExample.main(new String[0]); }
    @Test public void regression() { LinearRegressionExample.main(new String[0]); }
    @Test public void quadraticProgramming() throws Exception { QuadraticProgrammingExample.main(new String[0]); }
}
