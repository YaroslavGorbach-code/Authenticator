package yaroslavgorbach.totp.di.data.token

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yaroslavgorbach.totp.data.token.local.repo.TokenRepo
import yaroslavgorbach.totp.data.token.local.repo.TokenRepoImp
import yaroslavgorbach.totp.data.token.local.dao.TokensDao
import yaroslavgorbach.totp.data.token.local.TokensDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataTokenModule {

    @Provides
    fun provideTokenRepo(tokensDao: TokensDao): TokenRepo {
        return TokenRepoImp(tokensDao)
    }

    @Singleton
    @Provides
    fun provideTokenDatabase(application: Application): TokensDatabase {
        return TokensDatabase.getInstance(application)
    }

    @Singleton
    @Provides
    fun provideTokenDao(database: TokensDatabase): TokensDao {
        return database.tokens
    }
}