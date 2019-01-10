package Observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<ItemObserver> observers = new ArrayList<>();

    public void addObserver (ItemObserver itemObserver){
        if (!observers.contains(itemObserver)){
            observers.add(itemObserver);
        }
    }
}
