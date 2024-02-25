package com.github.achaaab.discussion.presentation;

import com.github.achaaab.discussion.controle.Discussion;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationDiscussion extends JSplitPane {

	/**
	 * 
	 */
	public PresentationDiscussion(Discussion discussion) {

		super(VERTICAL_SPLIT);

		setLeftComponent(new JScrollPane(discussion.getPresentationMessages()));
		setRightComponent(new JScrollPane(discussion.getPresentationMessage()));

		setDividerLocation(0.8);
		setDividerSize(10);
		setContinuousLayout(true);
	}
}
