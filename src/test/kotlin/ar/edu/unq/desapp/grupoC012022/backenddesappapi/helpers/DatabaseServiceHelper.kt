package ar.edu.unq.desapp.grupoC012022.backenddesappapi.helpers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DatabaseServiceHelper {
    @Autowired
    private lateinit var currencyRepository: CurrencyRepository
    @Autowired
    private lateinit var orderRepository: OrderRepository
    @Autowired
    private lateinit var priceRepository: PriceRepository
    @Autowired
    private lateinit var transactionRepository: TransactionRepository
    @Autowired
    private lateinit var userRepository: UserRepository

    fun clearDatabase() {
        transactionRepository.deleteAll()
        orderRepository.deleteAll()
        priceRepository.deleteAll()
        currencyRepository.deleteAll()
        userRepository.deleteAll()
    }
}