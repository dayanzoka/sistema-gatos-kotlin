package com.example.sistema_cuidadogatos.di

import androidx.room.Room
import com.example.sistema_cuidadogatos.api.JsonPlaceholderService
import com.example.sistema_cuidadogatos.database.CuiGatoDatabase
import com.example.sistema_cuidadogatos.repository.CuiGatoRepository
import com.example.sistema_cuidadogatos.ui.viewmodel.ApiDemoViewModel
import com.example.sistema_cuidadogatos.ui.viewmodel.GatoViewModel
import com.example.sistema_cuidadogatos.ui.viewmodel.RegistroSaudeViewModel
import com.example.sistema_cuidadogatos.ui.viewmodel.TratamentoViewModel
import com.example.sistema_cuidadogatos.ui.viewmodel.TutorViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    // --- Room Database ---
    single {
        Room.databaseBuilder(
            androidApplication(),
            CuiGatoDatabase::class.java,
            "sistema_cuidadogatos_db"
        ).fallbackToDestructiveMigration().build()
    }
    // DAOs - A injeção falha se o KSP não gerou este código
    single { get<CuiGatoDatabase>().tutorDao() }
    single { get<CuiGatoDatabase>().gatoDao() }
    single { get<CuiGatoDatabase>().tratamentoDao() }
    single { get<CuiGatoDatabase>().registroSaudeDao() }

    // --- Retrofit & API ---
    single {
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    // O Koin precisa da instância de Retrofit para criar o serviço
    single {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create()) // ⬅️ Falha se Gson não for resolvido
            .build()
    }
    single { get<Retrofit>().create(JsonPlaceholderService::class.java) }

    // --- Repository ---
    // O Koin injeta os 4 DAOs e o 1 API Service
    single { CuiGatoRepository(get(), get(), get(), get(), get()) }

    // --- ViewModels ---
    viewModel { TutorViewModel(get()) }
    viewModel { GatoViewModel(get()) }
    viewModel { TratamentoViewModel(get()) }
    viewModel { RegistroSaudeViewModel(get()) }
    viewModel { ApiDemoViewModel(get()) }
}