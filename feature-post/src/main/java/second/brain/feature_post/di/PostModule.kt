package second.brain.feature_post.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.repository.DataRepository
import network.ResponseHandler
import second.brain.feature_post.data.repository.PostsRepositoryImpl
import second.brain.feature_post.domain.repository.PostsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostsModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun providePostsRepository(
        firestore: FirebaseFirestore,
        responseHandler: ResponseHandler,
        dataRepository: DataRepository
    ): PostsRepository {
        return PostsRepositoryImpl(firestore, responseHandler, dataRepository)
    }
}