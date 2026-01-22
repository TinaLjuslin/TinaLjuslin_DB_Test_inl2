package com.ljuslin;

import com.ljuslin.entity.Material;
import com.ljuslin.entity.Tie;
import com.ljuslin.repo.*;
import com.ljuslin.service.ItemService;
import com.ljuslin.service.MemberService;
import com.ljuslin.service.RentalService;
import com.ljuslin.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;

public class Main {
    static void main() {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        BowtieRepositoryImpl bowtieRepository = new BowtieRepositoryImpl(sessionFactory);
        MemberRepositoryImpl memberRepository = new MemberRepositoryImpl(sessionFactory);
        PocketSquareRepositoryImpl pocketSquareRepository = new PocketSquareRepositoryImpl(sessionFactory);
        RentalRepositoryImpl rentalRepository = new RentalRepositoryImpl(sessionFactory);
        TieRepositoryImpl tieRepository = new TieRepositoryImpl(sessionFactory);

        ItemService itemService = new ItemService(bowtieRepository, pocketSquareRepository, tieRepository);
        MemberService memberService = new MemberService(memberRepository);
        RentalService rentalService = new RentalService(rentalRepository);




    }
}
