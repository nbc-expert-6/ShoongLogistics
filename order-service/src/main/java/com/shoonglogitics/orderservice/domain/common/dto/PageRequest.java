package com.shoonglogitics.orderservice.domain.common.dto;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.shoonglogitics.orderservice.domain.common.vo.PageSizeType;

public record PageRequest(
	Integer page,
	PageSizeType size,
	String sort,
	Boolean isAsc
) {
	public Pageable toPageable() {
		int pageNumber = (page != null && page >= 0) ? page : 0;
		int pageSize = (size != null) ? size.getValue() : PageSizeType.SIZE_10.getValue();
		String sortField = (sort != null && !sort.isBlank()) ? sort : "createdAt";
		Sort.Direction direction = (isAsc != null && isAsc) ? Sort.Direction.ASC : Sort.Direction.DESC;

		return org.springframework.data.domain.PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortField));
	}

	public Pageable toNativePageable() {
		int pageNumber = (page != null && page >= 0) ? page : 0;
		int pageSize = (size != null) ? size.getValue() : PageSizeType.SIZE_10.getValue();
		String sortField = (sort != null && !sort.isBlank()) ? sort : "created_at";
		Sort.Direction direction = (isAsc != null && isAsc) ? Sort.Direction.ASC : Sort.Direction.DESC;

		return org.springframework.data.domain.PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortField));
	}
}