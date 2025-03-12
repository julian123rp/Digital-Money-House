package com.example.DigitalMoneyHouseJR.usersservice.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
    @Component
    public class AliasCvuGenerator {
private final Random random = new Random();
        private static final Logger logger = LogManager.getLogger(AliasCvuGenerator.class);

        public String generateAlias() {
            List<String> aliasOptions = new ArrayList<>();
            List<String> chosenAlias = new ArrayList<>();

            // Intenta leer el archivo desde resources
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("alias.txt")) {
                if (inputStream == null) {
                    throw new FileNotFoundException("No se encontró alias.txt en resources");
                }

                try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                    String word;
                    while ((word = br.readLine()) != null) {
                        aliasOptions.add(word);
                    }
                }

                if (aliasOptions.isEmpty()) {
                    logger.warn("El archivo está vacío.");
                    return "error al crear el CVU";
                }


                for (int i = 0; i < 3; i++) {
                    int randomPosition;
                    String randomAlias;

                    do {
                        randomPosition = random.nextInt(aliasOptions.size());
                        randomAlias = aliasOptions.get(randomPosition);
                    } while (chosenAlias.contains(randomAlias));

                    chosenAlias.add(randomAlias);
                logger.info("alias generado =" + randomAlias);
                }
                return String.join(".", chosenAlias);

            } catch (IOException e) {
                logger.warn("Error al leer el archivo: " + e.getMessage());
                return "error al crear el CVU";
            }
        }
        public String generateCvu() {

            StringBuilder cvu= new StringBuilder("7031990");

            for(int i=0; i<15; i++) {
                int randomNumber = random.nextInt(10);
                cvu.append(randomNumber);
            }

            return cvu.toString();
        }
    }

