package model;

import Exceptions.ItemDoesntExist;

import java.util.ArrayList;

public class ItemSingleton {
    private static ItemSingleton Instance;
    private static ArrayList<LoanItem> list = null;


    public static ItemSingleton getInstance(){
        if(Instance == null) {
            Instance = new ItemSingleton();
        }
        return Instance;
    }

    private ItemSingleton(){
        list = new ArrayList<>();
        addToList(item1);
        addToList(item2);
        addToList(item3);
    }

    public static ArrayList<LoanItem> getList() {
        return list;
    }

    public void addToList(LoanItem item){
        list.add(item);
    }

    public LoanItem getItem(String name) throws ItemDoesntExist{
        if (list.size() > 0) {
            for (LoanItem item : list) {
                if (name.equals(item.getName())) {
                    return item;
                } else throw new ItemDoesntExist();
            }
        } else{
            throw new ItemDoesntExist();
        }
        return null;
    }

    LoanItem item1 = new UrgentReturn("shirt", "black", "small", true, 11, 4, false, new ArrayList<>());
    LoanItem item2 = new NonUrgentReturn("scarf", "blue", "one-size", false, 11, 4, false, new ArrayList<>());
    LoanItem item3 = new UrgentReturn("skirt", "red", "medium", true, 12, 11, false, new ArrayList<>());
}
