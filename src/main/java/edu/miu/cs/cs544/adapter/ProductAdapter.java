package edu.miu.cs.cs544.adapter;

import edu.miu.cs.cs544.domain.Product;
import edu.miu.cs.cs544.domain.enums.ProductType;
import edu.miu.cs.cs544.dto.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductAdapter {

    public ProductDTO entityToDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setExcerpt(product.getExcerpt());
        productDTO.setType(String.valueOf(product.getType()));
        productDTO.setMaxOccupancy(product.getMaxOccupancy());

        return productDTO;
    }

    public Product dtoToEntity(ProductDTO productDTO){
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setExcerpt(productDTO.getExcerpt());
        product.setType(ProductType.valueOf(productDTO.getType()));
        product.setMaxOccupancy(productDTO.getMaxOccupancy());

        return product;
    }

    public List<ProductDTO> entityToDTOAll(List<Product> products){
        return products.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    public List<Product> dtoToEntityAll(List<ProductDTO> productDTOs){
        return productDTOs.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}