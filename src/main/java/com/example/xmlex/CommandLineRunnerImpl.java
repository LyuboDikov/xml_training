package com.example.xmlex;

import com.example.xmlex.models.dtos.CategorySeedRootDto;
import com.example.xmlex.models.dtos.UserSeedRootDto;
import com.example.xmlex.services.CategoryService;
import com.example.xmlex.services.UserService;
import com.example.xmlex.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {


    private static final String FILES_PATH = "src/main/resources/files/";
    private static final String CATEGORIES_FILE_NAME = "categories.xml";
    private static final String USERS_FILE_NAME = "users.xml";
    private final XmlParser xmlParser;
    private final CategoryService categoryService;

    private final UserService userService;

    public CommandLineRunnerImpl(XmlParser xmlParser, CategoryService categoryService, UserService userService) {
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        seedData();
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
    }
}
