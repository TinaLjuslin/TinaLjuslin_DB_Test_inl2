package com.ljuslin.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private LocalDate rentalDate;
    @Column(name = "return_date", nullable = true)
    private LocalDate returnDate;
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

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}