package cl.uach.inf.bachimovil;

import java.util.Date;

public abstract class Contribution  {
    private String id;
    private String userId;
    private java.util.Date created;

    public Contribution(String id, String userId, java.util.Date created) {
        this.id = id;
        this.userId = userId;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Date getCreated() {
        return created;
    }

    public abstract String getJson();



}
