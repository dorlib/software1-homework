package il.ac.tau.cs.sw1.ex7;
import java.util.*;


public class Graph implements Greedy<Graph.Edge> {
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,..., n]

    Graph(int n1, List<Edge> lst1) {
        n = n1;
        lst = lst1;
    }

    public static class Edge {
        int node1, node2;
        double weight;

        Edge(int n1, int n2, double w) {
            node1 = n1;
            node2 = n2;
            weight = w;
        }

        @Override
        public String toString() {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }
    }

    @Override
    public Iterator<Edge> selection() {
        lst = sortEdges(lst);
        return lst.iterator();
    }

    @Override
    public boolean feasibility(List<Edge> candidates_lst, Edge element) {
        for (Edge edge : candidates_lst) {
            int[] routes = getRoute(element, edge);

            if (routes != null) {
                if (checkRoute(candidates_lst, edge, routes[0])) {

                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void assign(List<Edge> candidates_lst, Edge element) {
        if (element != null) {
            candidates_lst.add(element);
        }
    }

    @Override
    public boolean solution(List<Edge> candidates_lst) {
        // if there are more than n edges, there must be an cycle.
        if (candidates_lst != null) {
            return candidates_lst.size() == n;
        }

        return false;
    }

    private boolean checkRoute(List<Edge> lst, Edge startEdge, int finishNode) {
        if (lst.size() == 0) {
            return true;
        }

        return checkRouteRec(lst, startEdge, finishNode);
    }

    private boolean checkRouteRec(List<Edge> lst, Edge startEdge, int finishNode) {
        for (Edge edge : lst) {
            int[] routes = getRoute(startEdge, edge);

            if (routes != null) {
                if (routes[1] == finishNode) {
                    return true;
                }

                List<Edge> arr = new ArrayList<>();
                for (Edge e : lst) {
                    if (e != edge) {
                        arr.add(e);
                    }
                }

                return checkRoute(arr, edge, finishNode);
            }
        }

        return false;
    }

    private int[] getRoute(Edge startEdge, Edge finishEdge) {
        int[] result = new int[2];
        if (startEdge.node1 == finishEdge.node1) {
            result[0] = startEdge.node2;
            result[1] = finishEdge.node2;
        } else if (startEdge.node2 == finishEdge.node2) {
            result[0] = startEdge.node1;
            result[1] = finishEdge.node1;
        } else if (startEdge.node1 == finishEdge.node2) {
            result[0] = startEdge.node2;
            result[1] = finishEdge.node1;
        } else if (startEdge.node2 == finishEdge.node1) {
            result[0] = startEdge.node1;
            result[1] = finishEdge.node2;
        } else {
            result = null;
        }

        return result;
    }

    private List<Edge> sortEdges(List<Edge> lst) {
        List<Edge> candidates = new ArrayList<>();
        Iterator<Edge> iterator = lst.iterator();

        if (iterator.hasNext()) {
            candidates.add(iterator.next());
        } else {
            return candidates;
        }

        while (iterator.hasNext()) {
            Edge edge = iterator.next();
            boolean entered = false;

            for (int i = 0; i < candidates.size(); i++) {
                if (edge.weight < candidates.get(i).weight) {
                    candidates.add(i, edge);
                    entered = true;
                    break;
                } else if (edge.weight == candidates.get(i).weight && edge.node1 < candidates.get(i).node1) {
                    candidates.add(i, edge);
                    entered = true;
                    break;
                } else if (edge.weight == candidates.get(i).weight && edge.node1 == candidates.get(i).node1 && edge.node2 < candidates.get(i).node2) {
                    candidates.add(i, edge);
                    entered = true;
                    break;
                } else if (i == candidates.size() - 1) {
                    break;
                }
            }

            if (!entered) {
                candidates.add(edge);
            }
        }

        return candidates;
    }
}