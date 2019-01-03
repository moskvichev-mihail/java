import customer.Customer;
import customer.TypesOfPeople;
import payment_method.Methods;
import payment_method.PaymentMethod;
import product.Product;
import product.TypesOfProducts;
import product.UnitOfProduct;
import product_residue.ProductResidue;
import supermarket.Supermarket;

import java.util.*;

import static functions.FunctionsForSupermarket.*;

public class main {
  public static void main(String args[]) {
    Random myRandom = new Random(System.currentTimeMillis());
    Supermarket supermarketMysticFalls = new Supermarket(1000);
    List<String> productNameList = new LinkedList<String>();
    List<Customer> queueWithCustomers = new LinkedList<Customer>();
    int numberOfCustomer = 1;

    AddProductsToTheSupermarket(supermarketMysticFalls);
    AddProductsNameToTheList(productNameList, supermarketMysticFalls);
    PrintInfoAboutProducts(supermarketMysticFalls);
    long startTime = System.currentTimeMillis();
    Date now = new Date();
    long finishTime = startTime + supermarketMysticFalls.GetTimeToWork();
    supermarketMysticFalls.ToOpen();
    System.out.println("[8:00:00] Supermarket is opened.");
    Customer customer;

    while (System.currentTimeMillis() <= finishTime) {
      if (queueWithCustomers.size() != 0) {
        Customer customerInCashDesk = ((LinkedList<Customer>) queueWithCustomers).removeFirst();
        ToInitializeValuesOfCashdesk(supermarketMysticFalls, customerInCashDesk);

        if (supermarketMysticFalls.cashdesk.BillWasPaid()) {
          TakeProductsFromMarket(supermarketMysticFalls, customerInCashDesk);
        }

        boolean bonusesAccrued = supermarketMysticFalls.cashdesk.BonusesAccrued();
        PrintInfoAboutSoldProducts(bonusesAccrued, startTime, customerInCashDesk, supermarketMysticFalls);
        PrintTime(startTime);
        PrintInfoAboutPayment(customerInCashDesk, supermarketMysticFalls.cashdesk.GetBill());
        supermarketMysticFalls.cashdesk.CleanBill();
      }

      if (myRandom.nextInt(1000000) == 0) {
        customer = GetRandomCustomer(numberOfCustomer);
        PrintTime(startTime);
        System.out.println("Customer \"customer#" + numberOfCustomer + "\" arrived.");
        numberOfCustomer++;
        ToFillTheBasket(customer, supermarketMysticFalls, productNameList);

        if (customer.GetBasket().GetProducts().size() != 0) {
          queueWithCustomers.add(customer);
          PrintTime(startTime);
          PrintListOfProductsInBasketCustomer(customer);
        } else {
          System.out.println("Customer \"customer#" + customer.GetNumberOfCustomer() + "\" left.");
        }
      }
    }

    if (queueWithCustomers.size() != 0) {
      PrintTime(startTime);
      System.out.println("Supermarket was closed for other buyers.");
    }

    while (queueWithCustomers.size() != 0) {
      Customer customerInCashDesk = ((LinkedList<Customer>) queueWithCustomers).removeFirst();
      ToInitializeValuesOfCashdesk(supermarketMysticFalls, customerInCashDesk);

      if (supermarketMysticFalls.cashdesk.BillWasPaid()) {
        TakeProductsFromMarket(supermarketMysticFalls, customerInCashDesk);
      }

      boolean bonusesAccrued = supermarketMysticFalls.cashdesk.BonusesAccrued();
      PrintInfoAboutSoldProducts(bonusesAccrued, startTime, customerInCashDesk, supermarketMysticFalls);
      PrintTime(startTime);
      PrintInfoAboutPayment(customerInCashDesk, supermarketMysticFalls.cashdesk.GetBill());
      supermarketMysticFalls.cashdesk.CleanBill();
    }

    PrintTime(startTime);
    System.out.println("Supermarket is closed.");
    PrintTime(startTime);
    System.out.println("Report:");
    supermarketMysticFalls.cashdesk.PrintReport();
  }
}