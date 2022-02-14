package com.atos.model;

import com.atos.enums.CountryEnum;
import com.atos.enums.GenderEnum;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "USER_NAME", nullable = false, columnDefinition="TEXT")
	private String username;

	@Column(name = "DOB")
	private LocalDate dateOfBirth;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "COUNTRY")
	private CountryEnum country;

	@Column(name = "PHONE_NUMBER", nullable = true)
	private String phoneNumber;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "GENDER", nullable = true)
	private GenderEnum gender;
}
