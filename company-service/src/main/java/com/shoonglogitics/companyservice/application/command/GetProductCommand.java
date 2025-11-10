package com.shoonglogitics.companyservice.application.command;

import java.util.UUID;

public record GetProductCommand(
	UUID companyId,
	UUID productId
) {
}
