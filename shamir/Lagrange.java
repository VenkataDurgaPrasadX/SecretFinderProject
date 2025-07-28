package shamir;

import java.math.BigInteger;

public class Lagrange {
    public static BigInteger interpolateAtZero(BigInteger[] x, BigInteger[] y) {
        BigInteger result = BigInteger.ZERO;

        for (int i = 0; i < x.length; i++) {
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            for (int j = 0; j < x.length; j++) {
                if (i != j) {
                    numerator = numerator.multiply(x[j].negate());
                    denominator = denominator.multiply(x[i].subtract(x[j]));
                }
            }

            BigInteger term = y[i].multiply(numerator).divide(denominator);
            result = result.add(term);
        }

        return result;
    }
}
