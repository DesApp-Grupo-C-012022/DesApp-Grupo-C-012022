package ar.edu.unq.desapp.grupoC012022.backenddesappapi.helpers

import org.mockito.Mockito

class MockitoHelper {
    companion object {
        fun <T> anyObject(): T {
            Mockito.any<T>()
            return uninitialized()
        }
        @Suppress("UNCHECKED_CAST")
        fun <T> uninitialized(): T =  null as T
    }
}