package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis.DolarSiApi
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.*
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Transaction
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.CurrencyService
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.OrderService
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TransactionService {

    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var orderService: OrderService
    @Autowired
    lateinit var transactionActionFactory: TransactionActionFactory
    @Autowired
    lateinit var transactionRepository: TransactionRepository
    @Autowired
    lateinit var currencyService: CurrencyService
    @Autowired
    lateinit var dolarSiApi: DolarSiApi

    @Transactional
    fun processTransaction(transaction: TransactionDto): TransactionCompletedDto {
        val order = orderService.findById(transaction.orderId)
        val executingUser = userService.findById(transaction.userId)
        return transactionActionFactory
            .createFromAction(transaction.action)
            .process(order, executingUser)
            .toTransactionCompletedDto()
    }

    fun getOperatedVolumeBetweenDates(operatedVolumeRequestDto: OperatedVolumeRequestDto): OperatedVolumeResultDto {
        val user = userService.findById(operatedVolumeRequestDto.userId)
        val result = transactionRepository.findTransactionsBetweenDates(
            operatedVolumeRequestDto.userId,
            operatedVolumeRequestDto.fromDate.atStartOfDay(),
            operatedVolumeRequestDto.toDate.atStartOfDay()
        )
        val listOfCurrencies = mutableListOf<OperatedVolumeResultCriptoDto>()
        val alreadyCheckedCurrencies = mutableListOf<String>()
        var totalUsd: Long = 0
        var totalArs: Long = 0
        val usdToArs = dolarSiApi.getMepUsdToArs()
        for (t: Transaction in result) {
            totalArs = totalArs.plus(t.price.sellingPrice)
            totalUsd = totalUsd.plus(t.price.sellingPrice/usdToArs).toLong()
            if (!alreadyCheckedCurrencies.contains(t.currency.ticker)) {
                alreadyCheckedCurrencies.add(t.currency.ticker)
                val currency = currencyService.getOrUpdateCurrency(t.currency.ticker)
                listOfCurrencies.add(OperatedVolumeResultCriptoDto(currency, t.quantity, (currency.usdPrice * usdToArs).toLong()))
            }
        }
        return OperatedVolumeResultDto(user.toDeserializableUser(), LocalDateTime.now(), totalUsd, totalArs, listOfCurrencies)
    }
}

@Service
class MercadoPagoApi {
    fun transferMoney(totalAmountArs: Long, fromMercadoPagoCvu: String, toMercadoPagoCvu: String) {
        // do stuff
    }
}

@Service
class CriptoExchanger {
    fun transferCriptoCurrency(totalAmountCriptoCurrency: Long, criptoActive: String, fromWallet: String, toWallet: String) {
        // do stuff
    }
}