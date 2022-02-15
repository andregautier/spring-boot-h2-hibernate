package com.myapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.myapi.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class TestOfferApplicationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Test
  @Order(1)
  public void t1_givenValidUser1_thenRegisterUserOperationShouldSucceedWithCREATED_201()
      throws Exception {

    UserDto dto = UserDto.builder()
        .country("FRANCE")
        .username("JoeBlog1")
        .dateOfBirth("2004-02-12")
        .build();

    String json = mapper.writeValueAsString(dto);

    MvcResult requestResult = mockMvc.perform(put("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated()).andReturn();

    UserDto resultDto = parseResponse(requestResult, UserDto.class);
    Assertions.assertEquals(dto, resultDto);
  }

  @Test
  @Order(2)
  public void t2_givenValidUser2_thenRegisterUserOperationShouldSucceedWithCREATED_201()
      throws Exception {

    UserDto dto = UserDto.builder()
        .country("FRANCE")
        .username("JoeBlog2")
        .dateOfBirth("2004-02-12")
        .gender("MALE")
        .phoneNumber("123456789")
        .build();

    String json = mapper.writeValueAsString(dto);

    MvcResult requestResult = mockMvc.perform(put("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated()).andReturn();

    UserDto resultDto = parseResponse(requestResult, UserDto.class);
    Assertions.assertEquals(dto, resultDto);
  }

  @Test
  @Order(3)
  public void t3_givenDuplicateUser_thenRegisterUserOperationShouldFailWithCONFLICT_409()
      throws Exception {

    UserDto dto = UserDto.builder()
        .country("FRANCE")
        .username("JoeBlog1")
        .dateOfBirth("2004-02-12")
        .build();

    String json = mapper.writeValueAsString(dto);

    MvcResult requestResult = mockMvc.perform(put("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict()).andReturn();

    Assertions.assertEquals("User JoeBlog1 already registered",
        requestResult.getResponse().getContentAsString());
  }


  @Test
  @Order(4)
  public void t4_givenCountryNotAllowedForRegistration_thenRegisterUserOperationShouldFailWithCONFLICT_403()
      throws Exception {

    UserDto dto = UserDto.builder()
        .country("CHINA")
        .username("JoeBlog1")
        .dateOfBirth("2004-02-12")
        .build();

    String json = mapper.writeValueAsString(dto);

    MvcResult requestResult = mockMvc.perform(put("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden()).andReturn();

    Assertions.assertEquals("User registration not permitted",
        requestResult.getResponse().getContentAsString());
  }



  @Test
  @Order(5)
  public void t5_givenDOBNotAllowedForRegistration_thenRegisterUserOperationShouldFailWithCONFLICT_403()
      throws Exception {

    UserDto dto = UserDto.builder()
        .country("FRANCE")
        .username("JoeBlog1")
        .dateOfBirth("2020-02-12")
        .build();

    String json = mapper.writeValueAsString(dto);

    MvcResult requestResult = mockMvc.perform(put("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden()).andReturn();

    Assertions.assertEquals("User registration not permitted",
        requestResult.getResponse().getContentAsString());
  }

  @Test
  @Order(6)
  public void t6_givenNoFields_thenRegisterUserOperationShouldSucceedWithBAD_REQUEST_400()
      throws Exception {

    UserDto dto = UserDto.builder().build();

    String json = mapper.writeValueAsString(dto);

    mockMvc.perform(put("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.validationErrors[*].message",
            Matchers.containsInAnyOrder(
                "The username is required",
                "The country is required",
                "The date of birth is required")));
  }


  @Test
  @Order(7)
  public void t7_givenEmptyFields_thenRegisterUserOperationShouldSucceedWithBAD_REQUEST_400()
      throws Exception {

    UserDto dto = UserDto.builder()
        .country("")
        .username("")
        .dateOfBirth("")
        .gender("")
        .phoneNumber("")
        .build();

    String json = mapper.writeValueAsString(dto);

    mockMvc.perform(put("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.validationErrors[*].message",
            Matchers.containsInAnyOrder(
                "The username is required",
                "The country is required",
                "The date of birth is required")));
  }


  @Test
  @Order(8)
  public void t8_givenInvalidCountry_thenRegisterUserOperationShouldSucceedWithBAD_REQUEST_400()
      throws Exception {

    UserDto dto = UserDto.builder()
        .country("CHINE")
        .username("JoeBlog1")
        .dateOfBirth("2020-02-12")
        .build();

    String json = mapper.writeValueAsString(dto);

    mockMvc.perform(put("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.validationErrors[*].message",
            Matchers.containsInAnyOrder(
                "Invalid country, The country name must be in English and uppercase")));
  }


  @Test
  @Order(9)
  public void t9_givenInvalidGender_thenRegisterUserOperationShouldSucceedWithBAD_REQUEST_400()
      throws Exception {

    UserDto dto = UserDto.builder()
        .country("FRANCE")
        .username("JoeBlog1")
        .dateOfBirth("2020-02-12")
        .gender("HOMME")
        .phoneNumber("abcdefg")
        .build();

    String json = mapper.writeValueAsString(dto);

    mockMvc.perform(put("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.validationErrors[*].message",
            Matchers.containsInAnyOrder(
                "Invalid gender, it must be MALE or FEMALE",
                "Invalid phone number, it can only contain numbers")));
  }


  @Test
  @Order(10)
  public void t10_givenNoContent_thenRegisterUserOperationShouldSucceedWithBAD_REQUEST_204()
      throws Exception {

    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("username", "Odile");

    mockMvc.perform(get("/api/users")
        .params(requestParams))
        .andExpect(status().isNoContent());
  }

  @Test
  @Order(11)
  public void t11_givenUserInDB_thenRegisterUserOperationShouldSucceedWithBAD_REQUEST_204()
      throws Exception {

    UserDto dto = UserDto.builder()
        .country("FRANCE")
        .username("JoeBlog11")
        .dateOfBirth("2004-02-12")
        .build();

    String json = mapper.writeValueAsString(dto);

    MvcResult putResult = mockMvc.perform(put("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated()).andReturn();

    UserDto putResultDto = parseResponse(putResult, UserDto.class);

    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    requestParams.add("username", "JoeBlog11");

    MvcResult getResult = mockMvc.perform(get("/api/users")
            .params(requestParams))
        .andExpect(status().isOk()).andReturn();

    UserDto getResultDto = parseResponse(getResult, UserDto.class);

    Assertions.assertEquals(putResultDto, getResultDto);

  }

  public <T> T parseResponse(MvcResult result, Class<T> responseClass) {
    try {
      String contentAsString = result.getResponse().getContentAsString();
      return mapper.readValue(contentAsString, responseClass);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}