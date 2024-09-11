package com.shopper.ecomm.service;

import com.shopper.ecomm.exceptions.ResourceNotFoundException;
import com.shopper.ecomm.model.Category;
import com.shopper.ecomm.model.Product;
import com.shopper.ecomm.payload.ProductDTO;
import com.shopper.ecomm.payload.ProductResponse;
import com.shopper.ecomm.repositories.CategoryRepository;
import com.shopper.ecomm.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        product.setImage("placeholder productImage.png");
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        Product product = modelMapper.map(productDTO, Product.class);
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDiscount(product.getDiscount());
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        existingProduct.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(existingProduct);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {

        Product existingProduct = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        String path = "productImages/";
        String filename = uploadImage(path, image);
        existingProduct.setImage(filename);
        Product savedProduct = productRepository.save(existingProduct);

        return modelMapper.map(existingProduct, ProductDTO.class);

    }

    private String uploadImage(String path, MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new RuntimeException("file not specified");
        }

        // check if file has extension
        if(!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpg") && !file.getContentType().equals("image/jpeg")){
            throw new RuntimeException("invalid file type");
        }

        String originalFilename = file.getOriginalFilename();
        String randomName =  UUID.randomUUID().toString();

        String newFilename = randomName.concat(originalFilename.substring(originalFilename.lastIndexOf('.')));

        String filePath = path + File.separator + newFilename;

        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return newFilename;
    }

}
