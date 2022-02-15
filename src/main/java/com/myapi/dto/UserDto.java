package com.myapi.dto;

import com.myapi.enums.CountryEnum;
import com.myapi.validation.CountryTypeChecker;
import com.myapi.validation.DateChecker;
import com.myapi.validation.EnumNamePatternChecker;
import com.myapi.validation.PhoneNumberChecker;
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
