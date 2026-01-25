package com.ljuslin.app;

import com.ljuslin.controller.ItemController;
import com.ljuslin.controller.MemberController;
import com.ljuslin.controller.RentalController;
import com.ljuslin.repo.*;

import com.ljuslin.service.ItemService;
import com.ljuslin.service.MemberService;
import com.ljuslin.service.RentalService;
import com.ljuslin.service.RevenueService;
import com.ljuslin.util.HibernateUtil;
import com.ljuslin.view.MainView;
import com.ljuslin.view.ItemView;
import com.ljuslin.view.MemberView;

import com.ljuslin.view.RentalView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

/**
 * Main application, creates all instances that are needed for the program to work and then
 * starts application.
 */
public class WigellApplication extends Application {
    private MemberView memberView = new MemberView();

    private ItemView itemView = new ItemView();
    private RentalView rentalView = new RentalView();
//    private RevenueView revenueView = new RevenueView();

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private BowtieRepositoryImpl bowtieRepository = new BowtieRepositoryImpl(sessionFactory);
    private MemberRepositoryImpl memberRepository = new MemberRepositoryImpl(sessionFactory);
    private PocketSquareRepositoryImpl pocketSquareRepository = new PocketSquareRepositoryImpl(sessionFactory);
    private RentalRepositoryImpl rentalRepository = new RentalRepositoryImpl(sessionFactory);
    private TieRepositoryImpl tieRepository = new TieRepositoryImpl(sessionFactory);
    private HistoryRepositoryImpl historyRepository = new HistoryRepositoryImpl(sessionFactory);
    private RentalRepositoryImpl rentalRepo = new RentalRepositoryImpl(sessionFactory);

    private ItemService itemService = new ItemService(bowtieRepository, pocketSquareRepository, tieRepository);
    private MemberService memberService = new MemberService(memberRepository, historyRepository);
    private RentalService rentalService = new RentalService(rentalRepository);
  //  private RevenueService revenueService = new RevenueService(rentalRepo);

    private MemberController memberController = new  MemberController(memberService,memberView);

        private ItemController itemController = new ItemController(itemService, itemView);
        private RentalController rentalController = new RentalController(rentalService,
                memberController, itemController, rentalView);
   /*     private RevenueController revenueController = new RevenueController(revenueService,
                memberController, itemController, revenueView);
*//*
    private MainView mainWiew = new MainView(memberController, itemController, rentalController,
            revenueController);
*/private MainView mainWiew = new MainView(memberController, itemController, rentalController);

    public WigellApplication() {
    }

    @Override
    public void start(Stage stage) {
        memberView.setMemberController(memberController);
        //memberView.setRentalController(rentalController);

        itemView.setItemController(itemController, rentalController);
        rentalView.setRentalController(rentalController, itemController);
        /*        revenueView.setRevenueController(revenueController);
*/
        mainWiew.start(stage);
    }
}
