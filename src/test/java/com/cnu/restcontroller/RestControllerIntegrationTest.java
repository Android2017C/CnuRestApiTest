package com.cnu.restcontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cnu.dto.ProducerDTO;
import com.cnu.entity.ProducerEntity;
import com.cnu.repo.ProducerRepository;
import com.cnu.service.ProducerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerIntegrationTest {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ProducerRepository producerRepository;
	@Autowired
	@Qualifier("JPA_ProducerService")
	ProducerServiceImpl producerServiceImpl;
	@Autowired
	ObjectMapper ObjectMapper;

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

	private List<ProducerEntity> getProducerEntityListFromDTOList(List<ProducerDTO> producerEntityList) {
		List<ProducerEntity> producerDTODTOList = new ArrayList<ProducerEntity>();
		for (ProducerDTO producerEntity : producerEntityList) {
			producerDTODTOList.add(getProducerEntityFromDTO(producerEntity));
		}
		return producerDTODTOList;
	}

	@Test
	public void saveProducerTest() throws JsonProcessingException, Exception {

		ProducerDTO savedProducerDTO = new ProducerDTO();
		// savedProducerDTO.setId(4);
		savedProducerDTO.setCommand("javacommand");
		savedProducerDTO.setItemDescription("java");
		savedProducerDTO.setItemHeight(4);
		savedProducerDTO.setItemLength(6);
		savedProducerDTO.setItemName("java");
		savedProducerDTO.setItemWeight(3);
		savedProducerDTO.setItemWidth(6);
		savedProducerDTO.setMessageName("javamessage");
		savedProducerDTO.setStatus("created");

		String url = "/cnu/producerData/create";
		String inputjson = ObjectMapper.writeValueAsString(savedProducerDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("utf-8").content(inputjson).contentType(MediaType.APPLICATION_JSON_VALUE);
		MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputjson = response.getContentAsString();
		ProducerDTO producer = ObjectMapper.readValue(outputjson, ProducerDTO.class);
		System.out.println("producer id := " + producer.getId());
		ProducerEntity savedproducerEntity = producerRepository.findById(producer.getId()).get();
		ProducerDTO savedproducer = getProducerDTODTOFromEntity(savedproducerEntity);
		assertEquals(savedproducer.getId(), producer.getId());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void updateProducerTest() throws JsonProcessingException, Exception {

		ProducerDTO updateProducerDTO = new ProducerDTO();
		updateProducerDTO.setId(2);
		updateProducerDTO.setCommand("OraclecommandUpdated=");
		updateProducerDTO.setItemDescription("oracleDescriptionUpdated=");
		updateProducerDTO.setItemHeight(4);
		updateProducerDTO.setItemLength(6);
		updateProducerDTO.setItemName("updated=");
		updateProducerDTO.setItemWeight(3);
		updateProducerDTO.setItemWidth(6);
		updateProducerDTO.setMessageName("updatedmessage");
		updateProducerDTO.setStatus("created");

		ProducerEntity producerEntity = getProducerEntityFromDTO(updateProducerDTO);

		Optional<ProducerEntity> optionalProducerEntity = producerRepository.findById(producerEntity.getId());
		if (optionalProducerEntity.isPresent()) {
			ProducerEntity producerEntityData = optionalProducerEntity.get();
			producerEntityData.setCommand(updateProducerDTO.getCommand());
			producerEntityData.setItemDescription(updateProducerDTO.getItemDescription());
			producerEntityData.setItemHeight(updateProducerDTO.getItemHeight());
			producerEntityData.setItemLength(updateProducerDTO.getItemLength());
			producerEntityData.setItemName(updateProducerDTO.getItemName());
			producerEntityData.setItemWeight(updateProducerDTO.getItemWeight());
			producerEntityData.setItemWidth(updateProducerDTO.getItemWidth());
			producerEntityData.setMessageName(updateProducerDTO.getMessageName());
			producerEntityData.setStatus(updateProducerDTO.getStatus());

			String url = "http://localhost:8787/cnu/producerData/update/" + producerEntityData.getId();
			String inputjson = ObjectMapper.writeValueAsString(updateProducerDTO);

			RequestBuilder requestBuilderupdate = MockMvcRequestBuilders.put(url)
					.accept(MediaType.APPLICATION_JSON_VALUE).characterEncoding("utf-8").content(inputjson)
					.contentType(MediaType.APPLICATION_JSON_VALUE);

			MvcResult result = mockMvc.perform(requestBuilderupdate).andDo(print()).andReturn();
			MockHttpServletResponse response = result.getResponse();

			String outputjson = response.getContentAsString();
			System.out.println("=========" + outputjson);
			assertEquals(HttpStatus.OK.value(), response.getStatus());

		}
	}
	
	@Test
	public void deleteProducerById() throws Exception {
		Integer deleteNum=9;
		//Optional<ProducerEntity> optionalProducerEntity = producerRepository.findById(num);
		//ProducerEntity producerEntityData = optionalProducerEntity.get();
		String url = "http://localhost:8787/cnu/producerData/deleteByID/" + deleteNum;
		mockMvc.perform(MockMvcRequestBuilders.delete(url).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk());
		assertEquals(HttpStatus.OK.value(), 200);
	}
	
	
	@Test
	public void testGetAllProducerData() throws Exception {
		
		String url = "http://localhost:8787/cnu/producerData/getAll";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON_VALUE);

		MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputjson = response.getContentAsString();

		System.out.println("output Producer data " + outputjson);
		ProducerDTO[] producer = ObjectMapper.readValue(outputjson, ProducerDTO[].class);
		System.out.println("producer length := " + producer.length);
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertThat(producer).hasSize(2);

	}
	
	
	@Test
	public void getProducerById() throws Exception {
		Integer getProducerByIdNum=2;
		Optional<ProducerEntity> optionalProducerEntity = producerRepository.findById(getProducerByIdNum);
		if(optionalProducerEntity.isPresent()) {
		ProducerEntity producerEntity = optionalProducerEntity.get();
		String url = "http://localhost:8787/cnu/producerData/findByID/" + producerEntity.getId();
		
		String inputjson = ObjectMapper.writeValueAsString(producerEntity);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
				.accept(MediaType.APPLICATION_JSON_VALUE).characterEncoding("utf-8").content(inputjson)
				.contentType(MediaType.APPLICATION_JSON_VALUE);
		
		MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputjson = response.getContentAsString();

		ProducerDTO producer = ObjectMapper.readValue(outputjson, ProducerDTO.class);
		System.out.println("producer id := " + producer.getId());
		assertEquals(2, producer.getId());

		assertThat(outputjson).isEqualTo(inputjson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		
		}
	}
	
	

}
