package com.upgrade

import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import net.corda.core.transactions.LedgerTransaction

/**
 * Created by nitesh on 10-06-2018.
 */

open class UpgradeState : UpgradedContractWithLegacyConstraint<OldState, UpgradeState.NewState> {

    override val legacyContract: ContractClassName = Contract.id

    override val legacyContractConstraint: AttachmentConstraint = AlwaysAcceptAttachmentConstraint

    override fun upgrade(oldState: OldState) = UpgradeState.NewState(oldState.a, oldState.b, 0)

    data class NewState(val a: AbstractParty, val b: AbstractParty, val value:Int ) : ContractState {
        override val participants get() = listOf(a, b)
    }

    override fun verify(tx: LedgerTransaction) {}
}
