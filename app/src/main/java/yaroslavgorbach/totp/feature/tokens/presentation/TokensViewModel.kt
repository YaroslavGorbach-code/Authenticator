package yaroslavgorbach.totp.feature.tokens.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import yaroslavgorbach.totp.business.token.ObserveTokensInteractor
import yaroslavgorbach.totp.feature.tokens.model.TokensActions
import yaroslavgorbach.totp.feature.tokens.model.TokensUiMassage
import yaroslavgorbach.totp.feature.tokens.model.TokensViewState
import yaroslavgorbach.totp.utill.UiMessageManager
import javax.inject.Inject

@HiltViewModel
class TokensViewModel @Inject constructor(
    observeTokensInteractor: ObserveTokensInteractor
) : ViewModel() {
    private val pendingActions = MutableSharedFlow<TokensActions>()

    private val uiMessageManager: UiMessageManager<TokensUiMassage> = UiMessageManager()

    val state: StateFlow<TokensViewState> = combine(
        observeTokensInteractor(),
        uiMessageManager.message,
        ::TokensViewState
    ).stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = TokensViewState.Empty
    )

    init {
        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    else -> error("$action is not handled")
                }
            }
        }
    }

    fun submitAction(action: TokensActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

    fun clearMessage(id: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(id)
        }
    }

}



