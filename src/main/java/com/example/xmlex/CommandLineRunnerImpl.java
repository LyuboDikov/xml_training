package com.example.xmlex;

import com.example.xmlex.models.dtos.CategorySeedRootDto;
import com.example.xmlex.services.CategoryService;
import com.example.xmlex.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {


    private static final String FILES_PATH = "src/main/resources/files/";
    private static final String CATEGORIES_FILE_NAME = "categories.xml";
    private final XmlParser xmlParser;
    private final CategoryService categoryService;

    public CommandLineRunnerImpl(XmlParser xmlParser, CategoryService categoryService) {
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {

        seedData();
    }

    private void seedData() throws FileNotFoundException, JAXBException {

        CategorySeedRootDto categorySeedRootDto = xmlParser.fromFile(
                FILES_PATH + CATEGORIES_FILE_NAME,
                CategorySeedRootDto.class
        );

        categoryService.seedCategories(categorySeedRootDto.getCategories());
    }
}
