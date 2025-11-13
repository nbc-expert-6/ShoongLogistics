package com.shoonglogitics.companyservice.application.command;

import java.util.UUID;

import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;

public record DeleteCompanyCommand(
	AuthUser authUser,
	UUID companyId,
	UUID hubId
) {
}
