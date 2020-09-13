package com.example.spring.ladp.config;

import com.example.spring.ladp.domain.Person;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.directory.Attributes;

public class PersonAttributesMapper implements AttributesMapper<Person> {

    @Override
    public Person mapFromAttributes(Attributes attrs) throws NamingException, javax.naming.NamingException {
        Person person = new Person();
        person.setPersonName((String) attrs.get("cn").get());
        person.setOrgId((String) attrs.get("orgId").get());
        return person;
    }
}
