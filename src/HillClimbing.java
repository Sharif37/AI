import java.util.*;

public class HillClimbing {
    // Objective function (example: maximizing a simple quadratic function)
    public static double objectiveFunction(double x) {
        return -Math.pow(x, 2) + 4 * x; // Example function: f(x) = -x^2 + 4x
    }

    public static double hillClimb(double start, double stepSize, int maxIterations) {
        double current = start;
        double currentValue = objectiveFunction(current);

        for (int i = 0; i < maxIterations; i++) {
            double next = current + stepSize; // Move right
            double prev = current - stepSize; // Move left

            double nextValue = objectiveFunction(next);
            double prevValue = objectiveFunction(prev);

            if (nextValue > currentValue) {
                current = next;
                currentValue = nextValue;
            } else if (prevValue > currentValue) {
                current = prev;
                currentValue = prevValue;
            } else {
                // Local maximum found
                break;
            }
        }
        return current;
    }

    public static void main(String[] args) {
        double start = 0; // Starting point
        double stepSize = 0.1; // Step size for neighbors
        int maxIterations = 1000; // Maximum number of iterations

        double result = hillClimb(start, stepSize, maxIterations);
        System.out.println("Local Maximum Found at: " + result);
        System.out.println("Maximum Value: " + objectiveFunction(result));
    }
}
