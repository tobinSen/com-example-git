package com.example.spring.ladp.dao;

import com.example.spring.ladp.domain.Person;
import org.springframework.ldap.core.LdapTemplate;

import java.util.List;

public interface IPersonRepo {

    void setLdapTemplate(LdapTemplate ldapTemplate);

    List<String> getAllPersonNames();

    List<String> getAllPersonNamesWithTraditionalWay();

    List<Person> getAllPersons();

    Person findPersonWithDn(String dn);

    List<String> getPersonNamesByOrgId(String orgId);

}
