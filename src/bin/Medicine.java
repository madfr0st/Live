package bin;

public class Medicine {
    int srNo;
    String manufacturer;
    String brand;
    String type;
    String category;
    String unit;
    String packageUnit;
    double price;
    double unitPrice;

    public Medicine(int srNo, String manufacturer, String brand, String type, String category, String unit, String packageUnit, double price, double unitPrice) {
        this.srNo = srNo;
        this.manufacturer = manufacturer;
        this.brand = brand;
        this.type = type;
        this.category = category;
        this.unit = unit;
        this.packageUnit = packageUnit;
        this.price = price;
        this.unitPrice = unitPrice;
    }

    Medicine() {
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "srNo=" + srNo +
                ", manufacturer='" + manufacturer + '\'' +
                ", brand='" + brand + '\'' +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", unit='" + unit + '\'' +
                ", packageUnit='" + packageUnit + '\'' +
                ", price=" + price +
                ", unitPrice=" + unitPrice +
                '}'+"\n";
    }

    public String print(){
        return srNo+"; "+manufacturer+"; "+brand+"; "+type+"; "+category+"; "+unit+"; "+packageUnit+"; "+price+"; "+unitPrice+"\n";
    }

}
