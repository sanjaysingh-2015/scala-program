package com.techsophy.codeexplainer.controller;

import com.openai.OpenAI;
import com.openai.api.models.CompletionRequest;
import com.openai.api.models.CompletionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitMarkdownGeneratorController {

    @Value("${openai.apiKey}")
    private String apiKey;

    @PostMapping("/generate-markdown")
    public ResponseEntity<String> generateMarkdownDocument(@RequestBody String repoLink) {
        try {
            // Create an instance of the OpenAI client
            OpenAI openai = new OpenAI(apiKey);

            // Prepare the prompt for generating the Markdown document
            String prompt = String.format("Create an application that takes a Git repository link (%s) and generates a Markdown document.", repoLink);

            // Set the parameters for the completion request
            CompletionRequest completionRequest = new CompletionRequest.Builder()
                    .prompt(prompt)
                    .maxTokens(200)
                    .build();

            // Generate the Markdown document using ChatGPT
            CompletionResponse completionResponse = openai.getLanguageApi().createCompletion(completionRequest);

            // Retrieve the generated Markdown document
            String markdownDocument = completionResponse.getChoices().get(0).getText();

            return ResponseEntity.ok(markdownDocument);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while generating the Markdown document.");
        }
    }
}