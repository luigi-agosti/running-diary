package com.la.runners.client.widget.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.la.runners.client.Context;
import com.la.runners.shared.Profile;

public abstract class NewProfileDialog extends CenteredDialog {
    
    public NewProfileDialog(final Context context) {
        add(new Label(context.strings.dialogNewProfileInfo()));
        addToolbarButton(new Button(context.strings.dialogCancelButton(), new ClickHandler() {
            public void onClick(ClickEvent event) {
                context.getService().save(new Profile(), new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        finish(new Profile());
                        hide();
                    }

                    @Override
                    public void onSuccess(Void result) {
                        finish(new Profile());
                        hide();
                    }
                });
            }
        }));
        addToolbarButton(new Button(context.strings.dialogContinueButton(), new ClickHandler() {
            public void onClick(ClickEvent event) {
                Window.Location.replace("/profile.jsp");
            }
        }));
    }
    
    public abstract void finish(Profile profile);
    
}
