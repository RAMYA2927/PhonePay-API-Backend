package com.example.phonepay;

import com.example.phonepay.dto.CreateWalletRequest;
import com.example.phonepay.dto.TransferRequest;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhonePayApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateWalletAndTransferMoney() {
        CreateWalletRequest alice = new CreateWalletRequest();
        alice.setUserName("alice");
        alice.setInitialBalance(new BigDecimal("1000.00"));
        alice.setEmail("alice@example.com");
        alice.setPhoneNumber("9999999999");

        CreateWalletRequest bob = new CreateWalletRequest();
        bob.setUserName("bob");
        bob.setInitialBalance(new BigDecimal("100.00"));
        bob.setEmail("bob@example.com");
        bob.setPhoneNumber("8888888888");

        ResponseEntity<JsonNode> createAlice = restTemplate.postForEntity("/api/wallets", alice, JsonNode.class);
        ResponseEntity<JsonNode> createBob = restTemplate.postForEntity("/api/wallets", bob, JsonNode.class);

        assertThat(createAlice.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createBob.getStatusCode()).isEqualTo(HttpStatus.OK);

        TransferRequest transfer = new TransferRequest();
        transfer.setSender("alice");
        transfer.setReceiver("bob");
        transfer.setAmount(new BigDecimal("150.25"));

        ResponseEntity<JsonNode> transferResp = restTemplate.postForEntity("/api/payments/transfer", transfer, JsonNode.class);
        assertThat(transferResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(transferResp.getBody()).isNotNull();
        assertThat(transferResp.getBody().path("success").asBoolean()).isTrue();
        assertThat(transferResp.getBody().path("data").path("status").asText()).isEqualTo("SUCCESS");

        ResponseEntity<JsonNode> aliceWallet = restTemplate.exchange(
                "/api/wallets/alice", HttpMethod.GET, HttpEntity.EMPTY, JsonNode.class);
        ResponseEntity<JsonNode> bobWallet = restTemplate.exchange(
                "/api/wallets/bob", HttpMethod.GET, HttpEntity.EMPTY, JsonNode.class);

        assertThat(aliceWallet.getBody()).isNotNull();
        assertThat(bobWallet.getBody()).isNotNull();
        assertThat(aliceWallet.getBody().path("data").path("balance").asText()).isEqualTo("849.75");
        assertThat(bobWallet.getBody().path("data").path("balance").asText()).isEqualTo("250.25");
    }
}
