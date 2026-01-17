package test.kyrie.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import test.kyrie.core.domain.model.CurrencyDomain
import test.kyrie.core.domain.repository.CurrencyRepository
import javax.inject.Inject

class ObserveLocalCurrenciesUseCase
    @Inject
    constructor(
        private val currencyRepository: CurrencyRepository,
    ) {
        operator fun invoke(): Flow<List<CurrencyDomain>> = currencyRepository.getLocalCurrencies()
    }
