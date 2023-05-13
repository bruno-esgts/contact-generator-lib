# Random Contact Generation Lib

**Small utility library that uses Faker in order to generate contacts**

## Getting started

### Code example
Generate two lists of contacts executing a benchmark.
You can use this code to check if it's worth it to use
the parallel stream approach. 

For small lists just use the non-parallel generation.

```java
package pt.brunojesus.contactslib;

import pt.brunojesus.contactslib.model.Contact;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        System.out.println("IntStream");
        System.out.println("Has " + Runtime.getRuntime().availableProcessors() + " processors");
        System.out.println("Will use " + ForkJoinPool.commonPool().getParallelism() + " processors");

        final ContactApi contactApi = new ContactApi();

        long start = System.currentTimeMillis();
        List<Contact> l1 = contactApi.generateContactsParallel(100000);

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.printf("Generating %s contacts took %sms\n", l1.size(), timeElapsed);

        System.out.println("For Loop");

        start = System.currentTimeMillis();
        List<Contact> l2 = contactApi.generateContacts(100000);

        finish = System.currentTimeMillis();
        timeElapsed = finish - start;
        System.out.printf("Generating %s contacts took %sms\n", l2.size(), timeElapsed);
    }
}
```

Will result in something like:
```text
IntStream
Has 8 processors
Will use 7 processors
Generating 100000 contacts took 2073ms
For Loop
Generating 100000 contacts took 3084ms
```