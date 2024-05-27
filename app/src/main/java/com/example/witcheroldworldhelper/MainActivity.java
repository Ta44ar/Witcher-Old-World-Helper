package com.example.witcheroldworldhelper;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.witcheroldworldhelper.database.StanZetonowDB;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private TextView resultTextView2;



    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        StanZetonowDB db = Room.databaseBuilder(getApplicationContext(),
                StanZetonowDB.class, "Stan-Zetonow-db")
                .allowMainThreadQueries()
                .build();

        viewModel.setDb(db);
        viewModel.pobierzStanZetonowZBazy();
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        resultTextView = findViewById(R.id.resultTextView);
        resultTextView2 = findViewById(R.id.resultTextView2);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button plotkaButton1 = findViewById(R.id.plotkaButton1);
        Button plotkaButton2 = findViewById(R.id.plotkaButton2);
        Button plotkaButton3 = findViewById(R.id.plotkaButton3);
        Button wykluczButton = findViewById(R.id.wykluczZeton);
        Button przywrocButton = findViewById(R.id.przywrocZeton);
        Button resetButton = findViewById(R.id.reset);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokazWynikDlaZetonuSlabosci(viewModel.las);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokazWynikDlaZetonuSlabosci(viewModel.woda);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokazWynikDlaZetonuSlabosci(viewModel.gory);
            }
        });

        plotkaButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokazWynikLosowaniaPlotki(viewModel.las);
            }
        });

        plotkaButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokazWynikLosowaniaPlotki(viewModel.woda);
            }
        });

        plotkaButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokazWynikLosowaniaPlotki(viewModel.gory);
            }
        });

        wykluczButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wykluczZeton();
            }
        });

        przywrocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { przywrocZeton(); }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { resetujAplikacje(); }
        });
    }
    void pokazWynikDlaZetonuSlabosci(final ArrayList<Integer> srodowisko) {
        int randomValue = viewModel.losujZetonSlabosci(srodowisko);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wynik losowania");
        builder.setMessage("Wylosowano numer: " + randomValue);
        builder.setPositiveButton("Losuj ponownie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Losowanie ponownie po naciśnięciu przycisku "Losuj ponownie"
                pokazWynikDlaZetonuSlabosci(srodowisko);
            }
        });
        builder.setNegativeButton("Zamknij", null);

        // Wyświetlanie okna dialogowego
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void pokazWynikLosowaniaPlotki(ArrayList<Integer> srodowisko) {

        int[] wynik = viewModel.losujPlotke(srodowisko);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wynik losowania Płotki");

        if (wynik == null) {
            builder.setMessage("Stos jest pusty.");
        } else if (wynik.length == 1) {
            builder.setMessage("Stos zawiera tylko jeden zeton.\n Wylosowany numer:" + wynik[0]);
        } else {
            builder.setMessage("Wylosowane numery: " + wynik[0] + ", " + wynik[1]);
        }

        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void przywrocZeton() {
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Przywróć wybrany żeton:")
                .setView(input)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(input.getText().toString() != "") {
                            String result = viewModel.przywrocZeton(Integer.valueOf(input.getText().toString()));

                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void wykluczZeton() {
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Zajmij żeton")
                .setView(input)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(input.getText().toString() != "") {
                            String result = viewModel.wykluczZeton(Integer.valueOf(input.getText().toString()));

                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                    }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void resetujAplikacje() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset aplikacji");
        builder.setMessage("Czy na pewno chcesz zresetować aplikację? Aktualny stan żetonów zostanie utracony!");
        builder.setPositiveButton("TAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.resetAplikacji();
            }
        });
        builder.setNegativeButton("NIE", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }




}