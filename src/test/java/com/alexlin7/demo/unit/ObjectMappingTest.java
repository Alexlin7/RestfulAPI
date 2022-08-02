package com.alexlin7.demo.unit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class ObjectMappingTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerializeBookToJSON() throws Exception {
        Book book = new Book();
        book.setId("B0001");
        book.setName("Computer Science");
        book.setPrice(350);
        book.setIsbn("978-986-123-456-7");
        book.setCreatedTime(new Date());

        String bookJSONStr = mapper.writeValueAsString(book);
        JSONObject bookJSON = new JSONObject(bookJSONStr);

        Assert.assertEquals(book.getId(), bookJSON.getString("id"));
        Assert.assertEquals(book.getName(), bookJSON.getString("name"));
        Assert.assertEquals(book.getPrice(), bookJSON.getInt("price"));
        Assert.assertEquals(book.getIsbn(), bookJSON.getString("isbn"));
        Assert.assertEquals(book.getCreatedTime().getTime(), bookJSON.getLong("createdTime"));
    }

    @Test
    public void testDeserializeJSONToPublisher() throws Exception {
        JSONObject publisherJSON = new JSONObject()
                .put("companyName", "Taipei Company")
                .put("address", "Taipei")
                .put("telephone", "02-1234-5678");

        String publisherJSONStr = publisherJSON.toString();
        Publisher publisher = mapper.readValue(publisherJSONStr, Publisher.class);

        Assert.assertEquals(publisherJSON.getString("companyName"), publisher.getCompanyName());
        Assert.assertEquals(publisherJSON.getString("address"), publisher.getAddress());
        Assert.assertEquals(publisherJSON.getString("telephone"), publisher.getTel());
    }

    @Getter
    @Setter
    private static class Book {

        private String id;
        private String name;
        private int price;
        private String isbn;
        private Date createdTime;
        private Publisher publisher;

    }

    @Getter
    @Setter
    private static class Publisher {

        private String companyName;
        private String address;

        @JsonProperty("telephone")
        private String tel;

    }
}
