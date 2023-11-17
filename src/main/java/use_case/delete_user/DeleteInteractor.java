package use_case.delete_user;

import data_access.FilePortfolioDataAccessObject;
import use_case.signup.SignupUserDataAccessInterface;
public class DeleteInteractor implements DeleteInputBoundary{
    final DeleteUserDataAccessInterface deleteDataAccessObject;
    final DeletePortfolioAccessInterface deletePortfolioDataAccessObject;
    final DeleteOutputBoundary deletePresenter;
    public DeleteInteractor(SignupUserDataAccessInterface deleteDataAccessObject, DeleteOutputBoundary deletePresenter, FilePortfolioDataAccessObject portfolioDataAccessObject){
        this.deletePresenter = deletePresenter;
        this.deleteDataAccessObject = (DeleteUserDataAccessInterface) deleteDataAccessObject;
        this.deletePortfolioDataAccessObject = portfolioDataAccessObject;
    }
    @Override
    public void execute(String id) {
        //TODO Fix this so that if the users/portfolio is not deleted it shows a fail view
        deleteDataAccessObject.deleteUser(id);
        deletePortfolioDataAccessObject.deletePortfolio(id);
        DeleteOutputData output = new DeleteOutputData(id, true);
        deletePresenter.prepareSuccessView(output);
    }
}
