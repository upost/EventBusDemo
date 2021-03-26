package de.androidnewcomer.eventbusdemo;

public class StartCalculationMessage {
    private final String parameter;

    public StartCalculationMessage(String parameter) {

        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
