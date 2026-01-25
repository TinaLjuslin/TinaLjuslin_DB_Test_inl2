package com.ljuslin;

//import com.ljuslin.controller.ItemController;
import com.ljuslin.app.WigellApplication;
import com.ljuslin.controller.MemberController;
//import com.ljuslin.controller.RentalController;
//import com.ljuslin.controller.RevenueController;
import com.ljuslin.repo.*;
import com.ljuslin.service.ItemService;
import com.ljuslin.service.MemberService;
import com.ljuslin.service.RentalService;
import com.ljuslin.service.RevenueService;
import com.ljuslin.util.HibernateUtil;
import com.ljuslin.view.*;
import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

/**
 * Holds main to start this application
 */
public class Main {
    public static void main(String[] args) {
        Application.launch(WigellApplication.class, args);
    }
}
