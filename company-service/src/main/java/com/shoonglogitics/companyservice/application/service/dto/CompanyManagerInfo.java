package com.shoonglogitics.companyservice.application.service.dto;

import java.util.UUID;

public record CompanyManagerInfo(Long userId,
								 UUID companyId) {
}
