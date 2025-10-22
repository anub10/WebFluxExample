package com.example.WebFluxExample;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
@Data
@AllArgsConstructor
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

    @Autowired
    private  final AccountRepository accountRepository;

    @PostMapping("/transfer")
    public Mono<ResponseEntity<String>> transfer(@RequestBody TransferRequest request) {
        return transactionService.transfer(request.getFromId(), request.getToId(), request.getAmount())
                .thenReturn(ResponseEntity.ok("Transfer Successful"))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(e.getMessage())));
    }

    @PostMapping("/create")
    public Mono<Account> createAccount(@RequestBody Account request){
        return accountRepository.save(request);
    }

    @GetMapping("/getAccounts")
    public Flux<Account> getAccounts() {
        return accountRepository.findAll();
    }

}
