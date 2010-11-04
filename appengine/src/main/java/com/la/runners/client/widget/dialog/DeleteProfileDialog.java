package com.la.runners.client.widget.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.la.runners.client.Context;

public class DeleteProfileDialog extends CenteredDialog {
    
    public DeleteProfileDialog(final Context context) {
        add(new Label(context.strings.dialogDeleteProfileInfo()));
        add(new Button(context.strings.dialogCancelButton(), new ClickHandler() {
            public void onClick(ClickEvent event) {
                hide();
            }
        }));
        add(new Button(context.strings.dialogContinueButton(), new ClickHandler() {
            public void onClick(ClickEvent event) {
                context.getService().deleteProfile(new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        hide();
                    }
                    @Override
                    public void onFailure(Throwable caught) {
                        hide();
                    }
                });
            }
        }));
    }
    
}
