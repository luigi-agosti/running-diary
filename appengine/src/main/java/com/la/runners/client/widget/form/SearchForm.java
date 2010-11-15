package com.la.runners.client.widget.form;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.la.runners.client.Context;
import com.la.runners.client.event.SearchProfileEvent;
import com.la.runners.client.widget.form.field.MandatoryTextBoxField;
import com.la.runners.client.widget.form.field.TextAreaField;
import com.la.runners.shared.ServerException;

public class SearchForm extends CustomForm {
    
    private MandatoryTextBoxField nicknameInput;
    private MandatoryTextBoxField emailInput;
    private TextAreaField messageInput;
    
    public SearchForm(Context _context) {
        super(_context, _context.strings.searchFormTitle());
        nicknameInput = addMandatoryTextBoxField(strings().searchFormNickname());
        addButton(strings().searchFormSearchButton(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if(nicknameInput.isValid()) {
                    nicknameInput.resetValidation();
                    eventBus().fireEvent(new SearchProfileEvent(nicknameInput.getValue()));
                }
            }
        });
        addSubtitle(strings().searchFormSendInviteSubtitle());
        emailInput = addMandatoryTextBoxField(strings().searchFormEmailLabel());
        messageInput = addTextAreaField(strings().searchFormPersonalMessage());
        addButton(strings().searchFormSendInviteButton(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                try {
                    if(emailInput.isValid()) {
                        emailInput.resetValidation();
                        String email = emailInput.getValue();
                        String message = messageInput.getValue();
                        service().sendInvite(email, message, new AsyncCallback<Void>() {
                            @Override
                            public void onSuccess(Void result) {
                                showMessage(strings().searchFormInviteSuccess());
                            }
                            @Override
                            public void onFailure(Throwable caught) {
                                showMessage(strings().searchFormInviteFailure());
                            }
                        });
                    }
                } catch (ServerException e) {
                    showMessage(e.getMessage());
                }
            }
        });
        addFooterForMessages();
    }

}
