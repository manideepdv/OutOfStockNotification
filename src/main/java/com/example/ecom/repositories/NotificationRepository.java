package com.example.ecom.repositories;

import com.example.ecom.models.Notification;
import com.example.ecom.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Override
    Optional<Notification> findById(Integer integer);

    List<Notification> findByProduct(Product product);
}
