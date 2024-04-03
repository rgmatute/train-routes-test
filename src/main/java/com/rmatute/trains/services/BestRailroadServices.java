package com.rmatute.trains.services;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.stereotype.Service;

@Service
public class BestRailroadServices {
	
	private final Map<String, Map<String, Integer>> graph;

    public BestRailroadServices() {
        graph = new HashMap<>();
    }

    public void addRoute(String start, String end, int distance) {
        graph.computeIfAbsent(start, k -> new HashMap<>()).put(end, distance);
    }
    
    public Map.Entry<Integer, List<String>> distanceOfRoute(String... route) {
        List<String> path = new ArrayList<>();
        int distance = 0;
        for (int i = 0; i < route.length - 1; i++) { 
            String current = route[i];
            String next = route[i + 1];
            if (!graph.containsKey(current) || !graph.get(current).containsKey(next)) {
                return new AbstractMap.SimpleEntry<>(-1, Collections.emptyList()); // Si no hay ruta válida
            }
            path.add(current);
            distance += graph.get(current).get(next);
        }
        path.add(route[route.length - 1]);
        return new AbstractMap.SimpleEntry<>(distance, path);
    }

    public Map.Entry<Long, List<List<String>>> countTripsWithMaxStops(String start, String end, int maxStops) {
        paths = new ArrayList<>(); // Reiniciar paths
        return new AbstractMap.SimpleEntry<>(countTripsWithMaxStopsHelper(start, end, maxStops, 0, new ArrayList<>()), paths);
    }

    private long countTripsWithMaxStopsHelper(String current, String end, int maxStops, int stops, List<String> path) {
        if (stops > maxStops) return 0;
        if (current.equals(end) && stops > 0) {
            path.add(current);
            paths.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return 1;
        }
        long count = 0;
        if (graph.containsKey(current)) {
            path.add(current);
            for (String neighbor : graph.get(current).keySet()) {
                count += countTripsWithMaxStopsHelper(neighbor, end, maxStops, stops + 1, path);
            }
            path.remove(path.size() - 1);
        }
        return count;
    }

    public Map.Entry<Long, List<List<String>>> countTripsWithExactStops(String start, String end, int exactStops) {
        paths = new ArrayList<>(); // Reiniciar paths
        return new AbstractMap.SimpleEntry<>(countTripsWithExactStopsHelper(start, end, exactStops, 0, new ArrayList<>()), paths);
    }

    private long countTripsWithExactStopsHelper(String current, String end, int exactStops, int stops, List<String> path) {
        if (stops == exactStops && current.equals(end)) {
            path.add(current);
            paths.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return 1;
        }
        if (stops >= exactStops) return 0;
        long count = 0;
        if (graph.containsKey(current)) {
            path.add(current);
            for (String neighbor : graph.get(current).keySet()) {
                count += countTripsWithExactStopsHelper(neighbor, end, exactStops, stops + 1, path);
            }
            path.remove(path.size() - 1);
        }
        return count;
    }
    
    public Map.Entry<Integer, List<String>> shortestRoute(String start, String end) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        
        // Inicializar distancias a infinito, excepto para el nodo de inicio
        graph.keySet().forEach(city -> distances.put(city, city.equals(start) ? 0 : Integer.MAX_VALUE));
        pq.add(start);
        
        while (!pq.isEmpty()) {
            String current = pq.poll();
            if (current.equals(end)) {
                List<String> path = new ArrayList<>();
                String node = current;
                while (node != null) {
                    path.add(0, node);
                    node = previous.get(node);
                }
                return new AbstractMap.SimpleEntry<>(distances.get(end), path);
            }
            if (!graph.containsKey(current)) continue;
            for (String neighbor : graph.get(current).keySet()) {
                int distance = distances.get(current) + graph.get(current).get(neighbor);
                if (distance < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, distance);
                    previous.put(neighbor, current);
                    pq.add(neighbor);
                }
            }
        }
        
        // Si no se puede llegar al nodo de destino
        return new AbstractMap.SimpleEntry<>(-1, Collections.emptyList());
    }

    public Map.Entry<Long, List<List<String>>> countDifferentRoutesWithDistanceLessThan(String start, String end, int maxDistance) {
        paths = new ArrayList<>(); // Reiniciar paths
        return new AbstractMap.SimpleEntry<>(countDifferentRoutesWithDistanceLessThanHelper(start, end, maxDistance, 0, new ArrayList<>()), paths);
    }

    private long countDifferentRoutesWithDistanceLessThanHelper(String current, String end, int maxDistance, int distance, List<String> path) {
        if (distance >= maxDistance) return 0;
        if (current.equals(end) && distance > 0) {
            path.add(current);
            paths.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return 1;
        }
        long count = 0;
        if (graph.containsKey(current)) {
            path.add(current);
            for (String neighbor : graph.get(current).keySet()) {
                int newDistance = distance + graph.get(current).get(neighbor);
                count += countDifferentRoutesWithDistanceLessThanHelper(neighbor, end, maxDistance, newDistance, path);
            }
            path.remove(path.size() - 1);
        }
        return count;
    }
    
    public int numberOfRoutesWithDistanceLessThan(String start, String end, int maxDistance) {
        return dfsCountRoutesWithDistanceLessThan(start, end, 0, maxDistance);
    }

    private int dfsCountRoutesWithDistanceLessThan(String current, String end, int currentDistance, int maxDistance) {
        if (currentDistance >= maxDistance) {
            return 0;
        }
        if (current.equals(end) && currentDistance > 0) {
            return 1;
        }
        int count = 0;
        if (!graph.containsKey(current)) {
            return 0;
        }
        for (String neighbor : graph.get(current).keySet()) {
            int distanceToNeighbor = graph.get(current).get(neighbor);
            count += dfsCountRoutesWithDistanceLessThan(neighbor, end, currentDistance + distanceToNeighbor, maxDistance);
        }
        return count;
    }
    
    public int numberOfTrips(String start, String end, int maxStops) {
        return dfsCountTrips(start, end, 0, maxStops);
    }

    private int dfsCountTrips(String current, String end, int stops, int maxStops) {
        if (stops > maxStops) {
            return 0;
        }
        if (current.equals(end) && stops > 0) {
            return 1;
        }
        int count = 0;
        if (!graph.containsKey(current)) {
            return 0;
        }
        for (String neighbor : graph.get(current).keySet()) {
            count += dfsCountTrips(neighbor, end, stops + 1, maxStops);
        }
        return count;
    }


    private List<List<String>> paths = new ArrayList<>();
    
    public Map showRoutes() {
    	return this.graph;
    }

}
