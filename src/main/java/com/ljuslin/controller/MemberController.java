package com.ljuslin.controller;

import com.ljuslin.entity.History;
import com.ljuslin.entity.Level;
import com.ljuslin.entity.Member;
import com.ljuslin.exception.*;
import com.ljuslin.service.MemberService;
import com.ljuslin.view.*;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import org.hibernate.action.internal.EntityActionVetoException;

import java.util.List;

/**
 * Controlls all Views for Members and calls the right service to perform operations
 *
 * @author Tina Ljuslin
 */
public class MemberController {
    private MemberService memberService;
    private MemberView memberView;
    private NewMemberView newMemberView;
    private SearchMemberView searchMemberView;
    private ChangeMemberView changeMemberView;
    private HistoryView historyView;
    private Stage stage;
    private Scene scene;

    public MemberController() {
    }

    public MemberController(MemberService memberService, MemberView memberView) {
        this.memberService = memberService;
        this.memberView = memberView;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Tab getTab() {
        return memberView.getTab();
    }

    public void populateTable() {
        try {
            memberView.populateTable(memberService.getAllMembers());
        } catch (LjuslinException e) {
            memberView.showInfoAlert(e.getMessage());
        } catch (Exception e) {
            memberView.showErrorAlert(e.getMessage());
        }
    }

    public List<Member> getAllMembers(View view) {
        try {
            return memberService.getAllMembers();
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return List.of();
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return List.of();
        }
    }

    public void newMemberView() {
        newMemberView = new NewMemberView(this);
        newMemberView.showPopUp(stage, scene);
    }

    public void searchMemberView() {
        searchMemberView = new SearchMemberView(this);
        searchMemberView.showPopUp(stage, scene);
    }

    public boolean searchMember(String searchText, View view) {
        try {
            List<Member> searchMembers = memberService.searchMembers(searchText);
            memberView.populateTable(searchMembers);
            return true;
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return false;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return false;
        }
    }

    public boolean newMember(String firstName, String lastName, String email, Level level, View view) {
        try {
            memberService.newMember(firstName, lastName, email, level);
            return true;
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return false;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return false;
        }
    }

    public void changeMemberView(Member member) {
        changeMemberView = new ChangeMemberView(this);
        changeMemberView.showPopUp(stage, member);
    }

    public boolean changeMember(Member member, View view) {
        try {
             memberService.changeMember(member);
             return true;
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return false;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return false;
        }
    }

    public boolean removeMember(Member member, View view) {
        try {
            memberService.removeMember(member);
            return true;
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return false;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return false;
        }
    }

    public boolean getHistoryView(Member member, View view) {
        try {
            historyView = new HistoryView();
            List<History> list = memberService.getHistory(member);
            historyView.showPopUp(stage, member, list);
            return true;
        } catch (LjuslinException e) {
            view.showInfoAlert(e.getMessage());
            return false;
        } catch (Exception e) {
            view.showErrorAlert(e.getMessage());
            return false;
        }
    }
}
