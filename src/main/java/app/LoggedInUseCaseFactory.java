package app;

import data_access.FilePortfolioDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.delete_user.DeleteController;
import interface_adapter.delete_user.DeletePresenter;
import interface_adapter.delete_user.DeleteState;
import interface_adapter.delete_user.DeleteViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import use_case.delete_user.DeleteInputBoundary;
import use_case.delete_user.DeleteInteractor;
import use_case.delete_user.DeleteOutputBoundary;
import use_case.delete_user.DeleteUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import view.LoggedInView;

import javax.swing.*;
import java.io.IOException;

public class LoggedInUseCaseFactory {
    private LoggedInUseCaseFactory() {
    }

    public static LoggedInView create(LoggedInViewModel loggedInViewModel,
                                      LoginViewModel loginViewModel,
                                      ViewManagerModel viewManagerModel
            , SignupUserDataAccessInterface userDataAccessInterface,
                                      DeleteViewModel deleteViewModel,
                                      FilePortfolioDataAccessObject portfolioDataAccessObject) {
        try {
            DeleteController deleteController = createDeleteController(deleteViewModel, loginViewModel, viewManagerModel,
                    userDataAccessInterface, loggedInViewModel, portfolioDataAccessObject);
            DeleteState deleteState = new DeleteState();
            return new LoggedInView(loggedInViewModel, deleteState, deleteController);
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Could not open user data file");
        }
        return null;
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
}
