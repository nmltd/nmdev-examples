/* Copyright (c) NM LTD. See the repository LICENSE. */
package dev.nm.benchmark;

import java.util.Random;

/** Identical seeded, well-conditioned inputs for all adapters. */
final class Inputs {
    final double[][] a, b, spd, rhs;
    Inputs(int n) {
        Random random = new Random(20261018L + n);
        a = new double[n][n]; b = new double[n][n]; rhs = new double[n][1];
        for (int i=0;i<n;i++) {
            rhs[i][0] = random.nextDouble()-0.5;
            for(int j=0;j<n;j++) {
                a[i][j] = (random.nextDouble()-0.5)/n;
                b[i][j] = (random.nextDouble()-0.5)/n;
            }
            a[i][i] += 1.0 + (double)i/n;
        }
        spd = Validation.multiply(Validation.transpose(a),a);
    }
}
