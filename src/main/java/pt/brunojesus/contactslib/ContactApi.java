package pt.brunojesus.contactslib;

import com.github.javafaker.Faker;
import pt.brunojesus.contactslib.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Generates contacts
 *
 * @author Bruno Jesus
 * @version 1.0
 * @since 2023-05-13
 */
public class ContactApi {

    private final Faker faker;

    public ContactApi() {
        this.faker = new Faker();
    }

    public ContactApi(Faker faker) {
        this.faker = faker;
    }

    /**
     * Randomly generates a single {@link Contact}
     *
     * @return a randomly generated {@link Contact}
     */
    public Contact generateContact() {
        return new Contact()
                .setFirstName(faker.name().firstName())
                .setLastName(faker.name().lastName())
                .setAddress(faker.address().fullAddress())
                .setCompany(faker.company().bs())
                .setPhoneNumber(faker.phoneNumber().phoneNumber())
                .setEmail(faker.internet().emailAddress());
    }

    /**
     * Generates a randomly generated contact list with the specified number of contacts
     * <p>
     * This method will distribute the load using a parallel stream, this might be faster
     * if you want to generate a big chunk of data (millions of records)
     * For small sets of data it will most likely be slower than the single-thread method
     *
     * @param numberOfContacts the amount of contacts to generate
     * @return a {@link List} of randomly generated {@link Contact}'s
     * @see java.util.concurrent.ForkJoinPool
     * @see #generateContacts(int) for a sigle threaded alternative
     */
    public List<Contact> generateContactsParallel(int numberOfContacts) {
        return IntStream.range(0, numberOfContacts)
                .parallel()
                .mapToObj(i -> generateContact())
                .toList();
    }

    /**
     * Generates a randomly generated contact list with the specified number of contacts
     * <p>
     * This method is single threaded, if you need a multi-thread solution see
     * {@link #generateContactsParallel(int)}.
     * For small sets of data this method will most likely be faster than the multi-thread
     * alternative.
     *
     * @param numberOfContacts the amount of contacts to generate
     * @return a {@link List} of randomly generated {@link Contact}'s
     * @see #generateContactsParallel(int)
     */
    public List<Contact> generateContacts(int numberOfContacts) {
        List<Contact> list = new ArrayList<>(numberOfContacts);
        for (int i = 0; i < numberOfContacts; i++) {
            list.add(generateContact());
        }
        return list;
    }
}
