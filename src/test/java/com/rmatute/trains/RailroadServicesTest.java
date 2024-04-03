package com.rmatute.trains;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import com.rmatute.trains.services.BestRailroadServices;
import com.rmatute.trains.services.RailroadServices;
import com.rmatute.trains.services.TrainRoutesServices;
import static org.junit.Assert.assertEquals;

public class RailroadServicesTest {
	
	
	RailroadServices railroadServices;
	
	@Before
    public void setUp() {
		railroadServices = new RailroadServices();
		
        // Configurar rutas comunes para todas las pruebas
		railroadServices.addRoute("A", "B", 5);
		railroadServices.addRoute("B", "C", 4);
		railroadServices.addRoute("C", "D", 8);
		railroadServices.addRoute("D", "C", 8);
		railroadServices.addRoute("D", "E", 6);
		railroadServices.addRoute("A", "D", 5);
		railroadServices.addRoute("C", "E", 2);
		railroadServices.addRoute("E", "B", 3);
		railroadServices.addRoute("A", "E", 7);
    }
	
	@Test
    public void testDistanceOfRouteABC() {
//        assertEquals(9, railroadServices.distanceOfRoute("A", "B", "C"));
    }

    @Test
    public void testDistanceOfRouteAD() {
//        assertEquals(5, railroadServices.distanceOfRoute("A", "D"));
    }

    @Test
    public void testDistanceOfRouteADC() {
//        assertEquals(13, railroadServices.distanceOfRoute("A", "D", "C"));
    }

    @Test
    public void testDistanceOfRouteAEBCD() {
//        assertEquals(22, railroadServices.distanceOfRoute("A", "E", "B", "C", "D"));
    }

    @Test
    public void testDistanceOfRouteAED() {
//        assertEquals(-1, railroadServices.distanceOfRoute("A", "E", "D"));
    }

    @Test
    public void testNumberOfTripsStartingAtCAndEndingAtCWithMaxStops() {
//        assertEquals(2, railroadServices.countTripsWithMaxStops("C", "C", 3));
    }

    @Test
    public void testNumberOfTripsStartingAtAAndEndingAtCWithExactly4Stops() {
//        assertEquals(3, railroadServices.countTripsWithExactStops("A", "C", 4));
    }

    @Test
    public void testShortestRouteFromAToC() {
//        assertEquals(9, railroadServices.shortestRoute("A", "C"));
    }

    @Test
    public void testShortestRouteFromBToB() {
//        assertEquals(9, railroadServices.shortestRoute("B", "B"));
    }

    @Test
    public void testNumberOfDifferentRoutesFromCToCWithDistanceLessThan30() {
//        assertEquals(7, railroadServices.countDifferentRoutesWithDistanceLessThan("A", "E", 30));
    }

}
