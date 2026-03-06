package com.example.phonepay.service;

import com.example.phonepay.dto.CreateWalletRequest;
import com.example.phonepay.dto.TransferRequest;
import com.example.phonepay.exception.BadRequestException;
import com.example.phonepay.exception.NotFoundException;
import com.example.phonepay.model.Transaction;
import com.example.phonepay.model.Wallet;
import com.example.phonepay.repository.TransactionRepository;
import com.example.phonepay.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Wallet createWallet(CreateWalletRequest request) {
        if (walletRepository.existsByUserName(request.getUserName())) {
            throw new BadRequestException("Wallet already exists for userName: " + request.getUserName());
        }

        Wallet wallet = new Wallet();
        wallet.setUserName(request.getUserName().trim());
        wallet.setBalance(request.getInitialBalance().setScale(2, RoundingMode.HALF_UP));
        wallet.setEmail(request.getEmail());
        wallet.setPhoneNumber(request.getPhoneNumber());

        return walletRepository.save(wallet);
    }

    public Wallet getWallet(String userName) {
        return walletRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("Wallet not found for userName: " + userName));
    }

    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    @Transactional
    public Transaction transfer(TransferRequest request) {
        String sender = request.getSender().trim();
        String receiver = request.getReceiver().trim();
        BigDecimal amount = request.getAmount().setScale(2, RoundingMode.HALF_UP);

        if (sender.equalsIgnoreCase(receiver)) {
            throw new BadRequestException("Sender and receiver cannot be the same");
        }

        Wallet senderWallet = walletRepository.findByUserName(sender)
                .orElseThrow(() -> new NotFoundException("Sender wallet not found: " + sender));

        Wallet receiverWallet = walletRepository.findByUserName(receiver)
                .orElseThrow(() -> new NotFoundException("Receiver wallet not found: " + receiver));

        if (senderWallet.getBalance().compareTo(amount) < 0) {
            throw new BadRequestException("Insufficient balance in sender wallet");
        }

        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        receiverWallet.setBalance(receiverWallet.getBalance().add(amount));

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(amount);
        transaction.setStatus("SUCCESS");
        transaction.setTimestamp(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getHistory(String userName) {
        if (userName == null || userName.isBlank()) {
            return transactionRepository.findAll();
        }

        if (!walletRepository.existsByUserName(userName)) {
            throw new NotFoundException("Wallet not found for userName: " + userName);
        }

        return transactionRepository.findBySenderOrReceiverOrderByTimestampDesc(userName, userName);
    }
}
