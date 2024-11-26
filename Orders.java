import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Orders {
    private String product;
    private double cost;

    public Orders(String product, double cost) {
        this.product = product;
        this.cost = cost;
    }

    public String getProduct() {
        return product;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
    
        return getProduct() + ": " + getCost();
    }


    public static void main(String[] args) {
                List<Orders> orders = List.of(
                new Orders("Laptop", 1200.0),
                new Orders("Smartphone", 800.0),
                new Orders("Laptop", 1500.0),
                new Orders("Tablet", 500.0),
                new Orders("Smartphone", 900.0)
        );

        Map<String, List<Orders>> ordersGroup = orders.stream().collect(Collectors.groupingBy(Orders::getProduct));
        ordersGroup.forEach((product, productOrder) -> {System.out.println(product + ": " + productOrder);});
        System.out.println();

        Map<String, Double> totalCost = orders.stream().collect(Collectors.groupingBy(Orders::getProduct, Collectors.summingDouble(Orders::getCost)));
        totalCost.forEach((product, total) -> {System.out.println(product + " total cost : " + total);});
        System.out.println();
        
        List<Map.Entry<String, Double>> totalCostSort = totalCost.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).collect(Collectors.toList());
        totalCostSort.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
        System.out.println();

        orders.stream().sorted(Comparator.comparingDouble(Orders::getCost).reversed()).limit(3).forEach(order -> {System.out.println(order.getProduct() + ": " + order.getCost());});
        System.out.println();

        orders.stream()
        .collect(Collectors.groupingBy(
                Orders::getProduct,
                Collectors.summingDouble(Orders::getCost)
        ))
        .entrySet()
        .stream()
        .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
        .limit(3)
        .collect(Collectors.teeing(
                Collectors.mapping(Map.Entry::getKey, Collectors.toList()),
                Collectors.summingDouble(Map.Entry::getValue),
                (names, total) -> {
                    System.out.println("Products: " + names);
                    System.out.println("Total Cost: " + total);
                    return null; 
                }
        ));
        
    }
}
