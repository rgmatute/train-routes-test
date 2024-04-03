package com.rmatute.trains;

import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rmatute.trains.services.BestRailroadServices;
import com.rmatute.trains.services.RailroadServices;
import com.rmatute.trains.utils.Utils;

@SpringBootApplication
public class BackTrainsTestApplication {

	public static void main(String[] args) {
		
		// inicia la aplicación spring boot
		 SpringApplication.run(BackTrainsTestApplication.class, args);
		
		
		// resuelve el problema de los trenes
				
		
		RailroadServices railroad = new RailroadServices();
		
		// rutas
        railroad.addRoute("A", "B", 5);
        railroad.addRoute("B", "C", 4);
        railroad.addRoute("C", "D", 8);
        railroad.addRoute("D", "C", 8);
        railroad.addRoute("D", "E", 6);
        railroad.addRoute("A", "D", 5);
        railroad.addRoute("C", "E", 2);
        railroad.addRoute("E", "B", 3);
        railroad.addRoute("A", "E", 7);
        System.out.println("\n");

        // resultados
        
        Utils.debug("RESULTADO NORMAL", 
        		Utils.replaceText(
        				"Output #1: {}"
        				+ "\nOutput #2: {}"
        				+ "\nOutput #3: {}"
        				+ "\nOutput #4: {}"
        				+ "\nOutput #5: {}"
        				+ "\nOutput #6: {}"
        				+ "\nOutput #7: {}"
        				+ "\nOutput #8: {}"
        				+ "\nOutput #9: {}"
        				+ "\nOutput #10: {}", 
        				railroad.distanceOfRoute("A", "B", "C"), 
        				railroad.distanceOfRoute("A", "D"), 
        				railroad.distanceOfRoute("A", "D", "C"),
        				railroad.distanceOfRoute("A", "E", "B", "C", "D"),
        				(railroad.distanceOfRoute("A", "E", "D") == -1 ? "NO SUCH ROUTE" : railroad.distanceOfRoute("A", "E", "D")),
        				railroad.countTripsWithMaxStops("C", "C", 3),
        				railroad.countTripsWithExactStops("A", "C", 4),
        				railroad.shortestRoute("A", "C"),
        				railroad.shortestRoute("B", "B"),
        				railroad.countDifferentRoutesWithDistanceLessThan("A", "E", 30)
        		)
        );
        
        
        System.out.println("\n***** RESULTADO CON CAMINOS VISIBLES *****");
        
        BestRailroadServices bestRailroadServices = new BestRailroadServices();
        
     // rutas
        bestRailroadServices.addRoute("A", "B", 5);
        bestRailroadServices.addRoute("B", "C", 4);
        bestRailroadServices.addRoute("C", "D", 8);
        bestRailroadServices.addRoute("D", "C", 8);
        bestRailroadServices.addRoute("D", "E", 6);
        bestRailroadServices.addRoute("A", "D", 5);
        bestRailroadServices.addRoute("C", "E", 2);
        bestRailroadServices.addRoute("E", "B", 3);
        bestRailroadServices.addRoute("A", "E", 7);
        System.out.println("\n");
        
     // resultados
        
        Map.Entry<Integer, List<String>> result1 = bestRailroadServices.distanceOfRoute("A", "B", "C");
        System.out.println("Output #1: Distance: " + result1.getKey() + ", Path: " + result1.getValue());
        
        Map.Entry<Integer, List<String>> result2 = bestRailroadServices.distanceOfRoute("A", "D");
        System.out.println("Output #2: Distance: " + result2.getKey() + ", Path: " + result2.getValue());
        
        Map.Entry<Integer, List<String>> result3 = bestRailroadServices.distanceOfRoute("A", "D", "C");
        System.out.println("Output #3: Distance: " + result3.getKey() + ", Path: " + result3.getValue());
        
        Map.Entry<Integer, List<String>> result4 = bestRailroadServices.distanceOfRoute("A", "E", "B", "C", "D");
        System.out.println("Output #4: Distance: " + result4.getKey() + ", Path: " + result4.getValue());
        
        Map.Entry<Integer, List<String>> result5 = bestRailroadServices.distanceOfRoute("A", "E", "D");
        System.out.println("Output #5: Distance: " + (result5.getKey() == -1 ? "NO SUCH ROUTE" : result5.getKey()) + ", Path: " + result5.getValue());
        
        Map.Entry<Long, List<List<String>>> result6 = bestRailroadServices.countTripsWithMaxStops("C", "C", 3);
        System.out.println("Output #6: Count: " + result6.getKey() + ", Paths: " + result6.getValue());
        
        Map.Entry<Long, List<List<String>>> result7 = bestRailroadServices.countTripsWithExactStops("A", "C", 4);
        System.out.println("Output #7: Count: " + result7.getKey() + ", Paths: " + result7.getValue());
        
        Map.Entry<Integer, List<String>> result8 = bestRailroadServices.shortestRoute("A", "C");
        System.out.println("Output #8: Distance: " + result8.getKey() + ", Path: " + result8.getValue());
        
        Map.Entry<Integer, List<String>> result9 = bestRailroadServices.shortestRoute("B", "B");
        System.out.println("Output #9: Distance: " + result9.getKey() + ", Path: " + result9.getValue());
        
        Map.Entry<Long, List<List<String>>> result10 = bestRailroadServices.countDifferentRoutesWithDistanceLessThan("A", "E", 30);
        System.out.println("Output #10: Count: " + result10.getKey() + ", Paths: " + result10.getValue());
        
        System.out.println("\n***** *********************** *****");
        
	}

}
