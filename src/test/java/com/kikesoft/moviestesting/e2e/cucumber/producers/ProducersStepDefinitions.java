package com.kikesoft.moviestesting.e2e.cucumber.producers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.kikesoft.moviestesting.e2e.cucumber.CucumberScenarioContext;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProducersStepDefinitions {

    private String createdProducerName;
    private String createdProducerProfile;

    @Given("the user opens the producers list page")
    public void theUserOpensTheProducersListPage() {
        CucumberScenarioContext.getProducersBot().openList();
        CucumberScenarioContext.getProducersBot().waitUntilListReady();
    }

    @Then("the producers list title should be visible")
    public void theProducersListTitleShouldBeVisible() {
        assertTrue(
                CucumberScenarioContext.getProducersBot().isListTitleVisible(),
                "Expected producers list title to be visible"
        );
    }

    @Then("each listed producer should expose a details link")
    public void eachListedProducerShouldExposeADetailsLink() {
        List<String> producerIds = CucumberScenarioContext.getProducersBot().getListedProducerIds();

        assertTrue(!producerIds.isEmpty(), "Expected at least one producer row in list");

        for (String producerId : producerIds) {
            String href = CucumberScenarioContext.getProducersBot().getProducerDetailsHref(producerId);
            assertTrue(
                    href != null && href.endsWith("/en-US/producers/" + producerId),
                    "Expected details link to end with /en-US/producers/" + producerId
            );
        }
    }

    @Given("the user opens the create producer form")
    public void theUserOpensTheCreateProducerForm() {
        CucumberScenarioContext.getProducersBot().openCreateForm();
    }

    @When("the user fills producer profile with 50 characters and keeps name empty")
    public void theUserFillsProducerProfileWithFiftyCharactersAndKeepsNameEmpty() {
        CucumberScenarioContext.getProducersBot().setProducerName("");
        CucumberScenarioContext.getProducersBot().setProducerProfile(buildProfileWith50Chars());
    }

    @Then("the save producer button should be disabled")
    public void theSaveProducerButtonShouldBeDisabled() {
        assertTrue(
                !CucumberScenarioContext.getProducersBot().isSaveEnabled(),
                "Expected save button to stay disabled when producer name is empty"
        );
    }

    @When("the user enters valid producer data")
    public void theUserEntersValidProducerData() {
        createdProducerName = "Producer " + System.currentTimeMillis();
        createdProducerProfile = buildProfileWith50Chars();

        CucumberScenarioContext.getProducersBot().setProducerName(createdProducerName);
        CucumberScenarioContext.getProducersBot().setProducerProfile(createdProducerProfile);

        assertTrue(
                CucumberScenarioContext.getProducersBot().isSaveEnabled(),
                "Expected save button to be enabled for a valid producer form"
        );
    }

    @And("the user saves the producer")
    public void theUserSavesTheProducer() {
        CucumberScenarioContext.getProducersBot().saveProducer();
    }

    @Then("the new producer should be visible in the producers list")
    public void theNewProducerShouldBeVisibleInTheProducersList() {
        assertTrue(
                CucumberScenarioContext.getProducersBot().hasProducerNamed(createdProducerName),
                "Expected newly created producer to be visible in producers list"
        );
    }

    private static String buildProfileWith50Chars() {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit.".substring(0, 50);
    }
}