package com.shoonglogitics.userservice.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

	@Size(min = 4, max = 10, message = "아이디는 4~10자이어야 합니다.")
	@Pattern(regexp = "^[a-z0-9]{4,10}$",
		message = "아이디는 소문자(a~z)와 숫자(0~9)만 사용 가능합니다.")
	private String userName;

	@Size(min = 8, max = 15, message = "비밀번호는 8~15자리이어야 합니다.")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
		message = "비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함해야합니다.")

	private String password;

	private String email;
	private String name;
	private String slackId;
	private String phoneNumber;

	public abstract <T> T toCommand();

}
