import com.fasterxml.jackson.databind.ObjectMapper;
import input.Input;
import output.Output;
import session.Session;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class Main {
    private Main() {
    }

    /**
     * @param args the command line arguments (input file and output file)
     * @throws IOException if the input/output files are not found
     */
    public static void main(final String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Input input = mapper.readValue(new File(args[0]), Input.class);

        Session session = new Session(input.getUsers(), input.getMovies());
        ArrayList<Output> output = new ArrayList<>(session.runSession(input.getActions()));
        String outputString = args[0].replace("/in/", "/out/");

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(args[1]), output);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputString), output);
    }
}
