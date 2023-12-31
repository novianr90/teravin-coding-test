package id.novian.teravin_challenge.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.novian.teravin_challenge.data.dataSource.local.LocalDatabase
import id.novian.teravin_challenge.data.dataSource.network.ApiService
import id.novian.teravin_challenge.data.repository_impl.MovieRepositoryImpl
import id.novian.teravin_challenge.domain.repository.MovieRepository
import id.novian.teravin_challenge.util.Constant
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "local-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRxJava3CallAdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.apiUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(localDatabase: LocalDatabase): MovieRepository {
        return MovieRepositoryImpl(localDatabase.dao)
    }

    @Provides
    @Singleton
    @Named("IO")
    fun provideSchedulerIo(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    @Named("MAIN")
    fun provideSchedulerMain(): Scheduler = AndroidSchedulers.mainThread()

}