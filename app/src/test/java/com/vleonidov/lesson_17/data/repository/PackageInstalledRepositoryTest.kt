package com.vleonidov.lesson_17.data.repository

import com.vleonidov.lesson_17.data.model.InstalledPackageModel
import com.vleonidov.lesson_17.data.provider.PackageInstalledProvider
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class PackageInstalledRepositoryTest {

    private val provider: PackageInstalledProvider = mockk()
    private lateinit var repository: PackageInstalledRepository

    @Before
    fun setUp() {
        repository = PackageInstalledRepository(provider)
    }

    @Test
    fun loadDataAsyncRx() {

        // Arrange
        val data: List<InstalledPackageModel> = mockk()

        every { provider.getData(IS_SYSTEM) } returns data

        // Act
        val testSubscriber = repository.loadDataAsyncRx(IS_SYSTEM)
            .test()

        // Assert
        testSubscriber
            .assertResult(data)
            .assertComplete()
        testSubscriber.dispose()
    }

    private companion object {
        const val IS_SYSTEM = false
    }
}