package com.example.witcheroldworldhelper;

import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModel;

import com.example.witcheroldworldhelper.database.StanZetonow;
import com.example.witcheroldworldhelper.database.StanZetonowDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainViewModel extends ViewModel {
    public ArrayList<Integer> las = new ArrayList<>(Arrays.asList(6, 7, 8, 10, 16, 17));
    public ArrayList<Integer> woda = new ArrayList<>(Arrays.asList(1, 4, 5, 12, 14, 15));
    public ArrayList<Integer> gory = new ArrayList<>(Arrays.asList(2, 3, 9, 11, 13, 18));
    public  ArrayList<Integer> zajetyLas = new ArrayList<>(Arrays.asList());
    public  ArrayList<Integer> zajetaWoda = new ArrayList<>(Arrays.asList());
    public  ArrayList<Integer> zajeteGory = new ArrayList<>(Arrays.asList());
    public  Random random = new Random();

    public StanZetonowDB db;

    void pobierzStanZetonowZBazy() {

        try {
            StanZetonow stanZetonow = db.stanZetonowDao().getStanZetonow();
            las = stanZetonow.las;
            woda = stanZetonow.woda;
            gory = stanZetonow.gory;
            zajetyLas = stanZetonow.zajetyLas;
            zajetaWoda = stanZetonow.zajetaWoda;
            zajeteGory = stanZetonow.zajeteGory;

        } catch (Exception e) {
            las = new ArrayList<>(Arrays.asList(6, 7, 8, 10, 16, 17));
            woda = new ArrayList<>(Arrays.asList(1, 4, 5, 12, 14, 15));
            gory = new ArrayList<>(Arrays.asList(2, 3, 9, 11, 13, 18));
            zajetyLas = new ArrayList<>(Arrays.asList());
            zajetaWoda = new ArrayList<>(Arrays.asList());
            zajeteGory = new ArrayList<>(Arrays.asList());
        }
    }



    public void setDb(StanZetonowDB db) {
        this.db = db;
    }

    int losujZetonSlabosci(ArrayList<Integer> srodowisko) {
        return srodowisko.get(random.nextInt(srodowisko.size()));
    }

    int[] losujPlotke(ArrayList<Integer> srodowisko) {

        int[] wylosowaneZetony = new int[2];
        int[] ostatniZeton = new int[1];

        if (srodowisko.size() == 0) {
            return null;
        }

        if (srodowisko.size() < 2) {
            srodowisko.get(0);
            return ostatniZeton;
        }

        Random random = new Random();
        int pierwszyZeton = srodowisko.get(random.nextInt(srodowisko.size()));
        wylosowaneZetony[0] = pierwszyZeton;

        int drugiNumer;
        do {
            drugiNumer = srodowisko.get(random.nextInt(srodowisko.size()));
        } while (drugiNumer == pierwszyZeton);

        wylosowaneZetony[1] = drugiNumer;

        return wylosowaneZetony;
    }

    void wyswietlWykluczone() {

    }
    String przywrocZeton(int numer) {

        String message = "Pomyślnie przywrócono żeton" + numer;

        if (zajetyLas.contains(numer)) {
            zajetyLas.remove(Integer.valueOf(numer));
            las.add(numer);

            zapiszStanZetonow();
            return message;
        } else if (zajetaWoda.contains(numer)) {
            zajetaWoda.remove(Integer.valueOf(numer));
            woda.add(numer);

            zapiszStanZetonow();
            return message;
        } else if (zajeteGory.contains(numer)) {
            zajeteGory.remove(Integer.valueOf(numer));
            gory.add(numer);

            zapiszStanZetonow();
            return message;
        } else {
            return "Żeton " + numer + " nie znajduje się w puli wykluczonej.";
        }
    }

    String wykluczZeton(int numer) {

        String message = "Pomyślnie zajęto żeton" + numer;

        if(las.contains(numer)) {
            las.remove(Integer.valueOf(numer));
            zajetyLas.add(numer);

            zapiszStanZetonow();
            return message;
        } else if(woda.contains(numer)) {
            woda.remove(Integer.valueOf(numer));
            zajetaWoda.add(numer);

            zapiszStanZetonow();
            return message;
        } else if(gory.contains(numer)) {
            gory.remove(Integer.valueOf(numer));;
            zajeteGory.add(numer);

            zapiszStanZetonow();
            return message;
        } else {
            return "Żeton " + numer + " nie istnieje";
        }
    }

    void resetAplikacji() {
        las = new ArrayList<>(Arrays.asList(6, 7, 8, 10, 16, 17));
        woda = new ArrayList<>(Arrays.asList(1, 4, 5, 12, 14, 15));
        gory = new ArrayList<>(Arrays.asList(2, 3, 9, 11, 13, 18));
        zajetyLas = new ArrayList<>(Arrays.asList());
        zajetaWoda = new ArrayList<>(Arrays.asList());
        zajeteGory = new ArrayList<>(Arrays.asList());

        StanZetonow nowyStanZetonow = new StanZetonow(
                las,
                woda,
                gory,
                zajetyLas,
                zajetaWoda,
                zajeteGory
        );

        db.stanZetonowDao().updateStanZetnow(nowyStanZetonow);
    }

    void zapiszStanZetonow() {
        StanZetonow nowyStanZetonow = new StanZetonow(
                las,
                woda,
                gory,
                zajetyLas,
                zajetaWoda,
                zajeteGory
        );
        db.stanZetonowDao().updateStanZetnow(nowyStanZetonow);
    }


}
