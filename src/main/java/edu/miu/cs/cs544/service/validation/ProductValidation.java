package edu.miu.cs.cs544.service.validation;

import edu.miu.cs.cs544.domain.Product;
import edu.miu.cs.cs544.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductValidation {

    @Autowired
    private ProductRepository productRepository;

    public Product validateProduct(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product does not exist"));
    }

}
