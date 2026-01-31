package com.pagination.mongodb;

import com.pagination.mongodb.pagination.PaginationTokenCodec;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
public class PaginationTokenCodecTest {

    @Autowired
    private PaginationTokenCodec tokenCodec;

    @Test
    public void codecTest() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "a");
        params.put("age", 10);
        String tokenEncode = tokenCodec.encode(
            params
        );
        Map<String, Object> paramsDecode = tokenCodec.decode(tokenEncode);
        assertEquals(
            params,
            paramsDecode
        );
    }
}
