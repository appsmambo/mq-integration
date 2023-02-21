package com.fulfillment.mqintegration.controllers;

import com.fulfillment.mqintegration.models.Seller;
import com.fulfillment.mqintegration.services.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.*;
import org.springframework.jms.JmsException;
import org.springframework.web.bind.annotation.*;
import com.fulfillment.mqintegration.components.SellerResponse;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins="*")
public class QueueController {
    
    Logger logger = LoggerFactory.getLogger(QueueController.class);
    @Value("${ibm.mq.queue-name}")
    private String queueName;
    @Autowired
    private JmsOperations jmsOperations;

    private SellerService sellerService;

    @GetMapping(value="/", produces="application/json")
    public SellerResponse index() {
        SellerResponse eomResponse = new SellerResponse();
        eomResponse.setStatus(true);
        eomResponse.setMessage("Hello FulfillmentBy");
        eomResponse.setData(null);
        return eomResponse;
    }

    @PostMapping(value="/insert", 
            consumes={MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE}, 
            produces={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> putDataIntoDatabase(@RequestBody byte[] requestBody, HttpServletRequest request) {
        String tableName = request.getHeader("X-TABLE-NAME");
        String pk = request.getHeader("X-PK");
        String headerPkValue = request.getHeader("X-PK-VALUE");
        String pkValue = Optional.ofNullable(headerPkValue).orElse("");
        if (tableName.isEmpty() || pk.isEmpty() || pkValue.isEmpty()) {
            return new ResponseEntity<>("Missing TABLE-NAME, PK or PK-VALUE.", HttpStatus.OK);
        }
        try {
            String eom_object = Base64.getEncoder().encodeToString(requestBody);
            jmsOperations.convertAndSend(queueName, eom_object);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (JmsException e) {
            logger.error("::: JmsException :::", e);
            switch (tableName) {
                case "seller":
                    Seller seller = new Seller();
                    seller.setIsActiveEom(false);
                    sellerService.updateSeller(seller, pkValue);
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.CREATED);
                default:
            }
            return ResponseEntity.ok(e.getMessage());
        }
    }


    @GetMapping("/send")
    public String send() {
		String msg = "Hello FulfillmentBy";
        jmsOperations.convertAndSend(queueName, msg);
        return msg;
    }

}
