package com.example.movies.model;

public class MovieActor {
    private String actorId;
    private String imageUrl;
    private String name;
    private String characterName;

    public MovieActor(String actorId, String name, String characterName, String imageUrl) {
        this.actorId = actorId;
        this.name = name;
        this.characterName = characterName;
        this.imageUrl = imageUrl;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
