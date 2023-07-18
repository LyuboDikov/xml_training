package com.example.xmlex;

import com.example.xmlex.models.dtos.*;
import com.example.xmlex.services.CategoryService;
import com.example.xmlex.services.ProductService;
import com.example.xmlex.services.UserService;
import com.example.xmlex.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {


    private static final String FILES_PATH = "src/main/resources/files/";
    private static final String OUTPUT_FILES_PATH = "src/main/resources/files/output/";
    private static final String CATEGORIES_FILE_NAME = "categories.xml";
    private static final String USERS_FILE_NAME = "users.xml";
    private static final String PRODUCTS_FILE_NAME = "products.xml";
    private static final String PRODUCTS_IN_RANGE_FILE = "products-in-range.xml";
    private static final String SOLD_PRODUCTS_FILE = "sold-products.xml";
    private final XmlParser xmlParser;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;

    public CommandLineRunnerImpl(XmlParser xmlParser, CategoryService categoryService,
                                 UserService userService, ProductService productService) {
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {

        seedData();
        System.out.println("Please select task:");

        int taskNumber = Integer.parseInt(bufferedReader.readLine());

        switch (taskNumber) {
            case 1 -> productsInRange();
            case 2 -> usersWithSoldProducts();
        }
    }

    private void usersWithSoldProducts() throws JAXBException {
        UserViewRootDto userViewRootDto =
                userService.findUserWithMoreThanOneSoldProduct();

        xmlParser.writeToFile(OUTPUT_FILES_PATH + SOLD_PRODUCTS_FILE, userViewRootDto);
    }

    private void productsInRange() throws JAXBException {
        ProductViewRootDto rootDto =
                productService.findProductInRangeWithNoBuyer();

        xmlParser.writeToFile(OUTPUT_FILES_PATH + PRODUCTS_IN_RANGE_FILE, rootDto);
    }

    private void seedData() throws FileNotFoundException, JAXBException {

        if (categoryService.getEntityCount() == 0) {
            CategorySeedRootDto categorySeedRootDto =
                    xmlParser.fromFile(FILES_PATH + CATEGORIES_FILE_NAME,
                    CategorySeedRootDto.class);

            categoryService.seedCategories(categorySeedRootDto.getCategories());
        }

        if (userService.getUsersCount() == 0) {
            UserSeedRootDto userSeedRootDto =
                    xmlParser.fromFile(FILES_PATH + USERS_FILE_NAME,
                            UserSeedRootDto.class);

            userService.seedUsers(userSeedRootDto.getUsers());
        }

        if (productService.getProductsCount() == 0) {
            ProductSeedRootDto productSeedRootDto =
                    xmlParser.fromFile(FILES_PATH + PRODUCTS_FILE_NAME, ProductSeedRootDto.class);

            productService.seedProducts(productSeedRootDto.getProducts());
        }
    }
}
