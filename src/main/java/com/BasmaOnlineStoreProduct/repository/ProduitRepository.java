package com.BasmaOnlineStoreProduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BasmaOnlineStoreProduct.beans.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

}
