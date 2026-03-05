package eco.nis.utilities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class NullKeyMapSerializer extends StdSerializer<Object> {

    private static final String EMPTY = "";

    public NullKeyMapSerializer() {

        this(null);
    }

    public NullKeyMapSerializer(Class<Object> t) {

        super(t);
    }

    @Override
    public void serialize(Object nullKey, JsonGenerator jsonGenerator, SerializerProvider unused) throws IOException {

        jsonGenerator.writeFieldName(EMPTY);
    }
}
