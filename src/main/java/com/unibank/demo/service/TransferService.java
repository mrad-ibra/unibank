package com.unibank.demo.service;

import com.unibank.demo.dto.AccountResponse;
import com.unibank.demo.dto.TransferRequest;
import com.unibank.demo.entity.Account;
import com.unibank.demo.enums.ErrorCodeEnum;
import com.unibank.demo.exception.CustomException;
import com.unibank.demo.redis.service.CurrencyCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountService accountService;
    private final CurrencyCacheService currencyCacheService;

    public void transferMoney(TransferRequest request) {
        List<AccountResponse> myActiveAccounts = accountService.getAllActiveAccounts();
        Account myAccount = accountService.getAccountByAccountNumber(request.myAccount());

        checkAccountNumberExistence(myActiveAccounts, myAccount);
        Account targetAccount = accountService.getAccountByAccountNumber(request.targetAccount());

        checkTargetAccount(targetAccount);
        checkAccountState(targetAccount);
        checkSameAccount(myAccount, targetAccount);
        checkSufficientFunds(myAccount, request.amount());

        executeTransfer(myAccount, targetAccount, request);
    }

    private void executeTransfer(Account myAccount, Account targetAccount, TransferRequest request) {
        if (myAccount.getCurrencyType().equals(targetAccount.getCurrencyType())) {
            targetAccount.setBalance(targetAccount.getBalance().add(request.amount()));
        } else {
            String textCurrency = myAccount.getCurrencyType() + "-" + targetAccount.getCurrencyType();
            Double currency = currencyCacheService.readFromCache(textCurrency);
            BigDecimal lastAmount = request.amount().multiply(BigDecimal.valueOf(currency));
            targetAccount.setBalance(targetAccount.getBalance().add(lastAmount));
        }
        myAccount.setBalance(myAccount.getBalance().subtract(request.amount()));
        accountService.updateBalanceAfterTransfer(targetAccount, myAccount);
    }

    private void checkAccountNumberExistence(List<AccountResponse> myAccounts, Account myAccount) {
        boolean accountNumberExists = myAccounts.stream()
                .anyMatch(accountResponse -> accountResponse.accountNumber().equals(myAccount.getAccountNumber()));

        if (!accountNumberExists) {
            throw new CustomException(ErrorCodeEnum.TRANSFER_NOT_ALLOWED);
        }
    }

    private void checkTargetAccount(Account targetAccount) {
        if (targetAccount == null) {
            throw new CustomException(ErrorCodeEnum.ACCOUNT_NOT_FOUND);
        }
    }

    private void checkAccountState(Account targetAccount) {
        if (targetAccount.getState() == 0) {
            throw new CustomException(ErrorCodeEnum.INVALID_ACCOUNT);
        }
    }

    private void checkSameAccount(Account myAccount, Account targetAccount) {
        if (myAccount.getAccountNumber().equals(targetAccount.getAccountNumber())) {
            throw new CustomException(ErrorCodeEnum.SAME_ACCOUNT);
        }
    }

    private void checkSufficientFunds(Account myAccount, BigDecimal amount) {
        if (myAccount.getBalance().compareTo(amount) < 0) {
            throw new CustomException(ErrorCodeEnum.INSUFFICIENT_FUNDS);
        }
    }
}
