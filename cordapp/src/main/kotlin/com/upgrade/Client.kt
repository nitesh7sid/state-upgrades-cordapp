package com.upgrade

import net.corda.client.rpc.CordaRPCClient
import net.corda.core.flows.ContractUpgradeFlow
import net.corda.core.utilities.NetworkHostAndPort.Companion.parse
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger

fun main(args: Array<String>) {
    UpgradeContractClient().main(args)
}

private class UpgradeContractClient {
    companion object {
        val logger: Logger = loggerFor<UpgradeContractClient>()
    }

    fun main(args: Array<String>) {
        require(args.size == 2) { "Usage: TemplateClient <PartyA RPC address> <PartyB RPC address>" }

        // Create a connection to PartyA and PartyB.
        val (partyAProxy, partyBProxy) = args.map { arg ->
            val nodeAddress = parse(arg)
            val client = CordaRPCClient(nodeAddress)
            client.start("user1", "test").proxy
        }


        val partyBIdentity = partyBProxy.nodeInfo().legalIdentities.first()
        partyAProxy.startFlowDynamic(Initiator::class.java, partyBIdentity)

        Thread.sleep(5000)


        listOf(partyAProxy, partyBProxy).forEach { proxy ->
            // Extract all the unconsumed State instances from the vault.
            val stateAndRefs = proxy.vaultQuery(OldState::class.java).states

            // Run the upgrade flow for each one.
            stateAndRefs.forEach { stateAndRef ->
                proxy.startFlowDynamic(
                        ContractUpgradeFlow.Authorise::class.java,
                        stateAndRef,
                        UpgradeState::class.java)
            }
        }
        Thread.sleep(5000)


        partyAProxy.vaultQuery(OldState::class.java).states.forEach { stateAndRef ->
            partyAProxy.startFlowDynamic(
                    ContractUpgradeFlow.Initiate::class.java,
                    stateAndRef,
                    UpgradeState::class.java)
        }

        // Give the node the time to run the contract upgrade flows.
        Thread.sleep(10000)


        logger.info("The length is ",partyAProxy.vaultQuery(UpgradeState.NewState::class.java).states.size.toString())
        partyAProxy.vaultQuery(UpgradeState.NewState::class.java).states.forEach { logger.info("{}", it.state) }
    }
}
