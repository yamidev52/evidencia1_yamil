package com.yamidev.myapplication.resources;
import com.yamidev.myapplication.resources.Pokemon;

import com.yamidev.myapplication.resources.PokemonFetchResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonAPIService {
    @GET("pokemon/?limit=50")
    Call<PokemonFetchResults> getPokemons();


    @GET("pokemon/{name}")
    Call<Pokemon> getPokemon(@Path("name") String name);
}
