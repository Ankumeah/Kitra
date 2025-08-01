package com.ankumeah.github.kitra.classes

class Contact(
    var contactName: String
) {
    fun changeContactName(oldContact: Contact, newContact: String) {
        if (newContact.isNotBlank()) {
            oldContact.contactName = newContact
        }
        else { Unit }
    }
}