package org.ejb.sample.ejb;

import org.ejb.sample.model.Product;
import java.util.List;
import org.springframework.stereotype.Component;

@Component()
public interface ShoppingCart {

    void addProduct(Product product);

    void completePurchase();

    List<String> getProducts();

    List<Product> getCartProducts();
}
