import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Random;

public class StockTradingPlatformGUI extends JFrame {

    private HashMap<String, Integer> portfolio; // Stock portfolio
    private HashMap<String, Double> marketData; // Simulated market data
    private JTextArea portfolioArea, marketDataArea;
    private JTextField stockInputField, quantityInputField;
    private JButton buyButton, sellButton, refreshMarketButton;
    private double cashBalance = 10000.00; // Starting balance

    public StockTradingPlatformGUI() {
        // Set up the frame
        setTitle("Stock Trading Platform");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize portfolio and market data
        portfolio = new HashMap<>();
        marketData = new HashMap<>();
        initializeMarketData();

        // Portfolio section
        JPanel portfolioPanel = new JPanel();
        portfolioPanel.setLayout(new BorderLayout());
        portfolioArea = new JTextArea(10, 20);
        portfolioArea.setEditable(false);
        portfolioPanel.add(new JLabel("Portfolio"), BorderLayout.NORTH);
        portfolioPanel.add(new JScrollPane(portfolioArea), BorderLayout.CENTER);

        // Market data section
        JPanel marketPanel = new JPanel();
        marketPanel.setLayout(new BorderLayout());
        marketDataArea = new JTextArea(10, 20);
        marketDataArea.setEditable(false);
        updateMarketDataDisplay();
        marketPanel.add(new JLabel("Market Data"), BorderLayout.NORTH);
        marketPanel.add(new JScrollPane(marketDataArea), BorderLayout.CENTER);

        // Buy/sell stock section
        JPanel tradePanel = new JPanel();
        tradePanel.setLayout(new GridLayout(3, 2));
        stockInputField = new JTextField(10);
        quantityInputField = new JTextField(10);
        buyButton = new JButton("Buy");
        sellButton = new JButton("Sell");

        tradePanel.add(new JLabel("Stock Symbol:"));
        tradePanel.add(stockInputField);
        tradePanel.add(new JLabel("Quantity:"));
        tradePanel.add(quantityInputField);
        tradePanel.add(buyButton);
        tradePanel.add(sellButton);

        // Action listeners for buying and selling stocks
        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleBuyStock();
            }
        });

        sellButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSellStock();
            }
        });

        // Button to refresh market data
        refreshMarketButton = new JButton("Refresh Market Data");
        refreshMarketButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshMarketData();
            }
        });

        // Add components to the main frame
        add(portfolioPanel, BorderLayout.WEST);
        add(marketPanel, BorderLayout.EAST);
        add(tradePanel, BorderLayout.SOUTH);
        add(refreshMarketButton, BorderLayout.NORTH);

        updatePortfolioDisplay();
    }

    // Method to initialize the simulated market data
    private void initializeMarketData() {
        marketData.put("APPLE", 150.00);
        marketData.put("GOOGLE", 2800.00);
        marketData.put("AMAZON", 3400.00);
        marketData.put("TESLA", 750.00);
    }

    // Method to update the market data display
    private void updateMarketDataDisplay() {
        marketDataArea.setText("");
        for (String stock : marketData.keySet()) {
            marketDataArea.append(stock + ": $" + String.format("%.2f", marketData.get(stock)) + "\n");
        }
    }

    // Method to refresh market data (simulate price changes)
    private void refreshMarketData() {
        Random rand = new Random();
        for (String stock : marketData.keySet()) {
            double newPrice = marketData.get(stock) * (1 + (rand.nextDouble() - 0.5) / 10); // +/- 5% change
            marketData.put(stock, newPrice);
        }
        updateMarketDataDisplay();
        JOptionPane.showMessageDialog(null, "Market data refreshed!");
    }

    // Method to update the portfolio display
    private void updatePortfolioDisplay() {
        portfolioArea.setText("");
        portfolioArea.append("Cash Balance: $" + String.format("%.2f", cashBalance) + "\n\n");
        portfolioArea.append("Stock Holdings:\n");
        for (String stock : portfolio.keySet()) {
            portfolioArea.append(stock + ": " + portfolio.get(stock) + " shares\n");
        }
    }

    // Method to handle buying stocks
    private void handleBuyStock() {
        String stock = stockInputField.getText().toUpperCase();
        String quantityText = quantityInputField.getText();
        if (!marketData.containsKey(stock)) {
            JOptionPane.showMessageDialog(null, "Invalid stock symbol.");
            return;
        }
        try {
            int quantity = Integer.parseInt(quantityText);
            double stockPrice = marketData.get(stock);
            double totalCost = stockPrice * quantity;

            if (totalCost > cashBalance) {
                JOptionPane.showMessageDialog(null, "Not enough cash to buy.");
                return;
            }

            cashBalance -= totalCost;
            portfolio.put(stock, portfolio.getOrDefault(stock, 0) + quantity);
            updatePortfolioDisplay();
            JOptionPane.showMessageDialog(null, "Bought " + quantity + " shares of " + stock + ".");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid quantity.");
        }
    }

    // Method to handle selling stocks
    private void handleSellStock() {
        String stock = stockInputField.getText().toUpperCase();
        String quantityText = quantityInputField.getText();
        if (!portfolio.containsKey(stock)) {
            JOptionPane.showMessageDialog(null, "You don't own any shares of this stock.");
            return;
        }
        try {
            int quantity = Integer.parseInt(quantityText);
            int currentHolding = portfolio.get(stock);
            if (quantity > currentHolding) {
                JOptionPane.showMessageDialog(null, "You don't have enough shares to sell.");
                return;
            }

            double stockPrice = marketData.get(stock);
            double totalSale = stockPrice * quantity;

            cashBalance += totalSale;
            portfolio.put(stock, currentHolding - quantity);
            if (portfolio.get(stock) == 0) {
                portfolio.remove(stock);
            }
            updatePortfolioDisplay();
            JOptionPane.showMessageDialog(null, "Sold " + quantity + " shares of " + stock + ".");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid quantity.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StockTradingPlatformGUI().setVisible(true);
            }
        });
    }
}