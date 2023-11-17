package edu.hw3;

import edu.hw3.Task6.Stock;
import edu.hw3.Task6.StockMarket;
import edu.hw3.Task6.StockMarketImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Task6Test {

    @ParameterizedTest
    @MethodSource("provideStocks")
    public void testMostValuableStock(Stock[] stocks, String expectedSymbol) {
        StockMarket stockMarket = new StockMarketImpl();
        for (Stock stock : stocks) {
            stockMarket.add(stock);
        }

        assertThat(stockMarket.mostValuableStock().symbol()).isEqualTo(expectedSymbol);
    }

    private static Stream<Arguments> provideStocks() {
        return Stream.of(
            Arguments.of(
                new Stock[] {new Stock("AAPL", 150.0), new Stock("GOOGL", 2000.0), new Stock("AMZN", 3500.0)},
                "AMZN"
            ),
            Arguments.of(
                new Stock[] {new Stock("AAPL", 150.0), new Stock("GOOGL", 2000.0), new Stock("MSFT", 300.0)},
                "GOOGL"
            ),
            Arguments.of(new Stock[] {new Stock("TSLA", 700.0), new Stock("AAPL", 150.0)}, "TSLA")
        );
    }

    @Test
    public void testMostValuableStockAfterRemoval() {
        StockMarket stockMarket = new StockMarketImpl();
        Stock googl = new Stock("GOOGL", 12000.0);
        stockMarket.add(new Stock("AAPL", 150.0));
        stockMarket.add(googl);
        stockMarket.add(new Stock("AMZN", 3500.0));

        stockMarket.remove(googl);

        assertThat(stockMarket.mostValuableStock().symbol()).isEqualTo("AMZN");
    }
}
