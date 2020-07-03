package graphicsInterface;

import client.servises.IAnswerHandler;
import graphicsInterface.controllers.Controllers;
import library.clientCommands.SpecialSignals;

import javax.swing.*;

public class GuiAnswerHandler implements IAnswerHandler {
    private volatile Controllers controllers;

    public GuiAnswerHandler(Controllers controllers) {
        this.controllers = controllers;
    }

    @Override
    public void answerHandle(Object answerObject) {
        if(answerObject instanceof SpecialSignals) {
            SpecialSignals specialSignal = (SpecialSignals) answerObject;

            if (specialSignal == SpecialSignals.REG_FALSE || specialSignal == SpecialSignals.REG_TRUE) {
                SwingUtilities.invokeLater(() -> {
                    controllers.regResponseHandle(specialSignal);
                    controllers.commandsResponseHandle(answerObject);});
            } else if (specialSignal == SpecialSignals.AUTHORIZATION_FALSE || specialSignal == SpecialSignals.AUTHORIZATION_TRUE) {
                System.out.println("tut");
                System.out.println(controllers);
                SwingUtilities.invokeLater(() -> {
                    controllers.logResponseHandle(specialSignal);
                    controllers.commandsResponseHandle(answerObject);});
            }
        } else {
            SwingUtilities.invokeLater(() -> controllers.commandsResponseHandle(answerObject));
        }



    }

    public Controllers getControllers() {
        return controllers;
    }

    public void setControllers(Controllers controllers) {
        this.controllers = controllers;
    }
}
