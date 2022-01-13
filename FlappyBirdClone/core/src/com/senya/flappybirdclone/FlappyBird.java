package com.senya.flappybirdclone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.sun.tools.javac.util.Log;

import java.util.Random;


public class FlappyBird extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture topTube;
    Texture bottomTube;
    Texture gameOver;
    Texture[] bird;
    int birdStateFlag = 0;
    float flyHeight;
    float fallingSpeed = 0;
    int screenWidth;
    int screenHeight;
    boolean gameStart = false;
    int spaceBetweenTubes = 500;
    Random random;
    int tubeSpeed = 5;
    int tubesNumbers = 5;
    float tubeX[] = new float[tubesNumbers];
    float tubeShift[] = new float[tubesNumbers];
    float distanceBetweenTubes;

    Circle birdCircle;
    Rectangle[] topTubeRectangles;
    Rectangle[] bottomTubeRectangles;
    //    ShapeRenderer shapeRenderer;
    int gameScore = 0;
    int passedTubeIndex;
    BitmapFont scoreFont;
    boolean isGameOver = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        topTube = new Texture("top_tube.png");
        bottomTube = new Texture("bottom_tube.png");
        gameOver = new Texture("game_over.png");

        bird = new Texture[2];
        bird[0] = new Texture("bird_wings_up.png");
        bird[1] = new Texture("bird_wings_down.png");

        birdCircle = new Circle();
        topTubeRectangles = new Rectangle[tubesNumbers];
        bottomTubeRectangles = new Rectangle[tubesNumbers];

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        random = new Random();
        distanceBetweenTubes = screenWidth / 1.2f;
        initGame();

        scoreFont = new BitmapFont();
        scoreFont.setColor(Color.GOLDENROD);
        scoreFont.getData().setScale(10);


//        shapeRenderer = new ShapeRenderer();
    }

    public void initGame() {
        flyHeight = screenHeight / 2 - bird[0].getHeight() / 2;
        for (int i = 0; i < tubesNumbers; i++) {
            tubeX[i] = screenWidth / 2 - topTube.getWidth() / 2 + screenWidth  + i * distanceBetweenTubes;
            tubeShift[i] = (random.nextFloat() - 0.5f) * (screenHeight - spaceBetweenTubes - 500);
            topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();
        }

        gameScore = 0;
        passedTubeIndex = 0;
        fallingSpeed = 0;
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, screenWidth, screenHeight);

        if (Gdx.input.justTouched()) {
            if (!gameStart) {
                gameStart = true;
                isGameOver = false;
                initGame();
            }
        }

        if (gameStart) {
            Gdx.app.log("gameScore", gameScore + "");

            if (tubeX[passedTubeIndex] < screenWidth / 2) {
                gameScore++;

                if (passedTubeIndex < tubesNumbers - 1) {
                    passedTubeIndex++;
                } else {
                    passedTubeIndex = 0;
                }
            }

            if (Gdx.input.justTouched()) {
                fallingSpeed = -20;
            }

            if (flyHeight > 0 || fallingSpeed < 0) {
                fallingSpeed++;
                flyHeight -= fallingSpeed;
            }

            if (birdStateFlag == 0) {
                birdStateFlag = 1;
            } else {
                birdStateFlag = 0;
            }

            for (int i = 0; i < tubesNumbers; i++) {
                if (tubeX[i] < -topTube.getWidth()) {
                    tubeX[i] = tubesNumbers * distanceBetweenTubes;
                } else {
                    tubeX[i] -= tubeSpeed;
                }

                tubeX[i] -= tubeSpeed;
                float topTubeY = screenHeight / 2 + spaceBetweenTubes / 2 + tubeShift[i];
                float bottomTubeY = screenHeight / 2 - spaceBetweenTubes / 2 - bottomTube.getHeight() + tubeShift[i];

                batch.draw(topTube, tubeX[i], topTubeY);
                batch.draw(bottomTube, tubeX[i], bottomTubeY);

                topTubeRectangles[i] = new Rectangle(tubeX[i], topTubeY, topTube.getWidth(), topTube.getHeight());
                bottomTubeRectangles[i] = new Rectangle(tubeX[i], bottomTubeY, bottomTube.getWidth(), bottomTube.getHeight());
            }
        }

        if (isGameOver) {
            batch.draw(gameOver, screenWidth / 2 - gameOver.getWidth() / 2, screenHeight / 2 - gameOver.getHeight() / 2);
        }

        Texture currentBird = bird[birdStateFlag];


        batch.draw(currentBird, screenWidth / 2 - currentBird.getWidth() / 2, flyHeight);
        scoreFont.draw(batch, String.valueOf(gameScore), screenWidth / 2 - 20, screenHeight - 50);
        batch.end();

        birdCircle.set(screenWidth / 2, flyHeight + bird[birdStateFlag].getHeight() / 2, bird[birdStateFlag].getWidth() / 2);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

        for (int i = 0; i < tubesNumbers; i++) {
            float topTubeY = screenHeight / 2 + spaceBetweenTubes / 2 + tubeShift[i];
            float bottomTubeY = screenHeight / 2 - spaceBetweenTubes / 2 - bottomTube.getHeight() + tubeShift[i];

//            shapeRenderer.rect(tubeX[i], topTubeY, topTube.getWidth(), topTube.getHeight());
//            shapeRenderer.rect(tubeX[i], bottomTubeY, bottomTube.getWidth(), bottomTube.getHeight());

            if (Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) {
                Gdx.app.log("gameOver", "END");
                gameStart = false;
                isGameOver = true;
            }
        }
//        shapeRenderer.end();
    }

    @Override
    public void dispose() {

    }
}
