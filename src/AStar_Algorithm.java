import java.util.*;

// Node class representing each state in the graph
class Node implements Comparable<Node> {
    String name;    // Node name
    int g;          // Cost from the start node
    int h;          // Heuristic estimate to goal
    Node parent;    // Parent node for path reconstruction

    public Node(String name, int g, int h, Node parent) {
        this.name = name;
        this.g = g;
        this.h = h;
        this.parent = parent;
    }

    public int getF() {
        return g + h; // f(n) = g(n) + h(n)
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.getF(), other.getF());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

// A* Algorithm class
class AStarAlgorithm {
    private final Map<String, List<Node>> graph; // Adjacency list
    private final Map<String, Integer> heuristic; // Heuristic values for nodes

    public AStarAlgorithm() {
        graph = new HashMap<>();
        heuristic = new HashMap<>();
    }

    // Add an edge between two nodes with a cost
    public void addEdge(String from, String to, int cost) {
        graph.putIfAbsent(from, new ArrayList<>());
        graph.get(from).add(new Node(to, cost, 0, null));
    }

    // Set heuristic for a node
    public void setHeuristic(String node, int value) {
        heuristic.put(node, value);
    }

    // Perform the A* Search algorithm
    public List<String> aStarSearch(String start, String goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<String> closedList = new HashSet<>();
        Map<String, Integer> gScores = new HashMap<>();

        openList.add(new Node(start, 0, heuristic.getOrDefault(start, 0), null));
        gScores.put(start, 0);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            // If the goal is reached, reconstruct the path
            if (current.name.equals(goal)) {
                return reconstructPath(current);
            }

            closedList.add(current.name);

            // Explore neighbors
            for (Node neighbor : graph.getOrDefault(current.name, new ArrayList<>())) {
                if (closedList.contains(neighbor.name)) {
                    continue; // Skip already visited nodes
                }

                int tentativeG = current.g + neighbor.g;

                if (!gScores.containsKey(neighbor.name) || tentativeG < gScores.get(neighbor.name)) {
                    gScores.put(neighbor.name, tentativeG);
                    Node neighborNode = new Node(
                            neighbor.name,
                            tentativeG,
                            heuristic.getOrDefault(neighbor.name, 0),
                            current
                    );

                    openList.add(neighborNode);
                }
            }
        }

        // If no path is found
        return null;
    }

    // Reconstruct the path from goal to start
    private List<String> reconstructPath(Node node) {
        List<String> path = new ArrayList<>();
        while (node != null) {
            path.add(node.name);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    // Display the graph (for debugging purposes)
    public void displayGraph() {
        for (Map.Entry<String, List<Node>> entry : graph.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (Node neighbor : entry.getValue()) {
                System.out.print(neighbor.name + "(" + neighbor.g + ") ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        AStarAlgorithm astar = new AStarAlgorithm();

        // Define the graph (nodes and edges with costs)
        astar.addEdge("A", "B", 1);
        astar.addEdge("A", "C", 4);
        astar.addEdge("B", "D", 2);
        astar.addEdge("C", "D", 1);
        astar.addEdge("D", "E", 3);

        // Define heuristic values (estimated cost to reach the goal)
        astar.setHeuristic("A", 5);
        astar.setHeuristic("B", 3);
        astar.setHeuristic("C", 2);
        astar.setHeuristic("D", 1);
        astar.setHeuristic("E", 0);

        // Display the graph structure
        System.out.println("Graph Structure:");
        astar.displayGraph();

        // Perform A* Search
        System.out.println("\nPerforming A* Search from A to E...");
        List<String> path = astar.aStarSearch("A", "E");

        if (path != null) {
            System.out.println("Path found: " + path);
        } else {
            System.out.println("No path found.");
        }
    }
}
