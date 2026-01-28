package com.ljuslin.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Connects a member and an item to make a rental in the shop, has rental date and return date
 * when rental is ended. Saves total revenue for this rental when rental is ended
 *
 * @author Tina Ljuslin
 */
@Entity
@Table(name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id", nullable = false)
    private Long rentalId;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(
            name = "member_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_rental_member")
    )
    private Member member;
    @Column(name = "rental_item_id", nullable = false)
    private Long itemId;
    @Enumerated(EnumType.STRING)
    @Column(name = "rental_type", nullable = false, length = 20)
    private RentalType rentalType;
    @Column(name = "total_revenue", precision = 12, scale = 2)
    private BigDecimal totalRevenue;
    @Column(name = "rental_date", nullable = false)
    private LocalDateTime rentalDate;
    @Column(name = "return_date", nullable = true)
    private LocalDateTime returnDate;
    protected Rental() {}

    public Rental(Member member, long itemId, RentalType rentalType) {
        this.member = member;
        this.itemId = itemId;
        this.rentalType = rentalType;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

    public void setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }
    @Override
    public String toString() {
        return "Rental, id:" + getRentalId() + " " + getMember().toString() + ", " +
                getRentalType() + ", " + getItemId() + ", " + getTotalRevenue();
    }
}