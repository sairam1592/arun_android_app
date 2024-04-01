package com.example.myapplication.arunproject.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.arunproject.common.AppConstants
import com.example.myapplication.arunproject.common.AppDatabase
import com.example.myapplication.arunproject.data.datasource.local.RecipeLocalDataSource
import com.example.myapplication.arunproject.data.datasource.local.RecipeLocalDataSourceImpl
import com.example.myapplication.arunproject.data.datasource.remote.RecipeRemoteDataSource
import com.example.myapplication.arunproject.data.datasource.remote.RecipeRemoteDataSourceImpl
import com.example.myapplication.arunproject.data.model.db.RecipeDao
import com.example.myapplication.arunproject.data.network.RecipeService
import com.example.myapplication.arunproject.data.repository.RecipeRepository
import com.example.myapplication.arunproject.data.repository.RecipeRepositoryImpl
import com.example.myapplication.arunproject.domain.usecase.GetRecipeByIdUseCase
import com.example.myapplication.arunproject.domain.usecase.GetRecipesUseCase
import com.example.myapplication.arunproject.presentation.viewmodel.RecipeViewModel
import com.example.myapplication.recipescreen.data.datasource.remote.FirebaseRemoteDataSource
import com.example.myapplication.recipescreen.data.datasource.remote.FirebaseRemoteDataSourceImpl
import com.example.myapplication.recipescreen.data.repository.RecipeDetailsRepository
import com.example.myapplication.recipescreen.data.repository.RecipeDetailsRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideRecipeService(retrofit: Retrofit): RecipeService {
        return retrofit.create(RecipeService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeRemoteDataSource(recipeService: RecipeService): RecipeRemoteDataSource {
        return RecipeRemoteDataSourceImpl(recipeService)
    }

    @Provides
    @Singleton
    fun provideRecipeLocalDataSource(recipeDao: RecipeDao): RecipeLocalDataSource {
        return RecipeLocalDataSourceImpl(recipeDao)
    }

    @Provides
    fun provideRecipeRepository(
        recipeRemoteDataSource: RecipeRemoteDataSource,
        recipeLocalDataSource: RecipeLocalDataSource
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeRemoteDataSource, recipeLocalDataSource)
    }

    @Provides
    fun provideGetRecipesUseCase(repository: RecipeRepository): GetRecipesUseCase {
        return GetRecipesUseCase(repository)
    }

    @Provides
    fun provideGetRecipeByIdUseCase(repository: RecipeRepository): GetRecipeByIdUseCase {
        return GetRecipeByIdUseCase(repository)
    }

    @Provides
    fun provideRecipeViewModel(
        getRecipesUseCase: GetRecipesUseCase,
        ioDispatcher: CoroutineDispatcher
    ): RecipeViewModel {
        return RecipeViewModel(getRecipesUseCase, ioDispatcher)
    }


    @Provides
    fun provideRecipeDetailsRepository(firebaseRemoteDataSourceImpl: FirebaseRemoteDataSourceImpl): RecipeDetailsRepository {
        return RecipeDetailsRepositoryImpl(firebaseRemoteDataSourceImpl)
    }

    @Provides
    @Singleton
    fun provideFirebaseRemoteDataSource(database: FirebaseFirestore): FirebaseRemoteDataSource {
        return FirebaseRemoteDataSourceImpl(database)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppConstants.DB_NAME
        ).build()
    }

    @Provides
    fun provideRecipeDao(database: AppDatabase): RecipeDao {
        return database.recipeDao()
    }
}