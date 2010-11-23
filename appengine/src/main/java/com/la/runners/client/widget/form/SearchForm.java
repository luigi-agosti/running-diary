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
    
    public static final String SEARCH = "search";
    public static final String SEND_INVITE = "sendInvite";
    public static final String ID = "searchForm";
    public static final String NICKNAME_ID = "nickname";
    public static final String EMAIL_ID = "email";
    public static final String MESSAGE_ID = "message";
    
    private MandatoryTextBoxField nicknameInput;
    private MandatoryTextBoxField emailInput;
    private TextAreaField messageInput;
    
    public SearchForm(Context _context) {
        super(_context, _context.strings.searchFormTitle(), ID);
        nicknameInput = addMandatoryTextBoxField(strings().searchFormNickname(), NICKNAME_ID);
        addButton(strings().searchFormSearchButton(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if(nicknameInput.isValid()) {
                    nicknameInput.resetValidation();
                    eventBus().fireEvent(new SearchProfileEvent(nicknameInput.getValue()));
                }
            }
        }, SEARCH);
        addSubtitle(strings().searchFormSendInviteSubtitle());
        emailInput = addMandatoryTextBoxField(strings().searchFormEmailLabel(), EMAIL_ID);
        messageInput = addTextAreaField(strings().searchFormPersonalMessage(), MESSAGE_ID);
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
        }, SEND_INVITE);
        addFooterForMessages();
    }

}
