package com.shoonglogitics.companyservice.application;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.companyservice.application.command.stock.CreateStockCommand;
import com.shoonglogitics.companyservice.application.command.stock.DecreaseStockCommand;
import com.shoonglogitics.companyservice.application.command.stock.DeleteStockCommand;
import com.shoonglogitics.companyservice.application.command.stock.GetStocksCommand;
import com.shoonglogitics.companyservice.application.command.stock.IncreaseStockCommand;
import com.shoonglogitics.companyservice.application.dto.stock.StockHistoryResult;
import com.shoonglogitics.companyservice.application.dto.stock.StockResult;
import com.shoonglogitics.companyservice.domain.stock.entity.Stock;
import com.shoonglogitics.companyservice.domain.stock.entity.StockHistory;
import com.shoonglogitics.companyservice.domain.stock.repository.StockRepository;
import com.shoonglogitics.companyservice.infrastructure.lock.DistributedLock;
import com.shoonglogitics.companyservice.presentation.common.dto.PageRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {
	private final StockRepository stockRepository;

	@Transactional
	public UUID createStock(CreateStockCommand command) {
		stockRepository.findByProductId(command.productId()).ifPresent(stock -> {
			throw new IllegalArgumentException("이미 해당 상품의 재고가 존재합니다.");
		});

		Stock stock = Stock.create(command.productId(), command.initialAmount());
		stockRepository.save(stock);

		return stock.getId();
	}

	@Transactional
	@DistributedLock(key = "'stock:' + #command.stockId()")
	public void increaseStock(IncreaseStockCommand command) {
		Stock stock = getStockById(command.stockId());
		stock.increaseStock(command.quantity(), command.reason());
	}

	@Transactional
	@DistributedLock(key = "'stock:' + #command.stockId()")
	public void decreaseStock(DecreaseStockCommand command) {
		Stock stock = getStockById(command.stockId());
		stock.decreaseStock(command.quantity(), command.reason());
	}

	@Transactional
	public void deleteStock(DeleteStockCommand command) {
		Stock stock = getStockById(command.stockId());
		stock.delete(command.authUser().getUserId());
	}

	public StockResult getStock(UUID stockId) {
		Stock stock = getStockById(stockId);
		return StockResult.from(stock);
	}

	public StockResult getStockByProductId(UUID productId) {
		Stock stock = stockRepository.findByProductId(productId)
			.orElseThrow(() -> new NoSuchElementException("해당 상품의 재고를 찾을 수 없습니다."));
		return StockResult.from(stock);
	}

	public Page<StockResult> getStocks(GetStocksCommand command) {
		Page<Stock> stocks = stockRepository.getStocks(
			command.productId(),
			command.toPageable()
		);
		return stocks.map(StockResult::from);
	}

	public Page<StockHistoryResult> getStockHistories(UUID stockId, PageRequest pageRequest) {
		// 재고 존재 여부 확인
		getStockById(stockId);

		Page<StockHistory> histories = stockRepository.getStockHistories(stockId, pageRequest.toPageable());
		return histories.map(StockHistoryResult::from);
	}

	private Stock getStockById(UUID stockId) {
		return stockRepository.findById(stockId)
			.orElseThrow(() -> new NoSuchElementException("재고를 찾을 수 없습니다."));
	}
}
