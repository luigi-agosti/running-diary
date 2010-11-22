package com.la.runners.widget.quickaction;

import com.la.runners.R;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Action item, displayed as menu with icon.
 * 
 * @author Boriero
 *
 */
public class ActionItem  {
	private Drawable icon;
	private OnClickListener listener;
	
	/**
	 * 
	 * Constructor
	 */
	public ActionItem() {}
	
	/**
	 * Constructor
	 * 
	 * @param icon {@link Drawable} action icon
	 */
	public ActionItem(Drawable icon) {
		this.icon = icon;
	}
	
	/**
	 * Set action icon
	 * 
	 * @param icon {@link Drawable} action icon
	 */
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	
	/**
	 * Get action icon
	 * 
	 * @return  {@link Drawable} action icon
	 */
	public Drawable getIcon() {
		return this.icon;
	}
	
	/**
	 * Get action item {@link View}
	 * 
	 * @param inflater  {@link LayoutInflater}	
	 * @return action item {@link View}
	 */
	public View getActionItemView(LayoutInflater inflater) {
		LinearLayout container	= (LinearLayout) inflater.inflate(R.layout.action_item, null);
		
		ImageView img			= (ImageView) container.findViewById(R.id.icon);
		
		if (icon != null) {
			img.setImageDrawable(icon);
		}
				
		
		if (listener != null) {
			container.setOnClickListener(listener);
		}
		
		container.setFocusable(true);
		container.setClickable(true);

		return container;
	}
	
	/**
	 * Set on click listener
	 * 
	 * @param listener on click listener {@link View.OnClickListener}
	 */
	public void setOnClickListener(OnClickListener listener) {
		this.listener = listener;
	}
	
	/**
	 * Get on click listener
	 * 
	 * @return on click listener {@link View.OnClickListener}
	 */
	public OnClickListener getListener() {
		return this.listener;
	}
}