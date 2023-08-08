package com.ics342.labs

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class NumbersRepository(private val database: Database, private val api: Api) {

    fun fetchNumbers(): List<Number> {
        val numbersInDatabase = database.getAllNumbers()
        return if (numbersInDatabase.isEmpty()) {
            val numbers = api.getNumbers()
            database.storeNumbers(numbers)
            numbers
        } else {
            numbersInDatabase
        }
    }

    fun getNumber(id: String): Number? {
        return database.getNumber(id) ?: api.getNumber(id)
    }

    @Test
    fun ifDatabaseIsEmptyShouldFetchNumbersFromApi() {

        val database = mockk<Database>()
        val api = mockk<Api>()
        val number = Number(UUID.randomUUID().toString(), Random.nextInt())

        every { database.getAllNumbers() } returns listOf()
        every { api.getNumbers() } returns listOf(number)
        every { database.storeNumbers(listOf(number)) } just Runs

        val numbersRepository = NumbersRepository(database, api)
        val result = numbersRepository.fetchNumbers()

        assertEquals(result, listOf(number))

        verify { database.getAllNumbers() }
        verify { api.getNumbers() }

    }
}
}
