package com.cnu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.dto.ProducerDTO;
import com.cnu.entity.ProducerEntity;
import com.cnu.repo.ProducerRepository;

@Service(value = "JPA_ProducerService")
public class ProducerServiceImpl implements ProducerService {

	@Autowired
	ProducerRepository producerRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProducerDTO createProducer(ProducerDTO producerDTO) {

		ProducerEntity producerEntity = getProducerEntityFromDTO(producerDTO);
		producerEntity = producerRepository.save(producerEntity);
		return getProducerDTODTOFromEntity(producerEntity);
	}

	@Override
	public ProducerDTO updateProducer(int producerID, ProducerDTO producerDTO) {
		Optional<ProducerEntity> optionalProducerEntity = producerRepository.findById(producerID);
		if (optionalProducerEntity.isPresent()) {
			ProducerEntity producerEntity = optionalProducerEntity.get();
			producerEntity.setCommand(producerDTO.getCommand());
			producerEntity.setItemDescription(producerDTO.getItemDescription());
			producerEntity.setItemHeight(producerDTO.getItemHeight());
			producerEntity.setItemLength(producerDTO.getItemLength());
			producerEntity.setItemName(producerDTO.getItemName());
			producerEntity.setItemWeight(producerDTO.getItemWeight());
			producerEntity.setItemWidth(producerDTO.getItemWidth());
			producerEntity.setMessageName(producerDTO.getMessageName());
			producerEntity.setStatus(producerDTO.getStatus());
			producerRepository.save(producerEntity);
		}
		else {
			
			System.out.println("entered id is not available in db");
		}
		return null;
	}

	@Override
	public ProducerDTO getProducerById(int producerId) {
		Optional<ProducerEntity> optionalProducerEntity = producerRepository.findById(producerId);
		if (optionalProducerEntity.isPresent()) {
			ProducerEntity producerEntity = optionalProducerEntity.get();
			return getProducerDTODTOFromEntity(producerEntity);
		}
		return null;
	}

	@Override
	public List<ProducerDTO> getAllProducerElements() {
		List<ProducerEntity> producerEntitiesList = producerRepository.findAll();
		return getProducerDTODTOListFromEntityList(producerEntitiesList);
	}

	@Override
	public boolean deleteProducerByID(int producerID) {
		producerRepository.deleteById(producerID);
		return true;
	}

	@Override
	public boolean deleteAllProducers() {
		producerRepository.deleteAll();
		return true;
	}

	private ProducerEntity getProducerEntityFromDTO(ProducerDTO producerDTO) {
		ProducerEntity advertisementEntity = this.modelMapper.map(producerDTO, ProducerEntity.class);
		return advertisementEntity;
	}

	private ProducerDTO getProducerDTODTOFromEntity(ProducerEntity producerEntity) {
		ProducerDTO ProducerDTO = this.modelMapper.map(producerEntity, ProducerDTO.class);
		return ProducerDTO;
	}

	private List<ProducerDTO> getProducerDTODTOListFromEntityList(List<ProducerEntity> producerEntityList) {
		List<ProducerDTO> producerDTODTOList = new ArrayList<ProducerDTO>();
		for (ProducerEntity producerEntity : producerEntityList) {
			producerDTODTOList.add(getProducerDTODTOFromEntity(producerEntity));
		}
		return producerDTODTOList;
	}
}
