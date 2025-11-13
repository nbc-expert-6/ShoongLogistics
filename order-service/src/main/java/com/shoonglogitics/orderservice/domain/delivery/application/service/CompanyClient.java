package com.shoonglogitics.orderservice.domain.delivery.application.service;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.CompanyInfo;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

public interface CompanyClient {
	CompanyInfo getCompanyInfo(UUID uuid, Long userId, UserRoleType role);
}
