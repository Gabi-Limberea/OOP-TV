import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import input.Input;
import output.Output;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);

        Input input = mapper.readValue(new File("checker/resources/in/basic_1.json"),
                                          Input.class
                                         );

        ArrayList<Output> output = new ArrayList<>();

        mapper.writerWithDefaultPrettyPrinter().writeValue(
                new File("output.json"), input);
    }
}
