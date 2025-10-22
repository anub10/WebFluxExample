package com.example.WebFluxExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public Mono<Void> transfer(Long fromId, Long toId, BigDecimal amt) {
        return checkBal(fromId,amt)
                .flatMap(fromAct -> {
                    fromAct.setBalance(fromAct.getBalance().subtract(amt));
                    return accountRepository.save(fromAct);
                } )
                .then(checkBal(toId,amt)
                        .flatMap(toAct -> {
                            toAct.setBalance(toAct.getBalance().add(amt));
                            return accountRepository.save(toAct);
                        })).then();
    }

    public Mono<Account> checkBal (Long actid, BigDecimal amt) {
        return accountRepository.findById(actid)
                .flatMap(account -> {
                    if(account.getBalance().compareTo(amt) <0){
                        return Mono.error(new RuntimeException("Insufficient balance"));
                    }
                    return Mono.just(account);
                });
    }

}
