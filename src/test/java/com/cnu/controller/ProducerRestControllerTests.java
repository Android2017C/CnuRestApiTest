package com.cnu.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cnu.dto.ProducerDTO;
import com.cnu.entity.ProducerEntity;
import com.cnu.repo.ProducerRepository;
import com.cnu.service.ProducerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ProducerController.class)
public class ProducerRestControllerTests {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	MockMvc mockMvc;
	@MockBean
	ProducerRepository producerRepository;
	@MockBean
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
	public void insertProducerTest() throws JsonProcessingException, Exception {

		ProducerDTO savedProducerDTO = new ProducerDTO();
		savedProducerDTO.setId(3);
		savedProducerDTO.setCommand("androidcommand");
		savedProducerDTO.setItemDescription("abc");
		savedProducerDTO.setItemHeight(4);
		savedProducerDTO.setItemLength(6);
		savedProducerDTO.setItemName("Android");
		savedProducerDTO.setItemWeight(3);
		savedProducerDTO.setItemWidth(6);
		savedProducerDTO.setMessageName("androirmessage");
		savedProducerDTO.setStatus("created");

		String inputjson = ObjectMapper.writeValueAsString(savedProducerDTO);

		String url = "/cnu/producerData/create";
		Mockito.when(producerServiceImpl.createProducer(Mockito.any(ProducerDTO.class))).thenReturn(savedProducerDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON_VALUE)
				.content(inputjson).contentType(MediaType.APPLICATION_JSON_VALUE);
		MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputjson = response.getContentAsString();

		assertThat(outputjson).isEqualTo(inputjson);
		int finalout = response.getStatus();// 200
		assertEquals(HttpStatus.OK.value(), finalout);

	}

