package de.androidnewcomer.eventbusdemo;

public class CalculationResultMessage {
    private final String result;

    public CalculationResultMessage(String result) {

        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
