package com.yamidev.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.yamidev.myapplication.resources.ApiClient;
import com.yamidev.myapplication.resources.Pokemon;
import com.yamidev.myapplication.resources.PokemonAPIService;

import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etPokemon;
    private ImageView ivPokemon;
    private Button btnBuscar, btnLimpiar;
    private ImageButton btnRandom;
    private CheckBox chkShiny;
    private ToggleButton toggleSprite;
    private RadioButton rbFrente, rbEspalda;
    private SwitchCompat swDetalles;
    private TextView tvDetalles;

    private PokemonAPIService apiService;
    private final Random rnd = new Random();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Mantiene tu padding edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        etPokemon = findViewById(R.id.etPokemon);
        ivPokemon = findViewById(R.id.ivPokemon);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnRandom = findViewById(R.id.btnRandom);
        chkShiny = findViewById(R.id.chkShiny);
        toggleSprite = findViewById(R.id.toggleSprite);
        rbFrente = findViewById(R.id.rbFrente);
        rbEspalda = findViewById(R.id.rbEspalda);
        swDetalles = findViewById(R.id.swDetalles);
        tvDetalles = findViewById(R.id.tvDetalles);


        // —— API Retrofit ——
        apiService = ApiClient.getClient().create(PokemonAPIService.class);


        btnBuscar.setOnClickListener(v -> {
            String q = etPokemon.getText().toString().trim();
            if (q.isEmpty()) {
                Toast.makeText(this, "Ingresa nombre o ID", Toast.LENGTH_SHORT).show();
            } else {
                buscarPokemon(q.toLowerCase(Locale.ROOT));
            }
        });


        btnRandom.setOnClickListener(v -> {
            // Pokedex nacional (1–1010 aprox)
            int randomId = rnd.nextInt(1010) + 1;
            buscarPokemon(String.valueOf(randomId));
        });


        btnLimpiar.setOnClickListener(v -> limpiarUI());


        CompoundButton.OnCheckedChangeListener repaint = (button, isChecked) -> {
            Object tag = ivPokemon.getTag();
            if (tag instanceof Pokemon) {
                mostrarPokemon((Pokemon) tag);
            }
        };
        chkShiny.setOnCheckedChangeListener(repaint);
        toggleSprite.setOnCheckedChangeListener(repaint);
        rbFrente.setOnCheckedChangeListener(repaint);
        rbEspalda.setOnCheckedChangeListener(repaint);
        swDetalles.setOnCheckedChangeListener(repaint);

    }

    private void buscarPokemon(String nameOrId) {
        ocultarDetalles();
        ivPokemon.setImageDrawable(null);
        Call<Pokemon> call = apiService.getPokemon(nameOrId);
        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(MainActivity.this, "Pokémon no encontrado", Toast.LENGTH_SHORT).show();
                    return;
                }
                Pokemon p = response.body();
                ivPokemon.setTag(p); // guardo para re-render
                mostrarPokemon(p);
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void mostrarPokemon(Pokemon p) {
        String url = getSelectedImageUrl(p);
        if (url == null || url.isEmpty()) {
            Toast.makeText(this, "No hay sprite disponible para esta combinación", Toast.LENGTH_SHORT).show();
            ivPokemon.setImageDrawable(null);
        } else {
            Glide.with(this).load(url).into(ivPokemon);
        }

        if (swDetalles.isChecked()) {
            StringBuilder sb = new StringBuilder();
            sb.append("ID: ").append(p.getId()).append("\n");
            sb.append("Nombre: ").append(p.getName()).append("\n");
            sb.append("Peso: ").append(p.getWeight()).append("\n");

            sb.append("Tipos: ");
            if (p.getTypes() != null) {
                for (Pokemon.TypeSlot t : p.getTypes()) {
                    if (t.getType() != null && t.getType().getName() != null) {
                        sb.append(t.getType().getName()).append(" ");
                    }
                }
            }

            sb.append("\nHabilidades: ");
            if (p.getAbilities() != null) {
                for (Pokemon.AbilitySlot a : p.getAbilities()) {
                    if (a.getAbility() != null && a.getAbility().getName() != null) {
                        sb.append(a.getAbility().getName()).append(" ");
                    }
                }
            }

            tvDetalles.setText(sb.toString());
            tvDetalles.setVisibility(View.VISIBLE);
        } else {
            ocultarDetalles();
        }
    }

    private String getSelectedImageUrl(Pokemon p) {
        boolean shiny = chkShiny.isChecked();
        boolean artwork = toggleSprite.isChecked();
        boolean frente = rbFrente.isChecked();

        if (p.getSprites() == null) return null;

        if (artwork) {
            if (p.getSprites().getOther() != null &&
                    p.getSprites().getOther().getOfficialArtwork() != null) {
                return p.getSprites().getOther().getOfficialArtwork().getFrontDefault();
            }
            return null;
        } else {
            if (frente) {
                return shiny ? p.getSprites().getFrontShiny() : p.getSprites().getFrontDefault();
            } else {
                return shiny ? p.getSprites().getBackShiny() : p.getSprites().getBackDefault();
            }
        }
    }


    private void limpiarUI() {
        etPokemon.setText("");
        ivPokemon.setImageDrawable(null);
        ivPokemon.setTag(null);
        chkShiny.setChecked(false);
        toggleSprite.setChecked(false); // false = sprite
        rbFrente.setChecked(true);
        rbEspalda.setChecked(false);
        swDetalles.setChecked(false);
        ocultarDetalles();
    }

    private void ocultarDetalles() {
        tvDetalles.setText("");
        tvDetalles.setVisibility(View.GONE);
    }


}