package cube.model.food;

import java.util.Date;

import cube.model.food.foodutil.SortByExpiry;
import cube.model.food.foodutil.SortByName;
import cube.model.food.foodutil.SortByPrice;
import cube.model.food.foodutil.SortByStock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FoodUtilTest {
    private class FoodStub extends Food {
        String name;
        int stock;
        double price;
        Date date;

        public FoodStub(){
        }

        public FoodStub(String name) {
            this.name = name;
        }

        public FoodStub(int stock) {
            this.stock = stock;
        }

        public FoodStub(double price) {
            this.price = price;
        }

        public FoodStub(Date date) {
            this.date = date;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getStock() {
            return stock;
        }

        @Override
        public double getPrice() {
            return price;
        }

        @Override
        public Date getExpiryDate() {
            return date;
        }
    }

    @Test
    public void sortByNameTest() {
        FoodStub f1 = new FoodStub("a");
        FoodStub f2 = new FoodStub("b");
        SortByName sort = new SortByName();
        int smaller = sort.compare(f1, f2);
        int larger = sort.compare(f2, f1);
        int equal = sort.compare(f1, f1);
        assertEquals(smaller, -1);
        assertEquals(larger, 1);
        assertEquals(equal, 0);
        assertFalse(sort.compare(f1,f2) == 0);
    }

    @Test
    public void sortByStockTest() {
        FoodStub f1 = new FoodStub(-1);
        FoodStub f2 = new FoodStub(1);
        SortByStock sort = new SortByStock();
        int smaller = sort.compare(f1, f2);
        int larger = sort.compare(f2, f1);
        int equal = sort.compare(f1, f1);
        assertEquals(smaller, -1);
        assertEquals(larger, 1);
        assertEquals(equal, 0);
        assertFalse(sort.compare(f1, f2) == 0);
    }

    @Test
    public void sortByPriceTest() {
        FoodStub f1 = new FoodStub(-1d);
        FoodStub f2 = new FoodStub(1d);
        SortByPrice sort = new SortByPrice();
        int smaller = sort.compare(f1, f2);
        int larger = sort.compare(f2, f1);
        int equal = sort.compare(f1, f1);
        assertEquals(smaller, -1);
        assertEquals(larger, 1);
        assertEquals(equal, 0);
        assertFalse(sort.compare(f1, f2) == 0);
    }

    @Test
    public void sortByDateTest() {
        FoodStub f1 = new FoodStub(new Date(2019, 10, 30));
        FoodStub f2 = new FoodStub(new Date(2019, 11, 30));
        FoodStub f3 = new FoodStub();
        SortByExpiry sort = new SortByExpiry();
        int smaller = sort.compare(f1, f2);
        int equal = sort.compare(f1, f1);
        int larger = sort.compare(f2, f1);
        final int nullSmaller = sort.compare(f1, f3);
        final int nullEqual = sort.compare(f3, f3);
        final int nullLarger = sort.compare(f3, f1);
        assertEquals(smaller, -1);
        assertEquals(larger, 1);
        assertEquals(equal, 0);
        assertEquals(nullSmaller, -1);
        assertEquals(nullLarger, 1);
        assertEquals(nullEqual, 0);
        assertFalse(sort.compare(f1, f2) == 0);
        assertFalse(sort.compare(f1, f3) == 0);        
    }

}
