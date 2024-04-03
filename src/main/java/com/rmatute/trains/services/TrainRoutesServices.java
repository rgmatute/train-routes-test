package com.rmatute.trains.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import com.rmatute.trains.utils.GenericException;
import com.rmatute.trains.utils.GenericResponse;

@SuppressWarnings("rawtypes")
@Service
public class TrainRoutesServices {
	
    private final BestRailroadServices railroadServices;
    
    private final Environment environment;

    public TrainRoutesServices(BestRailroadServices railroadServices, Environment environment) {
        this.railroadServices = railroadServices;
        this.environment = environment;
    }
    
	public GenericResponse<Map> baseResolve() {
    	
    	String nodosBase = environment.getProperty("nodosBase");
    	
    	if(Objects.isNull(nodosBase)) {
    		throw new GenericException("Especificación de nodos, no presente en el properties.", Status.BAD_REQUEST);
    	}
    	
    	String[] nodos = nodosBase.split(",");
    	
    	for (String nodeRoute : nodos) {
    		if(nodeRoute.length() == 3) {
        		railroadServices.addRoute(
        				Objects.toString(nodeRoute.charAt(0)), 
        				Objects.toString(nodeRoute.charAt(1)), 
        				Integer.parseInt(Objects.toString(nodeRoute.charAt(2), "0"))
        		);
    		} else {
    			throw new GenericException("Verifique , que los nodos sean de 3 caracteres", Status.BAD_REQUEST);
    		}
        }
    	
    	LinkedHashMap<String, Object> response = new LinkedHashMap<>();
    	response.put("nodes", nodosBase);
    	response.put("connected_nodes", Objects.toString(railroadServices.showRoutes()));
    	
    	Entry<Integer, List<String>> entry1 = railroadServices.distanceOfRoute("A", "B", "C");
    	response.put("output_1", Map.of("value", entry1.getKey(), "path", Objects.toString(entry1.getValue())));
    	
    	
    	Entry<Integer, List<String>> entry2 = railroadServices.distanceOfRoute("A", "D");
    	response.put("output_2", Map.of("value", entry2.getKey(), "path", Objects.toString(entry2.getValue())));
    	
    	
    	Entry<Integer, List<String>> entry3 = railroadServices.distanceOfRoute("A", "D", "C");
    	response.put("output_3", Map.of("value", entry3.getKey(), "path", Objects.toString(entry3.getValue())));
    	
    	
    	Entry<Integer, List<String>> entry4 = railroadServices.distanceOfRoute("A", "E", "B", "C", "D");
    	response.put("output_4", Map.of("value", entry4.getKey(), "path", Objects.toString(entry4.getValue())));
    	
    	
    	Entry<Integer, List<String>> entry5 = railroadServices.distanceOfRoute("A", "E", "D");
    	response.put("output_5", Map.of("value", entry5.getKey() == -1 ? "NO SUCH ROUTE" : entry5.getKey() , "path", Objects.toString(entry5.getValue()))); //(railroadServices.distanceOfRoute("A", "E", "D").getKey() == -1 ? "NO SUCH ROUTE" : railroadServices.distanceOfRoute("A", "E", "D")));
    	
    	
    	Entry<Long, List<List<String>>> entry6 = railroadServices.countTripsWithMaxStops("C", "C", 3);
    	response.put("output_6", Map.of("value", entry6.getKey(), "path", Objects.toString(entry6.getValue())));
    	
    	
    	Entry<Long, List<List<String>>> entry7 = railroadServices.countTripsWithExactStops("A", "C", 4);
    	response.put("output_7", Map.of("value", entry7.getKey(), "path", Objects.toString(entry7.getValue())));
    	
    	
    	Entry<Integer, List<String>> entry8 = railroadServices.shortestRoute("A", "C");
    	response.put("output_8", Map.of("value", entry8.getKey(), "path", Objects.toString(entry8.getValue())));  
    	
    	
    	Entry<Integer, List<String>> entry9 = railroadServices.shortestRoute("B", "B");
    	response.put("output_9", Map.of("value", entry9.getKey(), "path", Objects.toString(entry9.getValue())));
    	
    	
    	Entry<Long, List<List<String>>> entry10 = railroadServices.countDifferentRoutesWithDistanceLessThan("A", "E", 30);
    	response.put("output_10", Map.of("value", entry10.getKey(), "path", Objects.toString(entry10.getValue())));
    	
    	return new GenericResponse<Map>()
    			.data(response)
    			.message("PROBLEM ONE: TRAINS - SOLVED");
    	
    }

}
