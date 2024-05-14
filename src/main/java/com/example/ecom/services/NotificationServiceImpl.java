package com.example.ecom.services;

import com.example.ecom.exceptions.*;
import com.example.ecom.models.*;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.NotificationRepository;
import com.example.ecom.repositories.ProductRepository;
import com.example.ecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationServiceImpl(InventoryRepository inventoryRepository, ProductRepository productRepository, UserRepository userRepository, NotificationRepository notificationRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification registerUser(int userId, int productId) throws UserNotFoundException, ProductNotFoundException, ProductInStockException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product not found!");
        }
        Product product = optionalProduct.get();
        Optional<Inventory> optionalInventory = inventoryRepository.findByProduct(product);
        if (optionalInventory.isEmpty()) {
            return null;
        }
        Inventory inventory = optionalInventory.get();
        if (inventory.getQuantity() > 0) {
            throw new ProductInStockException("Product in stock!");
        }
        Notification notification = new Notification();
        User user = optionalUser.get();
        notification.setProduct(product);
        notification.setUser(user);
        notification.setStatus(NotificationStatus.PENDING);
        return notificationRepository.save(notification);
    }

    @Override
    public void deregisterUser(int userId, int notificationId) throws UserNotFoundException, NotificationNotFoundException, UnAuthorizedException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);
        if (optionalNotification.isEmpty()) {
            throw new NotificationNotFoundException("Notification not found!");
        }
        User user = optionalUser.get();
        Notification notification = optionalNotification.get();
        if (notification.getUser().getId() != user.getId()) {
            throw new UnAuthorizedException("You are not allowed to deregister user!");
        }
        notificationRepository.deleteById(notificationId);
    }
}
