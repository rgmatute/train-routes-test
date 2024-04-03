package com.rmatute.trains.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rmatute.trains.controller.VM.TrainRoutesVM;
import com.rmatute.trains.services.TrainRoutesServices;
import com.rmatute.trains.utils.GenericResponse;

@RequestMapping("/api")
@RestController
public class TrainRoutesController {
	
	private final TrainRoutesServices trainRoutesServices;
	
	public TrainRoutesController(TrainRoutesServices trainRoutesServices) {
		this.trainRoutesServices = trainRoutesServices;
	}
	
	
	@GetMapping(value = "train")
	public ResponseEntity<GenericResponse<Map>> resolberProblema() {
		
		return ResponseEntity.ok(trainRoutesServices.baseResolve());
	}
	
	@PostMapping(value = "train")
	public ResponseEntity<GenericResponse<Map>> resolberProblema(@RequestBody TrainRoutesVM trainRoutesVM) {
		
		
		return null;
	}

}
