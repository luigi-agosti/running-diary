package com.la.runners.widget.quickaction;

import java.util.ArrayList;

import com.la.runners.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


/**
 * Popup window, shows action list as icon and text like the one in Gallery3D
 * app.
 * 
 * @author Boriero A.
 * 
 *         a revisitation of an Lorensius. W. T idea
 */
public class QuickAction extends PopupWindow {
	
	public static final int ANIM_GROW_FROM_LEFT = 1;
	public static final int ANIM_GROW_FROM_RIGHT = 2;
	public static final int ANIM_GROW_FROM_CENTER = 3;
	public static final int ANIM_REFLECT = 4;
	public static final int ANIM_AUTO = 5;
	
	private final View quickActionView;
	protected final View anchor;
	private final ImageView arrowUp;
	private final ImageView arrowDown;
	private final LayoutInflater inflater;
	private final Context context;
	private final WindowManager windowManager;
	private int animStyle;
	private LinearLayout actionItemsContainer;
	private ArrayList<ActionItem> actionItemList;

	/**
	 * Constructor
	 * 
	 * @param anchor
	 *            {@link View} the view the quickAction popup window should attached
	 */
	public QuickAction(View anchor) {
		super(anchor);
		this.anchor = anchor;

		actionItemList = new ArrayList<ActionItem>();
		context = anchor.getContext();
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		quickActionView = (ViewGroup) inflater.inflate(R.layout.quick_action,
				null);

		arrowDown = (ImageView) quickActionView.findViewById(R.id.arrow_down);
		arrowUp = (ImageView) quickActionView.findViewById(R.id.arrow_up);

		actionItemsContainer = (LinearLayout) quickActionView
				.findViewById(R.id.actionItemsContainer);
		animStyle = ANIM_REFLECT;
		
		actionItemsContainer.setBackgroundColor(Color.BLACK);
		
		setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}

				return false;
			}
		});

		/*
		 *  needed to determine the size of the screen
		 */
		windowManager = (WindowManager) anchor.getContext().getSystemService(
				Context.WINDOW_SERVICE);
	}

	/**
	 * Set animation style
	 * 
	 * @param animStyle
	 *            animation style, default is set to ANIM_AUTO
	 */
	public void setAnimStyle(int animStyle) {
		this.animStyle = animStyle;
	}

	/**
	 * Add action item
	 * 
	 * @param action
	 *            {@link ActionItem} object
	 */
	public void addActionItem(ActionItem action) {
		actionItemList.add(action);
	}

	/**
	 * Show popup window.
	 * 		Pay attention on the space between the screen and the position of the anchor
	 * 		it has to be enough to contain the quickAction View (the quickAction View Height
	 * 		is approximately the same of the contained icons  
	 * 
	 */
	public void show() {
		preShow();

		int quickActionViewYPos;

		int[] anchorLocationOnScreen = new int[2];

		anchor.getLocationOnScreen(anchorLocationOnScreen);

		Rect anchorRect = new Rect(anchorLocationOnScreen[0],
				anchorLocationOnScreen[1], anchorLocationOnScreen[0]
						+ anchor.getWidth(), anchorLocationOnScreen[1]
						+ anchor.getHeight());

		addAllActionItems();

		quickActionView.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		quickActionView.measure(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		
		int actionViewHeight = quickActionView.getMeasuredHeight();

		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();

		int spaceBetweenAnchorAndScreenTop = anchorRect.top;
		int spaceBetweenAnchorAndScreenBottom = screenHeight
				- anchorRect.bottom;

		boolean putArrowOnTop = (spaceBetweenAnchorAndScreenTop > spaceBetweenAnchorAndScreenBottom) ? true
				: false;

		if (putArrowOnTop) {
			/*
			 * may happen the space between anchor and the screen top is not
			 * enough to contain the quickActionViewYPos, but i think (and hope) it's
			 * really hard so for now i don't care 
			 */
			quickActionViewYPos = anchorRect.top - actionViewHeight;
		} else {
			/*
			 * may happen the space between anchor and the screen bottom is not
			 * enough to contain the quickActionViewYPos, ... the same as before
			 */
			quickActionViewYPos = anchorRect.bottom;
		}

		showArrow(((putArrowOnTop) ? R.id.arrow_down : R.id.arrow_up),
				anchorRect.centerX());

		setAnimationStyle(screenWidth, anchorRect.centerX(), putArrowOnTop);

		this.showAtLocation(anchor, Gravity.TOP, 0, quickActionViewYPos);
	}

	protected void preShow() {
		if (quickActionView == null) {
			throw new IllegalStateException(
					"setContentView was not called with a view to display.");
		}

		/*
		 *  quoted by Lorensius. W. T : 
		 *  "if using PopupWindow#setBackgroundDrawable this is the only values of
		 *  the width and height that make it work
		 *  otherwise you need to set the background of the root viewgroup
		 *  and set the popupwindow background to an empty BitmapDrawable"
		 *  
		 *  Thanks
		 */

		this.setBackgroundDrawable(new BitmapDrawable());

		this.setWidth(WindowManager.LayoutParams.FILL_PARENT);
		this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		this.setTouchable(true);
		this.setFocusable(true);
		this.setOutsideTouchable(true);

		this.setContentView(quickActionView);
	}

	private void setAnimationStyle(int screenWidth, int requestedX,
			boolean putArrowOnTop) {

		switch (animStyle) {
		case ANIM_GROW_FROM_LEFT:
			this.setAnimationStyle((putArrowOnTop) ? R.style.Animations_PopUpMenu_Left
					: R.style.Animations_PopDownMenu_Left);
			break;

		case ANIM_GROW_FROM_RIGHT:
			setAnimationStyle((putArrowOnTop) ? R.style.Animations_PopUpMenu_Right
					: R.style.Animations_PopDownMenu_Right);
			break;

		case ANIM_GROW_FROM_CENTER:
			setAnimationStyle((putArrowOnTop) ? R.style.Animations_PopUpMenu_Center
					: R.style.Animations_PopDownMenu_Center);
			break;

		case ANIM_REFLECT:
			setAnimationStyle((putArrowOnTop) ? R.style.Animations_PopUpMenu_Reflect
					: R.style.Animations_PopDownMenu_Reflect);
			break;

		}
	}

	private void addAllActionItems() {
		View view;

		for (int i = 0; i < actionItemList.size(); i++) {
			view = actionItemList.get(i).getActionItemView(inflater);
			actionItemsContainer.addView(view);
		}
		
	}

	private void showArrow(int whichArrow, int anchorCenterXPosition) {
		final View showArrow = (whichArrow == R.id.arrow_up) ? arrowUp
				: arrowDown;
		final View hideArrow = (whichArrow == R.id.arrow_up) ? arrowDown
				: arrowUp;

		final int arrowWidth = arrowUp.getMeasuredWidth();

		showArrow.setVisibility(View.VISIBLE);
		hideArrow.setVisibility(View.INVISIBLE);

		ViewGroup.MarginLayoutParams marginLayoutParam = (ViewGroup.MarginLayoutParams) showArrow
				.getLayoutParams();

		marginLayoutParam.leftMargin = anchorCenterXPosition - arrowWidth / 2;
		
	}
}