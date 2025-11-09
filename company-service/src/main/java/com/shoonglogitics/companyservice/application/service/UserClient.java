package com.shoonglogitics.companyservice.application.service;

import java.util.List;
import java.util.UUID;

import com.shoonglogitics.companyservice.application.service.dto.CompanyManagerInfo;
import com.shoonglogitics.companyservice.application.service.dto.HubManagerInfo;

public interface UserClient {

	/**
	 * @param companyId
	 * @return 업체 아이디로 업체 담당자 목록 조회
	 */
	List<CompanyManagerInfo> getCompanyManagerInfos(UUID companyId);

	/**
	 * @param hubId
	 * @return 허브 아이디로 허브 담당자 목록 조회
	 */
	List<HubManagerInfo> getHubManagerInfos(UUID hubId);


	/**
	 * @param userId 업체 매니저 유저 ID
	 * @return 업체 담당자 삭제 요청 성공 여부
	 */
	boolean deleteCompanyManager(Long userId);
}
