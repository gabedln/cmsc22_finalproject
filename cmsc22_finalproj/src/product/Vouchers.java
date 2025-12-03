package product;

import user.Seller;
import java.io.Serializable;

public class Vouchers implements Serializable {
    private Seller seller;
    private static int codeCounter = 0;
    private int voucherCode;
    private int discount;   
    private int quantity;   
    private float cap;      
    private float min;      

    public Vouchers(Seller seller, int discount, int quantity, float cap, float min) {
        this.seller = seller;
        this.discount = discount;
        this.quantity = quantity;
        this.cap = cap;
        this.min = min;
        this.voucherCode = ++codeCounter;
    }

    public void reduceQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }

    public int getVoucherCode() { return voucherCode; }
    public int getQuantity() { return quantity; }
    public float getMinimum() { return min; }
    public float getCap() { return cap; }
    public Seller getSeller() { return seller; }
    public float getDiscount() { return discount; } // returns decimal (e.g., 0.10 for 10%)
}
