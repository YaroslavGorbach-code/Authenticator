package yaroslavgorbach.totp.di.data.token

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yaroslavgorbach.totp.data.token.TokenRepo
import yaroslavgorbach.totp.data.token.TokenRepoImp

@Module
@InstallIn(SingletonComponent::class)
class DataTokenModule {

    @Provides
    fun provideTokenRepo(application: Application): TokenRepo {
        return TokenRepoImp(application)
    }

}