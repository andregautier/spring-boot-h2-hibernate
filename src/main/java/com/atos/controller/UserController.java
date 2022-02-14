package com.atos.controller;

import static com.atos.utils.DateUtils.isAdult;

import com.atos.dto.UserDto;
import com.atos.enums.CountryEnum;
import com.atos.logging.LogExecution;
import com.atos.model.User;
import com.atos.service.UserService;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserService userService;

	// TODO - Use swagger for API documentation

	@GetMapping
	public ResponseEntity<User> getUserByName(@RequestParam String username){

		Optional<User> optUser = userService.retrieveUserByName(username);

		if (!optUser.isPresent()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(optUser.get());
	}

	@PutMapping
	@LogExecution
	public ResponseEntity registerUser(@Valid @RequestBody UserDto userDto) {

		User user = convertToEntity(userDto);

		String context = "CONTEXT - " + user.toString();

		// =====================================================================
		// Only French adults are allowed to register
		// =====================================================================

		if (CountryEnum.FRANCE != user.getCountry() || !isAdult(user.getDateOfBirth())) {
			log.warn("WARNING - Only French adults are allowed to register - " + context);
			return ResponseEntity
					.status(HttpStatus.FORBIDDEN)
					.body("User registration not permitted");
		}

		// =====================================================================
		// Duplicate users (based on username) are not allowed to register again
		// Not in the spec., had to decide
		// =====================================================================

		Optional<User> optUser = userService.retrieveUserByName(userDto.getUsername());

		if (optUser.isPresent()) {
			log.warn("WARNING - Duplicate users not allowed - " + context);
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body("User " + userDto.getUsername() + " already registered");
		}

		user = userService.registerUser(convertToEntity(userDto));

		log.warn("INFO - User registered - " + context);
		return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(user));
	}

	private User convertToEntity(UserDto userDto) {
		return modelMapper.map(userDto, User.class);
	}

	private UserDto convertToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}
}

