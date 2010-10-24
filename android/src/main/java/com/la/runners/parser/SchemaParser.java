
package com.la.runners.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.la.runners.R;
import com.la.runners.exception.ParserException;

public class SchemaParser {

    private static final String NAME = "name";

    private static final String VERSION = "version";

    private static final String DROP_STATEMENT = "dropStatements";

    private static final String CREATE_STATEMENT = "createStatements";

    private String name;

    private String version;

    private List<String> dropStms;

    private List<String> createStms;
    
    private static final String CREATE_RUN_TRIGGER = "create trigger update_locations " +
    		"after update on Run when new.id not null begin " +
    		"update Location set runId = new.id where runId = old._id; end;";

    private static final String DROP_RUN_TRIGGER = "drop trigger if exists update_locations;";

    public SchemaParser(InputStream inputStream) throws ParserException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readValue(inputStream, JsonNode.class);
            name = rootNode.get(NAME).getTextValue();
            version = rootNode.get(VERSION).getTextValue();
            dropStms = getListFromArray(rootNode, DROP_STATEMENT);
            dropStms.add(DROP_RUN_TRIGGER);
            createStms = getListFromArray(rootNode, CREATE_STATEMENT);
            createStms.add(CREATE_RUN_TRIGGER);
        } catch (Throwable e) {
            throw new ParserException(R.string.error_1, e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        version = version.substring(version.length()-4, version.length()-1);
        return Integer.valueOf(version);
    }

    public List<String> getDropStms() {
        return dropStms;
    }

    public List<String> getCreateStms() {
        return createStms;
    }
    
    private List<String> getListFromArray(JsonNode rootNode, String attribute) {
        List<String> strings = new ArrayList<String>();
        JsonNode node = rootNode.get(attribute);
        if(node != null) {
            Iterator<JsonNode> i = node.getElements();
            while(i.hasNext()) {
                strings.add(i.next().getTextValue());
            }
        }
        return strings;
    }

}
