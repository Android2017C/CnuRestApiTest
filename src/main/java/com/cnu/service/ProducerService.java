package com.cnu.service;

import java.util.List;

import com.cnu.dto.ProducerDTO;

public interface ProducerService {

	public ProducerDTO createProducer(ProducerDTO producerDTO);

	public ProducerDTO updateProducer(int producerID, ProducerDTO producerDTO);

	public ProducerDTO getProducerById(int producerId);

	public List<ProducerDTO> getAllProducerElements();

	public boolean deleteProducerByID(int producerID);

	public boolean deleteAllProducers();

}
