package com.upgrade

import net.corda.core.contracts.ContractState
import net.corda.core.contracts.TypeOnlyCommandData
import net.corda.core.contracts.UpgradedContract
import net.corda.core.identity.AbstractParty
import net.corda.core.transactions.LedgerTransaction
import javax.enterprise.inject.New

/**
 * Created by nitesh on 10-06-2018.
 */

open class UpgradeState : UpgradedContract<OldState, UpgradeState.NewState> {

    override val legacyContract = Contract.id

    data class NewState(val a: AbstractParty) : ContractState {
        override val participants get() = listOf(a)
    }
    // Again, we're not upgrading the state, so we leave the states unmodified.
    override fun upgrade(oldState: OldState) = NewState(oldState.a)

    override fun verify(tx: LedgerTransaction) {}
}
