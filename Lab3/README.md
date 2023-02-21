3. vježba

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.example.Lab3.Device.deserializeDevice;
// DESERIALIZATION FROM JSON
        String filePath = "/directory/sensors.json";
        deserializeDevice(filePath);


    public static void deserializeDevice(String filePath) {
        Device deserialize = new Device();
        try {
            deserialize = new ObjectMapper()
            .readValue(new File(filePath), Device.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        deserialize.runClient();
     

```
