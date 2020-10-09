package org.ejb.sample.servlet;

import org.ejb.sample.ejb.ShoppingCart;
import org.ejb.sample.model.Product;
import org.ejb.sample.model.UserDao;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component()
public class ShoppingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String SHOPPING_CART_BEAN_SESION_KEY = "shoppingCart";

    @Autowired()
    private UserDao userDao;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (shoppingCartBean == null) {
            // so let's fetch a new one from the container
            try {
                InitialContext ic = new InitialContext();
                // put EJB in HTTP session for future servlet calls
                request.getSession().setAttribute(SHOPPING_CART_BEAN_SESION_KEY, shoppingCartBean);
            } catch (NamingException e) {
                throw new ServletException(e);
            }
        }
        String productName = request.getParameter("product");
        if (productName != null && productName.length() > 0) {
            // Product is present in the HTTP request.
            // Let's add it to the shopping cart
            Product product = new Product();
            product.setDescription(productName);
            System.out.println("Adding product with name -- " + product.getId() + " -- " + product.getDescription());
            shoppingCartBean.addProduct(product);
        }
        String persist = request.getParameter("complete");
        if (persist != null && persist.equalsIgnoreCase("yes")) {
            // Request instructs to complete the purchase
            System.out.println("Request recieved to purchase items");
            shoppingCartBean.completePurchase();
        }
        Map requestParameters = request.getParameterMap();
        if (requestParameters.isEmpty()) {
            // Request instructs to complete the purchase
            System.out.println("Getting all items");
            Collection<String> allProducts = shoppingCartBean.getProducts();
            request.setAttribute("allProducts", allProducts);
            // System.out.println("Forwarding to products page----");
            // allProducts.forEach(product -> System.out.println("Product -- " + product ));
            Collection<Product> cartProducts = shoppingCartBean.getCartProducts();
            request.setAttribute("cartProducts", cartProducts);
            getServletConfig().getServletContext().getRequestDispatcher("/products.jsp").forward(request, response);
        }
    }

    @Autowired()
    private ShoppingCart shoppingCartBean;
}
