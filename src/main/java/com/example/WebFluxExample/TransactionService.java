package com.example.WebFluxExample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionService {

    @Autowired
    private  AccountRepository accountRepository;

    @Transactional
    public Mono<Void> transfer(Long fromId, Long toId, BigDecimal amount) {
        return accountRepository.findById(fromId)
                .flatMap(fromAccount -> {
                    if(fromAccount.getBalance().compareTo(amount) <0){
                        return Mono.error(new RuntimeException("Insufficient Funds"));
                    }
                    fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
                    return accountRepository.save(fromAccount);
                })
                .then(accountRepository.findById(toId))
                .flatMap(toAccount -> {
                    toAccount.setBalance(toAccount.getBalance().add(amount));
                            return accountRepository.save(toAccount);
                        }).then();
    }
}
