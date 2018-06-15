

# State Upgrades CorDapp

This CorDapp shows how to upgrade a state without upgrading the Contract. The Upgraded state will have an additional member variable of type Int.

# Pre-requisites:
  
See https://docs.corda.net/getting-set-up.html.

# Usage

## Running the nodes:

See https://docs.corda.net/tutorial-cordapp.html#running-the-example-cordapp.

## Upgrading the State:

Run the following command from the project's root folder:

* Unix/Mac OSX: `./gradlew runUpgradeStateClient`
* Windows: `gradlew runUpgradeContractClient`

Afte upgrading the state, the client will fetch the new state from the vault and print the new upgraded state:
```
[StateAndRef(state=TransactionState(data=NewState(a=O=PartyB, L=New York, C=US, b=O=PartyA, L=London, C=GB, value=0),contract=com.upgrade.UpgradeState, notary=O=Notary Service, L=Zurich, C=CH,encumbrance=null,constraint=HashAttachmentConstraint(attachmentId=8710C495B07B0FF933A8FA41A92FC9AB9D86ADB54CB8382CC441427FCE3919D7)), ref=8989BFF3BEDE0E01A7A0A052D03F8352BBC8573C3BBA6B412D4A9B481A423B10(0))]I 21:29:23 1 UpgradeStateClient.main -TransactionState(data=NewState(a=O=PartyB, L=New York, C=US, b=O=PartyA, L=London, C=GB, value=0), contract=com.upgrade.UpgradeState, notary=O=Notary Service, L=Zurich, C=CH, encumbrance=null,constraint=HashAttachmentConstraint(attachmentId=8710C495B07B0FF933A8FA41A92FC9AB9D86ADB54CB8382CC441427FCE3919D7))
```
