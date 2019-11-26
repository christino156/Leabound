package com.maanoo.leabound.face.widget;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.maanoo.leabound.core.Player;


public class ViewMessage extends Group {

	// TODO clean up

	private final Player player;

	private final Image back;
	private final Label label;

	public ViewMessage(Skin skin, Player player) {
		this.player = player;

		setHeight(24);

		back = new Image(skin, "solid-black");
		back.getColor().a = 0.7f;

		label = new Label("", skin);

		addActor(back);
		addActor(label);
	}

	private float posX;
	private float posY;
	private int posAlignment;

	public void setFixedPosition(float x, float y, int alignment) {
		posX = x;
		posY = y;
		posAlignment = alignment;

		setPosition(posX, posY, posAlignment);
	}

	public void message(String text) {

		final float durationMin = .5f;
		float durationMax = 3.0f;

		if (text.charAt(0) == '!') {
			text = text.substring(1);

			durationMax = 10;
		}

		label.setText(text);
		label.layout();

		setWidth(label.getPrefWidth() + 30);
		back.setSize(getWidth(), getHeight());

		label.setPosition(15, getHeight() / 2);

		setPosition(posX, posY - 14, posAlignment);

		addAction(Actions.sequence(

				Actions.moveBy(0, 20, 0.3f, Interpolation.circleOut),
				Actions.moveBy(0, -6, 0.3f, Interpolation.circleOut),
				Actions.delay(durationMin)

		));
		addAction(Actions.sequence(

				Actions.delay(durationMax),
				Actions.moveBy(0, -getHeight(), 0.3f, Interpolation.circleIn)

		));
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (getActions().size <= 100 && player.messages.size > 0) {
			clearActions();

			message(player.messages.removeIndex(0));
		}
	}

}