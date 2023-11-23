package use_case.removeStock;

import java.util.List;

import interface_adapter.logged_in.LoggedInViewModel;
import use_case.portfolioDataAccessInterface.PortfolioDataAccessInterface;
import entity.Portfolio;
import entity.Stock;
import entity.User;

public class RemoveStockInteractor implements RemoveStockInputBoundary{
    final RemoveStockUserDataAccessInterface userDataAccessObject;
    final PortfolioDataAccessInterface portfolioDataAccessObject;
    final RemoveStockOutputBoundary removeStockPresenter;
    final LoggedInViewModel loggedInViewModel;
    public RemoveStockInteractor(RemoveStockUserDataAccessInterface userDataAccessObject,
                                 PortfolioDataAccessInterface portfolioDataAccessObject,
                                 RemoveStockOutputBoundary removeStockPresenter,
                                 LoggedInViewModel loggedInViewModel){
        this.userDataAccessObject = userDataAccessObject;
        this.portfolioDataAccessObject = portfolioDataAccessObject;
        this.removeStockPresenter = removeStockPresenter;
        this.loggedInViewModel = loggedInViewModel;
    }

    public void execute(RemoveStockInputData removeStockInputData){
        String tickerNumber = removeStockInputData.getTickerNumber();
        String username = loggedInViewModel.getLoggedInUser();
        User user = userDataAccessObject.get(username);
        int userId = user.getUserID();
        Portfolio portfolio = portfolioDataAccessObject.getPortfolioByID(userId);
        List<Stock> stockList = portfolio.getStockList();

        stockList.remove(tickerNumber);
        portfolio.setStockList(stockList);

        removeStockPresenter.prepareView();
    }
}
