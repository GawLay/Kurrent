package test.kyrie.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import test.kyrie.core.domain.model.CurrencyDomain
import test.kyrie.core.domain.repository.CurrencyRepository
import test.kyrie.core.domain.util.Result
import javax.inject.Inject

class GetCurrenciesUseCase
    @Inject
    constructor(
        private val currencyRepository: CurrencyRepository,
    ) {
        suspend operator fun invoke(forceRefresh: Boolean = false): Flow<Result<List<CurrencyDomain>>> =
            currencyRepository.getCurrencies(forceRefresh)
    }
