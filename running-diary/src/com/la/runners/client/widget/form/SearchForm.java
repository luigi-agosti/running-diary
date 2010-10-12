package com.la.runners.client.widget.form;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.la.runners.client.ServiceAsync;
import com.la.runners.client.event.SearchProfileEvent;

public class SearchForm extends BaseForm {
    
    private TextBox nicknameInput;
    private TextBox emailInput;
    private TextArea messageInput;
    
    public SearchForm(final HandlerManager eventBus, final ServiceAsync service) {
        super("Search");
        nicknameInput = addTextBoxWithLabel("Nickname");
        addButton("Search", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new SearchProfileEvent(nicknameInput.getText()));
            }
        });
        addSubtitle("Send invite");
        emailInput = addTextBoxWithLabel("E-mail");
        messageInput = addTextAreaWithLabel("Personal Message");
        addButton("Invite", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.sendInvite(emailInput.getText(), messageInput.getText(), new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        showMessage("Success, invite sent");
                    }
                    @Override
                    public void onFailure(Throwable caught) {
                        showMessage("Umm, there is a problem!");
                    }
                });
            }
        });
        showMessage("");
    }

}
