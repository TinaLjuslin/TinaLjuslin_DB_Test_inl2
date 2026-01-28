package com.ljuslin.service;

import com.ljuslin.entity.*;
import com.ljuslin.exception.ValidationException;
import com.ljuslin.repo.BowtieRepositoryImpl;
import com.ljuslin.repo.PocketSquareRepositoryImpl;
import com.ljuslin.repo.TieRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class RentalObjectServiceTest {
    private RentalObjectService rentalObjectService;
    private BowtieRepositoryImpl bowtieRepository;
    private PocketSquareRepositoryImpl pocketSquareRepository;
    private TieRepositoryImpl tieRepository;

    @BeforeEach
    void setUp() {
        bowtieRepository = mock(BowtieRepositoryImpl.class);
        pocketSquareRepository = mock(PocketSquareRepositoryImpl.class);
        tieRepository = mock(TieRepositoryImpl.class);
        rentalObjectService = new RentalObjectService(bowtieRepository, pocketSquareRepository, tieRepository);
    }

    @Test
    void newRentalObject_shouldSaveProperRentalObject() {
        rentalObjectService.newRentalObject(RentalType.TIE, Material.COTTON, "rosa", "12,5");
        verify(tieRepository, times(1)).save(any(Tie.class));
        verify(bowtieRepository, times(0)).save(any(Bowtie.class));
        verify(pocketSquareRepository, times(0)).save(any(PocketSquare.class));
    }
    @Test
    void newRentalObject_shouldThrowValidationException_whenWrongfulPrice() {
        //exception thrown in ValidationUtil
        assertThrows(ValidationException.class,
                () -> rentalObjectService.newRentalObject(RentalType.TIE, Material.COTTON, "rosa"
                        , "pris"));
    }
    @Test
    void searchRentalObject_shouldRemoveDuplicates() {
        Tie tie = new Tie(Material.SILK, "grön", new BigDecimal(22.5));
        when(bowtieRepository.search(any())).thenReturn(List.of());
        when(pocketSquareRepository.search(any())).thenReturn(List.of());
        when(tieRepository.getAll()).thenReturn(List.of(tie));
        when(tieRepository.search("slips")).thenReturn(List.of(tie));
        List<RentalObject> list = rentalObjectService.searchRentalObjects("slips");
        assertEquals(1, list.size(), "Det ska bara finnas en slips och inte samma slips två " +
                "gånger, även om den hämtas i både getAll och search.");
    }
}