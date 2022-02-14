package com.atos.dto;

import com.atos.enums.CountryEnum;
import com.atos.validation.CountryTypeChecker;
import com.atos.validation.DateChecker;
import com.atos.validation.EnumNamePatternChecker;
import com.atos.validation.PhoneNumberChecker;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class UserDto {

  @NotBlank(message = "The username is required")
  private String username;

  @DateChecker(pattern = "yyyy-MM-dd")
  private String dateOfBirth;

  @CountryTypeChecker(enumClass = CountryEnum.class)
  private String country;

  @PhoneNumberChecker()
  private String phoneNumber;

  @EnumNamePatternChecker(regexp = "MALE|FEMALE")
  private String gender;
}
