package jsf.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the order_items database table.
 * 
 */
@Entity
@Table(name="order_items")
@NamedQuery(name="OrderItem.findAll", query="SELECT o FROM OrderItem o")
public class OrderItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_order_item")
	private Integer idOrderItem;

	@Column(name="combined_price")
	private float combinedPrice;

	private int discount;

	private int quantity;

	//bi-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name="id_order")
	private Order order;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="id_product")
	private Product product;

	public OrderItem() {
	}

	public Integer getIdOrderItem() {
		return this.idOrderItem;
	}

	public void setIdOrderItem(Integer idOrderItem) {
		this.idOrderItem = idOrderItem;
	}

	public float getCombinedPrice() {
		return combinedPrice;
	}

	public void setCombinedPrice(float combinedPrice) {
		this.combinedPrice = combinedPrice;
	}

	public int getDiscount() {
		return this.discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}