package com.example.ps;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author AnuragSingh
 */
@RefreshScope
@RestController
@RequestMapping("/product")
public class ProductRestController {

    @Value("${msg}")
    private String title;

    @GetMapping("/data")
    public ResponseEntity<String> showProductMsg() {
        return new ResponseEntity<>("Value of title from Config Server: " + title, HttpStatus.OK);
    }
}
