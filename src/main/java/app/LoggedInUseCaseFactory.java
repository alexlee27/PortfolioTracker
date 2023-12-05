package app;

import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logged_in.add_stock.AddStockController;
import interface_adapter.logged_in.add_stock.AddStockPresenter;
import use_case.add_stock.AddStockInputBoundary;
import use_case.add_stock.AddStockInteractor;
import use_case.add_stock.AddStockOutputBoundary;
import use_case.add_stock.StockCalculationService;
import use_case.show.StockPriceDataAccessInterface;
import use_case.signup.PortfolioDataAccessInterface;
import view.LoggedInView;
import view.validation.StockFieldValidator;
import view.validation.StockFieldValidatorImpl;
import data_access.FilePortfolioDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.delete_user.DeleteController;
import interface_adapter.delete_user.DeletePresenter;
import interface_adapter.delete_user.DeleteState;
import interface_adapter.delete_user.DeleteViewModel;
import interface_adapter.editStock.EditStockController;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout_user.LogoutController;
import interface_adapter.logout_user.LogoutPresenter;
import use_case.delete_user.DeleteInputBoundary;
import use_case.delete_user.DeleteInteractor;
import use_case.delete_user.DeleteOutputBoundary;
import use_case.delete_user.DeleteUserDataAccessInterface;
import use_case.editStock.EditStockInputBoundary;
import use_case.editStock.EditStockOutputBoundary;
import use_case.editStock.EditStockUserDataAccessInterface;
import interface_adapter.editStock.EditStockPresenter;
import use_case.editStock.EditStockInteractor;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.signup.SignupUserDataAccessInterface;
import view.LoginView;
import view.ViewManager;
import view.validation.StockFieldValidator;

import javax.swing.*;
import java.io.IOException;

public class LoggedInUseCaseFactory {
    private LoggedInUseCaseFactory() {
    }

    public static LoggedInView create(JFrame appFrame, LoggedInViewModel loggedInViewModel,
                                      LoginViewModel loginViewModel, ViewManagerModel viewManagerModel,
                                      SignupUserDataAccessInterface userDataAccessInterface,
                                      DeleteViewModel deleteViewModel,
                                      FilePortfolioDataAccessObject portfolioDataAccessObject,
                                      StockPriceDataAccessInterface stockPriceClientImpl, LoginView loginView,
                                      StockCalculationService stockCalculationServiceImpl,
                                      EditStockUserDataAccessInterface editStockUserDataAccessInterface) {
        try {
            DeleteController deleteController = createDeleteController(deleteViewModel, loginViewModel, viewManagerModel,
                    userDataAccessInterface, loggedInViewModel, portfolioDataAccessObject);
            DeleteState deleteState = new DeleteState();
            EditStockController editStockController = createEditStockController(viewManagerModel, loggedInViewModel, editStockUserDataAccessInterface, portfolioDataAccessObject);

            AddStockController addStockController = createAddStockUseCase(stockPriceClientImpl,
                    portfolioDataAccessObject, loggedInViewModel, stockCalculationServiceImpl);
            StockFieldValidator stockFieldValidator = new StockFieldValidatorImpl();

            LogoutController logoutController = createLogoutController(loginViewModel, loggedInViewModel, viewManagerModel);

            return new LoggedInView(appFrame, loggedInViewModel, deleteState, deleteController, loginView, stockFieldValidator, addStockController, logoutController, editStockController);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file");
        }
        return null;
    }

    private static LogoutController createLogoutController(LoginViewModel loginViewModel, LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel) {
        LogoutOutputBoundary logoutPresenter = new LogoutPresenter(loginViewModel, loggedInViewModel, viewManagerModel);
        LogoutInputBoundary logoutInteractor = new LogoutInteractor(logoutPresenter);
        return new LogoutController(logoutInteractor);
    }

    private static DeleteController createDeleteController(DeleteViewModel deleteViewModel,
                                                           LoginViewModel loginViewModel,
                                                           ViewManagerModel viewManagerModel,
                                                           SignupUserDataAccessInterface userDataAccessInterface,
                                                           LoggedInViewModel loggedInViewModel,
                                                           FilePortfolioDataAccessObject portfolioDataAccessObject) throws IOException {
        DeleteOutputBoundary deleteOutputBoundary = new DeletePresenter(deleteViewModel, viewManagerModel,
                loggedInViewModel, loginViewModel);
        DeleteInputBoundary deleteInteractor = new DeleteInteractor(userDataAccessInterface, deleteOutputBoundary, portfolioDataAccessObject);
        return new DeleteController(deleteInteractor);
    }

    private static EditStockController createEditStockController(ViewManagerModel viewManagerModel,
                                                                 LoggedInViewModel loggedInViewModel,
                                                                 EditStockUserDataAccessInterface userDataAccessInterface,
                                                                 FilePortfolioDataAccessObject portfolioDataAccessObject) {
        //Output boundary
        EditStockOutputBoundary editStockOutputBoundary = new EditStockPresenter(loggedInViewModel, viewManagerModel);

        //Input boundary
        EditStockInputBoundary editStockInteractor = new EditStockInteractor(loggedInViewModel, editStockOutputBoundary, portfolioDataAccessObject, userDataAccessInterface);

        return new EditStockController(editStockInteractor);
    }

    private static AddStockController createAddStockUseCase(StockPriceDataAccessInterface stockPriceClientImpl,
                                                            PortfolioDataAccessInterface portfolioDataAccessInterface,
                                                            LoggedInViewModel loggedInViewModel,
                                                            StockCalculationService stockCalculationServiceImpl) {
        AddStockOutputBoundary addStockPresenter = new AddStockPresenter(loggedInViewModel);
        AddStockInputBoundary addStockInteractor = new AddStockInteractor(stockPriceClientImpl,
                portfolioDataAccessInterface, addStockPresenter, stockCalculationServiceImpl);
        return new AddStockController(addStockInteractor);
    }
}
