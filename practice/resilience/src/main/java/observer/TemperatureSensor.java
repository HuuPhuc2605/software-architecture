package observer;

import java.util.ArrayList;
import java.util.List;

class TemperatureSensor {
    private List<Observer> observers = new ArrayList<>();
    private int temperature;

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer o : observers) {
            o.update(temperature);
        }
    }
}

