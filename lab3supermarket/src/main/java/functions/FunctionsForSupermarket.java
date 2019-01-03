package functions;

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

public class FunctionsForSupermarket {
  public static void PrintInfoAboutPayment(Customer customer, float sum) {
    if (customer.GetMethodOfPay().getMethod() == Methods.WithCash)
      PrintMessage("cash", sum);
    else if(customer.GetMethodOfPay().getMethod() == Methods.WithCreditCard)
      PrintMessage("credit card", sum);
    else
      PrintMessage("bonuses", sum);
  }

  public static void PrintMessage(String message, float sum)
  {
    System.out.println("Customer paid " + sum + "$ by " + message+ ";");
  }

  public static void TakeProductsFromMarket(Supermarket supermarket, Customer customer){
    for (Map.Entry<String, ProductResidue> myPair : customer.GetBasket().GetProducts().entrySet())
      supermarket.ProductTaken(myPair.getKey(), myPair.getValue().GetCountOfProduct());
  }

  public static void PrintInfoAboutProducts(Supermarket supermarket) {
    System.out.println("[7:50:00] Supermarket products have been formed:");
    for (Map.Entry<String, ProductResidue> myPair : supermarket.GetProducts().entrySet())
      System.out.println(myPair.getKey() + ": price - " + myPair.getValue().GetTypeOfProduct().GetUnitPrice() + "$, count - " + myPair.getValue().GetCountOfProduct() + " pieces;");
  }

  public static void AddProductsNameToTheList(List<String> productNameList, Supermarket supermarket) {
    for (Map.Entry<String, ProductResidue> myPair : supermarket.GetProducts().entrySet())
      productNameList.add(myPair.getKey());
  }

  public static void PrintInfoAboutSoldProducts(boolean bonusesAccrued, long startTime, Customer customerInCashDesk, Supermarket supermarketVirginia){
    if (bonusesAccrued) {
      PrintTime(startTime);
      System.out.println("Customer \"customer#" + customerInCashDesk.GetNumberOfCustomer() + "\" at the cash desk, amount to pay: " + supermarketVirginia.cashdesk.GetBill() + "$;");
    } else {
      PrintTime(startTime);
      System.out.println("Customer \"customer#" + customerInCashDesk.GetNumberOfCustomer() +
              "\" at the cash desk, amount to pay: " + supermarketVirginia.cashdesk.GetBill()
              + "$ and got a lot of: " + supermarketVirginia.cashdesk.GetCountOfBonuses() + " bonuses.");
    }
  }

  public static Customer GetRandomCustomer(int numberOfCustomer) {
    Random myRandom = new Random(System.currentTimeMillis());
    TypesOfPeople TypeOfCustomer = GetRandomTypeOfCustomer();
    PaymentMethod methodOfPay = new PaymentMethod(GetRandomMethodOfPay());
    float cash = myRandom.nextInt(7000);
    cash += 10000;
    Customer customer = new Customer(TypeOfCustomer, cash, cash, cash, methodOfPay, numberOfCustomer);
    return customer;
  }

  public static TypesOfPeople GetRandomTypeOfCustomer() {
    Random myRandom = new Random(System.currentTimeMillis());

    if (myRandom.nextInt(1) == 0)
      return TypesOfPeople.Child;
    else
      return TypesOfPeople.Adult;
  }

  public static Methods GetRandomMethodOfPay() {
    Random myRandom = new Random(System.currentTimeMillis());

    int type = myRandom.nextInt(2);

    if (type == 0)
      return Methods.WithCash;
    else if (type == 1)
      return Methods.WithCreditCard;
    else
      return Methods.WithBonuses;
  }

  public static void PrintTime(long startTime) {
    int hours = 8;
    int minutes = 0;
    int seconds = 0;
    long now = System.currentTimeMillis();
    long difference = now - startTime;
    hours = (int) Math.floor(difference / 60);
    minutes = (int) Math.floor(difference % 60);
    seconds = (int) Math.floor((difference % 60) % 60);
    System.out.print("[" + (hours + 8) + ":" + minutes + ":" + seconds + "]");
  }

  public static void ToFillTheBasket(Customer customer, Supermarket supermarket, List<String> products) {
    Random myRandom = new Random(System.currentTimeMillis());
    Set<Integer> choosedProducts = new HashSet<Integer>();
    int countOfProductsInBasket = myRandom.nextInt(4) + 1;
    int randomIndexOfProduct;

    for (int i = 0; i < countOfProductsInBasket; i++) {
      int countProductForBuy = myRandom.nextInt(2) + 1;

      while (true) {
        randomIndexOfProduct = myRandom.nextInt(products.size());
        if (choosedProducts.contains(randomIndexOfProduct))
          continue;
        else
          break;
      }

      String nameOfProduct = products.get(randomIndexOfProduct);
      float countOfProductsInMarket = supermarket.GetProducts().get(nameOfProduct).GetCountOfProduct();
      if ((countOfProductsInMarket != 0) && (countOfProductsInMarket < countProductForBuy))
        customer.AddProductToBasket(countOfProductsInMarket, supermarket.GetProducts().get(nameOfProduct).GetTypeOfProduct());
      else
        customer.AddProductToBasket(countProductForBuy, supermarket.GetProducts().get(nameOfProduct).GetTypeOfProduct());
      randomIndexOfProduct++;
    }
  }

  public static void ToInitializeValuesOfCashdesk(Supermarket supermarketVirginia, Customer customerInCashDesk) {
    supermarketVirginia.cashdesk.SetCustomer(customerInCashDesk);
    supermarketVirginia.cashdesk.SetBasket(customerInCashDesk.GetBasket());
  }

  public static void PrintListOfProductsInBasketCustomer(Customer customer) {
    System.out.println("Customer \"customer#" + customer.GetNumberOfCustomer() + "\" picked up:");
    for (Map.Entry<String, ProductResidue> myPair : customer.GetBasket().GetProducts().entrySet())
      System.out.println(myPair.getValue().GetCountOfProduct() + " pieces of " + myPair.getKey() + ";");
  }

  public static void AddProductsToTheSupermarket(Supermarket supermarket) {
    Product product1 = new Product("Milk", TypesOfProducts.ForAdult, 50, 0, UnitOfProduct.byThePrice);
    Product product2 = new Product("Bread", TypesOfProducts.ForAll, 25, 0, UnitOfProduct.byThePrice, 19);
    Product product3 = new Product("Doshirak with beef", TypesOfProducts.ForAll, 14, 5, UnitOfProduct.byThePrice, 30);
    Product product4 = new Product("Doshirak with chicken", TypesOfProducts.ForAll, 149.9f, 0, UnitOfProduct.byTheWeight, 5);
    Product product5 = new Product("Water", TypesOfProducts.ForAdult, 14, 0, UnitOfProduct.byThePrice, 30);
    Product product6 = new Product("Bourbon", TypesOfProducts.ForAdult, 3350, 0, UnitOfProduct.byThePrice, 100);
    supermarket.AddProductToSupermarket(100, product1);
    supermarket.AddProductToSupermarket(60, product2);
    supermarket.AddProductToSupermarket(200, product3);
    supermarket.AddProductToSupermarket(150, product4);
    supermarket.AddProductToSupermarket(250, product5);
    supermarket.AddProductToSupermarket(400, product6);
  }
}
