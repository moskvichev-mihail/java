package report;

import org.junit.Test;
import product.Product;
import product.TypesOfProducts;
import product.UnitOfProduct;

import static org.junit.Assert.*;

public class ReportTest {

  @Test
  public void writeInTheReport() {
    Report report = new Report();
    Product product = new Product("milk", TypesOfProducts.ForAll, 35, 4.4f, UnitOfProduct.byThePrice);
    report.WriteInTheReport(35f, product);
  }

  @Test
  public void printReport() {
  }
}