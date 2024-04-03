package com.rmatute.trains.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class RailroadServices {
	
	private final Map<String, Map<String, Integer>> graph;

    public RailroadServices() {
        graph = new HashMap<>();
    }

    public RailroadServices addRoute(String start, String end, int distance) {
        graph.computeIfAbsent(start, k -> new HashMap<>()).put(end, distance);
        return this;
    }

    public int distanceOfRoute(String... route) {
        return Stream.iterate(0, i -> i + 1)
                .limit(route.length - 1)
                .map(i -> graph.getOrDefault(route[i], new HashMap<>()).getOrDefault(route[i + 1], -1))
                .reduce(0, (subtotal, element) -> element == -1 ? -1 : subtotal + element);
    }

    public long countTripsWithMaxStops(String start, String end, int maxStops) {
        return countTripsWithMaxStopsHelper(start, end, maxStops, 0);
    }

    private long countTripsWithMaxStopsHelper(String current, String end, int maxStops, int stops) {
        if (stops > maxStops) return 0;
        if (current.equals(end) && stops > 0) return 1;
        return graph.getOrDefault(current, new HashMap<>()).keySet().stream()
                .mapToLong(neighbor -> countTripsWithMaxStopsHelper(neighbor, end, maxStops, stops + 1))
                .sum();
    }

    public long countTripsWithExactStops(String start, String end, int exactStops) {
        return countTripsWithExactStopsHelper(start, end, exactStops, 0);
    }

    private long countTripsWithExactStopsHelper(String current, String end, int exactStops, int stops) {
        if (stops == exactStops && current.equals(end)) return 1;
        if (stops >= exactStops) return 0;
        return graph.getOrDefault(current, new HashMap<>()).keySet().stream()
                .mapToLong(neighbor -> countTripsWithExactStopsHelper(neighbor, end, exactStops, stops + 1))
                .sum();
    }

    public int shortestRoute2(String start, String end) {
        return shortestRouteHelper(start, end, new HashMap<>());
    }
    
    public int shortestRoute(String start, String end) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        
        // Inicializar distancias a infinito, excepto para el nodo de inicio
        graph.keySet().forEach(city -> distances.put(city, city.equals(start) ? 0 : Integer.MAX_VALUE));
        pq.add(start);
        
        while (!pq.isEmpty()) {
            String current = pq.poll();
            if (current.equals(end)) return distances.get(end);
            if (!graph.containsKey(current)) continue;
            for (String neighbor : graph.get(current).keySet()) {
                int distance = distances.get(current) + graph.get(current).get(neighbor);
                if (distance < distances.get(neighbor)) {
                    distances.put(neighbor, distance);
                    pq.add(neighbor);
                }
            }
        }
        
        // Si no se puede llegar al nodo de destino
        return -1;
    }
    
    public Map.Entry<Integer, List<String>> shortestRouteWithPath(String start, String end) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        
        // Inicializar distancias a infinito, excepto para el nodo de inicio
        graph.keySet().forEach(city -> distances.put(city, city.equals(start) ? 0 : Integer.MAX_VALUE));
        pq.add(start);
        
        while (!pq.isEmpty()) {
            String current = pq.poll();
            if (current.equals(end)) {
                // Reconstruir el camino más corto
                List<String> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = previous.get(current);
                }
                Collections.reverse(path);
                return Map.entry(distances.get(end), path);
            }
            if (!graph.containsKey(current)) continue;
            for (String neighbor : graph.get(current).keySet()) {
                int distance = distances.get(current) + graph.get(current).get(neighbor);
                if (distance < distances.get(neighbor)) {
                    distances.put(neighbor, distance);
                    previous.put(neighbor, current); // Actualizar el nodo anterior para el camino más corto
                    pq.add(neighbor);
                }
            }
        }
        
        // Si no se puede llegar al nodo de destino
        return Map.entry(-1, Collections.emptyList());
    }

    private int shortestRouteHelper(String current, String end, Map<String, Integer> visited) {
        if (current.equals(end)) return 0;
        if (visited.containsKey(current)) return visited.get(current);
        int shortest = Integer.MAX_VALUE;
        if (graph.containsKey(current)) {
            for (String neighbor : graph.get(current).keySet()) {
                int distance = graph.get(current).get(neighbor) + shortestRouteHelper(neighbor, end, visited);
                shortest = Math.min(shortest, distance);
            }
        }
        visited.put(current, shortest);
        return shortest;
    }

    public long countDifferentRoutesWithDistanceLessThan(String start, String end, int maxDistance) {
        return countDifferentRoutesWithDistanceLessThanHelper(start, end, maxDistance, 0);
    }

    private long countDifferentRoutesWithDistanceLessThanHelper(String current, String end, int maxDistance, int distance) {
        if (distance >= maxDistance) return 0;
        if (current.equals(end) && distance > 0) return 1;
        return graph.getOrDefault(current, new HashMap<>()).keySet().stream()
                .mapToLong(neighbor -> countDifferentRoutesWithDistanceLessThanHelper(neighbor, end, maxDistance, distance + graph.get(current).get(neighbor)))
                .sum();
    }
    
    private void dfs(String current, String end, Set<String> visited, List<String> path, List<List<String>> paths) {
        visited.add(current);
        path.add(current);

        if (current.equals(end)) {
            paths.add(new ArrayList<>(path));
        } else {
            Map<String, Integer> neighbors = graph.getOrDefault(current, Collections.emptyMap());
            for (String neighbor : neighbors.keySet()) {
                if (!visited.contains(neighbor)) {
                    dfs(neighbor, end, visited, path, paths);
                }
            }
        }

        visited.remove(current);
        path.remove(path.size() - 1);
    }
    
    public List<List<String>> allPathsFromBToB() {
    	List<List<String>> paths = new ArrayList<>();
        dfs("B", "B", new HashSet<>(), new ArrayList<>(), paths);
        return paths;
    }
    
    public Map showRoutes() {
    	return this.graph;
    }

}
