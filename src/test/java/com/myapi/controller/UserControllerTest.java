package com.myapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.myapi.dto.UserDto;
import com.myapi.enums.CountryEnum;
import com.myapi.model.User;
import com.myapi.service.UserService;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @Mock
  private UserService userService;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private UserController userController;

  @BeforeEach
  void setMockOutput() {
  }

  @Test
  public void shouldOnlyAllowCountryFRANCE() {

    UserDto dto = UserDto.builder()
        .country("CHINA")
        .username("JoeBlog11")
        .dateOfBirth("2004-02-12")
        .build();

    User user = User.builder()
        .country(CountryEnum.CHINA)
        .username("JoeBlog11")
        .dateOfBirth(LocalDate.of(2004, 2, 12))
        .build();

    when(modelMapper.map(dto, User.class)).thenReturn(user);

    ResponseEntity entity = userController.registerUser(dto);

    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldOnlyAllowAdults() {

    UserDto dto = UserDto.builder()
        .country("FRANCE")
        .username("JoeBlog11")
        .dateOfBirth("2010-02-12")
        .build();

    User user = User.builder()
        .country(CountryEnum.FRANCE)
        .username("JoeBlog11")
        .dateOfBirth(LocalDate.of(2010, 2, 12))
        .build();

    when(modelMapper.map(dto, User.class)).thenReturn(user);

    ResponseEntity entity = userController.registerUser(dto);

    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }


  @Test
  public void shouldNotAllowDuplicated() {

    UserDto dto = UserDto.builder()
        .country("FRANCE")
        .username("JoeBlog11")
        .dateOfBirth("2000-02-12")
        .build();

    User user = User.builder()
        .country(CountryEnum.FRANCE)
        .username("JoeBlog11")
        .dateOfBirth(LocalDate.of(2000, 2, 12))
        .build();

    when(modelMapper.map(dto, User.class)).thenReturn(user);
    when(userService.retrieveUserByName ("JoeBlog11")).thenReturn(Optional.of(user));

    ResponseEntity entity = userController.registerUser(dto);

    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
  }


  @Test
  public void shouldSucceed() {

    UserDto dto = UserDto.builder()
        .country("FRANCE")
        .username("JoeBlog11")
        .dateOfBirth("2000-02-12")
        .build();

    User user = User.builder()
        .country(CountryEnum.FRANCE)
        .username("JoeBlog11")
        .dateOfBirth(LocalDate.of(2000, 2, 12))
        .build();

    when(modelMapper.map(dto, User.class)).thenReturn(user);
    when(userService.retrieveUserByName ("JoeBlog11")).thenReturn(Optional.empty());

    ResponseEntity entity = userController.registerUser(dto);

    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }


  @Test
  public void shouldNotBeFound() {
    when(userService.retrieveUserByName ("JoeBlog11")).thenReturn(Optional.empty());

    ResponseEntity entity = userController.getUserByName("JoeBlog11");

    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  public void shouldBeFound() {

    User user = User.builder()
        .country(CountryEnum.FRANCE)
        .username("JoeBlog11")
        .dateOfBirth(LocalDate.of(2000, 2, 12))
        .build();

    when(userService.retrieveUserByName ("JoeBlog11")).thenReturn(Optional.of(user));

    ResponseEntity entity = userController.getUserByName("JoeBlog11");

    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}