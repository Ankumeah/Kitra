package com.ankumeah.github.kitra.viewModels

import com.ankumeah.github.kitra.classes.Contact

class SampleDataViewModel(
    val contactList: List<Contact> = listOf(Contact("Ankit"), Contact("Avni"), Contact("Really long name for testing powerhouses"), Contact("Dilly Demacus Jon the fifth"), Contact("Some random dude"), Contact("Maggi"), Contact("6"), Contact("7"), Contact("8"), Contact("9"), Contact("0"), Contact("1"), Contact("2"), Contact("3"), Contact("3"), Contact("3"), Contact("3"), Contact("3"), Contact("3")),
    val text: String = "Smt Smt Text",
    val textLong: String = "Big sample text to be used at the time of dev"
)