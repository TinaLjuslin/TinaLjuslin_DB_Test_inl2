package com.ljuslin.repo;

import com.ljuslin.entity.*;
import com.ljuslin.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RentalRepositoryImplTest {
    private static SessionFactory sessionFactory;
    private RentalRepositoryImpl rentalRepository;
    private TieRepositoryImpl tieRepository;
    private MemberRepositoryImpl memberRepository;
    private BowtieRepositoryImpl bowtieRepository;
    private PocketSquareRepositoryImpl pocketSquareRepository;

    @BeforeAll
    static void beforeAll() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @BeforeEach
    void beforeEach() {
        rentalRepository = new RentalRepositoryImpl(sessionFactory);
        bowtieRepository = new BowtieRepositoryImpl(sessionFactory);
        pocketSquareRepository = new PocketSquareRepositoryImpl(sessionFactory);
        tieRepository = new TieRepositoryImpl(sessionFactory);
        memberRepository = new MemberRepositoryImpl(sessionFactory);
    }

    @AfterAll
    static void afterAll() {
        HibernateUtil.shutdown();
    }

    @Test
    void save_rentalShouldBeSavedProperly() {
        Tie tie = new Tie(Material.SILK, "blå", new BigDecimal(27.5));
        Member member = new Member("Jörgen", "Nyqvist", "joggan@mail.com", Level.PREMIUM);
        tieRepository.save(tie);
        assertNotNull(tie);
        assertNotNull(tie.getItemId());
        memberRepository.save(member);
        assertNotNull(member);
        assertNotNull(member.getMemberId());
        Rental rental = new Rental(member, tie.getItemId(), RentalType.TIE);
        rentalRepository.save(rental);
        assertNotNull(rental);
        assertNotNull(rental.getRentalId());
    }

    @Test
    void checkMemberHasActiveRental_shouldReturnTrue_ifMemberHasOngoingRental() {
        Tie tie = new Tie(Material.SILK, "blå", new BigDecimal(27.5));
        Member member = new Member("Jörgen", "Nyqvist", "jogga@mail.com", Level.PREMIUM);
        member.setActive(true);
        tieRepository.save(tie);
        memberRepository.save(member);
        Rental rental = new Rental(member, tie.getItemId(), RentalType.TIE);
        rentalRepository.save(rental);
        assertTrue(rentalRepository.checkMemberHasActiveRental(member));
        rental.setReturnDate(LocalDateTime.now().plusDays(1));
        rental.setTotalRevenue(new BigDecimal(27.5));
        rentalRepository.change(rental);
        assertFalse(rentalRepository.checkMemberHasActiveRental(member));
    }

    @Test
    void search_shouldSearchMemberAndRentalObjectConnectedToRental_ifSearchMatches() {
        Bowtie bowtie = new Bowtie(Material.COTTON, "grön", new BigDecimal(10));
        PocketSquare pocketSquare = new PocketSquare(Material.SILK, "grön", new BigDecimal(20));
        Tie tie = new Tie(Material.SILK, "rosa", new BigDecimal(30));

        bowtieRepository.save(bowtie);
        pocketSquareRepository.save(pocketSquare);
        tieRepository.save(tie);

        assertNotNull(bowtie.getItemId());
        assertNotNull(pocketSquare.getItemId());
        assertNotNull(tie.getItemId());

        Member member1 = new Member("Jörgen", "Nyqvist", "a@mail.com", Level.PREMIUM);
        Member member2 = new Member("Rosa", "Nyqvist", "b@mail.com", Level.PREMIUM);
        //medlem heter rosa men har slips som är rosa, borde bara hämtas en gång
        Member member3 = new Member("Rosa", "Nyqvist", "c@mail.com", Level.PREMIUM);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        assertNotNull(member1.getMemberId());
        assertNotNull(member2.getMemberId());
        assertNotNull(member3.getMemberId());

        Rental bowtieRental = new Rental(member1, bowtie.getItemId(), RentalType.BOWTIE);
        Rental pocketSquareRental = new Rental(member2, pocketSquare.getItemId(),
                RentalType.POCKET_SQUARE);
        Rental tieRental = new Rental(member3, tie.getItemId(), RentalType.TIE);

        rentalRepository.save(bowtieRental);
        rentalRepository.save(pocketSquareRental);
        rentalRepository.save(tieRental);

        assertNotNull(bowtieRental.getRentalId());
        assertNotNull(pocketSquareRental.getRentalId());
        assertNotNull(tieRental.getRentalId());

        List<Rental> list = rentalRepository.search("rosa");
        assertNotNull(list);
        System.out.println(list.size());
        //en slips är rosa, två members heter Rosa, bara två poster ska returneras eftersom Rosa
        // har en rosa slips menska bara returneras en gång
        assertEquals(list.size(), 2);
    }
}
