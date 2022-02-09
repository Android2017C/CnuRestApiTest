package com.cnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.dto.ProducerDTO;
import com.cnu.service.ProducerService;
import com.cnu.test.pojo.UserDTO;

@RestController
@RequestMapping("cnu/producerData")
public class ProducerController {
	@Autowired
	@Qualifier("JPA_ProducerService") // IF data from Mysql database use ===> @Qualifier("JPA_SERVICE")
	//// IF data from Mongo database use ===> @Qualifier("MONGO_SERVICE")
	private ProducerService producerService;

	@PostMapping(value = "/create", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ProducerDTO createProducer(@RequestBody ProducerDTO producerDTO) {

		return producerService.createProducer(producerDTO);

	}

	@PutMapping(value = "/update/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ProducerDTO updateProducer(@PathVariable("id") int producerID, @RequestBody ProducerDTO producerDTO) {

		return producerService.updateProducer(producerID, producerDTO);
	}

	@GetMapping(value = "/getAll", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<ProducerDTO> getAllProducerElements() {

		return producerService.getAllProducerElements();
	}

	@DeleteMapping(value = "/deleteAll")
	public boolean deleteProducer() {

		return producerService.deleteAllProducers();
	}

	@GetMapping(value = "/findByID/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ProducerDTO getProducerById(@PathVariable("id") int producerId) {

		return producerService.getProducerById(producerId);
	}

	@DeleteMapping(value = "/deleteByID/{id}")
	public boolean deleteProducerByID(@PathVariable("id") int producerID) {

		return producerService.deleteProducerByID(producerID);
	}
	@PostMapping(value = "/user", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public UserDTO getuser(@RequestBody UserDTO userDTO) {
		
		UserDTO dto=new UserDTO();
		dto.setUsername(userDTO.getUsername());
		dto.setId(userDTO.getId());
		dto.setPassword(userDTO.getPassword());
		dto.setLoggedIn(userDTO.isLoggedIn());
		dto.setRegisterDTO(userDTO.getRegisterDTO());
		return dto;
	}
	@PostMapping(value = "/userResponse", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserDTO> getuserDetails(@RequestBody UserDTO userDTO) {
		
		UserDTO dto=new UserDTO();
		dto.setUsername(userDTO.getUsername());
		dto.setId(userDTO.getId());
		dto.setPassword(userDTO.getPassword());
		dto.setLoggedIn(userDTO.isLoggedIn());
		dto.setRegisterDTO(userDTO.getRegisterDTO());
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
}
