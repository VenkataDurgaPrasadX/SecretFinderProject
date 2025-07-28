import java.io.FileReader;
import java.math.BigInteger;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import shamir.Lagrange;

public class SecretFinder {
    public static void main(String[] args) throws Exception {
        System.out.println("Secret from input1.json: " + processJSON("input1.json"));
        System.out.println("Secret from input2.json: " + processJSON("input2.json"));
    }

    public static BigInteger processJSON(String filename) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new FileReader(filename));

        JSONObject keys = (JSONObject) json.get("keys");
        long k = (long) keys.get("k");

        List<BigInteger> xList = new ArrayList<>();
        List<BigInteger> yList = new ArrayList<>();

        for (Object keyObj : json.keySet()) {
            String key = (String) keyObj;
            if (key.equals("keys"))
                continue;

            JSONObject root = (JSONObject) json.get(key);
            int base = Integer.parseInt((String) root.get("base"));
            String value = (String) root.get("value");

            BigInteger x = new BigInteger(key);
            BigInteger y = new BigInteger(value, base);

            xList.add(x);
            yList.add(y);

            if (xList.size() == k)
                break;
        }

        BigInteger[] xArr = xList.toArray(new BigInteger[0]);
        BigInteger[] yArr = yList.toArray(new BigInteger[0]);

        return Lagrange.interpolateAtZero(xArr, yArr);
    }
}
