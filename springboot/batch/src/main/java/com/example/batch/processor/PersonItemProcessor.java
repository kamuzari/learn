package com.example.batch.processor;

import com.example.batch.dto.CsvPerson;
import com.example.batch.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<CsvPerson, Person> {
    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(CsvPerson item) throws Exception {
        log.info("Before Convert person From .csv: {}", item);
        Person person = new Person(item.firstName(), item.lastName());
        log.info("After Convert person Entity: {}", person);

        return person;
    }
}
