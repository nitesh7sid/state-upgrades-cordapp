package com.upgrade

import net.corda.core.contracts.Contract
import net.corda.core.contracts.ContractState
import net.corda.core.contracts.TypeOnlyCommandData
import net.corda.core.identity.AbstractParty
import net.corda.core.transactions.LedgerTransaction

data class OldState(val a: AbstractParty, val b: AbstractParty) : ContractState {
    override val participants get() = listOf(a, b)
}

open class Contract : Contract {
    companion object {
        val id = "com.upgrade.Contract"
    }

    override fun verify(tx: LedgerTransaction) {}

    class Action : TypeOnlyCommandData()
}