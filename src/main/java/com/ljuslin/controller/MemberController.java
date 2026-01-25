package com.ljuslin.controller;

import com.ljuslin.entity.Level;
import com.ljuslin.entity.Member;
import com.ljuslin.exception.DatabaseException;
import com.ljuslin.service.MemberService;
import com.ljuslin.view.*;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

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
        } catch (DatabaseException e) {
            memberView.showInfoAlert(e.getMessage());
        }
    }

    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    public void newMemberView() {
        newMemberView = new NewMemberView(this);
        newMemberView.showPopUp(stage, scene);
    }

    public void searchMemberView() {
        searchMemberView = new SearchMemberView(this);
        searchMemberView.showPopUp(stage, scene);
    }

    public void searchMember(String searchText) {
        List<Member> searchMembers = memberService.searchMembers(searchText);
        memberView.populateTable(searchMembers);
    }

    public void newMember(String firstName, String lastName, String email, Level level) {
        memberService.newMember(firstName, lastName, email, level);
    }

    public void changeMemberView(Member member) {
        changeMemberView = new ChangeMemberView(this);
        changeMemberView.showPopUp(stage, member);
    }

    public void changeMember(Member member) {
        memberService.changeMember(member);
    }

    public void removeMember(Member member) {
        memberService.removeMember(member);
    }

    public void getHistoryView(Member member) {
        historyView = new HistoryView();
        historyView.showPopUp(stage, member);
    }
}
