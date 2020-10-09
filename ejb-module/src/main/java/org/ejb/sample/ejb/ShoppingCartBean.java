package org.ejb.sample.ejb;

import org.ejb.sample.model.Product;
import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import javax.transaction.Transactional;
import java.io.Serializable;

@Component()
@SessionScope()
public class ShoppingCartBean implements ShoppingCart, Serializable {

    @PersistenceContext
    private EntityManager em;

    private List<Product> products;

    @PostConstruct
    private void init() {
        products = new ArrayList<>();
    }

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional()
    public void completePurchase() {
        for (Product product : products) {
            System.out.println("Persisting product -- " + product.getId() + " --- " + product.getDescription());
            em.persist(product);
            em.flush();
        }
        products.clear();
    }

    public List<Product> getCartProducts() {
        return this.products;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional()
    public List<String> getProducts() {
        return em.createNativeQuery("select description from product").getResultList();
    }
}
