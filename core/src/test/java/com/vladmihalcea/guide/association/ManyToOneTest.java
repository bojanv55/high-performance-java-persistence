package com.vladmihalcea.guide.association;

import com.vladmihalcea.book.hpjp.util.AbstractTest;
import org.junit.Test;

import javax.persistence.*;

/**
 * <code>ManyToOneTest</code> - @ManyToOne Test
 *
 * @author Vlad Mihalcea
 */
public class ManyToOneTest extends AbstractTest {

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[] {
            Person.class,
            Phone.class,
        };
    }

    @Test
    public void testLifecycle() {
        doInJPA(entityManager -> {
            Person person = new Person();
            entityManager.persist(person);

            Phone phone = new Phone("123-456-7890");
            phone.setPerson(person);
            entityManager.persist(phone);

            entityManager.flush();
            phone.setPerson(null);
        });
    }

    @Entity(name = "Person")
    public static class Person  {

        @Id
        @GeneratedValue
        private Long id;

        public Person() {}
    }

    @Entity(name = "Phone")
    public static class Phone  {

        @Id
        @GeneratedValue
        private Long id;

        private String number;

        @ManyToOne
        @JoinColumn(name = "person_id",
            foreignKey = @ForeignKey(name = "PERSON_ID_FK")
        )
        private Person person;

        public Phone() {}

        public Phone(String number) {
            this.number = number;
        }

        public Long getId() {
            return id;
        }

        public String getNumber() {
            return number;
        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }
    }
}