package com.shoonglogitics.userservice.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shoonglogitics.userservice.domain.repository.UserRepository;
import com.shoonglogitics.userservice.infrastructure.client.response.CompanyManagerResponse;
import com.shoonglogitics.userservice.infrastructure.client.response.HubManagerResponse;
import com.shoonglogitics.userservice.infrastructure.client.response.ShipperResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InternalUserService {

	private final UserRepository userRepository;

	public List<?> getInternalUsers(UUID hubId, UUID companyId) {
		List<Object> responses = new ArrayList<>();

		if (hubId != null) {
			List<HubManagerResponse> hubManagers = userRepository.findHubManagersByHubId(hubId)
				.stream().map(HubManagerResponse::from)
				.toList();

			List<ShipperResponse> shippers = userRepository.findShippersByHubId(hubId)
				.stream().map(ShipperResponse::from)
				.toList();

			responses.addAll(hubManagers);
			responses.addAll(shippers);
		}

		if (companyId != null) {
			List<CompanyManagerResponse> companyManagers =
				userRepository.findCompanyManagersByCompanyId(companyId)
					.stream().map(CompanyManagerResponse::from)
					.toList();

			responses.addAll(companyManagers);
		}

		return responses;
	}

}
