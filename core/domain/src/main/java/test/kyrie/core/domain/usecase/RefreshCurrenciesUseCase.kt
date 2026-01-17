package test.kyrie.core.domain.usecase

import test.kyrie.core.domain.repository.CurrencyRepository
import test.kyrie.core.domain.util.Result
import javax.inject.Inject

class RefreshCurrenciesUseCase
    @Inject
    constructor(
        private val currencyRepository: CurrencyRepository,
    ) {
        suspend operator fun invoke(): Result<Unit> = currencyRepository.refreshCurrencies()
    }
