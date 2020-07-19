package com.vintrace.exam.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

@ManagedBean
@ViewScoped
public class SmallNumberBean {
    private int smallNumber = 42;

    public int getSmallNumber() {
        return smallNumber;
    }

    public void setSmallNumber(int smallNumber) {
        this.smallNumber = smallNumber;
    }

    public void showErrors() {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "The low-priority message is displayed.");
        FacesContext.getCurrentInstance().addMessage("smallNumberID", fm);
        fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "This error message is oppressed, although it seems to be more important.");
        FacesContext.getCurrentInstance().addMessage("smallNumberID", fm);
    }

}
