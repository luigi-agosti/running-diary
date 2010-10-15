package com.la.runners.client.widget.form;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.la.runners.client.Context;
import com.la.runners.client.event.SearchProfileEvent;
import com.la.runners.shared.ServerException;

public class SearchForm extends BaseForm {
    
    private TextBox nicknameInput;
    private TextBox emailInput;
    private TextArea messageInput;
    
    public SearchForm(Context _context) {
        super(_context, _context.strings.searchFormTitle());
        nicknameInput = addTextBoxWithLabel(context.strings.searchFormNickname());
        addButton(context.strings.searchFormSearchButton(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                context.getEventBus().fireEvent(new SearchProfileEvent(nicknameInput.getText()));
            }
        });
        addSubtitle(context.strings.searchFormSendInviteSubtitle());
        emailInput = addTextBoxWithLabel(context.strings.searchFormEmailLabel());
        messageInput = addTextAreaWithLabel("Personal Message");
        addButton(context.strings.searchFormSendInviteButton(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                try {
                    context.getService().sendInvite(emailInput.getText(), messageInput.getText(), new AsyncCallback<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                            showMessage(context.strings.searchFormInviteSuccess());
                        }
                        @Override
                        public void onFailure(Throwable caught) {
                            showMessage(context.strings.searchFormInviteFailure());
                        }
                    });
                } catch (ServerException e) {
                    showMessage(e.getMessage());
                }
            }
        });
        addFooterForMessages();
    }

}
