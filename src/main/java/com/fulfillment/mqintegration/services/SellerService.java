package com.fulfillment.mqintegration.services;

import com.fulfillment.mqintegration.exception.EntityNotFoundException;
import com.fulfillment.mqintegration.models.Seller;
import com.fulfillment.mqintegration.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepo;

    public Iterable<Seller> getSellers() {
        return sellerRepo.findAll();
    }

    public Seller updateSeller(Seller seller, String id_seller) {
        if (id_seller != null) {
            throw new EntityNotFoundException("Missing id");
        }
        boolean exists = sellerRepo.existsById(id_seller);
        if (!exists) {
            throw new EntityNotFoundException("Seller id:" + id_seller + " not found");
        } else
            seller.setId(id_seller);
        return sellerRepo.save(seller);
    }
}
