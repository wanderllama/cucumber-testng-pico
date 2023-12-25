package jw.demo.stepDefinitions.dataprovider;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;

import java.util.List;
import java.util.Map;

public class StepDefs {

    @DataTableType
    public Author authorEntry(Map<String, String> entry) {
        return new Author(
                entry.get("title"),
                entry.get("author"),
                entry.get("yearOfPublishing"));
    }

    @DataTableType
    public Map<String, String> dataTableToMap(DataTable dataTable) {
        return dataTable.asMap();
    }

    @Given("the following books")
    public void theFollowingBooks(List<Author> authors) {
        System.out.println("START");
        System.out.println(authors);
        System.out.println("END");
    }

    @Given("the following data")
    public void the_following_data(DataTable dataTable) {
        Map<String, String> map = dataTableToMap(dataTable);
        map.forEach((key, value) -> {
            System.out.println("key" + key);
            System.out.println("value " + value);
        });

        for (Map.Entry<String, String> e : map.entrySet()) {
            System.out.println("key" + e.getKey());
            System.out.println("value " + e.getValue());
        }
    }

}
