import java.util.*;

class node implements Comparable<node> {
    String name;
    int heuristic; // h(n)
    List<node> neighbors;

    public node(String name, int heuristic) {
        this.name = name;
        this.heuristic = heuristic;
        this.neighbors = new ArrayList<>();
    }

    public void addNeighbor(node neighbor) {
        this.neighbors.add(neighbor);
    }

    @Override
    public int compareTo(node other) {
        return Integer.compare(this.heuristic, other.heuristic);
    }
}

public class GreedySearch {
    public static void greedySearch(node start, node goal) {
        PriorityQueue<node> queue = new PriorityQueue<>();
        Set<node> visited = new HashSet<>();

        queue.add(start);

        while (!queue.isEmpty()) {
            node current = queue.poll();
            System.out.println("Visiting: " + current.name);

            if (current == goal) {
                System.out.println("Goal reached: " + goal.name);
                return;
            }

            visited.add(current);

            for (node neighbor : current.neighbors) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }
    }

    public static void main(String[] args) {
        // Create graph nodes with heuristic values
        node S = new node("S", 6);
        node A = new node("A", 4);
        node B = new node("B", 2);
        node C = new node("C", 6);
        node D = new node("D", 3);
        node G = new node("G", 0);

        // Add neighbors
        S.addNeighbor(A);
        S.addNeighbor(B);
        A.addNeighbor(C);
        A.addNeighbor(D);
        B.addNeighbor(G);

        // Perform Greedy Search
        greedySearch(S, G);
    }
}
