package com.ljuslin.service;

import com.ljuslin.entity.*;
import com.ljuslin.repo.HistoryRepository;
import com.ljuslin.repo.HistoryRepositoryImpl;
import com.ljuslin.repo.RentalRepositoryImpl;
import com.ljuslin.util.ValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RentalServiceTest {
    private RentalService rentalService;
    private RentalRepositoryImpl rentalRepo;
    private HistoryRepositoryImpl historyRepo;
    private RentalObjectService rentalObjectService;

    @BeforeEach
    void setUp() {
        rentalRepo = mock(RentalRepositoryImpl.class);
        historyRepo = mock(HistoryRepositoryImpl.class);
        rentalObjectService = mock(RentalObjectService.class);
        rentalService = new RentalService(rentalRepo, historyRepo, rentalObjectService);
    }

    @Test
    void endRental_shouldCalculateCorrectPrice_perPricingPolicy() {
        Member premiumMember = new Member("Premmi", "Premium", "premium@mail.com", Level.PREMIUM);
        Member standardMember = new Member("Stan", "Standard", "standard@mail.com", Level.STANDARD);
        Member studentMember = new Member("Tina", "Student", "student@mail.com", Level.STUDENT);
        Rental rental = new Rental(premiumMember, 1L, RentalType.TIE);
        Rental rental2 = new Rental(standardMember, 1L, RentalType.TIE);
        Rental rental3 = new Rental(studentMember, 1L, RentalType.TIE);
        rental.setRentalDate(ValidationUtil.getNowAsLocalDateTime().minusDays(2));
        rental2.setRentalDate(ValidationUtil.getNowAsLocalDateTime().minusDays(2));
        rental3.setRentalDate(ValidationUtil.getNowAsLocalDateTime().minusDays(2));
        RentalObject tie = new Tie(Material.SILK, "gredelin", new BigDecimal(100));
        when(rentalObjectService.getRentalObjectById(any(), anyLong())).thenReturn(tie);

        rentalService.endRental(rental);
        rentalService.endRental(rental2);
        rentalService.endRental(rental3);
        assertNotNull(rental.getTotalRevenue());
        assertTrue(new BigDecimal(160).compareTo(rental.getTotalRevenue()) == 0);
        assertTrue(new BigDecimal(200).compareTo(rental2.getTotalRevenue()) == 0);
        assertTrue(new BigDecimal(180).compareTo(rental3.getTotalRevenue()) == 0);
    }


}