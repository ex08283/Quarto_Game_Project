package Quarto.View.MenuScreen;

import Quarto.Model.Quarto;
import Quarto.Model.QuartoException;
import Quarto.Model.SpelerRanking;
import Quarto.View.LastGameView.LastGamePresenter2;
import Quarto.View.LastGameView.LastGameView2;
import Quarto.View.LastGameView.LastGamePresenter;
import Quarto.View.NamesScreen.NamesPresenter;
import Quarto.View.NamesScreen.NamesView;
import Quarto.View.RankingScreen.RankingPresenter;
import Quarto.View.RankingScreen.RankingView;
import Quarto.View.UISettings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author Willem Kuijpers
 * @version 1.0 10-5-2021 15:46
 */
public class MenuScreenPresenter {

    private Quarto model;
    private MenuScreenView view;
    private UISettings uiSettings;


    public MenuScreenPresenter(Quarto model, MenuScreenView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        updateView();
        EventHandlers();
    }

    private void updateView() {

    }

    private void EventHandlers() {
        nieuwSpelHandler();
        rankingHandler();
        lastGameHandler();
        afsluitenHandler();
    }

    private void nieuwSpelHandler() {
        view.getNieuwSpel().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                NamesView namesView = new NamesView(uiSettings);
                NamesPresenter namesPresenter = new NamesPresenter(model,namesView,uiSettings);
                view.getScene().setRoot(namesView);
                namesView.getScene().getWindow().sizeToScene();
                try {
                    namesView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                } catch (MalformedURLException ex) {
                    // // do nothing, if toURL-conversion fails, program can continue
                }
                namesView.getScene().getWindow().sizeToScene();
//                namesView.getScene().getWindow().setX(uiSettings.getResX()/20);
//                namesView.getScene().getWindow().setY(uiSettings.getResY()/20);
                namesView.getScene().getWindow().setHeight(view.getHeight());
                namesView.getScene().getWindow().setWidth(view.getWidth());
                namesPresenter.windowsHandler();
            }
        });
    }

    private void rankingHandler() {
        view.getRanking().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                RankingView rankingView = new RankingView();
                SpelerRanking spelerRanking = new SpelerRanking();
                RankingPresenter rankingPresenter = new RankingPresenter(model,rankingView,uiSettings, spelerRanking);
                view.getScene().setRoot(rankingView);
                rankingView.getScene().getWindow().sizeToScene();
                try {
                    rankingView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                } catch (MalformedURLException ex) {
                    // // do nothing, if toURL-conversion fails, program can continue
                }
                rankingView.getScene().getWindow().sizeToScene();
                rankingView.getScene().getWindow().setX(uiSettings.getResX()/20);
                rankingView.getScene().getWindow().setY(uiSettings.getResY()/20);
                rankingView.getScene().getWindow().setHeight(9 * uiSettings.getResY()/10);
                rankingView.getScene().getWindow().setWidth(9 * uiSettings.getResX()/10);
//                rankingPresenter.windowsHandler();
            }
        });
    }

    private void lastGameHandler() {
        view.getToonLaatsteSpel().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    model.setPlayerForAnimation();
                } catch (IOException ioException) {
                    System.out.println("something went wrong with setting player for animation");
                } catch (QuartoException exception) {
                    exception.printStackTrace();
                }
                LastGameView2 lastGameView = new LastGameView2(uiSettings);
                LastGamePresenter2 lastGamePresenter = new LastGamePresenter2(model,lastGameView,uiSettings);
                view.getScene().setRoot(lastGameView);
                lastGameView.getScene().getWindow().sizeToScene();
                try {
                    lastGameView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                } catch (MalformedURLException ex) {
                    // // do nothing, if toURL-conversion fails, program can continue
                }
                lastGameView.getScene().getWindow().sizeToScene();
                lastGameView.getScene().getWindow().setX(uiSettings.getResX()/20);
                lastGameView.getScene().getWindow().setY(uiSettings.getResY()/20);
                lastGameView.getScene().getWindow().setHeight(9 * uiSettings.getResY()/10);
                lastGameView.getScene().getWindow().setWidth(9 * uiSettings.getResX()/10);
                lastGamePresenter.windowsHandler();
            }
        });
    }

    private void afsluitenHandler() {
        view.getAfsluiten().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleCloseEvent(actionEvent);
            }
        });
    }

    public void windowsHandler() {
        view.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) { handleCloseEvent(event); }});
    }

    private void handleCloseEvent(Event event){
        final Alert stopWindow = new Alert(Alert.AlertType.CONFIRMATION);
        stopWindow.setHeaderText("Je gaat de applicatie afsluiten.");
        stopWindow.setContentText("Ben je zeker? Onopgeslaagde data kan verloren gaan.");
        stopWindow.setTitle("WAARSCHUWING!");
        stopWindow.getButtonTypes().clear();
        ButtonType noButton = new ButtonType("Nee");
        ButtonType yesButton = new ButtonType("Ja");
        stopWindow.getButtonTypes().addAll(yesButton, noButton);
        stopWindow.showAndWait();
        if (stopWindow.getResult() == null || stopWindow.getResult().equals(noButton)) {
            event.consume();
        } else {
            view.getScene().getWindow().hide();
        }
    }
}

