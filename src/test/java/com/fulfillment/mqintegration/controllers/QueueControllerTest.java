package com.fulfillment.mqintegration.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QueueControllerTest {

    @Test
    public void testInsert() {
        QueueController TheQueue = new QueueController();
        TheQueue.index();
    }

}
