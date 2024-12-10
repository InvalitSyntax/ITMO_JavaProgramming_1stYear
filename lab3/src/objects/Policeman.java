package objects;

import exeptions.IllegalArrestException;
import interfaces.OrderObserver;
import records.ViolationDetail;

import java.util.ArrayList;
import java.util.List;

public class Policeman implements OrderObserver {
    private final List<LittleMan> arrestedLittleMen = new ArrayList<>();

    @Override
    public void monitorOrder() {
        System.out.println("Милиционер следит за порядком.");
    }

    public void arrest(LittleMan littleMan, String reason, int days) throws IllegalArrestException {
        if (littleMan.isAvailableToViolate()) {
            littleMan.setViolationDetail(new ViolationDetail(reason, days));
            arrestedLittleMen.add(littleMan);
            System.out.println(littleMan.getName() + " арестован на " + days + " дней.");
        } else {
            throw new IllegalArrestException("Нельзя арестовать умного коротышку!");
        }
    }
}

