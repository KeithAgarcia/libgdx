package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion down, up, right, left;
	float x, y, xv, yv;
	Animation walk;
	Animation upwalk;
	Animation downwalk;
	float time;

	static final int WIDTH = 18;
	static final int HEIGHT = 26;

	static final int DRAW_WIDTH = WIDTH * 3;
	static final int DRAW_HEIGHT = HEIGHT * 3;

	static final float MAX_VELOCITY = 500;
	static final float MAX_SPRINT = 1000;

	@Override
	public void create() {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		walk = new Animation(0.2f, grid[6][2], grid[6][3]);
		upwalk = new Animation(0.2f, grid[6][1], grid[7][1]);
		downwalk = new Animation(0.2f, grid[6][0], grid[7][0]);

		down = grid[6][0];
		up = grid[6][1];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);
	}

	@Override
	public void render() {
		time += Gdx.graphics.getDeltaTime();
		move();



		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(down, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			up = upwalk.getKeyFrame(time, true);
			batch.draw(up, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			down = downwalk.getKeyFrame(time, true);
			batch.draw(down, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			right = walk.getKeyFrame(time, true);
			batch.draw(right, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			right = walk.getKeyFrame(time, true);
			left = new TextureRegion(right);
			left.flip(true, false);
			batch.draw(left, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		batch.end();
	}


	float decelerate(float velocity) {
		float deceleration = 0.0003f; // the closer to 1, the slower the deceleration
		velocity *= deceleration;
		if (Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;
	}

	void move() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				yv = MAX_SPRINT;
		} else {
				yv = MAX_VELOCITY;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				yv = MAX_SPRINT * -1;
			} else {
				yv = MAX_VELOCITY * -1;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				xv = MAX_SPRINT;

			}else {
				xv = MAX_VELOCITY;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				xv = MAX_SPRINT * -1;

			} else {
				xv = MAX_VELOCITY * -1;
			}
		}
		if (y < -2) {
			y = 600;
		}
		if (y > 600) {
			y = 0;
		}
		if (x < -2){
			x = 800;
		}
		if (x > 800){
			x = 0;
		}

		y += yv * Gdx.graphics.getDeltaTime();
		x += xv * Gdx.graphics.getDeltaTime();

		yv = decelerate(yv);
		xv = decelerate(xv);
	}
}




