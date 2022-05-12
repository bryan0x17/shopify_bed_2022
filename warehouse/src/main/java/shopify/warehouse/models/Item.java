package shopify.warehouse.models;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private long id;

    private String name;
    private String description;
    private int amount = 0;

    private String deletionComments = null;

    private boolean deleted = false;

    public Item() {
    }

    public Item(String name, String description, int amount) {
        this.name = name;
        this.description = description;
        this.amount = amount;
    }

    public Item(String deletionComments) {
        this.deletionComments = deletionComments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDeletionComments() {
        return deletionComments;
    }

    public void setDeletionComments(String deletionComments) {
        this.deletionComments = deletionComments;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object other) {
        if (getClass() != other.getClass()) {
            return false;
        }
        return this.getId() == ((Item) other).getId();
    }
}
