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

import java.io.File;

import static io.restassured.RestAssured.given;

public class PutUpdateProductTest {
    private ExtentReportGenerate extentReportGenerate;
    private static final String BASE_URL = "https://fakestoreapi.com";

    @BeforeClass
    public void setUp() {
        extentReportGenerate = new ExtentReportGenerate();
    }
    File bodyRequest = new File("src/main/resources/bodyUpdateProduct.json");

    private RequestSpecification createRequest(String basePath) {
        return given()
                .baseUri(BASE_URL)
                .basePath(basePath)
                .header("Content-Type", "application/json")
                .body(bodyRequest);
    }

    @Test
    public void updateProductPassedResponse() {
        extentReportGenerate.generateReport("updateProductPassedResponse");
        Response response = createRequest("/products/21")
                .when()
                .put();

        extentReportGenerate.getExtentTestLog(Status.INFO, "Respuesta exitosa de la api");
        response.prettyPrint();
        int statusCode = response.getStatusCode();
        extentReportGenerate.getExtentTestLog(Status.INFO, String.format("El código recibido es %d.", statusCode));
        Assert.assertEquals(statusCode, 200);
        extentReportGenerate.getExtentTestLog(Status.INFO, "El resultado de la prueba fue exitoso.");
    }

    @Test
    public void updateProductFaildedResponse() {
        extentReportGenerate.generateReport("updateProductFaildedResponse");
        Response response = createRequest("/product/21")
                .when()
                .put();

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