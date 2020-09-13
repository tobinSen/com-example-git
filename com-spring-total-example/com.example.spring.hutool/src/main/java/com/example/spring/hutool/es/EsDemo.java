package com.example.spring.hutool.es;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.aliases.AddAliasMapping;
import io.searchbox.indices.aliases.ModifyAliases;
import io.searchbox.indices.aliases.RemoveAliasMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EsDemo {
    public static void main(String[] args) throws Exception {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(
                new HttpClientConfig.Builder("http://localhost:9200")
                        .multiThreaded(true)
                        .defaultMaxTotalConnectionPerRoute(2)
                        .maxTotalConnection(10)
                        .build());
        //1.核心对象jestClient
        JestClient jestClient = factory.getObject();

        JestResult result = jestClient.execute(new IndicesExists.Builder("employees").build());

        jestClient.execute(new CreateIndex.Builder("employees").build());

        Map<String, Object> settings = new HashMap<>();
        settings.put("number_of_shards", 11);
        settings.put("number_of_replicas", 2);
        jestClient.execute(new CreateIndex.Builder("employees").settings(settings).build());


        jestClient.execute(new ModifyAliases.Builder(
                new AddAliasMapping.Builder("employees", "e").build()).build());
        jestClient.execute(new ModifyAliases.Builder(
                new RemoveAliasMapping.Builder("employees", "e").build()).build());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode employeeJsonNode = mapper.createObjectNode()
                .put("name", "Michael Pratt")
                .put("title", "Java Developer")
                .put("yearsOfService", 2)
                .set("skills", mapper.createArrayNode()
                        .add("java")
                        .add("spring")
                        .add("elasticsearch"));
        jestClient.execute(new Index.Builder(employeeJsonNode.toString()).index("employees").build());


        Map<String, Object> employeeHashMap = new LinkedHashMap<>();
        employeeHashMap.put("name", "Michael Pratt");
        employeeHashMap.put("title", "Java Developer");
        employeeHashMap.put("yearsOfService", 2);
        employeeHashMap.put("skills", Arrays.asList("java", "spring", "elasticsearch"));
        jestClient.execute(new Index.Builder(employeeHashMap).index("employees").build());

//        Employee employee = new Employee();
//        employee.setName("Michael Pratt");
//        employee.setTitle("Java Developer");
//        employee.setYearsOfService(2);
//        employee.setSkills(Arrays.asList("java", "spring", "elasticsearch"));
//        jestClient.execute(new Index.Builder(employee).index("employees").build());

        jestClient.execute(new Get.Builder("employees", "17").build());

//        employee.setYearOfService(3);
//        jestClient.execute(new Update.Builder(employee).index("employees").id("1").build());

        jestClient.execute(new Delete.Builder("17").index("employees").build());

//        jestClient.execute(new Bulk.Builder()
//                .defaultIndex("employees")
//                .addAction(new Index.Builder(employeeObject1).build())
//                .addAction(new Index.Builder(employeeObject2).build())
//                .addAction(new Delete.Builder("17").build())
//                .build());


    }

}
