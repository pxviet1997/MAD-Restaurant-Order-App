package com.example.vietpham.restaurant;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Menu {
    private ArrayList<Meal> menu;
    private static ArrayList<Meal> orderedList;

    public Menu() {
        menu = new ArrayList<Meal>();
        orderedList = new ArrayList<Meal>();
    }

    public String menuList() {
        String menu = "";
        for (Meal m : this.menu) {
            menu += m;
        }
        return menu;
    }

    public ArrayList<Meal> getMenu() {
        return menu;
    }

    public ArrayList<Meal> getPizzaMenu() {
        ArrayList<Meal> pizzaMenu = new ArrayList<Meal>();
        for (int i=0; i<menu.size(); i++) {
            if (menu.get(i).getCategory().equals("pizza")) {
                pizzaMenu.add(menu.get(i));
            }
        }
        return pizzaMenu;
    }

    public ArrayList<Meal> getPastaMenu() {
        ArrayList<Meal> pastaMenu = new ArrayList<Meal>();
        for (int i=0; i<menu.size(); i++) {
            if (menu.get(i).getCategory().equals("pasta")) {
                pastaMenu.add(menu.get(i));
            }
        }
        return pastaMenu;
    }

    public int getOrderedListSize() {
        return orderedList.size();
    }

    public ArrayList<Meal> getDrinksMenu() {
        ArrayList<Meal> drinksMenu = new ArrayList<Meal>();
        for (int i=0; i<menu.size(); i++) {
            if (menu.get(i).getCategory().equals("drinks")) {
                drinksMenu.add(menu.get(i));
            }
        }
        return drinksMenu;
    }

    public ArrayList<Meal> getOrderedList() { return orderedList; }

    public Meal getMeal(String id) {
        for (int i=0; i< menu.size(); i++) {
            if (menu.get(i).getId().equals(id)) {
                return menu.get(i);
            }
        }
        return null;
    }

    public String orderedList() {
        String menu = "";
        for (Meal m : this.orderedList) {
            menu += m;
        }
        return menu;
    }

    public void addToMenu(Meal meal) {
        menu.add(meal);
    }

    public void addToOrderedList(Meal meal) {
        orderedList.add(meal);
    }

    public void resetOrderedList() {
        orderedList = new ArrayList<>();
    }

}

