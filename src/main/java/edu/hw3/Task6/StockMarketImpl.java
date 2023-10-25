package edu.hw3.Task6;

import java.util.Comparator;
import java.util.PriorityQueue;

public class StockMarketImpl implements StockMarket {
    private final PriorityQueue<Stock> stockQueue;

    public StockMarketImpl() {
        stockQueue = new PriorityQueue<>(Comparator.comparing(Stock::getPrice).reversed());
    }

    @Override
    public void add(Stock stock) {
        stockQueue.offer(stock);
    }

    @Override
    public void remove(Stock stock) {
        stockQueue.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return stockQueue.peek();
    }

}
