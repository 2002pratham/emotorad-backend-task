package com.example.emotorad.Backend_Task.service;

import com.example.emotorad.Backend_Task.model.Contact;
import com.example.emotorad.Backend_Task.model.LinkPrecedence;
import com.example.emotorad.Backend_Task.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Map<String, Object> processContact(String email, String phoneNumber) {
        // Fetch all related contacts based on email or phone number
        List<Contact> matchingContacts = findMatchingContacts(email, phoneNumber);

        if (matchingContacts.isEmpty()) {
            // No match found, create a new primary contact
            Contact newContact = createNewPrimaryContact(email, phoneNumber);
            return buildResponse(newContact, Collections.emptyList());
        } else {
            // Match found, determine primary and consolidate data
            return handleExistingContacts(matchingContacts, email, phoneNumber);
        }
    }

    private List<Contact> findMatchingContacts(String email, String phoneNumber) {
        List<Contact> contacts = new ArrayList<>();
        if (email != null && !email.isEmpty()) {
            contacts.addAll(contactRepository.findByEmail(email).stream().toList());
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            contacts.addAll(contactRepository.findByPhoneNumber(phoneNumber).stream().toList());
        }
        return contacts.stream().distinct().collect(Collectors.toList());
    }

    private Contact createNewPrimaryContact(String email, String phoneNumber) {
        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setPhoneNumber(phoneNumber);
        contact.setLinkPrecedence(LinkPrecedence.PRIMARY);
        return contactRepository.save(contact);
    }

    private Map<String, Object> handleExistingContacts(List<Contact> matchingContacts, String email, String phoneNumber) {
        // Determine the primary contact based on the earliest creation date
        Contact primaryContact = determinePrimaryContact(matchingContacts);

        // Consolidate information and create secondary contacts if needed
        boolean newDataAdded = false;
        if (email != null && !emailExists(primaryContact, matchingContacts, email)) {
            createSecondaryContact(primaryContact, email, null);
            newDataAdded = true;
        }
        if (phoneNumber != null && !phoneNumberExists(primaryContact, matchingContacts, phoneNumber)) {
            createSecondaryContact(primaryContact, null, phoneNumber);
            newDataAdded = true;
        }

        // Refresh the primary contact if changes were made
        if (newDataAdded) {
            matchingContacts = findMatchingContacts(primaryContact.getEmail(), primaryContact.getPhoneNumber());
        }

        return buildResponse(primaryContact, matchingContacts);
    }

    private Contact determinePrimaryContact(List<Contact> matchingContacts) {
        return matchingContacts.stream()
                .filter(contact -> contact.getLinkPrecedence() == LinkPrecedence.PRIMARY)
                .min(Comparator.comparing(Contact::getCreatedAt))
                .orElseThrow(() -> new IllegalStateException("No primary contact found in matching contacts."));
    }

    private boolean emailExists(Contact primaryContact, List<Contact> matchingContacts, String email) {
        return matchingContacts.stream().anyMatch(contact -> email.equals(contact.getEmail()));
    }

    private boolean phoneNumberExists(Contact primaryContact, List<Contact> matchingContacts, String phoneNumber) {
        return matchingContacts.stream().anyMatch(contact -> phoneNumber.equals(contact.getPhoneNumber()));
    }

    private Contact createSecondaryContact(Contact primaryContact, String email, String phoneNumber) {
        Contact secondaryContact = new Contact();
        secondaryContact.setLinkedId(primaryContact.getId());
        secondaryContact.setLinkPrecedence(LinkPrecedence.SECONDARY);
        secondaryContact.setEmail(email);
        secondaryContact.setPhoneNumber(phoneNumber);
        return contactRepository.save(secondaryContact);
    }

    private Map<String, Object> buildResponse(Contact primaryContact, List<Contact> relatedContacts) {
        Set<String> emails = relatedContacts.stream()
                .map(Contact::getEmail)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        emails.add(primaryContact.getEmail());

        Set<String> phoneNumbers = relatedContacts.stream()
                .map(Contact::getPhoneNumber)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        phoneNumbers.add(primaryContact.getPhoneNumber());

        List<Integer> secondaryContactIds = relatedContacts.stream()
                .filter(contact -> contact.getLinkPrecedence() == LinkPrecedence.SECONDARY)
                .map(Contact::getId)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("primaryContactId", primaryContact.getId());
        response.put("emails", new ArrayList<>(emails));
        response.put("phoneNumbers", new ArrayList<>(phoneNumbers));
        response.put("secondaryContactIds", secondaryContactIds);

        return response;
    }
}
