package paoo.game.main;

import paoo.game.panel.GamePanel;

public class EventHandler {
    private GamePanel gamePanel;
    private EventRect[][] eventRect;

    private int previousEventX;
    private int previousEventY;

    private boolean canTouchEvent = true;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        eventRect = new EventRect[gamePanel.getMAX_WORLD_COLUMN()][gamePanel.getMAX_WORLD_ROW()];

        int col = 0;
        int row = 0;

        while (col < gamePanel.getMAX_WORLD_COLUMN() && row < gamePanel.getMAX_WORLD_ROW()) {
            eventRect[col][row] = new EventRect();

            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 4;
            eventRect[col][row].height = 4;

            eventRect[col][row].setEventRectDefaultX(eventRect[col][row].x);
            eventRect[col][row].setEventRectDefaultY(eventRect[col][row].y);

            col++;
            if (col == gamePanel.getMAX_WORLD_COLUMN()) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {
        // Check if the player character is more than 1 tile away than from last event
        int xDistance = Math.abs(gamePanel.getPlayer().getWorldX() - previousEventX);
        int yDistance = Math.abs(gamePanel.getPlayer().getWorldY() - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gamePanel.getTILE_SIZE()) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
//            if (hit(40, 35, "any")) {
//                damage(47, 26, gamePanel.getDIALOG_STATE());
//            }
            if (hit(59, 40, "any")) {
                teleport(59, 40, gamePanel.getDIALOG_STATE());
            }
        }
    }

    public boolean hit(int eventCol, int eventRow, String reqDirection) {
        boolean hit = false;

        gamePanel.getPlayer().getSolidArea().x = gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getSolidArea().x;
        gamePanel.getPlayer().getSolidArea().y = gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getSolidArea().y;

        eventRect[eventCol][eventRow].x = eventCol * gamePanel.getTILE_SIZE() + eventRect[eventCol][eventRow].x;
        eventRect[eventCol][eventRow].y = eventRow * gamePanel.getTILE_SIZE() + eventRect[eventCol][eventRow].y;

        if (gamePanel.getPlayer().getSolidArea().intersects(eventRect[eventCol][eventRow])
                && !eventRect[eventCol][eventRow].isEventDone()) {
            if (gamePanel.getPlayer().getDirection().equals(reqDirection) || reqDirection.equals("any")) {
                hit = true;

                previousEventX = gamePanel.getPlayer().getWorldX();
                previousEventY = gamePanel.getPlayer().getWorldY();
            }
        }

        gamePanel.getPlayer().getSolidArea().x = gamePanel.getPlayer().getSolidAreaDefaultX();
        gamePanel.getPlayer().getSolidArea().y = gamePanel.getPlayer().getSolidAreaDefaultY();

        eventRect[eventCol][eventRow].x = eventRect[eventCol][eventRow].getEventRectDefaultX();
        eventRect[eventCol][eventRow].y = eventRect[eventCol][eventRow].getEventRectDefaultY();

        return hit;
    }

    public void damage(int col, int row, int gameState) {
        gamePanel.setGameState(gameState);
        gamePanel.getUi().setCurrentDialogue("You touched a poisson carrot!");
        gamePanel.getPlayer().setLife(gamePanel.getPlayer().getLife() - 1);

        // TODO: Decide what type of damage it will be
        // One time event
//        eventRect[col][row].setEventDone(true);

        // Having some distance before hitting again
        canTouchEvent = false;
    }

    public void teleport(int col, int row, int gameState) {
        gamePanel.setGameState(gameState);
        gamePanel.getUi().setCurrentDialogue("Teleport!");

        gamePanel.getPlayer().setWorldX(gamePanel.getTILE_SIZE() * 50);
        gamePanel.getPlayer().setWorldY(gamePanel.getTILE_SIZE() * 30);
    }
}
