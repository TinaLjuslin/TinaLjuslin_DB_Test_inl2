package com.ljuslin.app;

import com.ljuslin.controller.RentalObjectController;
import com.ljuslin.controller.MemberController;
import com.ljuslin.controller.RentalController;
import com.ljuslin.controller.RevenueController;
import com.ljuslin.repo.*;

import com.ljuslin.service.RentalObjectService;
import com.ljuslin.service.MemberService;
import com.ljuslin.service.RentalService;
import com.ljuslin.service.RevenueService;
import com.ljuslin.util.HibernateUtil;
import com.ljuslin.view.*;

import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

/**
 * Main application, creates all instances that are needed for the program to work and then
 * starts application.
 */
public class WigellApplication extends Application {
    private MemberView memberView = new MemberView();

    private RentalObjectView rentalObjectView = new RentalObjectView();
    private RentalView rentalView = new RentalView();
    private RevenueView revenueView = new RevenueView();

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private BowtieRepositoryImpl bowtieRepository = new BowtieRepositoryImpl(sessionFactory);
    private MemberRepositoryImpl memberRepository = new MemberRepositoryImpl(sessionFactory);
    private PocketSquareRepositoryImpl pocketSquareRepository = new PocketSquareRepositoryImpl(sessionFactory);
    private RentalRepositoryImpl rentalRepository = new RentalRepositoryImpl(sessionFactory);
    private TieRepositoryImpl tieRepository = new TieRepositoryImpl(sessionFactory);
    private HistoryRepositoryImpl historyRepository = new HistoryRepositoryImpl(sessionFactory);
    private RentalRepositoryImpl rentalRepo = new RentalRepositoryImpl(sessionFactory);

    private RentalObjectService rentalObjectService = new RentalObjectService(bowtieRepository, pocketSquareRepository, tieRepository);
    private MemberService memberService = new MemberService(memberRepository, historyRepository,
            rentalRepository);
    private RentalService rentalService = new RentalService(rentalRepository, historyRepository, rentalObjectService);
    private RevenueService revenueService = new RevenueService(rentalRepo);

    private MemberController memberController = new  MemberController(memberService,memberView);

        private RentalObjectController rentalObjectController = new RentalObjectController(rentalObjectService, rentalObjectView);
        private RentalController rentalController = new RentalController(rentalService,
                memberController, rentalObjectController, rentalView);
        private RevenueController revenueController = new RevenueController(revenueService,
                memberController, rentalObjectController, revenueView);

    private MainView mainWiew = new MainView(memberController, rentalObjectController, rentalController,
            revenueController);

    public WigellApplication() {
    }

    @Override
    public void start(Stage stage) {
        memberView.setMemberController(memberController);
        memberView.setRentalController(rentalController);

        rentalObjectView.setItemController(rentalObjectController, rentalController);
        rentalView.setRentalController(rentalController, rentalObjectController);
                revenueView.setRevenueController(revenueController);

        mainWiew.start(stage);
    }
}
