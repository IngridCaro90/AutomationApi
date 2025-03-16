package apis;

import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ExtentReportGenerate;

import static io.restassured.RestAssured.given;

public class GetProductsTest {
    private ExtentReportGenerate extentReportGenerate;
    private static final String BASE_URL = "https://fakestoreapi.com";

    @BeforeClass
    public void setUp() {
        extentReportGenerate = new ExtentReportGenerate();
    }

    private RequestSpecification createRequest(String basePath) {
        return given()
                .baseUri(BASE_URL)
                .basePath(basePath);
    }

    @Test
    public void getProductsPassedResponse() {
        extentReportGenerate.generateReport("getProductsPassedResponse");
        Response response = createRequest("/products")
                .when()
                .get();

        extentReportGenerate.getExtentTestLog(Status.INFO, "Respuesta exitosa de la api");
        response.prettyPrint();
        int statusCode = response.getStatusCode();
        extentReportGenerate.getExtentTestLog(Status.INFO, String.format("El código recibido es %d.", statusCode));
        Assert.assertEquals(statusCode, 200);
        extentReportGenerate.getExtentTestLog(Status.INFO, "El resultado de la prueba fue exitoso.");
    }

    @Test
    public void getProductsFailedResponse() {
        extentReportGenerate.generateReport("getProductsFailedResponse");
        Response response = createRequest("/product")
                .when()
                .get();

        extentReportGenerate.getExtentTestLog(Status.INFO, "Respuesta fallida de la api");
        response.prettyPrint();
        int statusCode = response.getStatusCode();
        extentReportGenerate.getExtentTestLog(Status.INFO, String.format("El código recibido es %d.", statusCode));
        Assert.assertEquals(statusCode, 404);
        extentReportGenerate.getExtentTestLog(Status.INFO, "El resultado de la prueba fue exitoso.");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentReportGenerate.getExtentTestLog(Status.FAIL, "Fue fallido el test: ");
        } else {
            extentReportGenerate.getExtentTestLog(Status.PASS, "Fue exitoso el test");
        }
        extentReportGenerate.closeConnectionExtent();
    }
}