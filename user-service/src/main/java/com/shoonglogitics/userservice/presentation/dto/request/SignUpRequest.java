package com.shoonglogitics.userservice.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	property = "userType"
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = MasterSignUpRequest.class, name = "MASTER"),
	@JsonSubTypes.Type(value = HubManagerSignUpRequest.class, name = "HUB_MANAGER"),
	@JsonSubTypes.Type(value = CompanyManagerSignUpRequest.class, name = "COMPANY_MANAGER"),
	@JsonSubTypes.Type(value = ShipperSignUpRequest.class, name = "SHIPPER")
})
public abstract class SignUpRequest {

	@NotBlank(message = "아이디 값은 필수입니다.")
	@Pattern(regexp = "^[a-z0-9]{4,10}$",
		message = "아이디는 소문자(a~z)와 숫자(0~9)만 사용 가능합니다.")
	private String userName;

	@NotBlank(message = "비밀번호 값은 필수입니다.")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
		message = "비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함해야합니다.")

	private String password;

	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	private String email;

	@NotBlank(message = "이름은 필수입니다.")
	private String name;

	@NotBlank(message = "Slack ID는 필수입니다.")
	private String slackId;

	@NotBlank(message = "전화번호는 필수입니다.")
	private String phoneNumber;

	public abstract <T> T toCommand();

}
