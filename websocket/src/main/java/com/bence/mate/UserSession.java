package com.bence.mate;

import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class UserSession implements Serializable {

    public String logout() throws ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest)FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequest();

        httpRequest.getSession().invalidate();
        httpRequest.logout();

        return "/index?faces-redirect=true";
    }
}