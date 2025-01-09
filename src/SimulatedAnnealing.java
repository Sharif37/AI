import java.util.Random;

public class SimulatedAnnealing {

    // Objective function: -x^2 + 4x
    public static double objectiveFunction(double x) {
        return -Math.pow(x, 2) + 4 * x;
    }

    // Generate a neighboring solution
    public static double getNeighbor(double currentSolution, double stepSize) {
        Random random = new Random();
        return currentSolution + (random.nextDouble() * 2 - 1) * stepSize; // Random step in [-stepSize, stepSize]
    }

    // Simulated Annealing Algorithm
    public static double simulatedAnnealing(double initialTemp, double coolingRate, double stoppingTemp, double stepSize) {
        Random random = new Random();

        // Initial solution (random starting point between 0 and 4)
        double currentSolution = random.nextDouble() * 4;
        double currentEnergy = -objectiveFunction(currentSolution);
        double bestSolution = currentSolution;
        double bestEnergy = currentEnergy;

        double temperature = initialTemp;

        while (temperature > stoppingTemp) {
            double neighbor = getNeighbor(currentSolution, stepSize);

            // Keep neighbor within bounds [0,4]
            if (neighbor < 0 || neighbor > 4) {
                continue;
            }

            double neighborEnergy = -objectiveFunction(neighbor);

            // Calculate energy difference
            double deltaEnergy = neighborEnergy - currentEnergy;

            // Acceptance criteria
            if (deltaEnergy < 0 || Math.exp(-deltaEnergy / temperature) > random.nextDouble()) {
                currentSolution = neighbor;
                currentEnergy = neighborEnergy;

                // Update best solution if found
                if (currentEnergy < bestEnergy) {
                    bestSolution = currentSolution;
                    bestEnergy = currentEnergy;
                }
            }

            // Cool down the temperature
            temperature *= coolingRate;
        }

        return bestSolution;
    }

    public static void main(String[] args) {
        double initialTemp = 1000; // Initial temperature
        double coolingRate = 0.95; // Cooling rate (between 0 and 1)
        double stoppingTemp = 0.01; // Stopping temperature
        double stepSize = 0.1; // Step size for generating neighbors

        double result = simulatedAnnealing(initialTemp, coolingRate, stoppingTemp, stepSize);
        System.out.println("Best Solution Found: x = " + result);
        System.out.println("Maximum Value: f(x) = " + objectiveFunction(result));
    }
}
