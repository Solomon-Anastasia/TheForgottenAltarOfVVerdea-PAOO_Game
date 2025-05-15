package paoo.game.main;

import java.awt.*;

public class EventRect extends Rectangle {
    private int eventRectDefaultX;
    private int eventRectDefaultY;

    private boolean eventDone = false;

    public void setEventRectDefaultX(int eventRectDefaultX) {
        this.eventRectDefaultX = eventRectDefaultX;
    }

    public void setEventRectDefaultY(int eventRectDefaultY) {
        this.eventRectDefaultY = eventRectDefaultY;
    }

    public void setEventDone(boolean eventDone) {
        this.eventDone = eventDone;
    }

    public int getEventRectDefaultX() {
        return eventRectDefaultX;
    }

    public int getEventRectDefaultY() {
        return eventRectDefaultY;
    }

    public boolean isEventDone() {
        return eventDone;
    }
}
