package com.example.animals

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animals.api.AnimalApi
import com.example.animals.di.AppModule
import com.example.animals.di.DaggerViewModelComponent
import com.example.animals.model.Animal
import com.example.animals.model.ApiKey
import com.example.animals.util.SharedPreferencesHelper
import com.example.animals.viewmodel.AnimalsViewModel
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.*
import org.junit.Assert.assertEquals
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class AnimalsViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock private lateinit var animalApi: AnimalApi
    @Mock private lateinit var prefs: SharedPreferencesHelper

    private val application: Application = Mockito.mock(Application::class.java)
    private var viewModel = AnimalsViewModel(application, true)
    private lateinit var closeable: AutoCloseable

    private val key = "Test key"

    @Before
    fun setup() {
        closeable = MockitoAnnotations.openMocks(this)
        DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(animalApi))
            .prefsModule(PrefsModuleTest(prefs))
            .build()
            .inject(viewModel)
    }

    @Before
    fun setupRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker({ it.run() }, true, true)
            }
        }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }

    @Test
    fun getAnimalsSuccess() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)

        val animalList = listOf(
            Animal("cow", null, null, null,
                null, null, null)
        )
        val testSingle = Single.just(animalList)

        Mockito.`when`(animalApi.getAnimals(key)).thenReturn(testSingle)

        viewModel.refresh()

        assertEquals(1, viewModel.animals.value?.size)
        assertEquals(false, viewModel.loadError.value)
        assertEquals(false, viewModel.loading.value)
    }

    @Test
    fun getAnimalsFailure() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)

        val testSingle = Single.error<List<Animal>>(Throwable())
        val keySingle = Single.just(ApiKey("Ok", key))

        Mockito.`when`(animalApi.getAnimals(key)).thenReturn(testSingle)
        Mockito.`when`(animalApi.getApiKey()).thenReturn(keySingle)

        viewModel.refresh()

        assertEquals(null, viewModel.animals.value)
        assertEquals(false, viewModel.loading.value)
        assertEquals(true, viewModel.loadError.value)
    }

    @Test
    fun getApiKeySuccess() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)

        val apiKey = ApiKey("Ok", key)
        val keySingle = Single.just(apiKey)

        Mockito.`when`(animalApi.getApiKey()).thenReturn(keySingle)

        val animalList = listOf(
            Animal("cow", null, null, null,
                null, null, null)
        )
        val testSingle = Single.just(animalList)

        Mockito.`when`(animalApi.getAnimals(key)).thenReturn(testSingle)

        viewModel.refresh()

        assertEquals(1, viewModel.animals.value?.size)
        assertEquals(false, viewModel.loading.value)
        assertEquals(false, viewModel.loadError.value)
    }

    @Test
    fun getKeyFailure() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)

        val keySingle = Single.error<ApiKey>(Throwable())

        Mockito.`when`(animalApi.getApiKey()).thenReturn(keySingle)

        viewModel.refresh()

        assertEquals(null, viewModel.animals.value)
        assertEquals(false, viewModel.loading.value)
        assertEquals(true, viewModel.loadError.value)
    }

    @After
    fun releaseMocks() {
        closeable.close()
    }
}