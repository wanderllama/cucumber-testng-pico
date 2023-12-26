import jw.demo.constantsAndEnums.DocuportUrl;
import jw.demo.constantsAndEnums.WaitTime;
import jw.demo.util.Log;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static jw.demo.constantsAndEnums.Constants.ACCESS_TOKEN;
import static jw.demo.constantsAndEnums.Constants.REFRESH_TOKEN;
import static jw.demo.constantsAndEnums.DocuportUrl.AUTHENTICATE;

public class test {

    private static final String[] tokenArray;

    static {
//        LOG = assignLoggerByClass();
        tokenArray = new String[]{ACCESS_TOKEN, REFRESH_TOKEN};
    }

    static int[] x = new int[1];

    public static void main(String[] args) {
        met();

    }

    @SneakyThrows
    private static void met() {
        By element = new By.ByName("///fewfddddfjkedsjfqewjfqiwjfeqwoefjieqwofjiqowjfiowejfiqo");
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "*/*")
                    .uri(new URI("123" + DocuportUrl.getApiUrl(AUTHENTICATE)))
                    .POST(HttpRequest.BodyPublishers.ofString("{}"))
                    .build();
        } catch (URISyntaxException e) {
            throw new JavascriptException(Log.exceptionErrorMsg(
                    String.format("JavascriptExecutor error while clicking locator"), e, WaitTime.SHORT, element));
        }

        //*               *
        //*               *
        //

    }
}
