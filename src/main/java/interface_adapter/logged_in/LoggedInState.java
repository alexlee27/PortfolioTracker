package interface_adapter.logged_in;

import java.util.Map;

public class LoggedInState {
    private String username = "";
    private int userID;
    private double netProfit = 0;
    private String addStockError;
    private Map<String, Double> tickersToAggregatedQuantities;

    public LoggedInState(LoggedInState copy) {
        username = copy.username;
        userID = copy.userID;
        addStockError = copy.addStockError;
        tickersToAggregatedQuantities = copy.getTickersToAggregatedQuantities();
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoggedInState() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNetProfit() {
        return Double.toString(netProfit);
    }

    public void setNetProfit(double netProfit) {
        this.netProfit = netProfit;
    }

    public String getAddStockError() {
        return addStockError;
    }

    public void setAddStockError(String addStockError) {
        this.addStockError = addStockError;
    }

    public Map<String, Double> getTickersToAggregatedQuantities() {
        return tickersToAggregatedQuantities;
    }

    public void setTickersToAggregatedQuantities(Map<String, Double> tickersToAggregatedQuantities) {
        this.tickersToAggregatedQuantities = tickersToAggregatedQuantities;
    }
}