	@Test
	public void saveProducerTest() throws JsonProcessingException, Exception {

		ProducerDTO savedProducerDTO = new ProducerDTO();
		savedProducerDTO.setId(4);
		savedProducerDTO.setCommand("androidcommand");
		savedProducerDTO.setItemDescription("abc");
		savedProducerDTO.setItemHeight(4);
		savedProducerDTO.setItemLength(6);
		savedProducerDTO.setItemName("Android");
		savedProducerDTO.setItemWeight(3);
		savedProducerDTO.setItemWidth(6);
		savedProducerDTO.setMessageName("androirmessage");
		savedProducerDTO.setStatus("created");

		Mockito.when(producerRepository.save(getProducerEntityFromDTO(savedProducerDTO)))
				.thenReturn(getProducerEntityFromDTO(savedProducerDTO));
		String url = "/cnu/producerData/create";
		Mockito.when(producerServiceImpl.createProducer(Mockito.any(ProducerDTO.class))).thenReturn(savedProducerDTO);

		// mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(ObjectMapper.writeValueAsString(savedProducerDTO))).andDo(print());
		String inputjson = ObjectMapper.writeValueAsString(savedProducerDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("utf-8").content(inputjson).contentType(MediaType.APPLICATION_JSON_VALUE);

		MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputjson = response.getContentAsString();

		ProducerDTO producer = ObjectMapper.readValue(outputjson, ProducerDTO.class);
		System.out.println("producer id := " + producer.getId());
		assertEquals(4, producer.getId());

		assertThat(outputjson).isEqualTo(inputjson).info.description();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void testAllProducerData() throws Exception {
		List<ProducerDTO> listofProducersdata = new ArrayList<ProducerDTO>();
		listofProducersdata.add(new ProducerDTO(1, "Androidname", "androidcommand", "helloandro", "wellcometo android",
				2, 5, 8, 10, "Created"));
		listofProducersdata.add(new ProducerDTO(2, "kotlin", "kotlincommand", "helloandro", "wellcometo kotlin", 2, 5,
				8, 10, "Created"));
		listofProducersdata.add(
				new ProducerDTO(3, "java", "javacommand", "helloandro", "wellcometo java", 2, 5, 8, 10, "Created"));
		listofProducersdata
				.add(new ProducerDTO(4, "PHP", "PHPcommand", "helloandro", "wellcometo PHP", 2, 5, 8, 10, "Created"));
		listofProducersdata.add(new ProducerDTO(5, "Oracle", "oracleommand", "helloandro", "wellcometo Oracle", 2, 5, 8,
				10, "Created"));
		Mockito.when(producerServiceImpl.getAllProducerElements()).thenReturn(listofProducersdata);

		Mockito.when(producerRepository.findAll()).thenReturn(getProducerEntityListFromDTOList(listofProducersdata));
		String url = "http://localhost:8787/cnu/producerData/getAll";
		/*
		 * String inputjson = null ; for(int i=0;i<listofProducersdata.size();i++) {
		 * inputjson = ObjectMapper.writeValueAsString(listofProducersdata.get(i)); }
		 */
		String inputjson = ObjectMapper.writeValueAsString(listofProducersdata);
		System.out.println("input Producer data " + inputjson);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("utf-8").content(inputjson).contentType(MediaType.APPLICATION_JSON_VALUE);

		MvcResult result = mockMvc.perform(requestBuilder).andDo(print()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputjson = response.getContentAsString();

		System.out.println("output Producer data " + outputjson);

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(5, producerServiceImpl.getAllProducerElements().size());
		assertThat(outputjson).isEqualTo(inputjson);

	}

	@Test
	public void updateProducerTest() throws JsonProcessingException, Exception {

		ProducerDTO updateProducerDTO = new ProducerDTO();
		updateProducerDTO.setId(4);
		updateProducerDTO.setCommand("updatedCommand");
		updateProducerDTO.setItemDescription("updatedDescription");
		updateProducerDTO.setItemHeight(4);
		updateProducerDTO.setItemLength(6);
		updateProducerDTO.setItemName("updated");
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
			
			
			Mockito.when(producerServiceImpl.updateProducer(producerEntityData.getId(), updateProducerDTO))
			.thenReturn(updateProducerDTO);
			
			Mockito.when(producerRepository.findById(producerEntityData.getId())).thenReturn(java.util.Optional.ofNullable(producerEntityData));
			Mockito.when(producerRepository.save(producerEntityData))
					.thenReturn(producerEntityData);

			String url = "http://localhost:8787/cnu/producerData/update/" + producerEntityData.getId();
			String inputjson = ObjectMapper.writeValueAsString(updateProducerDTO);

			RequestBuilder requestBuilderupdate = MockMvcRequestBuilders.put(url)
					.accept(MediaType.APPLICATION_JSON_VALUE).characterEncoding("utf-8").content(inputjson)
					.contentType(MediaType.APPLICATION_JSON_VALUE);

			MvcResult result = mockMvc.perform(requestBuilderupdate).andDo(print()).andReturn();
			MockHttpServletResponse response = result.getResponse();

			assertEquals(HttpStatus.OK.value(), response.getStatus());
		}

	}

	@Test
	public void deleteProducerById() throws Exception {
		/*
		 * Integer productID=4; String url =
		 * "http://localhost:8787/cnu/producerData/deleteByID/" +productID;
		 * Mockito.doNothing().when(producerRepository).deleteById(productID);
		 * mockMvc.perform(get(url)).andExpect(status().isOk());
		 * Mockito.verify(producerRepository,times(4)).deleteById(productID);
		 */
		
		ProducerDTO deleteProducerDTO = new ProducerDTO();
		deleteProducerDTO.setId(4);
		deleteProducerDTO.setCommand("updatedCommand");
		deleteProducerDTO.setItemDescription("updatedDescription");
		deleteProducerDTO.setItemHeight(4);
		deleteProducerDTO.setItemLength(6);
		deleteProducerDTO.setItemName("updated");
		deleteProducerDTO.setItemWeight(3);
		deleteProducerDTO.setItemWidth(6);
		deleteProducerDTO.setMessageName("updatedmessage");
		deleteProducerDTO.setStatus("created");

		Mockito.when(producerServiceImpl.deleteProducerByID(deleteProducerDTO.getId()))
		.thenReturn(true);
		Optional<ProducerEntity> optionalProducerEntity = producerRepository.findById(deleteProducerDTO.getId());
				Mockito.when(producerRepository.findById(deleteProducerDTO.getId())).thenReturn(optionalProducerEntity);
		String url = "http://localhost:8787/cnu/producerData/deleteByID/" + deleteProducerDTO.getId();
		mockMvc.perform(MockMvcRequestBuilders.delete(url).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk());
		assertEquals(HttpStatus.OK.value(), 200);
	}

	@Test
	public void getProducerById() throws Exception {
		
		ProducerDTO singleProducerDTO = new ProducerDTO();
		singleProducerDTO.setId(4);
		singleProducerDTO.setCommand("updatedCommand");
		singleProducerDTO.setItemDescription("updatedDescription");
		singleProducerDTO.setItemHeight(4);
		singleProducerDTO.setItemLength(6);
		singleProducerDTO.setItemName("updated");
		singleProducerDTO.setItemWeight(3);
		singleProducerDTO.setItemWidth(6);
		singleProducerDTO.setMessageName("updatedmessage");
		singleProducerDTO.setStatus("created");

		Mockito.when(producerServiceImpl.getProducerById(singleProducerDTO.getId()))
		.thenReturn(singleProducerDTO);
		Optional<ProducerEntity> optionalProducerEntity = producerRepository.findById(singleProducerDTO.getId());
		if(optionalProducerEntity.isPresent()) {
		ProducerEntity producerEntity = optionalProducerEntity.get();
		Mockito.when(producerRepository.findById(producerEntity.getId())).thenReturn(optionalProducerEntity);
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
		assertEquals(4, producer.getId());

		assertThat(outputjson).isEqualTo(inputjson).info.description();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		
		}
	}
	
	/*
	 * @Test public void deletebyid() throws Exception {
	 * 
	 * Integer producerID=1;
	 * Mockito.doNothing().when(producerRepository).deleteById(producerID); String
	 * url = "http://localhost:8787/cnu/producerData/deleteByID/" + producerID;
	 * mockMvc.perform(delete(url)).andExpect(status().isOk());
	 * Mockito.verify(producerRepository,times(1)).deleteById(producerID);
	 * 
	 * }
	 */
	

}
